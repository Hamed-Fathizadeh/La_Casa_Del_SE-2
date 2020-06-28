package org.bonn.se.model.dao;

import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.JavaMailUtil;

import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContainerAnzDAO extends AbstractDAO{

    private static ContainerAnzDAO instance;

    public static ContainerAnzDAO getInstance() {
        return instance == null ? instance = new ContainerAnzDAO() : instance;
    }

    public List<StellenanzeigeDTO> load() throws DatabaseException, SQLException {
        List<StellenanzeigeDTO> liste = new ArrayList<>();
        ResultSet set = null;
        Statement statement = JDBCConnection.getInstance().getStatement();

        try {
            set = statement.executeQuery("SELECT st.*, " +
                    "(SELECT avg(bew.anzahl_sterne) AS avg FROM lacasa.tab_bewertung bew where bew.firmenname = st.firmenname and bew.hauptsitz = st.hauptsitz GROUP BY bew.firmenname,  bew.hauptsitz) AS bewertung"+
                    " FROM lacasa.tab_stellen_anzeige st where status = 1 ");

            while (set.next()) {
                StellenanzeigeDTO sa_load = new StellenanzeigeDTO(
                        set.getInt(1),
                        set.getDate(2) == null? null: set.getDate(2).toLocalDate(),
                        set.getDate(3),
                        set.getString(4), set.getString(5), set.getInt(6),
                        set.getString(7), set.getString(8), set.getString(9),
                        set.getString(10),set.getString(11),set.getString(12),null,set.getDouble(13),set.getString(12)
                );

                liste.add(sa_load);
            }

        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            assert set != null;
            set.close();
            JDBCConnection.getInstance().closeConnection();
        }

        return liste;
    }



    public List<StellenanzeigeDTO> loadSuche(String suchbegriff, String ort, String bundesland, String umkreis, String artSuche, String einstellungsart, LocalDate ab_Datum, String branche) throws DatabaseException, SQLException {
        List<StellenanzeigeDTO> liste = new ArrayList<>();
        ResultSet set = null;
        Statement statement = JDBCConnection.getInstance().getStatement();
        try {


            StringBuilder sbSuchbeg = new StringBuilder(suchbegriff == null ? " " : " and a.suchbegriff = '" + suchbegriff + "' ");
            StringBuilder sBumkreis = new StringBuilder();
            StringBuilder sbEinstellungsart = new StringBuilder(" ");
            StringBuilder sbAb_Datum = new StringBuilder(" ");
            StringBuilder sbBranche = new StringBuilder(" ");

            if(artSuche.equals("Erweitert")){
                Date date = ab_Datum == null ? null : Date.valueOf(ab_Datum) ;
                sbEinstellungsart = new StringBuilder(einstellungsart == null ? " " : " and a.art = '" + einstellungsart + "' ");
                sbAb_Datum = new StringBuilder(date == null ? " " : " and a.datum >= '" + date + "' ");
                sbBranche = new StringBuilder(branche == null ? " " : " and u.branch_name = '" + branche + "' ");
            }

            if(umkreis.equals("Ganzer Ort") || ort == null){
                sBumkreis.append(" ");
            }else{
                int km = Integer.parseInt(umkreis.substring(0,umkreis.indexOf(' ')));

                sBumkreis.append(" or a.status = 1 "+ sbSuchbeg +sbEinstellungsart+ sbAb_Datum+sbBranche +" and a.ort in (SELECT a.ort FROM lacasa.tab_orte a \n" +
                        "  join lacasa.tab_orte b\n" +
                        "    on 1 = 1\n" +
                        "WHERE (\n" +
                        "          acos(sin(a.breitengrad * 0.0175) * sin(b.breitengrad * 0.0175) \n" +
                        "               + cos(a.breitengrad * 0.0175) * cos(b.breitengrad * 0.0175) *    \n" +
                        "                 cos((b.laengengrad * 0.0175) - (a.laengengrad * 0.0175))\n" +
                        "              ) * 3959 <= ("+km+" * 0.62137)\n" +
                        "      )\n" +
                        "and b.ort = '"+ort+"'   \n" +
                        "and b.bundesland = '"+bundesland+"') \n");

                sBumkreis.append(" and a.bundesland in (SELECT a.bundesland FROM lacasa.tab_orte a \n" +
                        "  join lacasa.tab_orte b\n" +
                        "    on 1 = 1\n" +
                        "WHERE (\n" +
                        "          acos(sin(a.breitengrad * 0.0175) * sin(b.breitengrad * 0.0175) \n" +
                        "               + cos(a.breitengrad * 0.0175) * cos(b.breitengrad * 0.0175) *    \n" +
                        "                 cos((b.laengengrad * 0.0175) - (a.laengengrad * 0.0175))\n" +
                        "              ) * 3959 <= ("+km+" * 0.62137)\n" +
                        "      )\n" +
                        "and b.ort = '"+ort+"'   \n" +
                        "and b.bundesland = '"+bundesland+"') \n");
            }

            set = statement.executeQuery("SELECT a.s_anzeige_id, a.datum, a.zeitstempel, a.titel, a.s_beschreibung, a.status\n" +
                    "      ,a.ort, a.bundesland, a.firmenname, a.hauptsitz, a.suchbegriff, a.art, u.logo ,  \n" +
                    "(SELECT avg(bew.anzahl_sterne) AS avg FROM lacasa.tab_bewertung bew where bew.firmenname = u.firmenname and bew.hauptsitz = u.hauptsitz GROUP BY bew.firmenname,  bew.hauptsitz) AS bewertung"+
                    ", u.branch_name"+
                    "  FROM lacasa.tab_stellen_anzeige a\n" +
                    "  join lacasa.tab_unternehmen u\n" +
                    "    on u.firmenname = a.firmenname and u.hauptsitz = a.hauptsitz\n" +
                    " where status = 1" + (ort == null ? " " : " and a.ort =  '" + ort + "' ") + (bundesland == null ? " " : " and a.bundesland =  '" + bundesland + "' ") + sbSuchbeg  + sbEinstellungsart + sbAb_Datum + sbBranche +sBumkreis
            );
            while (set.next()) {
                StellenanzeigeDTO sa_suche = new StellenanzeigeDTO(
                        set.getInt(1),
                        set.getDate(2) == null ? null: set.getDate(2).toLocalDate()
                        ,set.getDate(3),
                        set.getString(4),
                        set.getString(5),
                        set.getInt(6),
                        set.getString(7), set.getString(8), set.getString(9),
                        set.getString(10),set.getString(11),set.getString(12),
                        set.getBytes(13),set.getDouble(14),set.getString(15)
                );

                liste.add(sa_suche);
            }


        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            assert set != null;
            set.close();
            JDBCConnection.getInstance().closeConnection();
        }
        return liste;
    }


    public List<StellenanzeigeDTO> loadNeuigkeiten(String str) throws DatabaseException, SQLException {
        List<StellenanzeigeDTO> liste = new ArrayList<>();
        ResultSet set = null;
        Statement statement = JDBCConnection.getInstance().getStatement();

        try {
            String limit ="";
            if(!str.equals("Alle")) {
                limit = " LIMIT 5";
            }
            set = statement.executeQuery("select sa.*, u.logo, \n" +
                    "(SELECT avg(bew.anzahl_sterne) AS avg FROM lacasa.tab_bewertung bew where bew.firmenname = u.firmenname and bew.hauptsitz = u.hauptsitz GROUP BY bew.firmenname,  bew.hauptsitz) AS bewertung"+
                    ",u.branch_name"+
                    "  from lacasa.tab_stellen_anzeige sa\n" +
                    "  join lacasa.tab_unternehmen u\n" +
                    "    on sa.firmenname = u.firmenname and sa.hauptsitz = u.hauptsitz and sa.status = 1 order by sa.zeitstempel desc "+limit);

            while (set.next()) {
                StellenanzeigeDTO sa_neuigkeiten = new StellenanzeigeDTO(
                        set.getInt(1),set.getDate(2) == null? null: set.getDate(2).toLocalDate(),
                        set.getDate(3),
                        set.getString(4), set.getString(5), set.getInt(6),
                        set.getString(7), set.getString(8), set.getString(9),
                        set.getString(10),set.getString(11),set.getString(12),
                        set.getBytes(13),set.getDouble(14),set.getString(15)
                );

                liste.add(sa_neuigkeiten);


            }

        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            assert set != null;
            set.close();
            JDBCConnection.getInstance().closeConnection();
        }

        return liste;

    }

    public List<StellenanzeigeDTO> loadUnternehmenAnzeigen(String email) throws DatabaseException, SQLException {
        List<StellenanzeigeDTO> liste = new ArrayList<>();
        ResultSet set = null;
        Statement statement = JDBCConnection.getInstance().getStatement();

        try {
            set = statement.executeQuery("select sa.s_anzeige_id, sa.datum, sa.zeitstempel, sa.titel,sa.s_beschreibung," +
                    " sa.status, sa.ort, sa.bundesland, sa.firmenname, sa.hauptsitz, sa.suchbegriff,sa.art ,null \n" +
                    "  from lacasa.tab_stellen_anzeige sa\n" +
                    "  join lacasa.tab_unternehmen u\n" +
                    "    on u.firmenname = sa.firmenname\n" +
                    "   and u.hauptsitz = sa.hauptsitz\n" +
                    " where u.email = '" + email + "'");

            while (set.next()) {

                StellenanzeigeDTO sa_loadUnternehmenAnzeigen = new StellenanzeigeDTO(
                        set.getInt(1),set.getDate(2) == null? null: set.getDate(2).toLocalDate(),
                        set.getDate(3), set.getString(4), set.getString(5),
                        set.getInt(6), set.getString(7), set.getString(8),
                        set.getString(9), set.getString(10),set.getString(11),
                        set.getString(12),set.getInt(13)
                );

                liste.add(sa_loadUnternehmenAnzeigen);


            }

        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            assert set != null;
            set.close();
            JDBCConnection.getInstance().closeConnection();
        }

        liste.sort((o1, o2) -> {
            if (o1.getId() < o2.getId()) {
                return -1;
            } else if (o1.getId() == o2.getId()) {
                return 0;
            }
            return 1;
        });


        return liste;
    }



    public List<StellenanzeigeDTO> loadNeuBewerbungen(Unternehmen unternehmen) throws DatabaseException, SQLException {

        List<StellenanzeigeDTO> listeStellenanzeigenBew = new ArrayList<>();
        ResultSet set = null;
        Statement statement = JDBCConnection.getInstance().getStatement();

        try {

            set = statement.executeQuery("SELECT sa.s_anzeige_id, sa.datum, sa.zeitstempel, sa.titel,sa.s_beschreibung, \n" +
                    "       sa.status, sa.ort, sa.bundesland, sa.firmenname, sa.hauptsitz, sa.suchbegriff,sa.art, count( b.bewerbung_id) anzahlBewerbung\n" +
                    "  FROM lacasa.tab_stellen_anzeige sa\n" +
                    "  join lacasa.tab_bewerbung b\n" +
                    "    on sa.s_anzeige_id = b.s_anzeige_id\n" +
                    " where sa.firmenname = '"+unternehmen.getCname()+"' and sa.hauptsitz = '"+unternehmen.getHauptsitz()+"'\n" +
                    "   and b.status = 9\n" +
                    " group by sa.s_anzeige_id  "
            );

            while (set.next()) {

                StellenanzeigeDTO sa_loadNeueBewerbungen = new StellenanzeigeDTO(
                        set.getInt(1),set.getDate(2) == null? null: set.getDate(2).toLocalDate(),
                        set.getDate(3), set.getString(4), set.getString(5),
                        set.getInt(6), set.getString(7), set.getString(8),
                        set.getString(9), set.getString(10),set.getString(11),
                        set.getString(12),set.getInt(13)
                );

                listeStellenanzeigenBew.add(sa_loadNeueBewerbungen);


            }

        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            assert set != null;
            set.close();
            JDBCConnection.getInstance().closeConnection();
        }

        listeStellenanzeigenBew.sort((o1, o2) -> {
            if (o1.getId() < o2.getId()) {
                return -1;
            } else if (o1.getId() == o2.getId()) {
                return 0;
            }
            return 1;
        });


        return listeStellenanzeigenBew;
    }


    public void setAnzeige(Unternehmen user) throws DatabaseException {

        String sql = "INSERT INTO lacasa.tab_stellen_anzeige (datum,zeitstempel,titel,s_beschreibung,status,ort, bundesland,firmenname,hauptsitz, suchbegriff, art) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement statement = getPreparedStatement(sql);


        try {


            assert statement != null;
            statement.setDate(1, Date.valueOf(String.valueOf(user.getStellenanzeigeDTO().getDatum())));
            statement.setDate(2, Date.valueOf(LocalDate.now()));
            statement.setString(3, user.getStellenanzeigeDTO().getTitel());
            statement.setString(4, user.getStellenanzeigeDTO().getBeschreibung());
            statement.setInt(5, user.getStellenanzeigeDTO().getStatus());
            statement.setString(6, user.getStellenanzeigeDTO().getStandort());
            statement.setString(7, user.getStellenanzeigeDTO().getBundesland());
            statement.setString(8, user.getStellenanzeigeDTO().getFirmenname());
            statement.setString(9, user.getStellenanzeigeDTO().getHauptsitz());
            statement.setString(10, user.getStellenanzeigeDTO().getSuchbegriff());
            statement.setString(11, user.getStellenanzeigeDTO().getArt());

            statement.executeUpdate();
            sendEmail(user);


        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }

    public void sendEmail(Unternehmen unternehmen) throws DatabaseException, SQLException {
        HashMap<String, String> liste = new HashMap<>();
        ResultSet set = null;
        Statement statement = JDBCConnection.getInstance().getStatement();

        try {
            set = statement.executeQuery("select email ,vorname  \n" +
                    "  from lacasa.tab_user\n" +
                    " where email in (\n" +
                    "select email \n" +
                    "from lacasa.tab_student\n" +
                    "where benachrichtigung = 1 )");

            while (set.next()) {
                liste.put(set.getString(1),set.getString(2));
            }

        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            assert set != null;
            set.close();
            JDBCConnection.getInstance().closeConnection();
        }
        try {
            JavaMailUtil.sendMailToStudents(unternehmen, liste);
        } catch (Exception e) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void updateAnzeige(StellenanzeigeDTO stellenanzeige) throws DatabaseException {

        String sql = "UPDATE lacasa.tab_stellen_anzeige " +
                "SET titel = '" + stellenanzeige.getTitel() + "', s_beschreibung = '" +stellenanzeige.getBeschreibung()+ "' " +
                "WHERE s_anzeige_id = '" + stellenanzeige.getId()+ "'";

        PreparedStatement statement = getPreparedStatement(sql);


        try {
            statement.executeUpdate();
        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }

    public static void deleteAnzeige(StellenanzeigeDTO stellenanzeige) throws DatabaseException, SQLException {
        boolean result;
        String sql;
        Statement statement = JDBCConnection.getInstance().getStatement();

        try {
            result = statement.execute("" +
                    "SELECT lacasa.tab_bewerbung.s_anzeige_id " +
                    "FROM lacasa.tab_bewerbung " +
                    "WHERE lacasa.tab_bewerbung.s_anzeige_id = '" + stellenanzeige.getId() + "'");

            if(!result) {
                sql = "DELETE FROM lacasa.tab_stellen_anzeige WHERE s_anzeige_id = '" + stellenanzeige.getId()+ "';";
            } else {
                sql ="UPDATE lacasa.tab_bewerbung SET s_anzeige_id = '-1'" +
                        "WHERE lacasa.tab_bewerbung.s_anzeige_id = '" + stellenanzeige.getId()+ "';" +
                        "DELETE FROM lacasa.tab_stellen_anzeige WHERE s_anzeige_id = '" + stellenanzeige.getId()+ "';";
            }
            PreparedStatement preparedStatement = getPreparedStatement(sql);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, throwables);
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        } finally {
            statement.close();
            JDBCConnection.getInstance().closeConnection();
        }
    }


}
package org.bonn.se.model.dao;

import com.vaadin.ui.UI;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.JavaMailUtil;
import org.bonn.se.services.util.Roles;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContainerAnzDAO extends AbstractDAO{

    public static ContainerAnzDAO dao = null;

    private ContainerAnzDAO() {

    }

    public static ContainerAnzDAO getInstance() {
        if (dao == null) {
            dao = new ContainerAnzDAO();
        }
        return dao;
    }
    public static List<StellenanzeigeDTO> load() throws DatabaseException {
        List<StellenanzeigeDTO> liste = new ArrayList<>();
        ResultSet set;
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("SELECT * FROM lacasa.tab_stellen_anzeige where status = 1 ");
        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        }
        try {

            while (set.next()) {

                StellenanzeigeDTO sa = new StellenanzeigeDTO(
                        set.getInt(1),set.getDate(2),set.getDate(3),
                        set.getString(4), set.getString(5), set.getInt(6),
                        set.getString(7), set.getString(8), set.getString(9),
                        set.getString(10),set.getString(11),set.getString(12),
                        set.getBytes(13)
                                                            );

                   liste.add(sa);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
        return liste;
    }

    public static List<StellenanzeigeDTO> loadSuche(String suchbegriff, String ort, String bundesland, String umkreis, String artSuche, String einstellungsart, java.util.Date ab_Datum, String branche) throws DatabaseException {
        List<StellenanzeigeDTO> liste = new ArrayList<>();
        ResultSet set;
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();

            //Bundesland und fachgebiet mussen noch überarbeitet werden

            StringBuilder sbSuchbeg = new StringBuilder(suchbegriff == null ? " " : " and a.suchbegriff = \'" + suchbegriff + "\' ");
            StringBuilder sbOrt = new StringBuilder( ort == null ? " " : " and a.ort =  \'" +  ort + "\' ");
            StringBuilder sbBund = new StringBuilder(bundesland == null? " " : " and a.bundesland =  \'" +  bundesland + "\' ");
            StringBuilder sBumkreis = new StringBuilder();
            StringBuilder sbEinstellungsart = new StringBuilder(" ");
            StringBuilder sbAb_Datum = new StringBuilder(" ");
            StringBuilder sbBranche = new StringBuilder(" ");

            if(artSuche.equals("Erweitert")){

                sbEinstellungsart = new StringBuilder(einstellungsart == null ? " " : " and a.art = \'" + einstellungsart + "\' ");
                sbAb_Datum = new StringBuilder(ab_Datum == null ? " " : " and a.datum >= \'" + ab_Datum + "\' ");
                sbBranche = new StringBuilder(branche == null ? " " : " and b.name = \'" + branche + "\' ");
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
/*
            System.out.println("SELECT a.s_anzeige_id, a.datum, a.zeitstempel, a.titel, a.s_beschreibung, a.status\n" +
                    "      ,a.ort, a.bundesland, a.firmenname, a.hauptsitz, a.suchbegriff, a.art, u.logo \n" +
                    "  FROM lacasa.tab_stellen_anzeige a\n" +
                    "  join lacasa.tab_unternehmen u\n" +
                    "    on u.firmenname = a.firmenname and u.hauptsitz = a.hauptsitz\n" +
                    "  join lacasa.tab_unt_hat_branche b\n" +
                    "    on a.firmenname = b.firmenname and a.hauptsitz = b.hauptsitz\n" +
                    " where status = 1" + sbSuchbeg  + sbOrt + sbBund + sbEinstellungsart + sbAb_Datum + sbBranche +sBumkreis);*/


            set = statement.executeQuery("SELECT a.s_anzeige_id, a.datum, a.zeitstempel, a.titel, a.s_beschreibung, a.status\n" +
                    "      ,a.ort, a.bundesland, a.firmenname, a.hauptsitz, a.suchbegriff, a.art, u.logo \n" +
                    "  FROM lacasa.tab_stellen_anzeige a\n" +
                    "  join lacasa.tab_unternehmen u\n" +
                    "    on u.firmenname = a.firmenname and u.hauptsitz = a.hauptsitz\n" +
                    "  join lacasa.tab_unt_hat_branche b\n" +
                    "    on a.firmenname = b.firmenname and a.hauptsitz = b.hauptsitz\n" +
                    " where status = 1" + sbSuchbeg  + sbOrt + sbBund + sbEinstellungsart + sbAb_Datum + sbBranche +sBumkreis );


        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        }
        try {

            while (set.next()) {
                StellenanzeigeDTO sa = new StellenanzeigeDTO(
                        set.getInt(1),set.getDate(2),set.getDate(3),
                        set.getString(4), set.getString(5), set.getInt(6),
                        set.getString(7), set.getString(8), set.getString(9),
                        set.getString(10),set.getString(11),set.getString(12),
                        set.getBytes(13)
                );

                liste.add(sa);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
        return liste;
    }

    public static List<StellenanzeigeDTO> loadNeuigkeiten(String str) throws DatabaseException {
        List<StellenanzeigeDTO> liste = new ArrayList<>();
        ResultSet set;
        try {
            if(str.equals("Alle")){

                    Statement statement = JDBCConnection.getInstance().getStatement();
                    set = statement.executeQuery("select sa.*, u.logo\n" +
                            "  from lacasa.tab_stellen_anzeige sa\n" +
                            "  join lacasa.tab_unternehmen u\n" +
                            "    on sa.firmenname = u.firmenname and sa.hauptsitz = u.hauptsitz");

            }else{
                Statement statement = JDBCConnection.getInstance().getStatement();
                set = statement.executeQuery("select sa.*, u.logo\n" +
                        "  from lacasa.tab_stellen_anzeige sa\n" +
                        "  join lacasa.tab_unternehmen u\n" +
                        "    on sa.firmenname = u.firmenname and sa.hauptsitz = u.hauptsitz LIMIT 5");
            }
        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        }

        try {

            while (set.next()) {
                StellenanzeigeDTO sa = new StellenanzeigeDTO(
                        set.getInt(1),set.getDate(2),set.getDate(3),
                        set.getString(4), set.getString(5), set.getInt(6),
                        set.getString(7), set.getString(8), set.getString(9),
                        set.getString(10),set.getString(11),set.getString(12),
                        set.getBytes(13)
                );

                liste.add(sa);


            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
        return liste;

    }

    public static List<StellenanzeigeDTO> loadUnternehmenAnzeigen(String email) throws DatabaseException {
        List<StellenanzeigeDTO> liste = new ArrayList<>();
        ResultSet set;
        try {
                Statement statement = JDBCConnection.getInstance().getStatement();
                set = statement.executeQuery("select sa.s_anzeige_id, sa.datum, sa.zeitstempel, sa.titel,sa.s_beschreibung," +
                        " sa.status, sa.ort, sa.bundesland, sa.firmenname, sa.hauptsitz, sa.suchbegriff,sa.art \n" +
                        "  from lacasa.tab_stellen_anzeige sa\n" +
                        "  join lacasa.tab_unternehmen u\n" +
                        "    on u.firmenname = sa.firmenname\n" +
                        "   and u.hauptsitz = sa.hauptsitz\n" +
                        " where u.email = \'" + email + "\'");

        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        }

        try {

            while (set.next()) {
                StellenanzeigeDTO sa = new StellenanzeigeDTO(
                        set.getInt(1),set.getDate(2),set.getDate(3),
                        set.getString(4), set.getString(5), set.getInt(6),
                        set.getString(7), set.getString(8), set.getString(9),
                        set.getString(10),set.getString(11),set.getString(12)
                );

                liste.add(sa);


            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
        return liste;
    }

    public static void setAnzeige() throws DatabaseException {

        String sql = "INSERT INTO lacasa.tab_stellen_anzeige (datum,zeitstempel,titel,s_beschreibung,status,ort, bundesland,firmenname,hauptsitz, suchbegriff, art) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement statement = getPreparedStatement(sql);


            try {
                Unternehmen user = (Unternehmen) UI.getCurrent().getSession().getAttribute(Roles.Unternehmen);
                String[] sOrt = user.getStellenanzeige().getStandort().toString().split(" - ");


                assert statement != null;
                    statement.setDate(1, Date.valueOf(user.getStellenanzeige().getDatum()));
                    statement.setDate(2, Date.valueOf(LocalDate.now()));
                    statement.setString(3, user.getStellenanzeige().getTitel());
                    statement.setString(4, user.getStellenanzeige().getBeschreibung());
                    statement.setInt(5, user.getStellenanzeige().getStatus());
                    statement.setString(6, sOrt[0]);
                    statement.setString(7, sOrt[1]);
                    statement.setString(8, user.getStellenanzeige().getFirmenname());
                    statement.setString(9, user.getStellenanzeige().getHauptsitz());
                    statement.setString(10, user.getStellenanzeige().getSuchbegriff());
                    statement.setString(11, user.getStellenanzeige().getArt());
                    //statement.setString(8,user.getCname());
                    //statement.setString(9,user.getHauptsitz());

                    statement.executeUpdate();

                    //email senden
                    sendEmail(user);


            } catch (SQLException throwables) {
                throwables.printStackTrace();
                throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
            } finally {
                JDBCConnection.getInstance().closeConnection();
            }
    }

    public static void sendEmail(Unternehmen unternehmen) throws DatabaseException {
        HashMap<String, String> liste = new HashMap<String, String>();
        ResultSet set;
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            set = statement.executeQuery("select email ,vorname  \n" +
                    "  from lacasa.tab_user\n" +
                    " where email in (\n" +
                    "select email \n" +
                    "from lacasa.tab_student\n" +
                    "where benachrichtigung = 1 )");

        } catch (SQLException | DatabaseException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        }
        try {
            while (set.next()) {
                liste.put(set.getString(1),set.getString(2));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            JavaMailUtil.sendMailToStudents(unternehmen, liste);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void updateAnzeige(StellenanzeigeDTO stellenanzeige) throws DatabaseException {

        String sql = "UPDATE lacasa.tab_stellen_anzeige " +
                "SET titel = \'" + stellenanzeige.getTitel() +"\', s_beschreibung = \'"+stellenanzeige.getBeschreibung()+"\' " +
                "WHERE s_anzeige_id = \'"+ stellenanzeige.getId()+"\'";

        PreparedStatement statement = getPreparedStatement(sql);


        try {
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }
    public static void deleteAnzeige(StellenanzeigeDTO stellenanzeige) throws DatabaseException {

        String sql = "DELETE FROM lacasa.tab_stellen_anzeige WHERE s_anzeige_id = \'"+ stellenanzeige.getId()+ "\';";

        PreparedStatement statement = getPreparedStatement(sql);


        try {
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DatabaseException("Fehler im SQL Befehl! Bitte den Programmierer benachrichtigen.");
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }


}

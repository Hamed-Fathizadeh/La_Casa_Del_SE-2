package org.bonn.se.services.util;


import com.sun.mail.util.MailSSLSocketFactory;
import org.bonn.se.model.objects.entitites.Unternehmen;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class JavaMailUtil {

    public static void sendMail(String recepient, String vNummer, String name) throws Exception{

        System.out.println("Preparing to sen email");
        Properties generalProperties = new Properties();
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        generalProperties.put("mail.smtp.ssl.socketFactory", sf);


        generalProperties.put("mail.smtp.auth","true");
        generalProperties.put("mail.smtp.starttls.enable","true");
        generalProperties.put("mail.smtp.host","smtp.gmail.com");
        generalProperties.put("mail.smtp.port","587");

        String myAccountEmail = "lacolsco.webpage@gmail.com";

       Session session = Session.getInstance(generalProperties, new Authenticator(){
          @Override
          protected  PasswordAuthentication getPasswordAuthentication(){
              return new PasswordAuthentication(myAccountEmail,Password.MAIL);
            }
        });

        Message message =  prepareMessage(session, myAccountEmail, recepient, vNummer, name);

        assert message != null;
        Transport.send(message);
        System.out.println("sended email");
    }


    public static void sendMailToStudents(Unternehmen unternehmen, HashMap<String, String> liste) throws Exception{
        System.out.println("Preparing to sen email");
        Properties propertiesStudent = new Properties();
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        propertiesStudent.put("mail.smtp.ssl.socketFactory", sf);
        propertiesStudent.put("mail.smtp.auth","true");


        propertiesStudent.put("mail.smtp.host","smtp.gmail.com");

        propertiesStudent.put("mail.smtp.port","587");

        String myAccountEmail = "lacolsco.webpage@gmail.com";
        propertiesStudent.put("mail.smtp.starttls.enable","true");

        Session session = Session.getInstance(propertiesStudent, new Authenticator(){
            @Override
            protected  PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myAccountEmail,Password.MAIL);
            }
        });

        for (String email : liste.keySet()) {
            Message message = prepareMessageForStudents(session, myAccountEmail, email, liste.get(email), unternehmen);
            assert message != null;
            Transport.send(message);
        }

    }

    public static Message prepareMessageForStudents(Session session,String myAccountEmail, String recepient, String name,Unternehmen unternehmen){
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(recepient));
            message.setSubject("Lacolsco: Neue Stellenausschreibung");
            String msg = "</head>\n" +
                    "\n" +
                    "<body lang=DE style='tab-interval:35.4pt'>\n" +
                    "\n" +
                    "<div class=WordSection1>\n" +
                    "\n" +
                    "<p class=MsoNormal><span class=GramE><b><span style='font-size:20.0pt;\n" +
                    "line-height:107%;font-family:\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;\n" +
                    "mso-hansi-theme-font:minor-bidi;mso-bidi-theme-font:minor-bidi;color:#2F5496;\n" +
                    "mso-themecolor:accent1;mso-themeshade:191'>Hallo</span></b></span><b><span\n" +
                    "style='font-size:20.0pt;line-height:107%;font-family:\"Arial\",sans-serif;\n" +
                    "mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:minor-bidi;mso-bidi-theme-font:\n" +
                    "minor-bidi;color:#2F5496;mso-themecolor:accent1;mso-themeshade:191'> "+name+",<o:p></o:p></span></b></p>\n" +
                    "\n" +
                    "<p class=MsoNormal><b><span style='font-size:20.0pt;line-height:107%;\n" +
                    "font-family:\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:\n" +
                    "minor-bidi;mso-bidi-theme-font:minor-bidi;color:#2F5496;mso-themecolor:accent1;\n" +
                    "mso-themeshade:191'><o:p>&nbsp;</o:p></span></b></p>\n" +
                    "\n" +
                    "<p class=MsoNormal><b><span style='font-size:20.0pt;line-height:107%;\n" +
                    "font-family:\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:\n" +
                    "minor-bidi;mso-bidi-theme-font:minor-bidi;color:#2F5496;mso-themecolor:accent1;\n" +
                    "mso-themeshade:191'>wir haben eine neue Stellenanzeige für dich!<o:p></o:p></span></b></p>\n" +
                    "\n" +
                    "<p class=MsoNormal><b><span style='font-size:20.0pt;line-height:107%;\n" +
                    "font-family:\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:\n" +
                    "minor-bidi;mso-bidi-theme-font:minor-bidi;color:#2F5496;mso-themecolor:accent1;\n" +
                    "mso-themeshade:191'>Das Unternehmen <span class=SpellE>"+unternehmen.getCname()+"</span> hat\n" +
                    "eine Stelle mit der Titel: "+unternehmen.getStellenanzeigeDTO().getTitel() +" hochgeladen!<o:p></o:p></span></b></p>\n" +
                    "\n" +
                    "<p class=MsoNormal><b><span style='font-size:20.0pt;line-height:107%;\n" +
                    "font-family:\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:\n" +
                    "minor-bidi;mso-bidi-theme-font:minor-bidi;color:#2F5496;mso-themecolor:accent1;\n" +
                    "mso-themeshade:191'>Die Stelle ist ein "+unternehmen.getStellenanzeigeDTO().getArt()+" und fängt am "+unternehmen.getStellenanzeigeDTO().getDatum()+"\n" +
                    "an.<o:p></o:p></span></b></p>\n" +
                    "\n" +
                    "<p class=MsoNormal><b><span style='font-size:20.0pt;line-height:107%;\n" +
                    "font-family:\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:\n" +
                    "minor-bidi;mso-bidi-theme-font:minor-bidi;color:#2F5496;mso-themecolor:accent1;\n" +
                    "mso-themeshade:191'>Standort ist "+unternehmen.getStellenanzeigeDTO().getStandort()+" - "+unternehmen.getStellenanzeigeDTO().getBundesland()   +"<o:p></o:p></span></b></p>\n" +
                    "\n" +
                    "<p class=MsoNormal><b><span style='font-size:20.0pt;line-height:107%;\n" +
                    "font-family:\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:\n" +
                    "minor-bidi;mso-bidi-theme-font:minor-bidi;color:#2F5496;mso-themecolor:accent1;\n" +
                    "mso-themeshade:191'>Verliere nicht die Zeit und bewirb dich auf die Stelle.<o:p></o:p></span></b></p>\n" +
                    "\n" +
                    "<p class=MsoNormal><b><span style='font-size:20.0pt;line-height:107%;\n" +
                    "font-family:\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:\n" +
                    "minor-bidi;mso-bidi-theme-font:minor-bidi;color:#2F5496;mso-themecolor:accent1;\n" +
                    "mso-themeshade:191'><o:p>&nbsp;</o:p></span></b></p>\n" +
                    "\n" +
                    "<p class=MsoNormal><b><span style='font-size:20.0pt;line-height:107%;\n" +
                    "font-family:\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:\n" +
                    "minor-bidi;mso-bidi-theme-font:minor-bidi;color:#2F5496;mso-themecolor:accent1;\n" +
                    "mso-themeshade:191'>Liebe Grüße<o:p></o:p></span></b></p>\n" +
                    "\n" +
                    "<p class=MsoNormal><b><span style='font-size:20.0pt;line-height:107%;\n" +
                    "font-family:\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:\n" +
                    "minor-bidi;mso-bidi-theme-font:minor-bidi;color:#2F5496;mso-themecolor:accent1;\n" +
                    "mso-themeshade:191'>Dein <span class=SpellE>Lacolsco</span> Team<o:p></o:p></span></b></p>\n" +
                    "\n" +
                    "</div>\n" +
                    "\n" +
                    "</body>\n" +
                    "\n" +
                    "</html>";



            message.setContent(msg, "text/html; charset=utf-8");
            return message;
        }catch(AddressException ex){
            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }


        public static Message prepareMessage(Session session, String myAccountEmail, String recepient, String vNummer, String name){
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(myAccountEmail));
                message.setRecipient(Message.RecipientType.TO,new InternetAddress(recepient));
                message.setSubject("Lacolsco: Verifizierungscode: " + vNummer);
                String msg = "</head>\n" +
                        "\n" +
                        "<body lang=DE style='tab-interval:35.4pt'>\n" +
                        "\n" +
                        "<div class=WordSection1>\n" +
                        "\n" +
                        "<p class=MsoNormal><span style='font-size:14.0pt;line-height:107%;font-family:\n" +
                        "\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:minor-bidi;\n" +
                        "mso-bidi-theme-font:minor-bidi'>Hallo "+name+"<o:p></o:p></span></p>\n" +
                        "\n" +
                        "<p class=MsoNormal><span style='font-size:14.0pt;line-height:107%;font-family:\n" +
                        "\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:minor-bidi;\n" +
                        "mso-bidi-theme-font:minor-bidi'><o:p>&nbsp;</o:p></span></p>\n" +
                        "\n" +
                        "<p class=MsoNormal><span style='font-size:14.0pt;line-height:107%;font-family:\n" +
                        "\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:minor-bidi;\n" +
                        "mso-bidi-theme-font:minor-bidi'>Schreib die <span class=SpellE>Verifizierungscode</span>\n" +
                        "</span><b><span style='font-size:16.0pt;line-height:107%;font-family:\"Arial\",sans-serif;\n" +
                        "mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:minor-bidi;mso-bidi-theme-font:\n" +
                        "minor-bidi;color:red'>"+ vNummer +"</span></b><span style='font-size:14.0pt;line-height:\n" +
                        "107%;font-family:\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:\n" +
                        "minor-bidi;mso-bidi-theme-font:minor-bidi'> in der vorgegebenen Feld!<o:p></o:p></span></p>\n" +
                        "\n" +
                        "<p class=MsoNormal><span style='font-size:14.0pt;line-height:107%;font-family:\n" +
                        "\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:minor-bidi;\n" +
                        "mso-bidi-theme-font:minor-bidi'><o:p>&nbsp;</o:p></span></p>\n" +
                        "\n" +
                        "<p class=MsoNormal><span style='font-size:14.0pt;line-height:107%;font-family:\n" +
                        "\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:minor-bidi;\n" +
                        "mso-bidi-theme-font:minor-bidi'>Vielen Dank<o:p></o:p></span></p>\n" +
                        "\n" +
                        "<p class=MsoNormal><span style='font-size:14.0pt;line-height:107%;font-family:\n" +
                        "\"Arial\",sans-serif;mso-ascii-theme-font:minor-bidi;mso-hansi-theme-font:minor-bidi;\n" +
                        "mso-bidi-theme-font:minor-bidi'>Dein <span class=SpellE>Lacolsco</span> Team<o:p></o:p></span></p>\n" +
                        "\n" +
                        "</div>\n" +
                        "\n" +
                        "</body>\n" +
                        "\n" +
                        "</html>\n";



                message.setContent(msg, "text/html; charset=utf-8");
                return message;
            }catch(AddressException ex){
                Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }



}

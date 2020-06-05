package org.bonn.se.model.objects.dto;

import com.vaadin.ui.Image;

public class StudentDTO extends UserDTO {
        private String vorname;
        private String nachname;
        private String semester;
        private String studiengang;
        private String g_datum;
        private int tel_nr;
        private int mobil_nr;
        private String stasse;
        private int plz;
        private String ort;



        public StudentDTO() {
            super();
            vorname = " ";
            nachname = "";
            semester = "";
            studiengang = "";
            g_datum=null;
            Image profilbild = null;
            tel_nr = this.tel_nr;



        }



       /* public StudentDTO(String username, String firstname) {
            super(username);
            this.firstname = firstname;
            lastname = "";
            university = "";
            gender = -1;
            degree = "";
            program = "";
            faculty = -1;
            major = -1;
        }*/



        public String getVorname() {
            return vorname;
        }

        public void setVorname(String vorname) {
            this.vorname=vorname;
        }

        public String getNachname() {
            return nachname;
        }

        public void setNachname(String nachname) {
            this.nachname=nachname;
        }

        public String getSemester(){
            return semester;
        }
        public void setSemester(String semester) {
            this.semester=semester;
        }

        public String getStudiengang() {
            return studiengang;
        }
        public void setStudiengang(String studiengang) {
            this.studiengang=studiengang;
        }

    public String getG_datum() {
        return g_datum;
    }
    public void setG_datum(String g_datum) {
        this.g_datum=g_datum;
    }

    }

package org.bonn.se.control;

import org.bonn.se.model.dao.ProfilDAO;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.exception.DatabaseException;

public class ProfilControl {

    private static ProfilControl instance;

    public static ProfilControl getInstance() {
        if (instance == null){
            instance = new ProfilControl();
        }
        return instance;
    }

    public void  updateStudentDaten(Student student) {

        try {
            ProfilDAO.getInstance().updateStudentDaten(student);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    public void  updateStudentTaetigkeiten(Student student) {

        try {
            ProfilDAO.getInstance().updateStudentTaetigkeiten(student);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    public void  updateStudentKenntis(Student student) {

        try {
            ProfilDAO.getInstance().updateStudentKenntnis(student);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    public void  updateUnternehmen(Unternehmen unternehmen) {

        try {
            ProfilDAO.getInstance().updateUnternehmen(unternehmen);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
}

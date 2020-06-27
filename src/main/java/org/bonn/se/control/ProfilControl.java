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

    public void  updateStudent(Student student) {

        try {
            ProfilDAO.getInstance().updateStudent(student);
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

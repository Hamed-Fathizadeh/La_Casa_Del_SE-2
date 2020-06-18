package org.bonn.se.control.exception;

import org.bonn.se.model.dao.ProfilDAO;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.services.db.exception.DatabaseException;

public class ProfilControl {

    private static ProfilControl instance;

    public static ProfilControl getInstance() {
        return instance == null ? instance = new ProfilControl() : instance;
    }

    public void  updateStudent(Student student) {

        try {
            ProfilDAO.getInstance().updateStudent(student);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
}

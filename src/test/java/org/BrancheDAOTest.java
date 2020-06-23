package org;

import junit.util.UserTestFactory;
import org.bonn.se.model.dao.BrancheDAO;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.services.db.exception.DatabaseException;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

public class BrancheDAOTest {

    //public static BrancheDAO bd = null;

    BrancheDAO bd= BrancheDAO.getInstance();
    BrancheDAO bd1= new BrancheDAO() ;

    @Test
    public void getInstance(){
        Assert.assertNotEquals(bd,bd1);
    }

    @Test
    public void getBranche() throws DatabaseException, SQLException {
        System.out.println(bd.getBranche());
        Assert.assertEquals(bd.getBranche().size(),bd1.getBranche().size());
    }

    @Test
    public void test() throws DatabaseException {

        UserTestFactory userTestFactory = new UserTestFactory();
        Student student = userTestFactory.getProfilStudent();

        System.out.println(student);


    }

}
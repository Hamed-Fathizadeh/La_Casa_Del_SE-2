package org;

import org.bonn.se.model.dao.BrancheDAO;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class BrancheDAOTest {

    //public static BrancheDAO bd = null;

    BrancheDAO bd= new BrancheDAO() ;
    BrancheDAO bd1= new BrancheDAO() ;

    @Test
    public void getInstance(){

        Assert.assertNotEquals(bd,bd1);
    }

    @Test
    public void getBranche(){
        System.out.println(bd.getBranche());
        Assert.assertEquals(bd.getBranche().size(),bd1.getBranche().size());
    }

}
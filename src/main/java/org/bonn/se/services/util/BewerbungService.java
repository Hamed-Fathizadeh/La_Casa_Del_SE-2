package org.bonn.se.services.util;

import org.bonn.se.control.ComponentControl;
import org.bonn.se.model.dao.AbstractDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BewerbungService extends AbstractDAO {

    public static BewerbungService dao = null;

    private static List< String> listeBew =  new ArrayList<String>();

    public BewerbungService() {
        listeBew = ComponentControl.getInstance().getSuchbegriffe();
    }

    public  List< String> getBewerbung(){
        return listeBew;
    }

    public int count() {
        return listeBew.size();
    }

    public int count(String filter) {
        return (int) getBewerbung().stream()
                .filter(begrif -> filter == null || begrif
                        .toLowerCase().startsWith(filter.toLowerCase())
                        ||begrif.toLowerCase().contains(filter.toLowerCase())
                )
                .count();
    }

    public Stream<String> fetch(String filter, int offset, int limit) {
        return getBewerbung().stream()
                .filter(begrif -> filter == null || begrif
                        .toLowerCase().startsWith(filter.toLowerCase()) || begrif
                        .toLowerCase().contains(filter.toLowerCase())
                )
                .skip(offset).limit(limit);
    }



}


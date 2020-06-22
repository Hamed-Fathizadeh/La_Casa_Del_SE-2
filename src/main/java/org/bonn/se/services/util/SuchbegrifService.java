package org.bonn.se.services.util;


import org.bonn.se.control.ComponentControl;
import org.bonn.se.model.dao.AbstractDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SuchbegrifService extends AbstractDAO {

    private static SuchbegrifService dao = null;
    private static List< String> listeBeg = new ArrayList<>();

    public SuchbegrifService() {
        listeBeg = ComponentControl.getInstance().getSuchbegriffe();
    }

    public  List< String> getSuchbegriffe(){
        return listeBeg;
    }

    public int count() {
        return listeBeg.size();
    }

    public int count(String filter) {
        return (int) getSuchbegriffe().stream()
                .filter(begrif -> filter == null || begrif
                        .toLowerCase().startsWith(filter.toLowerCase())
               ||begrif.toLowerCase().contains(filter.toLowerCase())
                )
                .count();
    }

    public Stream<String> fetch(String filter, int offset, int limit) {
        return getSuchbegriffe().stream()
                .filter(begrif -> filter == null || begrif
                        .toLowerCase().startsWith(filter.toLowerCase()) || begrif
                        .toLowerCase().contains(filter.toLowerCase())
                )
                .skip(offset).limit(limit);
    }



}

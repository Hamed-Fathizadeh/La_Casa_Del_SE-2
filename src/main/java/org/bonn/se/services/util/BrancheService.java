package org.bonn.se.services.util;

import org.bonn.se.control.ComponentControl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BrancheService { public static SuchbegrifService dao = null;
    private static List< String> listeBranche =  new ArrayList<String>();

    public BrancheService() {
        listeBranche = ComponentControl.getInstance().getBranche();
    }

    public  List< String> getSuchbegriffe(){
        return listeBranche;
    }

    public int count() {
        return listeBranche.size();
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


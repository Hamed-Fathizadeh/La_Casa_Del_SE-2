package org.bonn.se.services.util;

import org.bonn.se.control.ComponentControl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BrancheService { // --Commented out by Inspection (22.06.20, 23:29):public static SuchbegrifService dao = null;
    private static List< String> listeBranche = new ArrayList<>();

    public BrancheService() {
        listeBranche = ComponentControl.getInstance().getBranche();
    }

    public  List< String> getSuchbegriffe(){
        return listeBranche;
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


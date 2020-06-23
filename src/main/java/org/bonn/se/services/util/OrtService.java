package org.bonn.se.services.util;

import org.bonn.se.control.ComponentControl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class OrtService {
    private static OrtService dao = null;
    private static List<String> listeOrt = new ArrayList<>();

    public OrtService( String str) {
        if(str.equals("Stadt, Bund")) {
            listeOrt = ComponentControl.getInstance().getOrt();
        }else{
            listeOrt = ComponentControl.getInstance().getBund();
        }
    }


    public static List<String> getOrte(){
        return listeOrt;
    }

    public int count(String filter) {
        return (int) getOrte().stream()
                .filter(begrif -> filter == null || begrif
                        .toLowerCase().startsWith(filter.toLowerCase())
                )
                .count();
    }

    public Stream<String> fetch(String filter, int offset, int limit) {
        return getOrte().stream()
                .filter(begrif -> filter == null || begrif
                        .toLowerCase().startsWith(filter.toLowerCase())
                )
                .skip(offset).limit(limit);

    }
}

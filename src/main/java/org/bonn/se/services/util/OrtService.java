package org.bonn.se.services.util;

import org.bonn.se.control.ComponentControl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class OrtService {

    public static OrtService dao = null;
    private static List<String> listeOrt = new ArrayList<>();

    public OrtService( String str) {
        if(str.equals("Stadt, Bund")) {
            listeOrt = ComponentControl.getInstance().getOrt();
        }else{
            listeOrt = ComponentControl.getInstance().getBund();
        }
    }

    public static List<String> OrtService(){
        return listeOrt;
    }

    public int count(String filter) {
        return (int) OrtService().stream()
                .filter(begrif -> filter == null || begrif
                        .toLowerCase().startsWith(filter.toLowerCase())
                        //||begrif.toLowerCase().contains(filter.toLowerCase())
                )
                .count();
    }

    public Stream<String> fetch(String filter, int offset, int limit) {
        return OrtService().stream()
                .filter(begrif -> filter == null || begrif
                        .toLowerCase().startsWith(filter.toLowerCase())
                        //|| begrif.toLowerCase().contains(filter.toLowerCase())
                )
                .skip(offset).limit(limit);

    }

}

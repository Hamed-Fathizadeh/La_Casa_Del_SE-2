package org.bonn.se.services.util;


/*
public class BundeslandService {

    public static BundeslandService dao = null;
    private static final List<String> listeBund = new ArrayList<>();

// --Commented out by Inspection START (22.06.20, 23:29):
//    public BundeslandService() {
//        listeBund.add("Baden-Württemberg"); listeBund.add("Bayern"); listeBund.add("Berlin"); listeBund.add("Brandenburg");
//        listeBund.add("Bremen"); listeBund.add("Hamburg"); listeBund.add("Hessen"); listeBund.add("Mecklenburg-Vorpommern");
//        listeBund.add("Niedersachsen"); listeBund.add("Nordrhein-Westfalen"); listeBund.add("Rheinland-Pfalz"); listeBund.add("Saarland");
//        listeBund.add("Sachsen"); listeBund.add("Sachsen-Anhalt"); listeBund.add("Schleswig-Holstein"); listeBund.add("Thüringen");
//    }
// --Commented out by Inspection STOP (22.06.20, 23:29)

    public static List<String> getBundesland(){
        return listeBund;
    }

    public int count() {
        return listeBund.size();
    }

    public int count(String filter) {
        return (int) getBundesland().stream()
                .filter(begrif -> filter == null || begrif
                        .toLowerCase().startsWith(filter.toLowerCase())
                        ||begrif.toLowerCase().contains(filter.toLowerCase())
                )
                .count();
    }

    public Stream<String> fetch(String filter, int offset, int limit) {
        return getBundesland().stream()
                .filter(begrif -> filter == null || begrif
                        .toLowerCase().startsWith(filter.toLowerCase()) || begrif
                        .toLowerCase().contains(filter.toLowerCase())
                )
                .skip(offset).limit(limit);
    }

    public List<String> fetchAll() {
        return getBundesland();
    }


}

 */

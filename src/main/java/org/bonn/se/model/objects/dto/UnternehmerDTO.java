package org.bonn.se.model.objects.dto;

public class UnternehmerDTO extends  UserDTO {

    private String name;
    private String ansprechpartner;//p
    private int id_branche;


    public UnternehmerDTO() {
        super();
        name = "";
        ansprechpartner = "";
        id_branche = 1;
    }


    public  String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnsprechpartner() {
        return ansprechpartner;
    }

    public void setAnsprechpartner(String ansprechpartner) {

        this.ansprechpartner = ansprechpartner;
    }

    public int getId_branche() {
        return id_branche;
    }

    public void setBranch(int id_branch) {
        this.id_branche = id_branch;
    }

}

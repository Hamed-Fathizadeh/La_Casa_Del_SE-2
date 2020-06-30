package org.bonn.se.services.util;

import org.bonn.se.model.objects.dto.BewerbungDTO;

import java.sql.Date;

abstract class DTOFactory {

    public DTOFactory(){

    }

    public abstract  BewerbungDTO createBewerbungDTO();
    
}

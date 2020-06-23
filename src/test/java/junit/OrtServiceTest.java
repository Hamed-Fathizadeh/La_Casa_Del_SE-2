package junit;

import org.bonn.se.control.ComponentControl;
import org.bonn.se.services.util.OrtService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class OrtServiceTest {

    @Test
    public void testortservice() {
        Assertions.assertEquals(14235, ComponentControl.getInstance().getOrt().size());
    }
    @Test
    public void testortbundservice() {
        Assertions.assertEquals(14235, ComponentControl.getInstance().getBund().size());
    }
    @Test
    public void testcount(){
        OrtService ort = new OrtService("Stadt, Bund");
        Assertions.assertEquals(3,ort.count("Aach") );
        Assertions.assertEquals(1,ort.count("Abentheuer") );
    }
    @Test
    public void testfetch(){
        OrtService ort = new OrtService("Stadt, Bund");
        Assertions.assertEquals(1,ort.fetch("A",1,1).count());


    }



}
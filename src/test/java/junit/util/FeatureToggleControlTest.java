package junit.util;
import org.bonn.se.control.FeatureToggleControl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class FeatureToggleControlTest {

    @Test
    public void testFeatureControl(){
        FeatureToggleControl featureToggleControl= FeatureToggleControl.getInstance();
        Assertions.assertFalse(featureToggleControl.featureIsEnabled("fh"));
        Assertions.assertTrue(featureToggleControl.featureIsEnabled("BEWERBUNGEN"));


    }

}

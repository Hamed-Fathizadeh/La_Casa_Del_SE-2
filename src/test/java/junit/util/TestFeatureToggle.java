package junit.util;

import org.bonn.se.control.FeatureToggleControl;
import org.junit.Test;

public class TestFeatureToggle {

    @Test
    public void testFeatureToggle(){

        FeatureToggleControl.getInstance().featureIsEnabled("BEWERBUNGEN");
    }
}

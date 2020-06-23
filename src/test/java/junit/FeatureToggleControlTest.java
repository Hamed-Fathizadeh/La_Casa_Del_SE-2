package junit;
import org.bonn.se.control.FeatureToggleControl;
import org.bonn.se.model.dao.FeatureToggleDAO;
import org.bonn.se.services.db.exception.DatabaseException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.SQLException;

public class FeatureToggleControlTest {

    @Test
    public void testFeatureControl() throws DatabaseException, SQLException {
        FeatureToggleControl featureToggleControl= FeatureToggleControl.getInstance();
        Assertions.assertFalse(featureToggleControl.featureIsEnabled("fh"));
        Assertions.assertTrue(featureToggleControl.featureIsEnabled("BEWERBUNGEN"));

        FeatureToggleDAO featureToggleDAO = FeatureToggleDAO.getInstance();
        Assertions.assertTrue(featureToggleDAO.featureIsEnabled("BEWERBUNGEN"));
    }

}

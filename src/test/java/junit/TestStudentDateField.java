package junit;

import com.vaadin.ui.DateField;
import org.bonn.se.gui.component.StudentDateField;
import org.junit.Assert;
import org.junit.Test;

public class TestStudentDateField {

    @Test
    public void testField(){
        StudentDateField studentDateField = new StudentDateField("Test");
        Assert.assertTrue(studentDateField instanceof DateField);
    }
}
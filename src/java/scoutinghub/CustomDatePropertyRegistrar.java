package scoutinghub;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDatePropertyRegistrar implements PropertyEditorRegistrar {

    public void registerCustomEditors(PropertyEditorRegistry registry) {

        String dateFormat = "MM/dd/yyyy";
        registry.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(dateFormat), true));
    }
}

package scoutinghub;

import java.beans.PropertyEditorSupport;

/**
 * Created with IntelliJ IDEA.
 * User: ericm
 * Date: 7/3/12
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParentGroupPropertyEditor extends PropertyEditorSupport {

    @Override
    Object getProperty(String property) {
        //I assume it will only get called for the appropriate caller
        def parentIds = []
        def object = super.getProperty(property)
        if(object != null || object instanceof ScoutGroup) {
            while (object != null) {
                parentsIds << object.id
                object = object.parent
            }
        }
        return parentIds
    }

    @Override
    Object getSource() {
        return super.getSource()    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    Object getValue() {
        return super.getValue()    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    String getAsText() {
        return super.getAsText()    //To change body of overridden methods use File | Settings | File Templates.
    }
}

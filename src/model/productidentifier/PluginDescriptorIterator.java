package model.productidentifier;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: nethier
 * Date: 11/28/12
 * Time: 8:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class PluginDescriptorIterator implements IPluginDescriptorIterator {

    Iterator<PluginDescriptor> iterator;

    public PluginDescriptorIterator(Iterator<PluginDescriptor> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Class<ProductIdentificationPlugin> next() {
        try {
            return (Class<ProductIdentificationPlugin>) Class
                    .forName(iterator.next().getClassName());
        } catch (ClassNotFoundException e) {
            //We should never get here
            assert (false);
            return null;
        }
    }

    @Override
    public void remove() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

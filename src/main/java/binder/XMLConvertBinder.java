package binder;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import converters.XMLConvert;

@Resource
@ManagedBean
public class XMLConvertBinder extends AbstractBinder {
	@Override
    protected void configure() {
        bind(XMLConvert.class).to(XMLConvert.class);
    }
}

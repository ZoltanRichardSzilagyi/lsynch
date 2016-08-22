package hu.zrs.lsynch.factory;

import java.nio.file.FileSystems;
import java.nio.file.WatchService;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class WatchServiceFactoryBean implements FactoryBean<WatchService> {

	@Override
	public WatchService getObject() throws Exception {
		return FileSystems.getDefault().newWatchService();
	}

	@Override
	public Class<?> getObjectType() {
		return WatchService.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}

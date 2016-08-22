package hu.zrs.lsynch.registry;

import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;
import java.util.concurrent.DelayQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import hu.zrs.lsynch.api.event.ChangeEvent;
import hu.zrs.lsynch.api.registry.EventRegistry;

@Component
public class FileSystemChangeEventRegistry implements EventRegistry<ChangeEvent<Path, Kind<Path>>> {

	private final Logger logger = LoggerFactory.getLogger(FileSystemChangeEventRegistry.class);

	private final DelayQueue<ChangeEvent<Path, Kind<Path>>> eventQueue = new DelayQueue<>();

	@Override
	public void registerEvent(ChangeEvent<Path, Kind<Path>> event) {
		eventQueue.put(event);
	}

	@Override
	public ChangeEvent<Path, Kind<Path>> take() {
		ChangeEvent<Path, Kind<Path>> event = null;
		try {
			event = eventQueue.take();
		} catch (final InterruptedException exception) {
			logger.error(exception.getMessage(), exception);
		}
		return event;
	}

}

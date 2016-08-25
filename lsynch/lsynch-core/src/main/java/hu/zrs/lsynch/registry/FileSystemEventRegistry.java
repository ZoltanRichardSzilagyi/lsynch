package hu.zrs.lsynch.registry;

import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;
import java.util.concurrent.DelayQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.zrs.lsynch.api.event.DelayedEvent;
import hu.zrs.lsynch.api.registry.EventRegistry;

public class FileSystemEventRegistry implements EventRegistry<DelayedEvent<Path, Kind<Path>>> {

	private final Logger logger = LoggerFactory.getLogger(FileSystemEventRegistry.class);

	private final DelayQueue<DelayedEvent<Path, Kind<Path>>> eventQueue = new DelayQueue<>();

	@Override
	public void registerEvent(DelayedEvent<Path, Kind<Path>> event) {
		logger.debug("Register event: {}, file: {}", event.getEventType().name(), event.getSource().toString());
		eventQueue.put(event);
	}

	@Override
	public DelayedEvent<Path, Kind<Path>> take() {
		DelayedEvent<Path, Kind<Path>> event = null;
		try {
			event = eventQueue.take();
		} catch (final InterruptedException exception) {
			logger.error(exception.getMessage(), exception);
		}
		return event;
	}

}

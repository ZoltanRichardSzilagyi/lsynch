package hu.zrs.lsynch.registry;

import java.util.concurrent.DelayQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.zrs.lsynch.api.event.FileSystemEvent;
import hu.zrs.lsynch.api.registry.EventRegistry;

public class FileSystemEventRegistry implements EventRegistry<FileSystemEvent> {

	private final Logger logger = LoggerFactory.getLogger(FileSystemEventRegistry.class);

	private final DelayQueue<FileSystemEvent> eventQueue = new DelayQueue<>();

	@Override
	public void registerEvent(FileSystemEvent event) {
		logger.debug("Register event: {}, file: {}", event.getEventType().name(), event.getSource().toString());
		eventQueue.put(event);
	}

	@Override
	public FileSystemEvent take() {
		FileSystemEvent event = null;
		try {
			event = eventQueue.take();
		} catch (final InterruptedException exception) {
			logger.error(exception.getMessage(), exception);
		}
		return event;
	}

}

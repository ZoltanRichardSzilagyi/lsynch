package hu.zrs.lsynch.consumer.processor;

import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import hu.zrs.lsynch.api.consumer.processor.EventProcessor;
import hu.zrs.lsynch.api.event.FileSystemEvent;

public class FileSystemEventProcessor implements EventProcessor<FileSystemEvent> {

	@Autowired
	private Map<Kind<Path>, EventProcessor<FileSystemEvent>> eventProcessors;

	@Override
	public FileSystemEvent process(FileSystemEvent event) {
		final EventProcessor<FileSystemEvent> eventProcessor = eventProcessors.get(event.getEventType());
		if (eventProcessor != null) {
			eventProcessor.process(event);
		}
		return event;
	}

}

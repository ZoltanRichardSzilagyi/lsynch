package hu.zrs.lsynch.consumer.processor;

import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.zrs.lsynch.api.consumer.processor.EventProcessor;
import hu.zrs.lsynch.api.event.DelayedEvent;

public class FileSystemEventLoggerProcessor implements EventProcessor<Path, Kind<Path>> {

	private static final Logger logger = LoggerFactory.getLogger(FileSystemEventLoggerProcessor.class);

	@Override
	public DelayedEvent<Path, Kind<Path>> process(DelayedEvent<Path, Kind<Path>> event) {
		logger.info("Process {}", event.getSource().toFile());
		return event;
	}


}

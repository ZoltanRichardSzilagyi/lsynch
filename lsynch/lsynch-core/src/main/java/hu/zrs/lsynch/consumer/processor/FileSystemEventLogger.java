package hu.zrs.lsynch.consumer.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.zrs.lsynch.api.consumer.processor.EventProcessor;
import hu.zrs.lsynch.api.event.FileSystemEvent;

public class FileSystemEventLogger implements EventProcessor<FileSystemEvent> {

	private static final Logger logger = LoggerFactory.getLogger(FileSystemEventLogger.class);

	@Override
	public FileSystemEvent process(FileSystemEvent event) {
		logger.info("Process {}", event.getSource().getRelativeSourcePath());
		return event;
	}


}

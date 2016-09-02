package hu.zrs.lsynch.consumer.processor;

import hu.zrs.lsynch.api.consumer.processor.EventProcessor;
import hu.zrs.lsynch.api.event.FileSystemEvent;

public class FileSystemEventBackupDispatcher implements EventProcessor<FileSystemEvent> {

	@Override
	public FileSystemEvent process(FileSystemEvent event) {
		return event;
	}

}

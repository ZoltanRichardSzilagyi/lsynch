package hu.zrs.lsynch.consumer.processor;

import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;

import hu.zrs.lsynch.api.consumer.processor.EventProcessor;
import hu.zrs.lsynch.api.event.DelayedEvent;

public class FileCopyProcessor implements EventProcessor<Path, Kind<Path>> {


	@Override
	public DelayedEvent<Path, Kind<Path>> process(DelayedEvent<Path, Kind<Path>> event) {
		return event;
	}

}

package hu.zrs.lsynch.factory;

import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;

import hu.zrs.lsynch.api.event.DelayedEvent;
import hu.zrs.lsynch.api.event.FileSystemEvent;
import hu.zrs.lsynch.api.factory.DelayedEventFactory;

public class FileSystemEventFactory implements DelayedEventFactory<Path, Kind<Path>> {

	@Value("${file.system.event.delay.ms}")
	private Integer delayTimeInMs;

	@Override
	public DelayedEvent<Path, Kind<Path>> createEvent(Path source, Kind<Path> eventType) {
		final Long delayInNanos = TimeUnit.MILLISECONDS.toNanos(delayTimeInMs);
		final Long executeAt = System.nanoTime() + delayInNanos;

		return new FileSystemEvent(source, eventType, executeAt);
	}

}

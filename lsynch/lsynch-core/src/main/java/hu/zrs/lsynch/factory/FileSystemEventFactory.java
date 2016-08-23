package hu.zrs.lsynch.factory;

import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import hu.zrs.lsynch.api.event.DelayedEvent;
import hu.zrs.lsynch.api.event.FileSystemEvent;
import hu.zrs.lsynch.api.factory.DelayedEventFactory;

@Component
public class FileSystemEventFactory implements DelayedEventFactory<Path, Kind<Path>> {

	private final Integer delayTimeInSeconds = 1;

	@Override
	public DelayedEvent<Path, Kind<Path>> createEvent(Path source, Kind<Path> eventType) {
		final Long delayInNanos = TimeUnit.SECONDS.toNanos(delayTimeInSeconds);
		final Long executeAt = System.nanoTime() + delayInNanos;

		return new FileSystemEvent(source, eventType, executeAt);
	}

}

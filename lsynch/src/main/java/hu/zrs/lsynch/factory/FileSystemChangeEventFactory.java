package hu.zrs.lsynch.factory;

import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import hu.zrs.lsynch.api.FileSystemChangeEvent;
import hu.zrs.lsynch.api.event.ChangeEvent;
import hu.zrs.lsynch.api.factory.ChangeEventFactory;

@Component
public class FileSystemChangeEventFactory implements ChangeEventFactory<Path, Kind<Path>> {

	private final Integer delayTimeInSeconds = 1;

	@Override
	public ChangeEvent<Path, Kind<Path>> createEvent(Path source, Kind<Path> eventType) {
		final Long delayInNanos = TimeUnit.SECONDS.toNanos(delayTimeInSeconds);
		final Long executeAt = System.nanoTime() + delayInNanos;

		return new FileSystemChangeEvent(source, eventType, executeAt);
	}

}

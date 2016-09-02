package hu.zrs.lsynch.api.event;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import hu.zrs.lsynch.api.entry.FileSystemEntry;

public class FileSystemEvent implements Event<FileSystemEntry, Kind<Path>>, Delayed {

	private final FileSystemEntry fileSystemEntry;

	private final Kind<Path> eventType;

	private final Long executeAt;

	public FileSystemEvent(FileSystemEntry fileSystemEntry, Kind<Path> eventType, Long executeAt) {
		this.fileSystemEntry = fileSystemEntry;
		this.eventType = eventType;
		this.executeAt = executeAt;
	}

	@Override
	public FileSystemEntry getSource() {
		return fileSystemEntry;
	}

	@Override
	public Kind<Path> getEventType() {
		return eventType;
	}

	@Override
	public int compareTo(Delayed other) {
		if (this == other) {
			return 0;
		}
		final long diff = getDelay(NANOSECONDS) - other.getDelay(NANOSECONDS);
		return diff < 0 ? -1 : diff > 0 ? 1 : 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(executeAt - System.nanoTime(), TimeUnit.NANOSECONDS);
	}

}

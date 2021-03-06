package hu.zrs.lsynch.watcher;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import hu.zrs.lsynch.api.entry.FileSystemEntry;
import hu.zrs.lsynch.api.event.FileSystemEvent;
import hu.zrs.lsynch.api.factory.DelayedEventFactory;
import hu.zrs.lsynch.api.registry.EventRegistry;
import hu.zrs.lsynch.api.registry.WatchKeyPathRegistry;
import hu.zrs.lsynch.api.watcher.DirectoryWatchExecutor;

public class SingleThreadDirectoryWatchExecutor implements DirectoryWatchExecutor {

	private static final Logger logger = LoggerFactory.getLogger(SingleThreadDirectoryWatchExecutor.class);

	private final ExecutorService watchExecutor = Executors.newSingleThreadExecutor();

	@Autowired
	private WatchService watchService;

	@Autowired
	private EventRegistry<FileSystemEvent> eventRegistry;

	@Autowired
	private DelayedEventFactory<FileSystemEvent, FileSystemEntry, Kind<Path>> fileSystemEventFactory;

	@Autowired
	private WatchKeyPathRegistry watchKeyPathRegistry;

	private boolean shutdown = false;

	@Override
	public void start() {
		watchExecutor.submit(() -> {
			while (!shutdown) {
				try {
					final WatchKey watchKey = watchService.take();
					processEvents(watchKey);
					final boolean valid = watchKey.reset();
					if (!valid) {
						logger.debug("watchkey {} is not valid anymore", watchKey.toString());
					}
				} catch (final Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		});

	}

	@SuppressWarnings("unchecked")
	private void processEvents(final WatchKey watchKey) {
		watchKey.pollEvents().forEach(event -> {
			final WatchEvent<Path> watchEvent = (WatchEvent<Path>) event;
			final Path basePath = watchKeyPathRegistry.get(watchKey);
			final Path fullPath = basePath.resolve(watchEvent.context());
			// TODO basePath is not correct
			final FileSystemEntry fileSystemEntry = new FileSystemEntry(fullPath, basePath);

			final FileSystemEvent changeEvent = fileSystemEventFactory.createEvent(fileSystemEntry,
					watchEvent.kind());
			eventRegistry.registerEvent(changeEvent);
		});
	}

	@Override
	public void stop() {
		shutdown = true;
	}

}

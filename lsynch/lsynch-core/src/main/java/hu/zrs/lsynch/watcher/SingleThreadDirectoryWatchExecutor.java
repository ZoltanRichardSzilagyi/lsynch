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
import org.springframework.stereotype.Component;

import hu.zrs.lsynch.api.event.DelayedEvent;
import hu.zrs.lsynch.api.factory.DelayedEventFactory;
import hu.zrs.lsynch.api.registry.EventRegistry;
import hu.zrs.lsynch.api.registry.WatchKeyPathRegistry;
import hu.zrs.lsynch.api.watcher.DirectoryWatchExecutor;

@Component
public class SingleThreadDirectoryWatchExecutor implements DirectoryWatchExecutor {

	private static final Logger logger = LoggerFactory.getLogger(SingleThreadDirectoryWatchExecutor.class);

	private final ExecutorService watchExecutor = Executors.newSingleThreadExecutor();

	@Autowired
	private WatchService watchService;

	@Autowired
	private EventRegistry<DelayedEvent<Path, Kind<Path>>> eventRegistry;

	@Autowired
	private DelayedEventFactory<Path, Kind<Path>> changeEventFactory;

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

			final DelayedEvent<Path, Kind<Path>> changeEvent = changeEventFactory.createEvent(fullPath,
					watchEvent.kind());
			eventRegistry.registerEvent(changeEvent);
		});
	}

	@Override
	public void stop() {
		shutdown = true;
	}

}

package hu.zrs.lsynch.watcher;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.zrs.lsynch.api.registry.WatchKeyPathRegistry;
import hu.zrs.lsynch.api.watcher.DirectoryWatchExecutor;
import hu.zrs.lsynch.api.watcher.DirectoryWatcher;
import hu.zrs.lsynch.visitor.DirectoryCollectorFileVisitor;

@Component
public class RecursiveDirectoryWatcher implements DirectoryWatcher{

	private static final Logger logger = LoggerFactory.getLogger(RecursiveDirectoryWatcher.class);

	@Autowired
	private WatchService watchService;

	@Autowired
	private WatchKeyPathRegistry watchKeyPathRegistry;

	@Autowired
	private DirectoryWatchExecutor watchExecutor;

	@Override
	public void startWatching() {
		watchExecutor.start();
	}

	@Override
	public void stopWatching() {
		watchExecutor.stop();
		try {
			watchService.close();
		} catch (final IOException exception) {
			logger.error(exception.getMessage(), exception);
		}
	}

	@Override
	public void addToWatch(Path path) {
		final File fileEntry = path.toFile();
		if(fileEntry.exists() && fileEntry.isDirectory()){
			try {
				visitSubDirectories(path);
			} catch (final IOException exception) {
				logger.error(exception.getMessage(), exception);
			}
		}
	}

	private void visitSubDirectories(Path path) throws IOException {
		final DirectoryCollectorFileVisitor fileVisitor = new DirectoryCollectorFileVisitor();
		Files.walkFileTree(path, fileVisitor);
		final List<Path> subDirectories = fileVisitor.getCollectedDirectories();
		subDirectories.forEach(directory -> registerDirectory(directory));
	}


	private void registerDirectory(Path directory) {
		logger.debug("Register new directory: {}", directory.getFileName());
		try {
			final WatchKey watchKey = directory.register(watchService,
					new WatchEvent.Kind[] { ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE, OVERFLOW });
			watchKeyPathRegistry.add(watchKey, directory);
		} catch (final IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

}

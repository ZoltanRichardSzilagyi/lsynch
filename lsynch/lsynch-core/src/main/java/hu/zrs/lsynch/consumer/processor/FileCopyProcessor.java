package hu.zrs.lsynch.consumer.processor;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.zrs.lsynch.api.consumer.processor.EventProcessor;
import hu.zrs.lsynch.api.event.FileSystemEvent;

public class FileCopyProcessor implements EventProcessor<FileSystemEvent> {

	private static final Logger logger = LoggerFactory.getLogger(FileCopyProcessor.class);

	public FileCopyProcessor(Path targetDirectory) {
		this.targetDirectory = targetDirectory;
	}

	private final Path targetDirectory;


	@Override
	public FileSystemEvent process(FileSystemEvent event) {
		final Path path = event.getSource();
		if (path.toFile().isFile()) {
			final Path target = Paths.get(targetDirectory.toString(), path.toString());
			// try {
			// Files.copy(path, target, StandardCopyOption.REPLACE_EXISTING);
			// } catch (final IOException exception) {
			// logger.error(exception.getMessage(), exception);
			// }
		}
		return event;
	}

}

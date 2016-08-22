package hu.zrs.lsynch.visitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirectoryCollectorFileVisitor extends SimpleFileVisitor<Path> {

	private static final Logger logger = LoggerFactory.getLogger(DirectoryCollectorFileVisitor.class);

	private final List<Path> collectedDirectories = new ArrayList<>();

	@Override
	public FileVisitResult preVisitDirectory(Path directory, BasicFileAttributes attrs) throws IOException {
		logger.debug("Visit directory: {}", directory);
		collectedDirectories.add(directory);
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		logger.error(exc.getMessage(), exc);
		return FileVisitResult.CONTINUE;
	}

	public List<Path> getCollectedDirectories() {
		return collectedDirectories;
	}

}

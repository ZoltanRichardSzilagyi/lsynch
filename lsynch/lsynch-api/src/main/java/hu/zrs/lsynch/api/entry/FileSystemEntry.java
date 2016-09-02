package hu.zrs.lsynch.api.entry;

import java.nio.file.Path;

public class FileSystemEntry {

	private final Path fullSourcePath;

	private final Path relativeSourcePath;

	public FileSystemEntry(Path fullSourcePath, Path relativeSourcePath) {
		this.fullSourcePath = fullSourcePath;
		this.relativeSourcePath = relativeSourcePath;
	}

	public Path getFullSourcePath() {
		return fullSourcePath;
	}

	public Path getRelativeSourcePath() {
		return relativeSourcePath;
	}

}

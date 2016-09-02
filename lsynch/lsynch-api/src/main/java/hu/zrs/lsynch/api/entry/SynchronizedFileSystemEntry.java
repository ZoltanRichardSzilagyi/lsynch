package hu.zrs.lsynch.api.entry;

import java.net.URI;

public class SynchronizedFileSystemEntry extends FileSystemEntry implements SynchronizedEntry {

	private final URI repositoryURI;

	private String repositoryId;

	public SynchronizedFileSystemEntry(final FileSystemEntry fileSystemEntry, final URI repositoryURI,
			String repositoryId) {
		super(fileSystemEntry.getFullSourcePath(), fileSystemEntry.getRelativeSourcePath());
		this.repositoryURI = repositoryURI;
	}

	@Override
	public URI getRepositoryURI() {
		return repositoryURI;
	}

	@Override
	public String getRepositoryId() {
		return repositoryId;
	}

}

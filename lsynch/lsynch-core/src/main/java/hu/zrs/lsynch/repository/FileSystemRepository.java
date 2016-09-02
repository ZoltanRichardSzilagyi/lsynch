package hu.zrs.lsynch.repository;

import java.nio.file.Path;
import java.nio.file.Paths;

import hu.zrs.lsynch.api.entry.FileSystemEntry;
import hu.zrs.lsynch.api.entry.SynchronizedFileSystemEntry;
import hu.zrs.lsynch.api.event.SynchronizationEvent;
import hu.zrs.lsynch.api.repository.AsyncOperationListener;
import hu.zrs.lsynch.api.repository.Repository;
import hu.zrs.lsynch.api.repository.RepositoryOperationStatus;

public class FileSystemRepository implements Repository {

	// Implement watching on side of repository, to make that able to notify
	// master if synchronized content is modified manually (guard)

	private final Path rootDirectory;

	public FileSystemRepository(Path rootDirectory) {
		this.rootDirectory = rootDirectory;
	}

	@Override
	public String getRepositoryId() {
		return rootDirectory.toString();
	}

	@Override
	public SynchronizationEvent synchronize(FileSystemEntry fileSystemEntry,
			AsyncOperationListener afterOperationReady) {
		final SynchronizationEvent task = initializeSynchronizationEvent(fileSystemEntry);

		return task;
	}

	private SynchronizationEvent initializeSynchronizationEvent(FileSystemEntry fileSystemEntry) {
		final Path repositoryPath = Paths.get(rootDirectory.toString(), fileSystemEntry.getRelativeSourcePath().toString());
		final SynchronizedFileSystemEntry synchronizedEntry = new SynchronizedFileSystemEntry(fileSystemEntry, repositoryPath.toUri(), getRepositoryId());
		final SynchronizationEvent task = new SynchronizationEvent(synchronizedEntry);
		task.setStatus(RepositoryOperationStatus.PENDING);
		return task;
	}


}

package hu.zrs.lsynch.api.event;

import hu.zrs.lsynch.api.entry.SynchronizedFileSystemEntry;
import hu.zrs.lsynch.api.repository.RepositoryOperationStatus;

public class SynchronizationEvent implements Event<SynchronizedFileSystemEntry, RepositoryOperationStatus> {

	private final SynchronizedFileSystemEntry synchronizedFileSystemEntry;

	private RepositoryOperationStatus status;

	public SynchronizationEvent(SynchronizedFileSystemEntry synchronizedFileSystemEntry) {
		this.synchronizedFileSystemEntry = synchronizedFileSystemEntry;
	}

	public String getRepositoryId() {
		return synchronizedFileSystemEntry.getRepositoryId();
	}

	public void setStatus(RepositoryOperationStatus status) {
		this.status = status;
	}

	@Override
	public SynchronizedFileSystemEntry getSource() {
		return synchronizedFileSystemEntry;
	}

	@Override
	public RepositoryOperationStatus getEventType() {
		return status;
	}

	public Boolean isDone() {
		return RepositoryOperationStatus.DONE.equals(status);
	}

	public Boolean isPending() {
		return RepositoryOperationStatus.PENDING.equals(status);
	}

	public Boolean isFailed() {
		return RepositoryOperationStatus.FAILED.equals(status);
	}


}

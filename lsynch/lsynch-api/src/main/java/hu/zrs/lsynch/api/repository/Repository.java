package hu.zrs.lsynch.api.repository;

import hu.zrs.lsynch.api.entry.FileSystemEntry;
import hu.zrs.lsynch.api.event.SynchronizationEvent;

public interface Repository {

	String getRepositoryId();

	SynchronizationEvent synchronize(FileSystemEntry path, AsyncOperationListener operationListener);



}

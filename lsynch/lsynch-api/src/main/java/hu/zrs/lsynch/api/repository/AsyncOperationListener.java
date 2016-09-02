package hu.zrs.lsynch.api.repository;

import hu.zrs.lsynch.api.event.SynchronizationEvent;

public interface AsyncOperationListener {

	void afterOperationReady(SynchronizationEvent backupEntry);

}

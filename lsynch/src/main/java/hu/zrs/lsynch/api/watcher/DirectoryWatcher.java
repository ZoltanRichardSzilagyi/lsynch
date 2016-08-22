package hu.zrs.lsynch.api.watcher;

import java.nio.file.Path;

public interface DirectoryWatcher {

	void startWatching();

	void stopWatching();

	void addToWatch(Path directory);


}

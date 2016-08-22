package hu.zrs.lsynch.api.registry;

import java.nio.file.Path;
import java.nio.file.WatchKey;

public interface WatchKeyPathRegistry {
	
	void add(WatchKey watchKey, Path path);
	
	Path get(WatchKey watchKey);

}

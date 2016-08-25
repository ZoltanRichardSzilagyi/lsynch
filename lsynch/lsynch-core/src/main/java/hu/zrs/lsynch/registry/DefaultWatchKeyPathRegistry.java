package hu.zrs.lsynch.registry;

import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import hu.zrs.lsynch.api.registry.WatchKeyPathRegistry;

public class DefaultWatchKeyPathRegistry implements WatchKeyPathRegistry {

	private final Map<WatchKey, Path> watchKeys = new ConcurrentHashMap<>();

	@Override
	public void add(WatchKey watchKey, Path path) {
		watchKeys.put(watchKey, path);
	}

	@Override
	public Path get(WatchKey watchKey) {
		return watchKeys.get(watchKey);
	}

}

package hu.zrs.lsynch.api;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class DirectoryWatcherSandbox {
	
	public static void main(String[] args) throws Exception {
		watchDirectory("/home/zrs");
		watchDirectory("/home/zrs/test");
	}

	private static void watchDirectory(String dirPath) throws IOException, InterruptedException {
		Path path = Paths.get(dirPath);
		final WatchService watchService = FileSystems.getDefault().newWatchService();
		
		path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.OVERFLOW);
		
		new Thread( ()-> {
		while(true){
			WatchKey key = null;
			try {
				key = watchService.take();
			} catch (Exception e) {
				e.printStackTrace();
			}
			key.pollEvents().forEach(event -> {
				System.out.println("-------------------"+path.getFileName()+"-------------------");
				WatchEvent.Kind<?> kind = event.kind();
				WatchEvent<Path> watchEvent = (WatchEvent<Path>)event;
				System.out.println(kind.name());
				System.out.println(watchEvent.context().toString());
				System.out.println("-------------------");
				
			});
			key.reset();
		}
		}).start();
	}

}

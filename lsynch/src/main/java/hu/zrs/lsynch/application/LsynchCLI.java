package hu.zrs.lsynch.application;

import java.nio.file.Paths;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import hu.zrs.lsynch.api.watcher.DirectoryWatcher;
import hu.zrs.lsynch.watcher.RecursiveDirectoryWatcher;

@Configuration
@ComponentScan("hu.zrs.lsynch")
public class LsynchCLI {

	public static void main(String[] args) {
		final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				LsynchCLI.class);
		final DirectoryWatcher directoryWatcher = applicationContext.getBean(RecursiveDirectoryWatcher.class);
		directoryWatcher.startWatching();

		directoryWatcher.addToWatch(Paths.get("/home/zrs"));

		directoryWatcher.stopWatching();
		// applicationContext.close();

	}

}

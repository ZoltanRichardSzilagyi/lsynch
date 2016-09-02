package hu.zrs.lsynch.it;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchService;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import hu.zrs.lsynch.api.consumer.EventConsumer;
import hu.zrs.lsynch.api.consumer.processor.EventProcessor;
import hu.zrs.lsynch.api.event.FileSystemEvent;
import hu.zrs.lsynch.api.registry.EventRegistry;
import hu.zrs.lsynch.api.registry.WatchKeyPathRegistry;
import hu.zrs.lsynch.api.watcher.DirectoryWatchExecutor;
import hu.zrs.lsynch.api.watcher.DirectoryWatcher;
import hu.zrs.lsynch.consumer.FileSystemEventConsumer;
import hu.zrs.lsynch.consumer.processor.FileSystemEventLogger;
import hu.zrs.lsynch.factory.FileSystemEventFactory;
import hu.zrs.lsynch.registry.DefaultWatchKeyPathRegistry;
import hu.zrs.lsynch.registry.FileSystemEventRegistry;
import hu.zrs.lsynch.watcher.RecursiveDirectoryWatcher;
import hu.zrs.lsynch.watcher.SingleThreadDirectoryWatchExecutor;

@Configuration
@ComponentScan("hu.zrs.lsynch")
@PropertySource("classpath:/hu/zrs/lsynch/it/IntegrationTest.properties")
public class IntegrationTestConfiguration {

	@Bean
	public Path sourceDirectory() throws IOException {
		return Files.createTempDirectory("source", new FileAttribute<?>[] {});
	}

	@Bean
	public Path targetDirectory() throws IOException {
		return Files.createTempDirectory("target", new FileAttribute<?>[] {});
	}

	@Bean
	public FileSystemEventFactory fileSystemEventFactory() {
		return new FileSystemEventFactory();
	}

	@Bean
	public List<EventProcessor<FileSystemEvent>> eventProcessors() throws IOException {
		final List<EventProcessor<FileSystemEvent>> eventProcessors = new ArrayList<>();
		eventProcessors.add(new FileSystemEventLogger());
		return eventProcessors;
	}

	@Bean
	public EventConsumer fileSystemEventConsumer() {
		return new FileSystemEventConsumer();
	}

	@Bean
	public WatchService watchService() throws IOException {
		return FileSystems.getDefault().newWatchService();
	}

	@Bean
	public WatchKeyPathRegistry defaultWatchKeyPathRegistry() {
		return new DefaultWatchKeyPathRegistry();
	}

	@Bean
	public EventRegistry<FileSystemEvent> fileSystemEventRegistry() {
		return new FileSystemEventRegistry();
	}

	@Bean
	public DirectoryWatcher recursiveDirectoryWatcher() {
		return new RecursiveDirectoryWatcher();
	}

	@Bean
	public DirectoryWatchExecutor singleThreadDirectoryWatchExecutor() {
		return new SingleThreadDirectoryWatchExecutor();
	}

}

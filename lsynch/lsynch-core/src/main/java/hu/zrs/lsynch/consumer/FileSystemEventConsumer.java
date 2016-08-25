package hu.zrs.lsynch.consumer;


import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;

import hu.zrs.lsynch.api.consumer.EventConsumer;
import hu.zrs.lsynch.api.consumer.processor.EventProcessor;
import hu.zrs.lsynch.api.event.DelayedEvent;
import hu.zrs.lsynch.api.registry.EventRegistry;

public class FileSystemEventConsumer implements EventConsumer {

	@Autowired
	private List<EventProcessor<Path, Kind<Path>>> eventProcessors;

	private final ExecutorService consumerExecutor = Executors.newSingleThreadExecutor();

	@Autowired
	private EventRegistry<DelayedEvent<Path, Kind<Path>>> eventRegistry;

	@Override
	public void startConsuming() {
		consumerExecutor.submit(() -> {
			DelayedEvent<Path, Kind<Path>> event = eventRegistry.take();
			for (final EventProcessor<Path, Kind<Path>> eventProcessor : eventProcessors) {
				event = eventProcessor.process(event);
			}
		});

	}

	@Override
	public void stopConsuming() {
		consumerExecutor.shutdown();
	}

}

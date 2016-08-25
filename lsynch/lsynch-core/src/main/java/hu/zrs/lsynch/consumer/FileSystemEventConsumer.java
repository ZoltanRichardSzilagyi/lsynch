package hu.zrs.lsynch.consumer;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;

import hu.zrs.lsynch.api.consumer.EventConsumer;
import hu.zrs.lsynch.api.consumer.processor.EventProcessor;
import hu.zrs.lsynch.api.event.FileSystemEvent;
import hu.zrs.lsynch.api.registry.EventRegistry;

public class FileSystemEventConsumer implements EventConsumer {

	@Autowired
	private List<EventProcessor<FileSystemEvent>> eventProcessors;

	private final ExecutorService consumerExecutor = Executors.newSingleThreadExecutor();

	@Autowired
	private EventRegistry<FileSystemEvent> eventRegistry;

	@Override
	public void startConsuming() {
		consumerExecutor.submit(() -> {
			FileSystemEvent event = eventRegistry.take();
			for (final EventProcessor<FileSystemEvent> eventProcessor : eventProcessors) {
				event = eventProcessor.process(event);
			}
		});

	}

	@Override
	public void stopConsuming() {
		consumerExecutor.shutdown();
	}

}

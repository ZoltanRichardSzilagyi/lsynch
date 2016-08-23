package hu.zrs.lsynch.api.consumer.processor;

import hu.zrs.lsynch.api.event.DelayedEvent;

public interface EventProcessor<S, T> {

	DelayedEvent<S, T> process(DelayedEvent<S, T> event);

}

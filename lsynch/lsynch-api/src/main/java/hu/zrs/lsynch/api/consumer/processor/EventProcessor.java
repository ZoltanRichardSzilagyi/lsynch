package hu.zrs.lsynch.api.consumer.processor;

import hu.zrs.lsynch.api.event.Event;

public interface EventProcessor<E extends Event<?, ?>> {

	E process(E event);

}

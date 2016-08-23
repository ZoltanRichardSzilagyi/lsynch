package hu.zrs.lsynch.api.factory;

import hu.zrs.lsynch.api.event.DelayedEvent;

public interface DelayedEventFactory<S, T> {

	DelayedEvent<S, T> createEvent(S source, T eventType);

}

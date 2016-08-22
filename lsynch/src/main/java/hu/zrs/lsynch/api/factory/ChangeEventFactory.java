package hu.zrs.lsynch.api.factory;

import hu.zrs.lsynch.api.event.ChangeEvent;

public interface ChangeEventFactory<S, T> {

	ChangeEvent<S, T> createEvent(S source, T eventType);

}

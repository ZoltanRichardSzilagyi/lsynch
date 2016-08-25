package hu.zrs.lsynch.api.factory;

public interface DelayedEventFactory<E, S, T> {

	E createEvent(S source, T eventType);

}

package hu.zrs.lsynch.api.event;

public interface Event<S, T> {

	public S getSource();

	public T getEventType();

}

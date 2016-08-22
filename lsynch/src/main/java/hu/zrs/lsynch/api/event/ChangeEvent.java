package hu.zrs.lsynch.api.event;

import java.util.concurrent.Delayed;

public interface ChangeEvent<S, T> extends Delayed {

	public S getSource();

	public T getEventType();

}

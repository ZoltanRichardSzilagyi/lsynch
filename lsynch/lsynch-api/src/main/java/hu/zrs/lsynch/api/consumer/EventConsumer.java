package hu.zrs.lsynch.api.consumer;

public interface EventConsumer<E> {

	void startConsuming();

	void stopConsuming();

}

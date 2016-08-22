package hu.zrs.lsynch.api.registry;

public interface EventRegistry<E> {

	void registerEvent(E event);

	E take();

}

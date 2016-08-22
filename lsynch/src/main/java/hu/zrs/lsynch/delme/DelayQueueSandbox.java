package hu.zrs.lsynch.delme;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueSandbox {

	private final DelayQueue<User> queue = new DelayQueue<>();

	public static void main(String[] args) throws Exception {
		new DelayQueueSandbox().test();
	}

	public void test() throws InterruptedException {
		final User user = new User();
		queue.add(user);
		final long startTime = System.currentTimeMillis();
		final User u = queue.take();
		System.out.println(System.currentTimeMillis() - startTime);
		System.out.println(u.toString());

	}

	class User implements Delayed {

		private final Long timeOfExecution = System.nanoTime() + TimeUnit.SECONDS.toNanos(5);

		@Override
		public int compareTo(Delayed other) {
			if (other == this) {
				return 0;
			}
			final long diff = getDelay(NANOSECONDS) - other.getDelay(NANOSECONDS);
			return diff < 0 ? -1 : diff > 0 ? 1 : 0;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return unit.convert(timeOfExecution - System.nanoTime(), TimeUnit.NANOSECONDS);
		}

	}

}

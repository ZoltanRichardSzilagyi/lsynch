package hu.zrs.lsynch.it;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import hu.zrs.lsynch.api.consumer.EventConsumer;
import hu.zrs.lsynch.api.watcher.DirectoryWatcher;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = IntegrationTestConfiguration.class)
public class IntegrationTest {

	private static final Logger logger = LoggerFactory.getLogger(IntegrationTest.class);

	@Autowired
	private Path sourceDirectory;

	@Autowired
	private Path targetDirectory;

	@Autowired
	private DirectoryWatcher directoryWatcher;

	@Autowired
	private EventConsumer fileSystemEventConsumer;

	@Before
	public void setup() {
		logger.info("Start watcher");
		directoryWatcher.startWatching();
		fileSystemEventConsumer.startConsuming();
	}

	@After
	public void cleanup() {
		logger.info("Stop watcher");
		directoryWatcher.stopWatching();
		fileSystemEventConsumer.stopConsuming();
		sourceDirectory.toFile().delete();
		targetDirectory.toFile().delete();
	}

	@Test(timeout = 50000)
	public void shouldSynchronizeOneNewFile() throws IOException {
		directoryWatcher.addToWatch(sourceDirectory);
		final Path testFile = Paths.get(sourceDirectory.toString(), "test.txt");
		testFile.toFile().createNewFile();

		final Path targetFile = Paths.get(targetDirectory.toString(), "test.txt");
		while (!targetFile.toFile().exists()) {

			if (targetFile.toFile().exists()) {
				logger.info(sourceDirectory.toString());
				logger.info(targetDirectory.toString());
				logger.info(targetFile.toFile().getAbsolutePath());
				logger.info("OK");
			}
		}

	}


}

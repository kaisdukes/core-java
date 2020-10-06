package core;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SemaphoreMaxLoginsExampleTest {

    public static class CapacityChecker {
        private int maxCapacity = Integer.MIN_VALUE;

        public synchronized void updateMaxCapacity(final int availableCapacity) {
            if (availableCapacity > maxCapacity) {
                maxCapacity = availableCapacity;
            }
        }

        public synchronized int getMaxCapacity() {
            return maxCapacity;
        }
    }

    @Test
    public void shouldCapNumberOfLogins() throws Exception {

        final ExecutorService threadPool = Executors.newFixedThreadPool(20);
        final int maxLogins = 5;
        final SemaphoreMaxLoginsExample loginAuthenticator = new SemaphoreMaxLoginsExample(maxLogins);

        final List<Future<Void>> tasks = new ArrayList<>();
        final Random random = new Random();
        final CapacityChecker capacityChecker = new CapacityChecker();

        for (int i = 0; i < 100; i++) {
            final Future<Void> task = threadPool.submit(
                    () -> {
                        Thread.sleep(random.nextInt(20));
                        capacityChecker.updateMaxCapacity(loginAuthenticator.availableCapacity());
                        if (loginAuthenticator.login()) {
                            Thread.sleep(random.nextInt(20));
                            loginAuthenticator.logout();
                        }
                        return null;
                    });
            tasks.add(task);
        }

        for (final Future<Void> task : tasks) {
            task.get();
        }

        threadPool.shutdown();
        threadPool.awaitTermination(30, TimeUnit.SECONDS);

        assertThat(capacityChecker.getMaxCapacity(), is(equalTo(maxLogins)));
    }
}
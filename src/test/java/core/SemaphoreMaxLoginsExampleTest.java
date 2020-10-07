package core;

import core.testing.SimpleThreadPool;
import org.junit.Test;

import java.util.Random;

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

        final SimpleThreadPool threadPool = new SimpleThreadPool();
        final int maxLogins = 5;
        final SemaphoreMaxLoginsExample loginAuthenticator = new SemaphoreMaxLoginsExample(maxLogins);

        final Random random = new Random();
        final CapacityChecker capacityChecker = new CapacityChecker();

        for (int i = 0; i < 100; i++) {
            threadPool.submit(
                    () -> {
                        Thread.sleep(random.nextInt(20));
                        capacityChecker.updateMaxCapacity(loginAuthenticator.availableCapacity());
                        if (loginAuthenticator.login()) {
                            Thread.sleep(random.nextInt(20));
                            loginAuthenticator.logout();
                        }
                    });
        }

        threadPool.executeAllTasks();
        threadPool.close();

        assertThat(capacityChecker.getMaxCapacity(), is(equalTo(maxLogins)));
    }
}
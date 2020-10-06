package core;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class VolatileLastUserExampleTest {

    @Test
    public void shouldGetLastValidUsername() throws Exception {

        final ExecutorService threadPool = Executors.newFixedThreadPool(20);

        final VolatileLastUserExample passwordChecker = new VolatileLastUserExample();
        final List<Future<Boolean>> tasks = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            final int userId = i;
            final Future<Boolean> task = threadPool.submit(
                    () -> passwordChecker.authenticate(
                            "user" + userId,
                            "password"));
            tasks.add(task);
        }

        for (final Future<Boolean> task : tasks) {
            assertThat(task.get(), is(true));
        }

        threadPool.shutdown();
        threadPool.awaitTermination(30, TimeUnit.SECONDS);

        assertThat(passwordChecker.getLastValidUsername().startsWith("user"), is(true));
    }
}
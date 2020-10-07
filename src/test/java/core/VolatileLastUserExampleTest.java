package core;

import core.testing.SimpleThreadPool;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class VolatileLastUserExampleTest {

    @Test
    public void shouldGetLastValidUsername() throws Exception {

        final SimpleThreadPool threadPool = new SimpleThreadPool();
        final VolatileLastUserExample passwordChecker = new VolatileLastUserExample();

        for (int i = 0; i < 1000; i++) {
            final int userId = i;
            threadPool.submit(
                    () -> passwordChecker.authenticate(
                            "user" + userId,
                            "password"));
        }

        threadPool.executeAllTasks();
        threadPool.close();

        assertThat(passwordChecker.getLastValidUsername().startsWith("user"), is(true));
    }
}
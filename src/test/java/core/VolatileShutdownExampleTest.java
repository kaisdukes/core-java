package core;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class VolatileShutdownExampleTest {

    @Test
    public void shouldShutdownUsingVolatile() {

        final VolatileShutdownExample thread = new VolatileShutdownExample();
        assertThat(thread.completed(), is(false));
        thread.start();
        thread.shutdown();
    }
}
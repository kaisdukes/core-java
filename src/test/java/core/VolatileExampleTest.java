package core;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class VolatileExampleTest {

    @Test
    public void shouldShutdownUsingVolatile() {

        final VolatileExample thread = new VolatileExample();
        assertThat(thread.completed(), is(false));
        thread.start();
        thread.shutdown();
    }
}
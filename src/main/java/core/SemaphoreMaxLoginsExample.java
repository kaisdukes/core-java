package core;

import java.util.concurrent.Semaphore;

public class SemaphoreMaxLoginsExample {
    private final Semaphore semaphore;

    public SemaphoreMaxLoginsExample(final int maxLogins) {
        semaphore = new Semaphore(maxLogins);
    }

    public boolean login() {
        return semaphore.tryAcquire();
    }

    public void logout() {
        semaphore.release();
    }

    public int availableCapacity() {
        return semaphore.availablePermits();
    }
}
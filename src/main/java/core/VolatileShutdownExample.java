package core;

public class VolatileShutdownExample extends Thread {

    // The volatile keyword indicates that the value of a field is not cached
    // locally and is always read from main memory. This guarantees that any
    // thread that reads the field will see the most recently written value.
    // Volatile variables also prevent instruction reordering (a normal
    // performance enhancement technique).
    //
    // - Volatile guarantees visibility but not atomicity.
    // - Synchronization/locking guarantees visibility and can guarantee atomicity.
    //
    // The volatile keyword should only be used when updating a reference and
    // not performing some additional operation on that variable, e.g.
    // incrementing a variable.
    //
    // A synchronized block can be used in place of volatile, but the reverse
    // is not always true.

    private volatile boolean done;

    @Override
    public void run() {
        while (!done) {
            Thread.yield();
        }

        System.out.println("Done!");
    }

    public void shutdown() {
        done = true;
    }

    public boolean completed() {
        return done;
    }
}
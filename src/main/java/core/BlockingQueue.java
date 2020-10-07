package core;

import java.util.LinkedList;

// Example implementation of a blocking queue.
//
// We need to use notifyAll() instead of notify() here, to avoid a deadlock
// for multiple producers/consumers.
//
// Details here:
// https://stackoverflow.com/questions/13774802/notify-instead-of-notifyall-for-blocking-queue

public class BlockingQueue<T> {

    private final LinkedList<T> list = new LinkedList<>();
    private final int limit;

    public BlockingQueue(final int limit) {
        this.limit = limit;
    }

    public void enqueue(final T item) throws InterruptedException {
        synchronized (list) {
            while (list.size() == limit) {
                list.wait();
            }
            if (list.isEmpty()) {
                list.notifyAll();
            }
            list.add(item);
        }
    }

    public T dequeue() throws InterruptedException {
        synchronized (list) {
            while (list.size() == 0) {
                list.wait();
            }
            if (list.size() == limit) {
                list.notifyAll();
            }
            return list.remove(0);
        }
    }
}
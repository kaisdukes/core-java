package core.testing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class SimpleThreadPool {

    private final ExecutorService executorService = Executors.newFixedThreadPool(20);
    private final List<Future<?>> tasks = new ArrayList<>();

    public void submit(final SimpleTask task) {
        tasks.add(executorService.submit(() -> {
            task.execute();
            return null;
        }));
    }

    public void executeAllTasks() throws ExecutionException, InterruptedException {
        for (final Future<?> task : tasks) {
            task.get();
        }
    }

    public void close() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(30, TimeUnit.SECONDS);
    }
}
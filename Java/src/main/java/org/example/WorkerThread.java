package org.example;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

// Реализуйте WorkerThread. Он должен позволять выполнять в себе задачи последовательно,
// а когда задач нет, поток не должен потреблять процессорное время.
public class WorkerThread extends Thread {

    private final Queue<Runnable> queue = new LinkedBlockingQueue<>();
    private final AtomicBoolean isStopped = new AtomicBoolean(false);

    @Override
    public void run() {
        while (!isStopped.get()) {
            synchronized (queue) {
                Runnable task = queue.poll();
                if (task != null) {
                    task.run();
                } else {
                    try {
                        queue.wait();
                    } catch (InterruptedException ignored) {
                        if (isStopped.get()) {
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void interrupt() {
        isStopped.set(true);
        super.interrupt();
    }

    void execute(Runnable task) {
        synchronized (queue) {
            queue.add(task);
            queue.notify();
        }
    }
}

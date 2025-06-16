package task_3;

import java.util.ArrayList;
import java.util.LinkedList;

public class ThreadPool {

    private final LinkedList<Runnable> tasks = new LinkedList<>();
    private final ArrayList<CustomThread> threads = new ArrayList<>();
    private volatile boolean isShutdown = false;

    public ThreadPool(int countThread) {
        for (int i = 0; i < countThread; i++) {
            CustomThread thread = new CustomThread(tasks, this);
            threads.add(thread);
            thread.start();
        }
    }

    public void execute(Runnable runnable){
        if (runnable == null) {
            throw new NullPointerException("Задача не должна быть null");
        }

        synchronized (tasks) {
            if (isShutdown) {
                throw new IllegalStateException("ThreadPool закрыт, задачи больше не принимаются");
            }
            tasks.addLast(runnable);
            tasks.notifyAll();
        }
    }

    public void shutdown() throws InterruptedException {
        synchronized (tasks) {
            isShutdown = true;
            tasks.notifyAll();
        }
        awaitTermination();
    }

    public boolean isShutdown() {
        synchronized (tasks) {
            return isShutdown;
        }
    }

    private void awaitTermination() throws InterruptedException{
        for (CustomThread thread : threads) {
            thread.join();
        }
    }
}

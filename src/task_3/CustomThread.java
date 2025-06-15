package task_3;

import java.util.LinkedList;

public class CustomThread extends Thread{
    private final LinkedList<Runnable> tasks;
    private final ThreadPool pool;

    public CustomThread(LinkedList<Runnable> tasks, ThreadPool threadPool) {
        this.tasks = tasks;
        this.pool = threadPool;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Runnable task = getTask();
                runTask(task);
            }
        } catch (InterruptedException e) {
            System.out.println("Поток " + Thread.currentThread().getName() + " завершен");
        }
    }

    private Runnable getTask() throws InterruptedException {
        synchronized (tasks) {
            while (tasks.isEmpty()) {
                if (pool.isShutdown()) {
                    throw new InterruptedException();
                }
                tasks.wait();
            }
            return tasks.removeFirst();
        }
    }

    private void runTask(Runnable task) {
        try {
            task.run();
        } catch (RuntimeException e) {
            System.out.println("Ошибка при выполнении задачи:" + e.getMessage());
        }
    }
}

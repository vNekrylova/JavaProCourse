import task_1.ClassForTest;
import task_1.TestRunner;
import task_2.UsingStream;
import task_3.ThreadPool;

public class Main {
    public static void main(String[] args) throws Exception {
        //task-1
        //TestRunner.runTests(ClassForTest.class);

        //task-2
        //UsingStream.run();

        //task-3
        ThreadPool threadPool = new ThreadPool(4);

        for(int i = 0; i < 15; i++){
            int j = i;
            threadPool.execute(()->{
                System.out.println(Thread.currentThread().getName() + ": Задача " + j);
            });
        }
        threadPool.shutdown();

        threadPool.execute(()->{
            System.out.println(Thread.currentThread().getName() + ": " + "Новая задача");
        });
    }
}
package task_1;

import task_1.annotations.*;

public class ClassForTest {
    @Test(priority = 9)
    private void criticalTask(){
        System.out.println("Выполняется задача высокого приоритета");
    }
    @Test(priority = 2)
    public void lowTask(){
        System.out.println("Выполняется задача низкого приоритета");
    }
    @Test
    public void mediumTask(){
        System.out.println("Выполняется задача среднего приоритета");
    }
    @BeforeSuite
    public static void startWork(){
        System.out.println("Начало работы");
    }
    @AfterSuite
    public static void endWork(){
        System.out.println("Окончание работы");
    }
    @BeforeTest
    private static void startTest() {
        System.out.println("Подготовка к новой задаче");
    }
    @AfterTest
    private static void endTest1() {
        System.out.println("Проверка результатов работы по задаче");
    }
    @AfterTest
    private static void endTest2() {
        System.out.println("Подведение итогов по задаче");
    }
    @CsvSource(value = "10, Java, 20, true")
    public void testMethod1(int a, String b, int c, boolean d) {
        System.out.println("Результаты работы: " + a + ", " + b + ", " + c + ", " + d);
    }
    @CsvSource(value = "101, JavaPro, 202, false, 2.1")
    public void testMethod2(int a, String b, int c, boolean d, double e) {
        System.out.println("Результаты работы: " + a + ", " + b + ", " + c + ", " + d + ", " + e);
    }
}

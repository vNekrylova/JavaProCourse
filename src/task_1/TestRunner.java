package task_1;

import task_1.annotations.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;


public class TestRunner {

    public static void runTests(Class<?> someClass) throws Exception {
        System.out.println("Запуск тестирования: " + someClass.getName());
        System.out.println("_________________________________________________________");

        Method[] methods = someClass.getDeclaredMethods();

        Method beforeSuite = null;
        Method afterSuite = null;
        ArrayList<Method> beforeTest = new ArrayList<>();
        ArrayList<Method> afterTest = new ArrayList<>();

        //Основные проверки и заполнение переменных
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                if (!isStaticMethod(method)) throw new RuntimeException("Метод с аннотацией task_1.annotation.BeforeSuite должен быть статическим");
                if (beforeSuite == null) beforeSuite = method;
                else throw new RuntimeException("Методов с аннотацией task_1.annotation.BeforeSuite не может быть больше 1");
            }
            if (method.isAnnotationPresent(AfterSuite.class)) {
                if (!isStaticMethod(method)) throw new RuntimeException("Метод с аннотацией task_1.annotation.AfterSuite должен быть статическим");
                if (afterSuite == null) afterSuite = method;
                else throw new RuntimeException("Методов с аннотацией task_1.annotation.AfterSuite не может быть больше 1");
            }
            if (method.isAnnotationPresent(Test.class)) {
                if (isStaticMethod(method)) throw new RuntimeException("Метод с аннотацией task_1.annotation.Test НЕ должен быть статическим");
                if (!validPriorityAnnotation(method.getAnnotation(Test.class).priority())) throw new RuntimeException("Приоритет аннотации task_1.annotation.Test должен быть в пределах от 1 до 10 включительно");
            }
            if (method.isAnnotationPresent(BeforeTest.class)) {
                if (!isStaticMethod(method)) throw new RuntimeException("Метод с аннотацией task_1.annotation.BeforeTest должен быть статическим");
                beforeTest.add(method);
            }
            if (method.isAnnotationPresent(AfterTest.class)) {
                if (!isStaticMethod(method)) throw new RuntimeException("Метод с аннотацией task_1.annotation.AfterTest должен быть статическим");
                afterTest.add(method);
            }
        }

        //Создание объекта
        Constructor<?> constructor = someClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object someClassObj = constructor.newInstance();

        //Запуск методов
        if (beforeSuite != null) startMethod(beforeSuite, null, null);

        Arrays.stream(methods).filter(m -> m.isAnnotationPresent(Test.class))
                //.sorted(Comparator.comparingInt(m -> m.getAnnotation(task_1.annotation.Test.class).priority())) //если больший приоритет 1
                 .sorted((m1, m2) -> (m2.getAnnotation(Test.class).priority() - m1.getAnnotation(Test.class).priority())) //если больший приоритет 10
                .forEach(m -> {
                    System.out.println("________");
                    for (Method before : beforeTest) {startMethod(before, null, null);}
                    startMethod(m, someClassObj, null);
                    for (Method after : afterTest) {startMethod(after, null, null);}
                    System.out.println("________");
                });

        if (afterSuite != null) startMethod(afterSuite, null, null);

        Arrays.stream(methods).filter(m -> m.isAnnotationPresent(CsvSource.class))
                .forEach(m -> {
                    String[] parts = m.getAnnotation(CsvSource.class).value().split(",");
                    startMethod(m, someClassObj, parseMass(parts, m.getParameterTypes()));
                });
    }

    private static void startMethod (Method method, Object obj, Object[] arg)  {
        try {
            method.setAccessible(true);

            if (arg == null || arg.length == 0) {
                method.invoke(obj);
            } else {
                method.invoke(obj, arg);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean isStaticMethod (Method method) {
        return Modifier.isStatic(method.getModifiers());
    }

    private static boolean validPriorityAnnotation (int priority) {
        return priority > 1 && priority <= 10;
    }

    private static Object[] parseMass (String[] parts, Class<?>[] classType) {
        Object[] objects = new Object[classType.length];
        for (int i = 0; i < classType.length; i++) {
            String part = parts[i].trim();

            if (classType[i] == int.class || classType[i] == Integer.class) objects[i] = Integer.parseInt(part);
            if (classType[i] == boolean.class || classType[i] == Boolean.class) objects[i] = Boolean.parseBoolean(part);
            if (classType[i] == double.class || classType[i] == Double.class) objects[i] = Double.parseDouble(part);
            if (classType[i] == float.class || classType[i] == Float.class) objects[i] = Float.parseFloat(part);
            if (classType[i] == String.class) objects[i] = part;
        }
        return objects;
    }
}

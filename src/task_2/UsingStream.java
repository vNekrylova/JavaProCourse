package task_2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UsingStream {

    public static void run() {
        List<Integer> numbers = List.of(5, 2, 9, 10, 4, 3, 10, 1, 13);
        List<String> words = List.of("найдите", "в", "списке", "слов", "самое", "длинное");
        List<String> lines = List.of("один два три четыре пять",
                                    "шесть семь восемь девять десять",
                                    "одиннадцать двенадцать тринадцать четырнадцать пятнадцать"
        );
        List<Employees> employees = List.of(new Employees("Иван", 50, Employees.Position.DIRECTOR),
                                            new Employees("Петр", 35, Employees.Position.MANAGER),
                                            new Employees("Василий", 43, Employees.Position.ENGINEER),
                                            new Employees("Федор", 33, Employees.Position.ENGINEER),
                                            new Employees("Анатолий", 39, Employees.Position.ENGINEER),
                                            new Employees("Витас", 40, Employees.Position.ENGINEER));

        String str = "имеется строка с набором слов в нижнем регистре";


        //Найдите в списке целых чисел 3-е наибольшее число
        numbers.stream().sorted(Comparator.reverseOrder()).skip(2).findFirst().ifPresent(System.out::println);

        System.out.println("________________");


        //Найдите в списке целых чисел 3-е наибольшее «уникальное» число
        numbers.stream().distinct().sorted(Comparator.reverseOrder()).skip(2).findFirst().ifPresent(System.out::println);

        System.out.println("________________");


        //Имеется список объектов типа Сотрудник (имя, возраст, должность)
        //Необходимо получить список имен 3 самых старших сотрудников с должностью «Инженер», в порядке убывания возраста
        employees.stream().filter(empl -> empl.getPosition() == Employees.Position.ENGINEER)
                .sorted(Comparator.comparingInt(Employees::getAge).reversed()).limit(3)
                .map(Employees::getName).forEach(System.out::println);

        System.out.println("________________");


        //Имеется список объектов типа Сотрудник (имя, возраст, должность)
        //Посчитайте средний возраст сотрудников с должностью «Инженер»
        employees.stream().filter(empl -> empl.getPosition() == Employees.Position.ENGINEER)
                .mapToInt(Employees::getAge).average().ifPresent(System.out::println);

        System.out.println("________________");


        //Найдите в списке слов самое длинное
        words.stream().max(Comparator.comparingInt(String::length)).ifPresent(System.out::println);

        System.out.println("________________");


        //Имеется строка с набором слов в нижнем регистре, разделенных пробелом
        //Постройте хеш-мапы, в которой будут хранится пары: слово - сколько раз оно встречается во входной строке
        Arrays.stream(str.split(" ")).collect(Collectors.groupingBy(s->s, Collectors.counting()))
                .forEach((k, v) -> System.out.println(k + ": " + v));

        System.out.println("________________");


        //Отпечатайте в консоль строки из списка в порядке увеличения длины слова,
        // если слова имеют одинаковую длины, то должен быть сохранен алфавитный порядок
        words.stream().sorted(Comparator.comparingInt(String::length)
                .thenComparing(Comparator.naturalOrder())).forEach(System.out::println);

        System.out.println("________________");


        //Имеется массив строк, в каждой из которых лежит набор из 5 слов, разделенных пробелом
        //Найдите среди всех слов самое длинное, если таких слов несколько, получите любое из них
        lines.stream().flatMap(line->Arrays.stream(line.split(" ")))
                .max(Comparator.comparingInt(String::length)).ifPresent(System.out::println);
    }
}

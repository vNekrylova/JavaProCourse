package task_2;

public class Employees {
    enum Position{
        ENGINEER, DIRECTOR, MANAGER
    }
    private final String name;
    private final int age;
    private final Position position;

    public Employees(String name, int age, Position position) {
        this.name = name;
        this.age = age;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Position getPosition() {
        return position;
    }
}

// Создаём новый класс Pet на основе класса Animal
public class Pet extends Animal {
    public Pet(String name, String birthDate, String[] commands) {
        this.name = name;
        this.birthDate = birthDate;
        this.commands = commands;
    }
}

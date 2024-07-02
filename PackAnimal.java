// Создаём новый класс PackAnimal на основе класса Animal
public class PackAnimal extends Animal {
    public PackAnimal(String name, String birthDate, String[] commands) {
        this.name = name;
        this.birthDate = birthDate;
        this.commands = commands;
    }
}
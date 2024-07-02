import java.util.HashSet;
import java.util.Set;

// Создаём новый класс Animal
public class Animal {
    protected int number;
    protected String name;
    protected String birthDate;
    protected String[] commands;
    // Требуется для подсчёта животных
    private static int count = 0;

    public Animal() {
        this.number = ++count;
        // Для проверки повторяющихся комманд
        commands = new HashSet<>().toArray(new String[0]);
    }
    // Записывает геттеры необходимые для выполнения программы
    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String[] getCommands() {
        return commands;
    }

    public void trainCommands(String[] newCommands) {
        this.commands = newCommands;
    }
}

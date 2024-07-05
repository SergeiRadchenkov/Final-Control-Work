import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AnimalRegistry {
    // Создаём массив для наполнения его списками животных
    private static List<Animal> animals = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Запуск меню
        while (true) {
            System.out.println("Меню приложения:");
            System.out.println("1. Вывести список животных");
            System.out.println("2. Добавьте новое животное");
            System.out.println("3. Вывести список команд животного");
            System.out.println("4. Обучение новым командам животного");
            System.out.println("5. Вывести общее количество животных");
            System.out.println("6. Выйти");
            System.out.print("Выберите пункт меню: ");
            // Приём и проверка ввода значения пользователем
            int choice = getValidMenuChoice(scanner);
            switch (choice) {
                case 1:
                    listAnimals();
                    break;
                case 2:
                    try (Counter counter = new Counter()) {
                        addAnimal(scanner);
                        counter.add();
                    } catch (Exception e) {
                        System.out.println("Ошибка при добавлении животного: " + e.getMessage());
                    }
                    break;
                case 3:
                    listAnimalCommands(scanner);
                    break;
                case 4:
                    trainNewCommands(scanner);
                    break;
                case 5:
                    System.out.println("Общее количество животных: " + Counter.getCount());
                    System.out.print("Нажмите любую клавишу для продолжения: ");
                    scanner.nextLine();
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.out.println("Неверный выбор. Введите номер меню от 1 до 6.");
                    System.out.print("Нажмите любую клавишу для продолжения: ");
                    scanner.nextLine();
            }
        }
    }
    // Функция выводит список животных, при этом проверяет наличие списка
    private static void listAnimals() {
        Scanner scanner = new Scanner(System.in);
        if (animals.isEmpty()) {
            System.out.println("Животных нет.");
            System.out.print("Нажмите любую клавишу для продолжения: ");
            scanner.nextLine();
            return;
        }
        for (Animal animal: animals) {
            System.out.println("Номер: " + animal.getNumber() + ", Имя: " + animal.getName() + ", Дата рождения: " +
                    animal.birthDate + ", Комманды: " + String.join(", ", animal.getCommands()));
        }
        System.out.print("Нажмите любую клавишу для продолжения: ");
        scanner.nextLine();
    }
    // Функция добавляет новое животное в список
    private static void addAnimal(Scanner scanner) {
        System.out.println("Введите имя животного: ");
        String name;
        while (true) {
            name = scanner.nextLine();
            if (!name.trim().isEmpty()) { // Проверяем, что имя не пустое после удаления пробелов
                break;
            } else {
                System.out.print("Имя животного не может быть пустым. Пожалуйста, введите имя заново: ");
            }
        }
        System.out.println("Введите дату рождения (YYYY-MM-DD): ");
        String birthDate;
        while (true) {
            birthDate = scanner.nextLine();
            if (isValidDate(birthDate)) {  // Проверяем, что дата рождения соответствует формату YYYY-MM-DD
                break;
            } else {
                System.out.print("Неверный формат даты. Пожалуйста, введите дату в формате YYYY-MM-DD: ");
            }
        }
        // Меню выбора типа животного
        System.out.println("Введите тип животного: ");
        System.out.println("1. Dog");
        System.out.println("2. Cat");
        System.out.println("3. Hamster");
        System.out.println("4. Horse");
        System.out.println("5. Camel");
        System.out.println("6. Donkey");
        int typeChoice = getValidAnimalType(scanner); // Проверяем, что введённое значение это число от 1 до 6

        String type;
        switch (typeChoice) {
            case 1:
                type = "Dog";
                break;
            case 2:
                type = "Cat";
                break;
            case 3:
                type = "Hamster";
                break;
            case 4:
                type = "Horse";
                break;
            case 5:
                type = "Camel";
                break;
            case 6:
                type = "Donkey";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + typeChoice);
        }
        System.out.println("Введите команды животного через запятую: ");
        String[] commands = scanner.nextLine().split(", ");
        // Добавляем животное благодаря полученному значению к класу Pets или PackAnimal
        Animal animal;
        if (type.equals("Dog") || type.equals("Cat") || type.equals("Hamster")) {
            animal = new Pet(name, birthDate, commands);
        } else {
            animal = new PackAnimal(name, birthDate, commands);
        }

        animals.add(animal);
        System.out.println("Животное добавлено.");
        System.out.print("Нажмите любую клавишу для продолжения: ");
        scanner.nextLine();
    }
    // Функция выводит список комманд животного, можно ввести номер животного или имя
    private static void listAnimalCommands(Scanner scanner) {
        System.out.println("Введите номер или имя животного: ");
        String input = scanner.nextLine().trim();
        try {
            int number = Integer.parseInt(input);
            for (Animal animal : animals) {
                if (animal.getNumber() == number) {
                    System.out.println("Комманды: " + String.join(", ", animal.getCommands()));
                    System.out.print("Нажмите любую клавишу для продолжения: ");
                    scanner.nextLine();
                    return;
                }
            }
        } catch (NumberFormatException e) {
            for (Animal animal : animals) {
                if (animal.getName().equals(input)) {
                    System.out.println("Комманды: " + String.join(", ", animal.getCommands()));
                    return;
                }
            }
        }
        System.out.println("Животное не найдено");
        System.out.print("Нажмите любую клавишу для продолжения: ");
        scanner.nextLine();
    }
    // Функция учит новым коммандам животного, можно ввести номер животного или имя
    private static void trainNewCommands(Scanner scanner) {
        System.out.println("Введите номер или имя животного: ");
        String input = scanner.nextLine();
        Animal animal = findAnimalByNumberOrName(input);
        if (animal == null) {
            System.out.println("Животное не найдено");
            return;
        }

        System.out.println("Введите новые команды животного через запятую: ");
        String[] newCommands = scanner.nextLine().split(", ");
        // Проверяем на наличие введённой команды
        Set<String> currentCommands = new HashSet<>(List.of(animal.getCommands()));
        for (String command : newCommands) {
            currentCommands.add(command);
        }

        animal.trainCommands(currentCommands.toArray(new String[0]));
        System.out.println("Животное обучено новым командам");
        System.out.print("Нажмите любую клавишу для продолжения: ");
        scanner.nextLine();
    }
    // Функция проверяет формат введённой даты рождения
    private static boolean isValidDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    // Функция проверяет введённый номер пункта меню на соответствие значению от 1 до 6
    private static int getValidMenuChoice(Scanner scanner) {
        int choice;
        while (true) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= 1 && choice <= 6) {
                    break;
                }
            } else {
                scanner.nextLine();
            }
            System.out.print("Неверный выбор. Введите номер меню от 1 до 6: ");
        }
        return choice;
    }
    // Функция проверяет введённый номер типа животного на соответствие значению от 1 до 6
    private static int getValidAnimalType(Scanner scanner) {
        int typeChoice;
        while (true) {
            if (scanner.hasNextInt()) {
                typeChoice = scanner.nextInt();
                scanner.nextLine();
                if (typeChoice >= 1 && typeChoice <= 6) {
                    break;
                }
            } else {
                scanner.nextLine();
            }
            System.out.print("Неверный выбор. Введите номер типа животного от 1 до 6: ");
        }
        return typeChoice;
    }
    // Функция находит животного по номеру или имени
    private static Animal findAnimalByNumberOrName(String input) {
        Animal animal = null;
        try {
            int number = Integer.parseInt(input);
            for (Animal a : animals) {
                if (a.getNumber() == number) {
                    animal = a;
                    break;
                }
            }
        } catch (NumberFormatException e) {
            // Если не удалось преобразовать в число, ищем по имени
            for (Animal a : animals) {
                if (a.getName().equalsIgnoreCase(input.trim())) {
                    animal = a;
                    break;
                }
            }
        }
        return animal;
    }
}


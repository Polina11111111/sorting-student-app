package com.example.sorting;

import com.example.sorting.collection.CustomArrayList;
import com.example.sorting.io.DataLoader;
import com.example.sorting.io.FileAppender;
import com.example.sorting.model.Student;
import com.example.sorting.strategy.*;
import com.example.sorting.threads.MultithreadCounter;

import java.util.Comparator;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        CustomArrayList<Student> students = new CustomArrayList<>();
        SortContext<Student> ctx = new SortContext<>();
        boolean evenOddMode = false;

        // Объявляем переменные заранее, чтобы использовать в нескольких switch case
        int n = 0;
        String path = "";

        while (true) {
            // Меню
            System.out.println("\n=== Student Sorting App ===");
            System.out.println("1. Заполнить случайными студентами");
            System.out.println("2. Открыть список студентов из файла");
            System.out.println("3. Ввести вручную");
            System.out.println("4. Сортировка");
            System.out.println("5. Включить/выключить режим сортировки чётных элементов "); //EvenOdd режим
            System.out.println("6. Сохранить список студентов в файл (добавить)");
            System.out.println("7. Проверить количество студентов по номеру зачетки");
            System.out.println("8. Показать список студентов");
            System.out.println("0. Выйти");
            System.out.print("Выберите действие: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "0":
                    System.out.println("Выйти из программы...");
                    sc.close();
                    return;

                case "1":
                    System.out.print("Введите количество студентов: ");
                    n = Integer.parseInt(sc.nextLine());
                    students = DataLoader.fromRandom(n);
                    System.out.println(n + " студентов сгенерированы случайно.");
                    break;

                case "2":
                    System.out.print("Введите путь к файлу: ");
                    path = sc.nextLine();
                    students = DataLoader.fromFile(path);
                    System.out.println("Студенты загружены из файла.");
                    break;

                case "3":
                    System.out.print("Введите количество студентов: ");
                    n = Integer.parseInt(sc.nextLine());
                    students = DataLoader.manualFill(n, sc);
                    System.out.println("Студенты введены вручную.");
                    break;

                case "4":
                    System.out.println("Выбрать сортировку: 1.Простая: для небольших списков (до 50 элементов) 2.Быстрая: для средних списков ((50–10 000 элементов) 3.Супербыстрая: для больших списков (более 10 000)");
                    String alg = sc.nextLine();
                    SortStrategy<Student> strat = switch (alg) {
                        case "1" -> new BubbleSortStrategy<>();
                        case "2" -> new MergeSortStrategy<>();
                        case "3" -> new QuickSortStrategy<>();
                        default -> new BubbleSortStrategy<>();
                    };

                    if (evenOddMode)
                        strat = new EvenOddDecoratorStrategy<>(strat, Student::getRecordBookNumber);

                    ctx.setStrategy(strat);

                    System.out.println("Выберите поле для сортировки: 1. Группа  2. Средний балл  3. Номер зачетки");
                    String field = sc.nextLine();
                    Comparator<Student> comp = switch (field) {
                        case "1" -> Comparator.comparing(Student::getGroupNumber);
                        case "2" -> Comparator.comparingDouble(Student::getGpa);
                        case "3" -> Comparator.comparingInt(Student::getRecordBookNumber);
                        default -> Comparator.comparing(Student::getGroupNumber);
                    };

                    ctx.sort(students, comp);
                    System.out.println("Студенты отсортированы!");
                    break;

                case "5":
                    evenOddMode = !evenOddMode;
                    System.out.println("Режим сортировки только чётных элементов: " + (evenOddMode ? "Включен" : "Выключен"));
                    break;

                case "6":
                    System.out.print("Введите путь к файлу для добавления студентов: ");
                    path = sc.nextLine();
                    FileAppender.appendStudents(path, students);
                    System.out.println("Студенты успешно добавлены в файл.");
                    break;

                case "7":
                    System.out.print("Введите номер зачетной книжки для подсчета: ");
                    int rec = Integer.parseInt(sc.nextLine());
                    MultithreadCounter<Student> counter = new MultithreadCounter<>(4);
                    int total = counter.countOccurrences(students,
                            new Student.Builder().setGroupNumber("G1").setGpa(0).setRecordBookNumber(rec).build());
                    System.out.println("Количество студентов с этим номером зачетки: " + total);
                    break;

                case "8":
                    System.out.println("=== Список студентов ===");
                    for (Student s : students) {
                        System.out.println(s);
                    }
                    break;

                default:
                    System.out.println("Неверный вариант. Попробуйте снова.");
            }
        }
    }
}
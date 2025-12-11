package com.example.sorting;


import com.example.sorting.collection.CustomArrayList;
import com.example.sorting.model.Student;
import com.example.sorting.strategy.*;
import com.example.sorting.io.FileAppender;
import com.example.sorting.threads.MultithreadCounter;

import java.util.Comparator;

public class TestStudentSorting {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Тестирование сортировок и функционала ===");

        // --- 1. Создание тестовой коллекции студентов ---
        CustomArrayList<Student> students = new CustomArrayList<>();
        students.add(new Student.Builder().setGroupNumber("G1").setGpa(4.5).setRecordBookNumber(101).build());
        students.add(new Student.Builder().setGroupNumber("G2").setGpa(3.7).setRecordBookNumber(102).build());
        students.add(new Student.Builder().setGroupNumber("G1").setGpa(4.0).setRecordBookNumber(103).build());
        students.add(new Student.Builder().setGroupNumber("G3").setGpa(4.8).setRecordBookNumber(104).build());

        System.out.println("\n--- Исходная коллекция ---");
        students.forEach(System.out::println);

        // --- 2. Тестируем BubbleSort ---
        SortContext<Student> ctx = new SortContext<>();
        ctx.setStrategy(new BubbleSortStrategy<>());
        ctx.sort(students, Comparator.comparingDouble(Student::getGpa));
        System.out.println("\n--- BubbleSort по GPA ---");
        students.forEach(System.out::println);

        // --- 3. Тестируем MergeSort ---
        ctx.setStrategy(new MergeSortStrategy<>());
        ctx.sort(students, Comparator.comparing(Student::getGroupNumber));
        System.out.println("\n--- MergeSort по группе ---");
        students.forEach(System.out::println);

        // --- 4. Тестируем QuickSort ---
        ctx.setStrategy(new QuickSortStrategy<>());
        ctx.sort(students, Comparator.comparingInt(Student::getRecordBookNumber));
        System.out.println("\n--- QuickSort по номеру зачетки ---");
        students.forEach(System.out::println);

        // --- 5. Тестируем EvenOddDecoratorStrategy ---
        ctx.setStrategy(new EvenOddDecoratorStrategy<>(new BubbleSortStrategy<>(), Student::getRecordBookNumber));
        ctx.sort(students, Comparator.comparingInt(Student::getRecordBookNumber));
        System.out.println("\n--- BubbleSort с EvenOdd (чётные номера зачетки сортируются) ---");
        students.forEach(System.out::println);

        // --- 6. Тестируем сохранение в файл ---
        String filePath = "students_test_output.txt";
        FileAppender.appendStudents(filePath, students);
        System.out.println("\n--- Студенты сохранены в файл: " + filePath + " ---");

        // --- 7. Тестируем многопоточный подсчёт ---
        MultithreadCounter<Student> counter = new MultithreadCounter<>(2);
        int recNumber = 102;
        int occurrences = counter.countOccurrences(students,
                new Student.Builder().setGroupNumber("G1").setGpa(0).setRecordBookNumber(recNumber).build());
        System.out.println("\n--- Вхождения студентов с номером зачетки " + recNumber + " = " + occurrences + " ---");

        System.out.println("\n=== Тестирование завершено ===");
    }
}

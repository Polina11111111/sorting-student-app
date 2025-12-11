package com.example.sorting.io;

import com.example.sorting.collection.CustomArrayList;
import com.example.sorting.model.Student;


import com.example.sorting.collection.CustomArrayList;
import com.example.sorting.model.Student;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;


public class DataLoader {


    public static CustomArrayList<Student> fromRandom(int n) {
        CustomArrayList<Student> list = new CustomArrayList<>();
        Random r = new Random();
        IntStream.range(0, n).forEach(i -> {
            Student s = new Student.Builder()
                    .setGroupNumber("G" + (r.nextInt(10) + 1))
                    .setGpa(Math.round(r.nextDouble() * 100.0) / 10.0)
                    .setRecordBookNumber(r.nextInt(1000) + 1)
                    .build();
            list.add(s);
        });
        return list;
    }


    public static CustomArrayList<Student> fromFile(String path) throws IOException {
        CustomArrayList<Student> list = new CustomArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 3) continue;
                String group = parts[0].trim();
                double gpa = Double.parseDouble(parts[1].trim());
                int recNo = Integer.parseInt(parts[2].trim());
                list.add(new Student.Builder().setGroupNumber(group).setGpa(gpa).setRecordBookNumber(recNo).build());
            }
        }
        return list;
    }


    public static CustomArrayList<Student> manualFill(int n, Scanner sc) {
        CustomArrayList<Student> list = new CustomArrayList<>();
        IntStream.range(0, n).forEach(i -> {
            System.out.println("Student #" + (i + 1));
            System.out.print("Group: "); String group = sc.nextLine();
            System.out.print("GPA: "); double gpa = Double.parseDouble(sc.nextLine());
            System.out.print("RecordBookNumber: "); int recNo = Integer.parseInt(sc.nextLine());
            list.add(new Student.Builder().setGroupNumber(group).setGpa(gpa).setRecordBookNumber(recNo).build());
        });
        return list;
    }
}
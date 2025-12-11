package com.example.sorting.io;

import com.example.sorting.collection.CustomArrayList;
import com.example.sorting.model.Student;


import java.io.FileWriter;
import java.io.IOException;


public class FileAppender {


    public static void appendStudents(String path, CustomArrayList<Student> list) {
        try (FileWriter fw = new FileWriter(path, true)) {
            for (Student s : list) {
                fw.write(s.getGroupNumber() + "," + s.getGpa() + "," + s.getRecordBookNumber() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
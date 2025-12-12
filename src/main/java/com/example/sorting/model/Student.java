package com.example.sorting.model;


import java.util.Objects;
import java.util.Comparator; // Добавлен для компараторов

public final class Student {
    private final String groupNumber;
    private final double gpa;
    private final int recordBookNumber;


    private Student(Builder b) {
        this.groupNumber = b.groupNumber;
        this.gpa = b.gpa;
        this.recordBookNumber = b.recordBookNumber;
    }


    // Геттеры

    public String getGroupNumber() { return groupNumber; }
    public double getGpa() { return gpa; }
    public int getRecordBookNumber() { return recordBookNumber; }


    // Статистические компараторы

    // Компаратор для сортировки по номеру зачетной книжки (RecordBookNumber)
    public static final Comparator<Student> BY_RECORD_BOOK =
            Comparator.comparingInt(Student::getRecordBookNumber);

    // Компаратор для сортировки по GPA (среднему баллу)
    public static final Comparator<Student> BY_GPA =
            Comparator.comparingDouble(Student::getGpa);

    //Компаратор для сортировки по номеру группы (GroupNumber)
    public static final Comparator<Student> BY_GROUP_NUMBER =
            Comparator.comparing(Student::getGroupNumber);

    // ----------------------------------------------------


    @Override
    public String toString() {
        return "Student{" +
                "group='" + groupNumber + '\'' +
                ", gpa=" + gpa +
                ", recNo=" + recordBookNumber +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student s = (Student) o;
        return Double.compare(s.gpa, gpa) == 0 &&
                recordBookNumber == s.recordBookNumber &&
                Objects.equals(groupNumber, s.groupNumber);
    }


    @Override
    public int hashCode() {
        return Objects.hash(groupNumber, gpa, recordBookNumber);
    }


    public static class Builder {
        private String groupNumber;
        private double gpa;
        private int recordBookNumber;

        public Builder setGroupNumber(String groupNumber) { this.groupNumber = groupNumber; return this; }
        public Builder setGpa(double gpa) { this.gpa = gpa; return this; }
        public Builder setRecordBookNumber(int recordBookNumber) { this.recordBookNumber = recordBookNumber; return this; }

        public Student build() {
            // Валидация данных
            if (groupNumber == null || groupNumber.isBlank())
                throw new IllegalArgumentException("groupNumber required");
            if (gpa < 0 || gpa > 10) throw new IllegalArgumentException("gpa must be 0..10");
            if (recordBookNumber <= 0) throw new IllegalArgumentException("recordBookNumber must be positive");

            return new Student(this);
        }
    }
}
package com.example.grade_tracker.model;

import jakarta.persistence.*;

@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private Integer grade;

    // Конструкторы (обязательны для JPA)
    public Grade() {
    }

    public Grade(String studentName, String subject, Integer grade) {
        this.studentName = studentName;
        this.subject = subject;
        this.grade = grade;
    }

    // Геттеры и сеттеры (обязательны для доступа к полям)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
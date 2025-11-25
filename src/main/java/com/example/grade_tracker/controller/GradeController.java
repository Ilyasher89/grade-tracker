package com.example.grade_tracker.controller;

import com.example.grade_tracker.model.Grade;
import com.example.grade_tracker.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/grades")
public class GradeController {

    private final GradeRepository gradeRepository;

    @Autowired
    public GradeController(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @GetMapping
    public String listGrades(Model model) {
        model.addAttribute("grades", gradeRepository.findAll());
        return "grades/list";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAddForm(Model model) {
        model.addAttribute("grade", new Grade());
        return "grades/form";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showEditForm(@PathVariable Long id, Model model) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid grade id: " + id));
        model.addAttribute("grade", grade);
        return "grades/form";
    }

    // ★ УНИВЕРСАЛЬНЫЙ МЕТОД ДЛЯ СОХРАНЕНИЯ И ОБНОВЛЕНИЯ ★
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveGrade(@ModelAttribute Grade grade) {
        gradeRepository.save(grade);
        return "redirect:/grades";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteGrade(@PathVariable Long id) {
        gradeRepository.deleteById(id);
        return "redirect:/grades";
    }
}
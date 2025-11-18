package com.example.grade_tracker.controller;

import com.example.grade_tracker.model.Grade;
import com.example.grade_tracker.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller // Говорим Spring, что этот класс - Веб-Контроллер
@RequestMapping("/grades") // Все методы этого контроллера будут начинаться с URL /grades
public class GradeController {

    // Внедряем репозиторий для работы с базой данных
    @Autowired
    private GradeRepository gradeRepository;

    // Обработчик для отображения списка всех оценок
    @GetMapping
    public String listGrades(Model model) {
        List<Grade> grades = gradeRepository.findAll(); // Достаем все оценки из БД
        model.addAttribute("grades", grades); // Передаем список в шаблон
        return "grades/list"; // Указываем, какой HTML-шаблон показать (src/main/resources/templates/grades/list.html)
    }

    // Обработчик для отображения формы добавления новой оценки
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("grade", new Grade()); // Создаем пустой объект для формы
        return "grades/form"; // Показываем форму (src/main/resources/templates/grades/form.html)
    }

    // Обработчик для сохранения новой оценки (срабатывает при нажатии кнопки "Сохранить" в форме)
    @PostMapping("/save")
    public String saveGrade(@ModelAttribute Grade grade) {
        gradeRepository.save(grade); // Сохраняем оценку в БД
        return "redirect:/grades"; // Перенаправляем пользователя обратно на список оценок
    }

    // Обработчик для отображения формы редактирования оценки
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Grade grade = gradeRepository.findById(id) // Ищем оценку по ID
                .orElseThrow(() -> new IllegalArgumentException("Неверный ID оценки: " + id)); // Если не нашли - ошибка
        model.addAttribute("grade", grade); // Передаем найденную оценку в форму
        return "grades/form"; // Показываем ту же самую форму, но уже заполненную данными
    }

    // Обработчик для удаления оценки
    @GetMapping("/delete/{id}")
    public String deleteGrade(@PathVariable("id") Long id) {
        gradeRepository.deleteById(id); // Удаляем оценку по ID
        return "redirect:/grades"; // Перенаправляем пользователя обратно на список оценок
    }
}
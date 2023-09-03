package com.bezkoder.spring.files.excel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.files.excel.model.Student;
import com.bezkoder.spring.files.excel.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;

	@PostMapping("/create")
	public Student createStudent(@RequestBody Student student) {
		Student createResponse = studentService.save(student);
		return createResponse;
	}
	
	// /student/allstudents
	@GetMapping("/allstudents")
	public List getStudents() {
		List getReponse = studentService.getStudents();
		return getReponse;
	}
}
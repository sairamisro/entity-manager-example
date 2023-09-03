package com.bezkoder.spring.files.excel.service;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bezkoder.spring.files.excel.model.Student;

@Component
public interface StudentService {

	public Student save(Student student);

	public List getStudents();
}
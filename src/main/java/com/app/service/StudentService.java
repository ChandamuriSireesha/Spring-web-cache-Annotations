package com.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.entity.Student;
import com.app.exception.DatabaseException;

@Service
public interface StudentService {

	public Student addStudent(Student student) throws DatabaseException;

	public Student readStudentById(Integer id) throws DatabaseException;

	public List<Student> readAllStudent() throws DatabaseException;

	public Student updateStudent(Student student) throws DatabaseException;

	public boolean removeStudentById(Integer id) throws DatabaseException;

	String clearCache();

}

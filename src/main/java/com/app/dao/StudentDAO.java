package com.app.dao;

import org.springframework.data.repository.CrudRepository;

import com.app.entity.Student;

public interface StudentDAO  extends CrudRepository<Student, Integer>{

}

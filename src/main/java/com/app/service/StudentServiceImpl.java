package com.app.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.StudentDAO;
import com.app.entity.Student;
import com.app.exception.DatabaseException;
import com.app.exception.RecordNotFoundException;

@Service
public class StudentServiceImpl implements StudentService{
	protected Logger logger = Logger.getLogger(StudentServiceImpl.class.getName());

	
	@Autowired
	StudentDAO dao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
	public Student addStudent(Student student) throws DatabaseException {
		logger.info("Adding new Student");
		try {
			student = dao.save(student);
		} catch (DataAccessException e) {
			throw new DatabaseException(" database issues !! " + e.getMessage());
		}
		return student;
	}

	@Override
	@Transactional(propagation = Propagation.NEVER)
	@Cacheable(cacheNames = "student", key = "#id",condition="#id < 10")
	public Student readStudentById(Integer id) throws DatabaseException {
		logger.info("Read Student by Id");
		boolean exist = dao.existsById(id);
		if (!exist)throw new RecordNotFoundException("The student with id : " + id + "  doest  not exist in database");

		Student student;
		try {
			student = dao.findById(id).get();
		} catch (DataAccessException e) {
			throw new DatabaseException(" database issues !! " + e.getMessage());
		}
		return student;
	}
	
	


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
	@CachePut(cacheNames = "student", key = "#id")
	public Student updateStudent(Student currentStudent) throws DatabaseException {
		logger.info("update Student");
		boolean exist = dao.existsById(currentStudent.getId());
		if (!exist)
			throw new RecordNotFoundException(
					"The student with id : " + currentStudent.getId() + " doest  not exist in database");

		try {
			dao.findById(currentStudent.getId()).get();
			dao.save(currentStudent);
		} catch (DataAccessException e) {
			throw new DatabaseException("database issues !! " + e.getMessage());
		}
		return currentStudent;

	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
	@CacheEvict(cacheNames = "student", key = "#id")
	public boolean removeStudentById(Integer id) throws DatabaseException {
		logger.info("remove Student by Id");
		
		boolean exist = dao.existsById(id);
		if (!exist)
			throw new RecordNotFoundException("The student with id : " + id + "  doest  not exist in db");

		try {
			dao.deleteById(id);
		} catch (DataAccessException e) {
			throw new DatabaseException(" database issues !! " + e.getMessage());
		}
		return true;
	}

	@Transactional(propagation = Propagation.NEVER)
	public List<Student> readAllStudent() throws DatabaseException {
		logger.info("Get All Students");
		List<Student> listStudent;
		try {
			listStudent = (List<Student>) dao.findAll();
		} catch (DataAccessException e) {
			throw new DatabaseException("database issues !! " + e.getMessage());
		}
		return listStudent;
	}
	
	
	@Override
	@CacheEvict(cacheNames = "student", allEntries = true)
	public String clearCache() {
		// TODO Auto-generated method stub
		return "Cleared cache";
	}

	
}

package com.app.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.Student;
import com.app.exception.DatabaseException;
import com.app.service.StudentService;

@RestController
@RequestMapping("/student")
/* @Api(value="StudentCrud", description="Operations for student ") */

public class StudentContrller {

	@Autowired
	StudentService studentService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Student> getPersons() throws DatabaseException {

		Iterable<Student> studentList = studentService.readAllStudent();
		return (List<Student>) studentList;

	}

	// @Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<Student> addNewPerson(@RequestBody Student student) throws DatabaseException {

		Student studentobj = studentService.addStudent(student);

		if (studentobj != null)
			return new ResponseEntity<Student>(studentobj, HttpStatus.CREATED);
		else
			return new ResponseEntity<Student>(studentobj, HttpStatus.BAD_REQUEST);
	}

	// @Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = { "application/json", "application/xml" })
	@ResponseBody
	public ResponseEntity<Student> updatePerson(@RequestBody Student student) throws DatabaseException {

		studentService.updateStudent(student);
		return new ResponseEntity<Student>(student, HttpStatus.OK);

	}

	@RequestMapping(value = "/read/{id}")
	@ResponseBody
	ResponseEntity<Student> getPersonById(@PathVariable Integer id) throws DatabaseException {

		Student student = studentService.readStudentById(id);
		return new ResponseEntity<Student>(student, HttpStatus.OK);

	}

	// @Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	ResponseEntity removeUser(@PathVariable int id) throws DatabaseException {

		boolean result = studentService.removeStudentById(id);
		if (result) {
			return new ResponseEntity<Boolean>(result, HttpStatus.OK);
		} else
			return new ResponseEntity(null, HttpStatus.NOT_FOUND);
	}

	@RequestMapping("/cleareCache")
	public ResponseEntity<String> clearCache() {
		String result = "";
		result = studentService.clearCache();
		if (result != "") {
			return new ResponseEntity<String>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(result, HttpStatus.BAD_REQUEST);
		}

	}

}

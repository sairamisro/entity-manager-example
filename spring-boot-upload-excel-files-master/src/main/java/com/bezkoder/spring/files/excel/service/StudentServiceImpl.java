package com.bezkoder.spring.files.excel.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.spring.files.excel.model.Student;

@Service
public class StudentServiceImpl implements StudentService {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public List getStudents() {
		String str = " SELECT * FROM tailor_admin.roles ";
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT t.id, title, i.exp ");
		sb.append(" FROM tailor_admin.tutorials t ");
		sb.append(" left join tailor_admin.inventory i ");
		sb.append(" on t.id = i.id ");

		System.out.println(sb.toString());
		
		Query q = entityManager.createNativeQuery(sb.toString());
		List list = q.getResultList();
		System.out.println("list :" + list.size());
		return list;
	}

	@Transactional
	public Student save(Student student) {
		entityManager.persist(student);
		return student;
	}

	@Transactional
	public List<Student> getStudent() {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
		Root<Student> studentRoot = criteriaQuery.from(Student.class);
		criteriaQuery.orderBy(criteriaBuilder.desc(studentRoot.get("name")));
		List<Student> students = entityManager.createQuery(criteriaQuery).getResultList();
		return students;
	}

}
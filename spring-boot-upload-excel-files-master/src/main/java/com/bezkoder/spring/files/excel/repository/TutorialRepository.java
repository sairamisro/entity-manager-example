package com.bezkoder.spring.files.excel.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.bezkoder.spring.files.excel.model.Tutorial;

@Repository
public class TutorialRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Tutorial> getTutorials() {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT t.id, title, i.exp ");
		sb.append(" FROM tailor_admin.tutorials t ");
		sb.append(" left join tailor_admin.inventory i ");
		sb.append(" on t.id = i.id ");

		System.out.println(sb.toString());
		
		Query query = entityManager.createQuery(sb.toString());
		
		return query.getResultList();
		
		//TypedQuery<Tutorial> query = entityManager.createQuery(sb.toString(), Tutorial.class);
		//return query.getResultList();
	}

	public List<Tutorial> findByTitleContaining(String title) {
		TypedQuery<Tutorial> query = entityManager.createQuery(
				"SELECT t FROM Tutorial t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :title,'%'))", Tutorial.class);
		return query.setParameter("title", title).getResultList();
	}

	public List<Tutorial> findByPublished(boolean isPublished) {
		TypedQuery<Tutorial> query = entityManager
				.createQuery("SELECT t FROM Tutorial t WHERE t.published=:isPublished", Tutorial.class);
		return query.setParameter("isPublished", isPublished).getResultList();
	}

	public List<Tutorial> findByTitleAndPublished(String title, boolean isPublished) {
		TypedQuery<Tutorial> query = entityManager.createQuery(
				"SELECT t FROM Tutorial t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :title,'%')) AND t.published=:isPublished",
				Tutorial.class);
		return query.setParameter("title", title).setParameter("isPublished", isPublished).getResultList();
	}

	@Transactional
	public Tutorial save(Tutorial tutorial) {
		entityManager.persist(tutorial);
		return tutorial;
	}

	public Tutorial findById(long id) {
		Tutorial tutorial = (Tutorial) entityManager.find(Tutorial.class, id);
		return tutorial;
	}

	public List<Tutorial> findAll() {
		TypedQuery<Tutorial> query = entityManager.createQuery("SELECT t FROM Tutorial t", Tutorial.class);
		return query.getResultList();
	}

	@Transactional
	public Tutorial update(Tutorial tutorial) {
		entityManager.merge(tutorial);
		return tutorial;
	}

	@Transactional
	public Tutorial deleteById(long id) {
		Tutorial tutorial = findById(id);
		if (tutorial != null) {
			entityManager.remove(tutorial);
		}
		return tutorial;
	}

	@Transactional
	public int deleteAll() {
		Query query = entityManager.createQuery("DELETE FROM Tutorial");
		return query.executeUpdate();
	}

}

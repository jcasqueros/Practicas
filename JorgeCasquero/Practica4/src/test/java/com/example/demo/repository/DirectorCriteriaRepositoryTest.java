package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.demo.exception.AlreadyExistsExeption;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Director;
import com.example.demo.repository.cb.impl.DirectorCriteriaImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@ExtendWith(MockitoExtension.class)
public class DirectorCriteriaRepositoryTest {

	@Mock
	private EntityManager entityManager;

	@Mock
	private CriteriaBuilder criteriaBuilder;

	@Mock
	private CriteriaQuery<Director> criteriaQuery;

	@Mock
	private Root<Director> root;

	@Mock
	private TypedQuery<Director> typedQuery;

	@InjectMocks
	private DirectorCriteriaImpl directorCriteria;

	private Director director;
	private Pageable pageable;
	private List<Director> directorList;
	private Page<Director> directorPage;

	@BeforeEach
	void setUp() {
		director = new Director();
		director.setIdDirector(1L);
		director.setNombre("Test Director");
		director.setEdad(50);
		director.setNacionalidad("Test Nationality");

		pageable = PageRequest.of(0, 5);
		directorList = Collections.singletonList(director);
		directorPage = new PageImpl<>(directorList, pageable, directorList.size());
	}

	@Test
	void testGetAll() {
		when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
		when(criteriaBuilder.createQuery(Director.class)).thenReturn(criteriaQuery);
		when(criteriaQuery.from(Director.class)).thenReturn(root);
		when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
		when(typedQuery.getResultList()).thenReturn(directorList);

		Page<Director> result = directorCriteria.getAll(pageable);
		assertEquals(directorPage.getContent(), result.getContent());
		assertEquals(directorPage.getTotalElements(), result.getTotalElements());
	}

	@Test
	void testGetById() throws NotFoundException {
		when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
		when(criteriaBuilder.createQuery(Director.class)).thenReturn(criteriaQuery);
		when(criteriaQuery.from(Director.class)).thenReturn(root);
		when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenReturn(director);

		Optional<Director> result = directorCriteria.getById(1L);
		assertEquals(Optional.of(director), result);
	}

	@Test
	void testCreate() throws AlreadyExistsExeption {
		when(entityManager.merge(any(Director.class))).thenReturn(director);

		Director result = directorCriteria.create(director);
		assertEquals(director, result);
		verify(entityManager).merge(director);
	}

	@Test
	void testDeleteById() throws NotFoundException {
		when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
		when(criteriaBuilder.createQuery(Director.class)).thenReturn(criteriaQuery);
		when(criteriaQuery.from(Director.class)).thenReturn(root);
		when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenReturn(director);

		directorCriteria.deleteById(1L);
		verify(entityManager).remove(any(Director.class));
	}

	@Test
	void testFindAndFilter() {
		when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
		when(criteriaBuilder.createQuery(Director.class)).thenReturn(criteriaQuery);
		when(criteriaQuery.from(Director.class)).thenReturn(root);
		when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
		when(typedQuery.getResultList()).thenReturn(directorList);

		Page<Director> result = directorCriteria.findAndFilter(pageable, Collections.singletonList("Test Director"),
				Collections.singletonList(50), Collections.singletonList("Test Nationality"));
		assertEquals(directorPage.getContent(), result.getContent());
		assertEquals(directorPage.getTotalElements(), result.getTotalElements());
	}
}

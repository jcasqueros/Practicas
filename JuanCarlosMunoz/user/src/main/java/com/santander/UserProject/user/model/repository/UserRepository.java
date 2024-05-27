package com.santander.UserProject.user.model.repository;


import com.santander.UserProject.user.model.entity.Users;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The interface Users repository.
 */
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @NonNull
    Page<Users> findAll(@NonNull Pageable pageable);

    /**
     * Find all by age equals a and order by name page.
     *
     * @param age
     *         the age
     * @param pageable
     *         the pageable
     * @return the page
     */
    Page<Users> findAllByAgeEquals(int age, Pageable pageable);

    /**
     * Find all by name and order by age page.
     *
     * @param name
     *         the name
     * @param pageable
     *         the pageable
     * @return the page
     */
    Page<Users> findAllByNameAndAge( String name,  int age, Pageable pageable);

}


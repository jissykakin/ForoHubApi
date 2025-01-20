package com.foro.hubApi.domain.topics;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query("""
            select t.status
            from Topic t
            where
            t.id = :id
            """)
    boolean findStatusById(Long id);

    boolean existsByIdAndStatusTrue(Long id);

    boolean existsByMessageAndStatusTrue(String message);

    boolean existsByTitleAndStatusTrue(String Title);

    Page<Topic> findAllByStatusTrue(Pageable pageable);

    Optional<Topic> findByTitle(String Title);

    @Query("SELECT t FROM Topic t JOIN FETCH t.course c WHERE  (:course IS NULL OR c.name LIKE %:course%) AND (:year IS NULL OR YEAR(t.createdAt) = :year) AND t.status = true")
    Page<Topic> findByCourseAndCreatedAt(String course, String year, Pageable pageable);

    @Query("SELECT t FROM Topic t LEFT JOIN FETCH t.answers WHERE t.id = :id AND t.status = true")
    Topic fetchByIdWithAnswers(Long id);


}

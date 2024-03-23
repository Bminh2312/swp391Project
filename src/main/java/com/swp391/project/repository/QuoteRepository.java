package com.swp391.project.repository;

import com.swp391.project.entity.ProjectEntity;
import com.swp391.project.entity.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface QuoteRepository extends JpaRepository<QuoteEntity,Integer> {
    List<QuoteEntity> findByProjectQuoteId(int projectId);


    @Query("SELECT SUM(q.total) FROM quote q WHERE q.projectQuote.id = :projectId")
    Double getTotalByProjectId(int projectId);
}

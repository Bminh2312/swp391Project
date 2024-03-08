package com.swp391.project.repository;

import com.swp391.project.entity.QuoteDetailEntity;
import com.swp391.project.entity.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteDetailRepository extends JpaRepository<QuoteDetailEntity,Integer> {
    List<QuoteDetailEntity> findByQuoteId(int quoteId);
}

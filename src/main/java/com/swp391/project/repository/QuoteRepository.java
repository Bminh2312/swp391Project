package com.swp391.project.repository;

import com.swp391.project.entity.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<QuoteEntity,Integer> {
}

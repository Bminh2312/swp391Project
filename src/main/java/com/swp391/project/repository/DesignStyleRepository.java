package com.swp391.project.repository;

import com.swp391.project.entity.DesignStyleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignStyleRepository extends JpaRepository<DesignStyleEntity,Integer> {


}

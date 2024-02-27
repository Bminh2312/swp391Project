package com.swp391.project.service;

import com.swp391.project.entity.DesignStyleEntity;
import com.swp391.project.repository.DesignStyleRepository;
import com.swp391.project.service.impl.DesignStyleImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignStyleService implements DesignStyleImp {

    @Autowired
    private DesignStyleRepository designStyleRepository;

    @Override
    public List<DesignStyleEntity> findAllDesign() {
        return designStyleRepository.findAll();
    }
}

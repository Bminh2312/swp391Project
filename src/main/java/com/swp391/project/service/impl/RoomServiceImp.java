package com.swp391.project.service.impl;

import com.swp391.project.dto.RoomDTO;

import java.util.List;

public interface RoomServiceImp {

    boolean create (String name);

    boolean update (int id, String name);

    List<RoomDTO> findAll();

    boolean delete (int id, String status);

}

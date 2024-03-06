package com.swp391.project.service;

import com.swp391.project.dto.RoomDTO;
import com.swp391.project.entity.RoomEntity;
import com.swp391.project.repository.RoomRepository;
import com.swp391.project.service.impl.RoomServiceImp;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class RoomService implements RoomServiceImp {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public boolean create(String name) {
        try{
            TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

            // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
            Calendar calendar = Calendar.getInstance(timeZone);
            Date currentTime = calendar.getTime();
            RoomEntity roomEntity = new RoomEntity();
            roomEntity.setName(name);
            roomEntity.setCreatedAt(currentTime);
            roomEntity.setUpdatedAt(currentTime);
            roomEntity.setStatus("ACTIVE");
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(int id, String name) {
        try {
            TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
            Optional<RoomEntity> roomEntity = roomRepository.findById(id);
            if (roomEntity.isPresent()) {
                // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
                Calendar calendar = Calendar.getInstance(timeZone);
                Date currentTime = calendar.getTime();
                roomEntity.get().setName(name);
                roomEntity.get().setUpdatedAt(currentTime);
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<RoomDTO> findAll() {
        return null;
    }

    @Override
    public boolean delete(String status) {
        return false;
    }
}

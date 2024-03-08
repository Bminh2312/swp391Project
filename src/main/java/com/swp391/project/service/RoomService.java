package com.swp391.project.service;

import com.swp391.project.dto.RoomDTO;
import com.swp391.project.entity.RoomEntity;
import com.swp391.project.repository.RoomRepository;
import com.swp391.project.service.impl.RoomServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomService implements RoomServiceImp {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public boolean create(String name) {
        try{
            Optional<RoomEntity> room = roomRepository.findByName(name);
            if(room.isEmpty()){
                TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

                // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
                Calendar calendar = Calendar.getInstance(timeZone);
                Date currentTime = calendar.getTime();
                RoomEntity roomEntity = new RoomEntity();
                roomEntity.setName(name);
                roomEntity.setCreatedAt(currentTime);
                roomEntity.setUpdatedAt(currentTime);
                roomEntity.setStatus("ACTIVE");
                roomRepository.save(roomEntity);
                return true;
            }
            return false;
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
            if (roomEntity.isPresent() && !(name.trim().isEmpty())) {
                // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
                Calendar calendar = Calendar.getInstance(timeZone);
                Date currentTime = calendar.getTime();
                if(!roomEntity.get().getName().equals(name)){
                    roomEntity.get().setName(name);
                }
                roomEntity.get().setUpdatedAt(currentTime);
                roomRepository.save(roomEntity.get());
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
        try{
            List<RoomEntity> roomEntities = roomRepository.findAll();
            List<RoomDTO> roomDTOS = new ArrayList<>();
            for (RoomEntity roomEntity:  roomEntities) {
                RoomDTO roomDTO = mapFromEntity(roomEntity);
                roomDTOS.add(roomDTO);
            }
            return roomDTOS;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean delete(int id, String status) {
        try {
            TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
            Optional<RoomEntity> roomEntity = roomRepository.findById(id);
            if (roomEntity.isPresent()) {
                // Lấy thời gian hiện tại dựa trên múi giờ của Việt Nam
                Calendar calendar = Calendar.getInstance(timeZone);
                Date currentTime = calendar.getTime();
                roomEntity.get().setStatus(status);
                roomEntity.get().setUpdatedAt(currentTime);
                roomRepository.save(roomEntity.get());
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public  RoomDTO mapFromEntity(RoomEntity roomEntity) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(roomEntity.getId());
        roomDTO.setName(roomEntity.getName());
        roomDTO.setCreatedAt(roomEntity.getCreatedAt());
        roomDTO.setUpdatedAt(roomEntity.getUpdatedAt());
        roomDTO.setStatus(roomEntity.getStatus());
        return roomDTO;
    }
}

package com.main.packme.services;

import com.main.packme.dao.entity.Pack;
import com.main.packme.repository.PacksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackService {
    @Autowired
    private PacksRepository packsRepository;

    public Pack findById(long id){
        return packsRepository.findById(id);
    }
    public void savePack(Pack pack){
        packsRepository.save(pack);
    }
    public void saveGeneral(long id, String name, String description, String type,String firstLn,String secondLn){
        Pack byId = packsRepository.findById(id);
        byId.setName(name);
        byId.setDescription(description);
        byId.setType(type);
        byId.setFirst_ln(firstLn);
        byId.setSecond_ln(secondLn);

        packsRepository.save(byId);
    }
//    public void saveLn(long id, String firstLn, String secondLn){
//        Pack byId = packsRepository.findById(id);
//        byId.setFirst_ln(firstLn);
//        byId.setSecond_ln(secondLn);
//        packsRepository.save(byId);
//    }
    public List<Pack> findAllPublicPacks(){
       return packsRepository.findAllByType("public");
    }
}

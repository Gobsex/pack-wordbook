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
    public List<Pack> findAllPublicPacks(){
       return packsRepository.findAllByType("public");
    }
}

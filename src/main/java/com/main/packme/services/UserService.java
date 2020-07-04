package com.main.packme.services;

import com.main.packme.dao.entity.Pack;
import com.main.packme.dao.entity.Role;
import com.main.packme.dao.entity.User;
import com.main.packme.repository.PacksRepository;
import com.main.packme.repository.RoleRepository;
import com.main.packme.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PacksRepository packsRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }
    public void addPack(String username, Pack pack){
        User user = userRepository.findByUsername(username);
        if(user!=null) {
            packsRepository.save(pack);
            user.addPack(pack);
            userRepository.save(user);
        }
        else System.out.println("user not exist");
    }
    public boolean isEditable(String username,long packId){
        User user = userRepository.findByUsername(username);
        List<Pack> packs = user.getPacks();
        for (Pack i:packs) {
            if(i.getId()==packId){
                return true;
            }
        }
        return false;
    }
    public boolean isSelectable(long packId){
        Pack pack = packsRepository.findById(packId);
        if(pack.getType().equals("public")){
            return true;
        }
        return false;
    }
    public List<Pack> findAllPacks(String username){
        User user = userRepository.findByUsername(username);
        return user.getPacks();
    }
    public List<Pack> findAllPublicPacks(String username){
        List<Pack> publicPacks = new ArrayList<>();

        User user = userRepository.findByUsername(username);
        List<Pack> packs = user.getPacks();
        for (Pack pack:packs) {
            if(pack.getType().equals("public")){
                publicPacks.add(pack);
            }
        }
        return publicPacks;
    }
    public List<Pack> findAllPrivatePacks(String username){
        List<Pack> privatePacks = new ArrayList<>();

        User user = userRepository.findByUsername(username);
        List<Pack> packs = user.getPacks();
        for (Pack pack:packs) {
            if(pack.getType().equals("private")){
                privatePacks.add(pack);
            }
        }
        return privatePacks;
    }
    public Pack findEditablePackById(String username,long packId){
        User user = userRepository.findByUsername(username);
        List<Pack> packs = user.getPacks();
        for (Pack i:packs) {
            if(i.getId()==packId){
                return i;
            }
        }
        return null;
    }
    public void removePack(String username,long packId){
        User user = userRepository.findByUsername(username);
        if(user!=null) {
            Pack pack = packsRepository.findById(packId);
            packsRepository.delete(pack);
            user.removePack(pack);
            userRepository.save(user);
        }
        else System.out.println("user not exsist");
    }
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }
        Role role = new Role(1L, "ROLE_USER");
        user.setRoles(Collections.singleton(role));
        roleRepository.save(role);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}

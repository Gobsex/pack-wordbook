package com.main.packme.dao.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "t_user")
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min=2, message = "Too short")
    private String username;
    @Size(min=2, message = "Too short")
    private String password;
    @Transient
    private String passwordConfirm;
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Set<Role> roles;
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH,mappedBy = "user")
    private List<Pack> packs;
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Pack> favoritePacks;
    public User() {
    }

    public List<Pack> getFavoritePacks() {
        if(favoritePacks==null){
            favoritePacks = new ArrayList<>();
        }
        return favoritePacks;
    }

    public void removeFromFavorites(Pack pack){
        if(pack!=null) {
            favoritePacks.remove(pack);
            System.out.println(favoritePacks);

        }
    }
    public void addToFavorites(Pack pack){
        if(favoritePacks==null){
            favoritePacks = new ArrayList<>();
        }
        if(!favoritePacks.contains(pack)){
            favoritePacks.add(pack);
        }
    }


    public void addPack(Pack pack){
        if(packs==null){
            packs = new ArrayList<>();
        }
        packs.add(pack);
        pack.setUser(this);
    }
    public void removePack(Pack pack){
        if(pack!=null) {
            packs.remove(pack);
        }
    }
    @JsonManagedReference
    public List<Pack> getPacks() {
        return packs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
    @JsonManagedReference
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}

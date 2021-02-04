package com.spring.spring.models.condot;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@org.hibernate.annotations.Table(appliesTo = "tbl_users")
public class tbl_users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;
    private String username;
    private String password;
    private boolean active;

    private String roleid;




    public tbl_users() {
    }

    public tbl_users(Long id, String username, String password, boolean active, String roles) {
        this.user_id = id;
        this.username = username;
        this.password = password;
        this.active = active;
        this.roleid = roles;
    }


    public Long getId() {
        return user_id;
    }

    public void setId(Long id) {
        this.user_id = id;
    }

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
        return isActive();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoles() {
//        System.out.println(roleid);
//        Set <Role> list=  new HashSet<>();
//        if (roleid.equals("7")) {
//            list.add(Role.ADMIN);
//        }
//        else {
//            list.add(Role.USER);
//        }
//        return list;
        return roleid;
    }

    public void setRoles(String roles) {
        this.roleid = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null ;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


}
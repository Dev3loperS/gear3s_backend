package cybersoft.java20.dev3lopers.gear3sproject.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Users;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

import java.util.Collections;
import java.util.List;

public class AccountDetailsImp implements UserDetails {
    private int id;
    private String email;
    private String fullname;
    private String avatar;
    private String role;

    @JsonIgnore
    List<GrantedAuthority> authorities = null;

    public AccountDetailsImp(int id, String email, String fullname, String avatar, String role, List<GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.fullname = fullname;
        this.avatar = avatar;
        this.role = role;
        this.authorities = authorities;
    }

    public static AccountDetailsImp build(Users user) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRoles().getName()));
        return new AccountDetailsImp(
                user.getId(),
                user.getEmail(),
                user.getFullname(),
                user.getAvatar(),
                user.getRoles().getName(),
                null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

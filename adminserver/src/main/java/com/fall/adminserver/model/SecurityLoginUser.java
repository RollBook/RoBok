package com.fall.adminserver.model;

import com.fall.adminserver.constant.AuthorityEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author FAll
 * @date 2023/4/12 下午5:51
 * Security用户详情类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityLoginUser implements UserDetails {

    private SysUser sysUser;

    private AuthorityEnum authority;

    @JsonIgnore
    private List<SimpleGrantedAuthority> authorities;

    public SecurityLoginUser(SysUser sysUser, AuthorityEnum authority){
        this.sysUser = sysUser;
        this.authority = authority;
    }

    @Override
    public List<SimpleGrantedAuthority> getAuthorities() {

        return Optional.ofNullable(this.authorities)
                .orElseGet(()->{
                    ArrayList<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
                    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority.toString());
                    grantedAuthorities.add(simpleGrantedAuthority);
                    this.authorities = grantedAuthorities;
                    return grantedAuthorities;
                });

    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getName();
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
}

/*
package com.hbsi.shopping.config.security;

import com.hbsi.shopping.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class SecurityUser extends User implements UserDetails {


    public SecurityUser(User user) {
        if (user != null) {
            this.setPassword(user.getPassword());
            this.setEmail(user.getEmail());
            this.setUserName(user.getUserName());
            this.setUserStatus(user.getUserStatus());
            this.setCreateTime(user.getCreateTime());
            this.setPhone(user.getPhone());
            this.setRoleId(user.getRoleId());
            this.setRoleName(user.getRoleName());
            this.setShopId(user.getShopId());
            this.setShopName(user.getShopName());
        }

    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        String username = this.getUsername();
        if (username != null) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(username);
            authorities.add(authority);
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return null;
    }

    //账户是否未过期,过期无法验证
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //指定用户是否解锁,锁定的用户无法进行身份验证
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //指示是否已过期的用户的凭据(密码),过期的凭据防止认证
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //是否可用 ,禁用的用户不能身份验证
    @Override
    public boolean isEnabled() {
        return true;
    }
}
*/

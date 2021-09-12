package com.wangjp.sell.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wangjp.sell.entity.Menu;
import com.wangjp.sell.entity.Role;
import com.wangjp.sell.entity.User;
import com.wangjp.sell.enums.IsEnableEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/9/12 4:22 下午
 * @detail 自定义 spring-security 的 UserDetails
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements UserDetails {

    public static UserInfo create(User user, List<Integer> roleIds, List<Menu> menus) {
        List<GrantedAuthority> authorities = menus.stream().map(menu -> new SimpleGrantedAuthority(menu.getCode())).collect(Collectors.toList());
        return new UserInfo(user.getId(), user.getUsername(), user.getPassword(), user.getOrganId(), user.getIsEnable(), user.getCreateTime(), roleIds, authorities);
    }

    private Integer id;

    private String username;

    @JsonIgnore
    private String password;

    private Integer organId;

    private Integer isEnable;

    private Date createTime;

    private List<Integer> roleIds;

    private Collection<? extends GrantedAuthority> privilegeList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return privilegeList;
    }

    @Override
    public String getPassword() {
        return password;
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
        return this.isEnable.equals(IsEnableEnum.ENABLED.getCode());
    }
}

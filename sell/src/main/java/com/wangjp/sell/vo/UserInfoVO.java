package com.wangjp.sell.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wangjp.sell.converter.Menu2MenuTreeNodeConverter;
import com.wangjp.sell.entity.Menu;
import com.wangjp.sell.entity.Organ;
import com.wangjp.sell.entity.Role;
import com.wangjp.sell.entity.User;
import com.wangjp.sell.enums.IsEnableEnum;
import com.wangjp.sell.pojo.MenuTreeNode;
import com.wangjp.sell.utils.TreeUtil;
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
public class UserInfoVO implements UserDetails {

    public static UserInfoVO create(User user, List<Role> roles, Organ organ, List<Menu> menus) {
        List<Integer> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
        String roleNames = roles.stream().map(Role::getName).collect(Collectors.joining(", "));

        // 创建菜单 menuTree
        List<MenuTreeNode> privilegeList = Menu2MenuTreeNodeConverter.convert(menus);
        List<MenuTreeNode> privilegeTreeList = (List<MenuTreeNode>) TreeUtil.list2Tree(Menu2MenuTreeNodeConverter.convert(menus), MenuTreeNode.class);
        TreeUtil.sortTree(privilegeTreeList, MenuTreeNode.class);

        return new UserInfoVO(
                user.getId(), user.getUsername(), user.getPassword(), user.getOrganId(), user.getIsEnable(),
                user.getCreateTime(), roleIds, roleNames, organ.getName(), privilegeList, privilegeTreeList
        );
    }

    private Integer id;

    private String username;

    @JsonIgnore
    private String password;

    private Integer organId;

    private Integer isEnable;

    private Date createTime;

    private List<Integer> roleIds;

    private String roleNames;

    private String organName;

    private List<MenuTreeNode> privilegeList;

    private List<MenuTreeNode> privilegeTreeList;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return privilegeList.stream().map(menu -> new SimpleGrantedAuthority(menu.getCode())).collect(Collectors.toList());
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
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return this.isEnable.equals(IsEnableEnum.ENABLED.getCode());
    }
}

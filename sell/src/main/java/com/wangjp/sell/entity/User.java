package com.wangjp.sell.entity;

import com.wangjp.sell.entity.base.AbstractAuditModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/4/27 9:03 下午
 * @detail
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends AbstractAuditModel {

    // 主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 用户名称
    private String username;

    // 用户密码
    private String password;

    // 是否启用
    private Integer isEnable;
}

package com.wangjp.sell.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/28 9:57 下午
 * @detail
 */
@Entity
@Data
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private Integer roleId;
}

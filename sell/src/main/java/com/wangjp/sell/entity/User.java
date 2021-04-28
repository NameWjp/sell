package com.wangjp.sell.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/4/27 9:03 下午
 * @detail
 */
@Entity
@Data
// 开启 jpa 审计功能（用于自动添加时间等）
@EntityListeners(AuditingEntityListener.class)
public class User {

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

    // 创建时间
    @CreatedDate
    private Date createTime;

    // 修改时间
    @LastModifiedDate
    private Date updateTime;
}

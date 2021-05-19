package com.wangjp.sell.entity.base;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/5/19 11:36 下午
 * @detail
 */
@Data
// 标识不是一个完整的实体类，不会映射到数据库表
@MappedSuperclass
// 开启 jpa 审计功能（用于自动添加时间等）
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditModel implements Serializable {

    // 创建时间
    @CreatedDate
    private Date createTime;

    // 修改时间
    @LastModifiedDate
    private Date updateTime;
}

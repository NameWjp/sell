package com.wangjp.sell.entity;

import com.wangjp.sell.entity.base.AbstractAuditModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/1/27 10:01 下午
 * @detail 商品类目实体类
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductCategory extends AbstractAuditModel {

    // 类目id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 类目名称
    private String name;

    // 类目编号
    private String code;

    // 排序
    private Integer sort;
}

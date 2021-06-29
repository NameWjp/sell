package com.wangjp.sell.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/5/22 5:29 下午
 * @detail
 */
@Data
public class UserVO {

    private Integer id;

    private String username;

    private Integer isEnable;

    private Integer organId;

    private String organName;

    private Date createTime;

    private Date updateTime;
}

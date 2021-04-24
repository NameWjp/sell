package com.wangjp.sell.exception;

import com.wangjp.sell.enums.ResultEnum;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/3/6 5:00 下午
 * @detail
 */
public class SellException extends RuntimeException {

    private Integer status;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.status = resultEnum.getStatus();
    }

    public SellException(Integer status, String message) {
        super(message);

        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}

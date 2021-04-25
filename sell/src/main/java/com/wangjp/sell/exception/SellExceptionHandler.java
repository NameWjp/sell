package com.wangjp.sell.exception;

import com.wangjp.sell.enums.ResultEnum;
import com.wangjp.sell.utils.ResultVOUtil;
import com.wangjp.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/4/24 6:52 下午
 * @detail
 */
@Slf4j
@RestControllerAdvice
public class SellExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResultVO<Object> paramsExceptionHandler(BindException e) {
        log.error("参数效验失败: {}", e.getFieldError());
        return ResultVOUtil.error(ResultEnum.PARAMS_ERROR.getStatus(), Objects.requireNonNull(e.getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(SellException.class)
    public ResultVO<Object> ExceptionHandler(SellException e) {
        log.error("捕获错误: status={} message={}", e.getStatus(), e.getMessage());
        return ResultVOUtil.error(e.getStatus(), e.getMessage());
    }
}

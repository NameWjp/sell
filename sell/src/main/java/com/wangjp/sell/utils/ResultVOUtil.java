package com.wangjp.sell.utils;

import com.wangjp.sell.vo.ResultVO;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/3/2 9:36 下午
 * @detail ResultVO 工具包
 */
public class ResultVOUtil {

    public static <T> ResultVO<T> success(T object) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setData(object);
        resultVO.setStatus(200);
        resultVO.setMessage("成功");
        return resultVO;
    }

    public static ResultVO<Object> success() {
        return success(null);
    }

    public static ResultVO<Object> error(Integer status, String msg) {
        ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setStatus(status);
        resultVO.setMessage(msg);
        return resultVO;
    }

    public static ResultVO<Object> error() {
        ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setStatus(500);
        resultVO.setMessage("服务器错误");
        return resultVO;
    }
}

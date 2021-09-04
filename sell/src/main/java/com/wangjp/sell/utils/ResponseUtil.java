package com.wangjp.sell.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/9/4 7:58 下午
 * @detail 响应工具包
 */
@Slf4j
public class ResponseUtil {

    public static void renderJson(HttpServletResponse response, Object data) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(200);

            response.getWriter().write(JSONUtil.toJsonStr(new JSONObject(data, false)));
        } catch (IOException e) {
            log.error("Response写JSON时异常：", e);
        }
    }
}

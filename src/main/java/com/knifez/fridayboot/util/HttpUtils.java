package com.knifez.fridayboot.util;

import cn.hutool.json.JSONUtil;
import com.knifez.fridayboot.model.HttpResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author KnifeZ
 */
public class HttpUtils {

    /**
     * 获取HttpServletRequest对象
     *
     * @return request
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 输出信息到浏览器
     *
     * @param response response
     * @param data     参数
     * @throws IOException exception
     */
    public static void write(HttpServletResponse response, Object data) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        HttpResult result = HttpResult.ok(data);
        String json = JSONUtil.toJsonStr(result);
        response.getWriter().print(json);
        response.getWriter().flush();
        response.getWriter().close();
    }
}

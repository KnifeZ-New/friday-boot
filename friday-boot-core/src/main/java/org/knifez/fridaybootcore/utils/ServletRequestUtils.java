package org.knifez.fridaybootcore.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ServletRequestUtils {

    /**
     * 获取servlet主机
     *
     * @return {@link String} scheme://server:port
     */
    public static String getServletHost() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        // 过滤80端口
        if (request.getServerPort() == 80) {
            return request.getScheme() + "://" + request.getServerName();
        }
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }
}

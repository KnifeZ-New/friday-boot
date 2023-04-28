package org.knifez.fridaybootcore.utils;

import cn.hutool.extra.servlet.ServletUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.xml.bind.DatatypeConverter;
import org.knifez.fridaybootcore.constants.AppConstants;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;

public class ServletRequestUtils {

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        return requestAttributes.getRequest();
    }

    /**
     * 获取servlet主机
     *
     * @return {@link String} scheme://server:port
     */
    public static String getServletHost() {
        var request = getRequest();
        // 过滤80端口
        if (request.getServerPort() == 80) {
            return request.getScheme() + "://" + request.getServerName();
        }
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }

    public static String getUserAgent() {
        var request = getRequest();
        String ua = request.getHeader("User-Agent");
        return ua != null ? ua : "";
    }
}

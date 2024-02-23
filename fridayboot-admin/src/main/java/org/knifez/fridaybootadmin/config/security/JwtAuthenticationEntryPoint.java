package org.knifez.fridaybootadmin.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.knifez.fridaybootcore.dto.FridayResult;
import org.knifez.fridaybootcore.enums.ResultStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author KnifeZ
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        String body = FridayResult.fail(ResultStatus.UNAUTHORIZED).toString();
        printWriter.write(body);
        printWriter.flush();
    }

}

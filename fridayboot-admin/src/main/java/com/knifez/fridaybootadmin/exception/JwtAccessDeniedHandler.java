package com.knifez.fridaybootadmin.exception;

import com.knifez.fridaybootcore.dto.FridayResult;
import com.knifez.fridaybootcore.enums.ResultStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zhang
 */
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        String body = FridayResult.fail(ResultStatus.FORBIDDEN).toString();
        printWriter.write(body);
        printWriter.flush();
    }
}

package com.roc.demo.common.security.component;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.ContentType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roc.demo.common.core.constant.CommonConstants;
import com.roc.demo.common.core.utils.R;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

/**
 * @Description 客户端异常处理 AuthenticationException 不同细化异常处理
 * @Author dongp
 * @Date 20
 * 22/6/30 0030 14:17
 */
@RequiredArgsConstructor
public class ResourceAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(ContentType.build(ContentType.JSON, CharsetUtil.CHARSET_UTF_8));
        R<String> result = new R<>();
        result.setCode(CommonConstants.FAIL);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        if (authException != null) {
            result.setMsg("error");
            result.setData(authException.getMessage());
        }

        // 针对令牌过期返回特殊的424
        if (authException instanceof InsufficientAuthenticationException) {
            response.setStatus(HttpStatus.FAILED_DEPENDENCY.value());
            result.setMsg("token expire");
        }
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(result));
    }
}

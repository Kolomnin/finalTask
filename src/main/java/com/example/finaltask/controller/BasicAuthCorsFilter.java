package com.example.finaltask.controller;


import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class BasicAuthCorsFilter extends OncePerRequestFilter {

    /**
     * Этот метод является реализацией метода doFilterInternal() из класса Filter.
     *
     * Он добавляет заголовок "Access-Control-Allow-Credentials" к объекту HttpServletResponse и
     * затем переходит к вызову следующего фильтра в цепочке, вызывая метод doFilter() для объекта FilterChain.
     *
     * @param httpServletRequest объект HttpServletRequest, представляющий запрос клиента
     * @param httpServletResponse объект HttpServletResponse, представляющий ответ, который будет отправлен клиенту
     * @param filterChain объект FilterChain, представляющий цепочку применяемых фильтров
     *
     * @throws ServletException, если в фильтре или сервлете возникает какая-либо ошибка
     * @throws IOException, если при обработке запроса или ответа возникает ошибка ввода-вывода.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

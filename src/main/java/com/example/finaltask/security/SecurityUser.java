package com.example.finaltask.security;

import com.example.finaltask.model.dto.SecurityUserDto;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Component
@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SecurityUser implements UserDetails {

    private SecurityUserDto securityUserDto;

    public void setUserDto(SecurityUserDto securityUserDto) {
        this.securityUserDto = securityUserDto;
    }

    /**
     * Извлекает полномочия, предоставленные пользователю.
     *
     * @return набор полномочий, предоставленных пользователю
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(securityUserDto)
                .map(SecurityUserDto::getRole)
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .map(Collections::singleton)
                .orElse(Collections.emptySet());
    }
    /**
     * Извлекает пароль из SecurityUserDto.
     *
     * @return Пароль, если он доступен, иначе null.
     */
    @Override
    public String getPassword() {
        return Optional.ofNullable(securityUserDto)
                .map(SecurityUserDto::getPassword)
                .orElse(null);
    }

    /**
     * Возвращает имя пользователя, связанное с этим пользователем.
     *
     * @вернуть имя пользователя в виде строки или null, если оно недоступно
     */
    @Override
    public String getUsername() {
        return Optional.ofNullable(securityUserDto)
                .map(SecurityUserDto::getEmail)
                .orElse(null);
    }

    /**
     * Возвращает имя пользователя, связанное с этим пользователем.
     *
     * @вернуть имя пользователя в виде строки или null, если оно недоступно
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Возвращает логическое значение, указывающее, заблокирована учетная запись пользователя или нет.
     *
     * @return {@code true}, если учетная запись пользователя не заблокирована, {@code false} в противном случае.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Этот метод проверяет, не просрочены ли учетные данные пользователя.
     *
     * @return true, если срок действия учетных данных не истек, иначе false
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Возвращает, включен компонент или нет.
     *
     * @return true, если компонент включен, иначе false
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
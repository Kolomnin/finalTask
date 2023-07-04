package com.example.finaltask.security;

import com.example.finaltask.mapping.UserMapper;
import com.example.finaltask.model.dto.SecurityUserDto;
import com.example.finaltask.model.entity.User;
import com.example.finaltask.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SecurityUser securityUser;

    UserDetailsServiceImp(UserRepository userRepository, UserMapper userMapper, SecurityUser securityUser) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.securityUser = securityUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));;
        SecurityUserDto securityUserDto = userMapper.toSecurityDto(user);
        securityUser.setUserDto(securityUserDto);
        return securityUser;
    }
}

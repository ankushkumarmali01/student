package com.ankush.sms.security;

import com.ankush.sms.entity.Admin;
import com.ankush.sms.repository.AdminRepository;
import com.ankush.sms.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        return adminRepository.findByUsername(username)
                .map(admin -> User.builder()
                        .username(admin.getUsername())
                        .password(admin.getPassword())
                        .authorities(admin.getRole().name())
                        .build())
                .orElseGet(() ->
                        studentRepository.findByStudentCode(username)
                                .map(student -> User.builder()
                                        .username(student.getStudentCode())
                                        .password("")
                                        .authorities("ROLE_STUDENT")
                                        .build())
                                .orElseThrow(() ->
                                        new UsernameNotFoundException(
                                                "User not found")));
    }
}
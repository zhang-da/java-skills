package com.da.learn.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("da".equals(username)) {
            List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admins, ROLE_role1");
            return new User("da", new BCryptPasswordEncoder().encode("da"), auths);
        } else if ("zhang".equals(username)) {
            List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("managers, ROLE_role2");
            return new User("zhang", new BCryptPasswordEncoder().encode("zhang"), auths);
        } else {
            //模拟未查到
            throw new UsernameNotFoundException("没有该用户");
        }
    }
}

package com.ndirituedwin.Service;

import com.ndirituedwin.Model.User;
import com.ndirituedwin.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor
public class UserDetailsServiceimpl implements UserDetailsService {
    public final UserRepository userRepository;
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> useroptional= userRepository.findByUsername(username);
        User user=useroptional.orElseThrow(() -> new UsernameNotFoundException("username " + username+ "could not be found "));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),user.isEnabled(),true,true,true,getAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String user) {
        return Collections.singletonList(new SimpleGrantedAuthority(user));
    }
}

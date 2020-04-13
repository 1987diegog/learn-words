package com.demente.ideas.learnwords.model.services;

import com.demente.ideas.learnwords.model.domain.entity.Role;
import com.demente.ideas.learnwords.model.domain.entity.User;
import com.demente.ideas.learnwords.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 1987diegog
 */
@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);

    @Autowired
    private IUserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("[AUTHENTICATION] - Se consulta si el usuario exite en BD findByUsername: " + username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("[AUTHENTICATION] - User not exist: " + username));

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            logger.info("Role:".concat(role.getAuthority().name()));
            authorities.add(new SimpleGrantedAuthority(role.getAuthority().name()));
        }

        if (authorities.isEmpty()) {
            logger.error("[AUTHENTICATION] - Error login: User " + username + ", dont have any roles");
            throw new UsernameNotFoundException("[AUTHENTICATION] - User " + username + ", dont have any roles");
        }

        // Return a userdetails type from Spring
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true, true, authorities);
    }
}

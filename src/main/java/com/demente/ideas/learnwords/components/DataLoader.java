package com.demente.ideas.learnwords.components;

import com.demente.ideas.learnwords.model.Role;
import com.demente.ideas.learnwords.model.RoleName;
import com.demente.ideas.learnwords.model.User;
import com.demente.ideas.learnwords.repository.RoleRepository;
import com.demente.ideas.learnwords.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public DataLoader(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) {

        System.out.println("[INIT_DATA] - LOADING DATA....");
        Role roleAdmin = roleRepository.save(new Role(RoleName.ROLE_ADMIN));
        Role roleUser = roleRepository.save(new Role(RoleName.ROLE_USER));
        System.out.println("[INIT_DATA] - INSERTS ROLES OK");

        User userAdmin = new User("Diego", "1987diegog", "1987diegog@gmail.com", "123");
        Set<Role> rolesAdmin = new HashSet<>();
        rolesAdmin.add(roleAdmin);
        rolesAdmin.add(roleUser);
        userAdmin.setRoles(rolesAdmin);

        User user = new User("Silvia", "silnarbaiz", "silnarbaiz@gmail.com", "456");
        Set<Role> rolesUser = new HashSet<>();
        rolesUser.add(roleUser);
        user.setRoles(rolesUser);

        userRepository.save(userAdmin);
        userRepository.save(user);
        System.out.println("[INIT_DATA] - INSERTS USERS OK");
        System.out.println("[INIT_DATA] - DATA LOADER OK");
    }
}
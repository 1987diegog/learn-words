package com.demente.ideas.learnwords.components;

import com.demente.ideas.learnwords.model.domain.entity.Role;
import com.demente.ideas.learnwords.model.domain.RoleName;
import com.demente.ideas.learnwords.model.domain.entity.User;
import com.demente.ideas.learnwords.repository.IRoleRepository;
import com.demente.ideas.learnwords.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 1987diegog
 */
@Component
public class DataLoader implements ApplicationRunner {

    private IUserRepository userRepository;
    private IRoleRepository roleRepository;

    @Autowired
    public DataLoader(IUserRepository userRepository, IRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        chargeUserAndRoles();

        System.out.println("[INIT_DATA] - LOADING DATA....");
        Role roleAdmin = roleRepository.save(new Role(RoleName.ROLE_ADMIN));
        Role roleUser = roleRepository.save(new Role(RoleName.ROLE_USER));
        System.out.println("[INIT_DATA] - INSERTS ROLES OK");

    }

    private void chargeUserAndRoles() {

        System.out.println("[INIT_DATA] - LOADING DATA....");
        Role roleAdmin = roleRepository.save(new Role(RoleName.ROLE_ADMIN));
        Role roleUser = roleRepository.save(new Role(RoleName.ROLE_USER));
        System.out.println("[INIT_DATA] - INSERTS ROLES OK");

        User userAdmin = new User(null, "Diego", "Gonzalez", "admin",
                "$2a$10$5kMK9E16nhbIXdi2GJcJp.ClfPLLYpsuAFWuoz3PizbPGEIW0UAAK", "1987diegog@gmail.com");
        Set<Role> rolesAdmin = new HashSet<>();
        rolesAdmin.add(roleAdmin);
        rolesAdmin.add(roleUser);
        userAdmin.setRoles(rolesAdmin);

        User user = new User(null, "Silvia", "silnarbaiz", "user",
                "$2a$10$w62honFZVoxpxVHxw6mrFuPbkvR47ACTTwviP7epKzpRXyZlmPDue", "silnarbaiz@gmail.com");
        Set<Role> rolesUser = new HashSet<>();
        rolesUser.add(roleUser);
        user.setRoles(rolesUser);

        userRepository.save(userAdmin);
        userRepository.save(user);

        System.out.println("[INIT_DATA] - INSERTS USERS OK");
        System.out.println("[INIT_DATA] - DATA LOADER OK");
    }
}
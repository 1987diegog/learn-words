package com.demente.ideas.learnwords.repository;

import com.demente.ideas.learnwords.model.Role;
import com.demente.ideas.learnwords.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
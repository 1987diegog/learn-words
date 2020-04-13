package com.demente.ideas.learnwords.repository;

import com.demente.ideas.learnwords.model.domain.entity.Role;
import com.demente.ideas.learnwords.model.domain.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author 1987diegog
 */
@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByAuthority(RoleName authority);
}
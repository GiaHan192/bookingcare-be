package com.company.myweb.repository;

import com.company.myweb.entity.Question;
import com.company.myweb.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}

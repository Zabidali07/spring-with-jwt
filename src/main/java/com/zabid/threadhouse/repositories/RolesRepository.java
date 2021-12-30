package com.zabid.threadhouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zabid.threadhouse.models.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long> {

	Roles findByRole(String role);
}

package com.zabid.threadhouse.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ROLES")
public class Roles {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ROLE_ID")
	private long roleId;
	
	@Column(name="ROLE_NAME")
	private String role;

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}

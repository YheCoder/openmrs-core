package org.openmrs.api;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.openmrs.Privilege;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.api.hibernate.HibernateUtil;
import org.openmrs.context.Context;
import org.openmrs.context.ContextAuthenticationException;
import org.openmrs.context.ContextFactory;

public class UserServiceTest extends TestCase {
	
	protected UserService us;
	protected Context context;
	
	public void setUp(){
		context = ContextFactory.getContext();
		
		try {
			context.authenticate("admin", "test");
		} catch (ContextAuthenticationException e) {
			
		}
		
		us = context.getUserService();
	}

	public void testUpdateUser() {
		assertTrue(context.isAuthenticated());
		User u = us.getUserByUsername("bwolfe");
		if (u == null)
			u = new User();
		u.setFirstName("Benjamin");
		u.setMiddleName("Alexander");
		u.setLastName("Wolfe");
		u.setUsername("bwolfe");
//		u.setRoles(new HashSet());
		
		us.updateUser(u);
		
		User u2 = us.getUserByUsername("bwolfe");
		
		assertTrue(u.equals(u2));
		
		//int len = u.getRoles().size();
		//System.out.println("length: " + len);
		
		Role role1 = new Role();
		role1.setDescription("testing1");
		role1.setRole("test1");
		Privilege p1 = us.getPrivileges().get(0);
		Set privileges1 = new HashSet();
		privileges1.add(p1);
		role1.setPrivileges(privileges1);
		
		Role role2 = new Role();
		role2.setDescription("testing2");
		role2.setRole("test2");
		Privilege p2 = us.getPrivileges().get(1);
		Set privileges2 = new HashSet();
		privileges2.add(p2);
		role2.setPrivileges(privileges2);
		
		us.grantUserRole(u, role1);
		us.grantUserRole(u, role2);
		
		System.out.println("Roles: " + u.getRoles().toString());
	}
	
	public static Test suite() {
		return new TestSuite(UserServiceTest.class, "Basic UserService functionality");
	}

}

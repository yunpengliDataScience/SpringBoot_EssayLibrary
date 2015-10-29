package com.library.essay.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import com.library.essay.persistence.entities.User;
import com.library.essay.persistence.entities.UserRole;
import com.library.essay.services.UserRoleService;
import com.library.essay.services.UserService;

/**
 * If a bean implements the ApplicationListener, then every time an
 * ApplicationEvent gets published to the ApplicationContext, that bean is
 * notified. ContextRefreshedEvent: This event is published when the
 * ApplicationContext is either initialized or refreshed. This can also be
 * raised using the refresh() method on the ConfigurableApplicationContext
 * interface. It can be used to perform some tasks after Spring
 * ApplicationContext is initialized.
 * 
 * @author yunpeng.li
 * 
 */
@Component
public class InitializeTasksListener implements
		ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRoleService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {

		seedSpringSecurityJDBCTables();

		printUserRoles();
	}

	private void printUserRoles() {

		List<User> users = userService.getUsers();

		System.out.println("==============Users=====================");
		for (User u : users) {
			System.out.println(u);
		}
		System.out.println("==============End Users=====================");
	}

	private void seedSpringSecurityJDBCTables() {

		// admin: ROLE_USER, ROLE_SUPER, ROLE_ADMIN
		if (userService.getUser("admin") == null) {

			User admin = new User();
			admin.setEnabled(true);
			admin.setUserName("admin");
			admin.setPassword("admin");

			userService.saveOrUpdate(admin);

			UserRole role1 = new UserRole();
			role1.setRole("ROLE_USER");
			// admin.addRole(role1);
			role1.setUser(admin);
			userRoleService.saveOrUpdate(role1);

			UserRole role2 = new UserRole();
			role2.setRole("ROLE_SUPER");
			// admin.addRole(role2);
			role2.setUser(admin);
			userRoleService.saveOrUpdate(role2);

			UserRole role3 = new UserRole();
			role3.setRole("ROLE_ADMIN");
			// admin.addRole(role3);
			role3.setUser(admin);
			userRoleService.saveOrUpdate(role3);
		}

		// yunpeng: ROLE_USER, ROLE_SUPER
		if (userService.getUser("yunpeng") == null) {

			User yunpeng = new User();
			yunpeng.setEnabled(true);
			yunpeng.setUserName("yunpeng");
			yunpeng.setPassword("yunpeng");

			userService.saveOrUpdate(yunpeng);

			UserRole role1 = new UserRole();
			role1.setRole("ROLE_USER");
			role1.setUser(yunpeng);
			userRoleService.saveOrUpdate(role1);

			yunpeng.addRole(role1);

			UserRole role2 = new UserRole();
			role2.setRole("ROLE_SUPER");
			role2.setUser(yunpeng);
			userRoleService.saveOrUpdate(role2);

			yunpeng.addRole(role2);

		}

		// bob: ROLE_USER
		if (userService.getUser("bob") == null) {

			User bob = new User();
			bob.setEnabled(true);
			bob.setUserName("bob");
			bob.setPassword("bob");

			userService.saveOrUpdate(bob);

			UserRole role1 = new UserRole();
			role1.setRole("ROLE_USER");
			role1.setUser(bob);
			userRoleService.saveOrUpdate(role1);

			bob.addRole(role1);

		}

	}

}

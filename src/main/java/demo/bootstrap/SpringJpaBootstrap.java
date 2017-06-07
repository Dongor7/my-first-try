package demo.bootstrap;

import demo.domain.Post;
import demo.domain.Role;
import demo.domain.User;
import demo.repository.PostRepository;
import demo.service.RoleService;
import demo.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class SpringJpaBootstrap implements ApplicationListener<ContextRefreshedEvent>{

    private PostRepository postRepository;
    private UserService userService;
    private RoleService roleService;

    private Logger logger = Logger.getLogger(SpringJpaBootstrap.class);

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadPosts();
        loadUsers();
        loadRoles();
        assignUsersToUserRole();
        assignUsersToAdminRole();
    }

    private void loadPosts(){
        Post first = new Post();
        first.setTitle("First Post");
        first.setDescription("My first post");
        postRepository.save(first);

        logger.info("Saved - " + first.getTitle());

        Post second = new Post();
        second.setTitle("Second Post");
        second.setDescription("My second post");
        postRepository.save(second);

        logger.info("Saved - " + second.getTitle());
    }

    private void loadUsers(){
        User user1 = new User();
        user1.setUsername("Denis");
        user1.setPassword("123456");
        userService.saveOrUpdate(user1);

        User user2 = new User();
        user2.setUsername("Chuck");
        user2.setPassword("123456");
        userService.saveOrUpdate(user2);
    }

    private void loadRoles(){
        Role role = new Role();
        role.setRole("USER");
        roleService.saveOrUpdate(role);
        logger.info("Saved role - " + role.getRole());

        Role admin = new Role();
        admin.setRole("ADMIN");
        roleService.saveOrUpdate(admin);
        logger.info("Saved role - " + admin.getRole());
    }

    private void assignUsersToUserRole(){
        List<Role> roles = (List<Role>) roleService.listAll();
        List<User> users = (List<User>) userService.listAll();

        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("USER")) {
              users.forEach(user -> {
                  if (user.getUsername().equalsIgnoreCase("Denis")){
                      user.addRole(role);
                      userService.saveOrUpdate(user);
                  }
              });
            }
        });
    }

    private void assignUsersToAdminRole() {
        List<Role> roles = (List<Role>) roleService.listAll();
        List<User> users = (List<User>) userService.listAll();

        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("ADMIN")) {
                users.forEach(user -> {
                    if (user.getUsername().equals("Chuck")) {
                        user.addRole(role);
                        userService.saveOrUpdate(user);
                    }
                });
            }
        });
    }
}

package demo.service;


import demo.domain.User;

public interface UserService extends CRUDService<User>{

    User findByUsername(String username);

}

package com.alessoft.LoginManager.Repo;

import com.alessoft.LoginManager.Model.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepo extends CrudRepository<User, Long> {
    User findByUsername(String username);
}

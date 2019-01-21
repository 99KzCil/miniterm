package com.alessoft;

import java.util.TimeZone;

import com.alessoft.LoginManager.Model.User;
import com.alessoft.LoginManager.Repo.UserRepo;
import com.alessoft.LoginManager.Utils.AES;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {
    {
        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private UserRepo userRepo;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        User niyazi = userRepo.findByUsername("niyazi");
        if (niyazi != null) {
            userRepo.delete(niyazi);
        }
        if (userRepo.count() > 0) return;
        System.out.println("add new user");
        User user = new User();
        user.setUsername("admin");
        user.setPassword(AES.encode("admin"));
        user.setType("admin");
        userRepo.save(user);
    }

}

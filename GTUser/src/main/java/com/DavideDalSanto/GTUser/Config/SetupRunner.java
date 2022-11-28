package com.DavideDalSanto.GTUser.Config;

import com.DavideDalSanto.GTUser.Entities.GTUser;
import com.DavideDalSanto.GTUser.Entities.JWTUser;
import com.DavideDalSanto.GTUser.Entities.Role;
import com.DavideDalSanto.GTUser.Services.GTUserService;
import com.DavideDalSanto.GTUser.Services.JWTUserService;
import com.DavideDalSanto.GTUser.Services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SetupRunner implements CommandLineRunner {

    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(GTUserConfig.class);

    @Autowired
    RoleService rs;

    @Autowired
    JWTUserService us;

    @Override
    public void run(String... args) throws Exception {

        log.info("RUNNER SETUP START");

//        manageBeans();

        log.info("RUNNER SETUP END");

    }

    private void manageBeans() {

        rs.save(ctx.getBean("rAdmin", Role.class));
        rs.save(ctx.getBean("rGTUser", Role.class));
        rs.save(ctx.getBean("rPT", Role.class));

        us.save(ctx.getBean("user1", GTUser.class));

    }
}

package com.DavideDalSanto.GTUser.Config;

import com.DavideDalSanto.GTUser.Entities.GTUser;
import com.DavideDalSanto.GTUser.Entities.JWTUser;
import com.DavideDalSanto.GTUser.Entities.Role;
import com.DavideDalSanto.GTUser.Entities.RoleType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class GTUserConfig {

    @Bean(name = "rAdmin")
    @Scope("singleton")
    public Role roleAdmin() {
        return new Role(RoleType.ROLE_ADMIN);
    }

    @Bean(name = "rGTUser")
    @Scope("singleton")
    public Role roleUser() {
        return new Role(RoleType.ROLE_GTUSER);
    }

    @Bean(name = "rPT")
    @Scope("singleton")
    public Role rolePT() {
        return new Role(RoleType.ROLE_GTPERSONALTRAINER);
    }

    @Bean
    @Scope("singleton")
    public List<Long> pids(){
        return new ArrayList<Long>(Arrays.asList(2L, 3L));
    }

    @Bean(name = "user1")
    @Scope("singleton")
    public JWTUser user1(){
        GTUser u = new GTUser();
        u.setUsername("ajeje");
        u.setEmail("dds@ds.dds");
        u.setPassword("test");
        u.setName("Davide");
        u.setLastname("Dal Santo");
        u.getRoles().add(roleAdmin());
        u.getRoles().add(roleUser());
        u.getRoles().add(rolePT());
        u.setUserPlansIds(pids());

        return u;
    }

}

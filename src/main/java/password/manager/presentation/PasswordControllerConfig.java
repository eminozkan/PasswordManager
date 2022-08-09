package password.manager.presentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import password.manager.business.PasswordService;
import password.manager.persistence.PasswordRepo;

@Configuration
public class PasswordControllerConfig {

    @Bean
    public PasswordRepo passwordRepo(){
        return new PasswordRepo();
    }


    @Bean
    public PasswordService passwordService(){
        return new PasswordService(passwordRepo());
    }

}

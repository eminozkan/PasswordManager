package password.manager.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import password.manager.business.password.Password;
import password.manager.persistence.AddPasswordResults;
import password.manager.persistence.PasswordRepo;

import java.util.UUID;


@Service
public class PasswordService {
    private PasswordRepo repo;

    @Autowired
    public PasswordService(PasswordRepo repo){
        this.repo = repo;
    }
    private Boolean isPasswordTitleEmpty(Password pass){
        return ObjectUtils.isEmpty(pass.getTitle());
    }

    public AddPasswordResults savePassword(Password pass){
        if(isPasswordTitleEmpty(pass)){
            return AddPasswordResults.TITLE_IS_NULL;
        }
        pass.setId(UUID.randomUUID().toString());
        repo.save(pass);
        return AddPasswordResults.SUCCESS;
    }

}

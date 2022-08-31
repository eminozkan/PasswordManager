package password.manager.business;

import org.springframework.util.ObjectUtils;
import password.manager.business.password.Password;
import password.manager.business.results.PasswordOperationResults;
import password.manager.persistence.PasswordRepository;

import java.util.List;
import java.util.UUID;

public class DefaultPasswordService implements PasswordService {
    private final PasswordRepository passwordRepository;

    public DefaultPasswordService(PasswordRepository repository){
        this.passwordRepository = repository;
    }

    public PasswordOperationResults savePassword(Password password) {
        if(ObjectUtils.isEmpty(password.getTitle())) {
            return PasswordOperationResults.TITLE_IS_NULL;
        }
        if(passwordRepository.isPasswordTitleExist(password.getTitle())){
            return PasswordOperationResults.TITLE_EXISTS;
        }

        password.setId(UUID.randomUUID().toString());
        passwordRepository.save(password);
        return PasswordOperationResults.SUCCESS;
    }

    @Override
    public List<Password> listPassword() {
        return null;
    }

    @Override
    public List<Password> listPasswordByDirectory(String directoryName) {
        return null;
    }

    @Override
    public PasswordOperationResults updatePassword(String id, Password newPass) {
        return null;
    }

    @Override
    public void deletePassword(String id) {

    }

    @Override
    public Password getPasswordById(String id, Boolean reveal) {
        return null;
    }
}

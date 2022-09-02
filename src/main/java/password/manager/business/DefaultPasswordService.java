package password.manager.business;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import password.manager.business.password.Password;
import password.manager.business.results.PasswordOperationResults;
import password.manager.persistence.PasswordRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static password.manager.business.results.PasswordOperationResults.*;

@Service
public class DefaultPasswordService implements PasswordService {
    private final PasswordRepository passwordRepository;

    public DefaultPasswordService(PasswordRepository repository) {
        this.passwordRepository = repository;
    }

    @Override
    public PasswordOperationResults savePassword(Password password) {
        if (ObjectUtils.isEmpty(password.getTitle())) {
            return TITLE_IS_NULL;
        }
        if (passwordRepository.isPasswordTitleExist(password.getTitle())) {
            return TITLE_EXISTS;
        }

        password.setId(UUID.randomUUID().toString());
        passwordRepository.save(password);
        return SUCCESS;
    }
    private Password mergePasswordsToCreateUpdatedPassword(Password oldPass, Password newPass) {
        Password updatedPassword = new Password();

        if (!ObjectUtils.isEmpty(newPass.getTitle())) {
            updatedPassword.setTitle(newPass.getTitle());
        } else {
            updatedPassword.setTitle(oldPass.getTitle());
        }

        if (!ObjectUtils.isEmpty(newPass.getDirectoryName())) {
            updatedPassword.setDirectoryName(newPass.getDirectoryName());
        } else {
            updatedPassword.setDirectoryName(oldPass.getDirectoryName());
        }

        if (!ObjectUtils.isEmpty(newPass.getUsername())) {
            updatedPassword.setUsername(newPass.getUsername());
        } else {
            updatedPassword.setUsername(oldPass.getUsername());
        }

        if (!ObjectUtils.isEmpty(newPass.getPassword())) {
            updatedPassword.setPassword(newPass.getPassword());
        } else {
            updatedPassword.setPassword(oldPass.getPassword());
        }

        if (!ObjectUtils.isEmpty(newPass.getNotes())) {
            updatedPassword.setNotes(newPass.getNotes());
        } else {
            updatedPassword.setNotes(oldPass.getNotes());
        }

        if (!ObjectUtils.isEmpty(newPass.getUrl())) {
            updatedPassword.setUrl(newPass.getUrl());
        } else {
            updatedPassword.setUrl(oldPass.getUrl());
        }

        return updatedPassword;
    }

    @Override
    public PasswordOperationResults updatePassword(String id, Password newPassword) {
        Password oldPassword = passwordRepository.findById(id);
        if (oldPassword == null) {
            return PASSWORD_NOT_EXISTS;
        }
        boolean arePasswordsTitleSame = oldPassword.getTitle().equals(newPassword.getTitle());
        if (!arePasswordsTitleSame && passwordRepository.isPasswordTitleExist(newPassword.getTitle())) {
            return TITLE_EXISTS;
        }
        Password updatedPassword = mergePasswordsToCreateUpdatedPassword(oldPassword, newPassword);
        updatedPassword.setId(id);
        passwordRepository.update(updatedPassword);
        return SUCCESS;
    }

    @Override
    public void deletePassword(String id) {
        passwordRepository.deleteById(id);
    }

    private List<Password> hidePasswordFields(List<Password> passwordList) {
        List<Password> passwords = new ArrayList<>();
        for (Password p : passwordList) {
            Password pass = new Password(p);
            passwords.add(hidePasswordField(pass));
        }
        return passwords;
    }

    @Override
    public List<Password> listPassword() {
        return hidePasswordFields(passwordRepository.list());
    }

    @Override
    public List<Password> listPasswordByDirectory(String directoryName) {
        List<Password> passwords = passwordRepository.list();
        List<Password> filteredPasswords = new ArrayList<>();
        for (Password p : passwords) {
            if (p.getDirectoryName().equals(directoryName)) {
                filteredPasswords.add(p);
            }
        }
        return hidePasswordFields(filteredPasswords);
    }

    private Password hidePasswordField(Password pass) {
        Password password = new Password(pass);
        password.setPassword("*".repeat(pass.getPassword().length()));
        return password;
    }

    @Override
    public Password getPasswordById(String id, Boolean reveal) throws PasswordException {
        Password fromDb = passwordRepository.findById(id);
        if (fromDb == null) {
            throw new PasswordException("Password not exist");
        }
        if (reveal) {
            return fromDb;
        }
        return hidePasswordField(fromDb);
    }

    public static class PasswordException extends Exception {
        public PasswordException(String msg) {
            super(msg);
        }
    }
}

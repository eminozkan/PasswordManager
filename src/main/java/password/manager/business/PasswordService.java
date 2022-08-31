package password.manager.business;

import password.manager.business.password.Password;
import password.manager.business.results.PasswordOperationResults;

import java.util.List;

public interface PasswordService {
    PasswordOperationResults savePassword(Password pass);

    List<Password> listPassword();

    List<Password> listPasswordByDirectory(String directoryName);

    PasswordOperationResults updatePassword(String id, Password newPass);

    void deletePassword(String id);

    Password getPasswordById(String id, Boolean reveal);

}

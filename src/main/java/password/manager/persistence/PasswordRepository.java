package password.manager.persistence;

import password.manager.business.password.Password;

import java.util.List;

public interface PasswordRepository {

    void save(Password password);

    void update(Password password);

    void deleteById(String id);

    List<Password> list();

    Password findById(String id);

    Boolean isPasswordTitleExist(String title);

    Boolean isPasswordExists(String id);


}

package password.manager.persistence;

import password.manager.business.password.Password;

import java.util.List;

public interface PasswordRepository {

    public void save(Password password);

    public void update(Password password);

    public void deleteById(String id);

    public List<Password> list();

    public Password findById(String id);

    public Boolean isPasswordTitleExist(String title);


}

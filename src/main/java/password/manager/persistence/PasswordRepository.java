package password.manager.persistence;

import password.manager.business.password.Password;

import java.util.List;

public interface PasswordRepository {

    public void save(Password password);

    public void update(Password password);

    public void changeDirectory(String id,String directoryName);

    public void deleteById(String id);

    public List<Password> listByDirectory(String directoryName);

    public List<Password> list();

    public Password findById(String id);

    public Boolean isPasswordTitleExist(String title);

    public Boolean isPasswordExists(String id);


}

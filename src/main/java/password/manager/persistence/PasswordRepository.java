package password.manager.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PasswordRepository {

    public void save(Password password);

    public void update(Password password);

    public void deleteById(String id);

    public List<Password> list();

    public Password findById(String id);

    public Boolean isPasswordExists(String id);


}

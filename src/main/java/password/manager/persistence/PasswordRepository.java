package password.manager.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PasswordRepository {

    public AddPasswordResults save(Map<String,Password> password);

    public void update();

    public void deleteById(String id);

    public List<Password> list();

    public Password findById(String id);

    public Boolean isPasswordExists(String id);


}

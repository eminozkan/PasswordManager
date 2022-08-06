package password.manager.persistence;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface PasswordRepository {
    public Map<String,Password> passwords = new HashMap<>();

}

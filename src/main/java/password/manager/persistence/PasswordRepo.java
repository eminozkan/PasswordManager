package password.manager.persistence;

public interface PasswordRepo {

    public void save(Password password);

    public void update();

}

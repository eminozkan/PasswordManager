package password.manager.business.password;


import java.util.Objects;

public class Password {
    private String id;
    private String directoryName;
    private String title;
    private String username;
    private String password;
    private String notes;
    private String url;

    public Password(){}

    public Password(Password pass){
        this.id = pass.getId();
        this.directoryName = pass.getDirectoryName();
        this.title = pass.getTitle();
        this.username = pass.getUsername();
        this.password = pass.getPassword();
        this.notes = pass.getNotes();
        this.url = pass.getUrl();
    }

    public String getId() {
        return id;
    }

    public Password setId(String id) {
        this.id = id;
        return this;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public Password setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Password setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Password setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Password setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public Password setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Password setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password1 = (Password) o;
        return Objects.equals(id, password1.id) && Objects.equals(directoryName, password1.directoryName) && Objects.equals(title, password1.title) && Objects.equals(username, password1.username) && Objects.equals(password, password1.password) && Objects.equals(notes, password1.notes) && Objects.equals(url, password1.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, directoryName, title, username, password, notes, url);
    }
}

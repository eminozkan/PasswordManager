package password.manager.business.password;

public class GeneratedPassword {

    private int length;

    private String password;

    private String passwordId;

    private boolean hasSpecialCharacters;

    private boolean hasNumericCharacters;

    private boolean hasUpperCaseCharacters;

    private boolean hasLowerCaseCharacters;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordId() {
        return passwordId;
    }

    public void setPasswordId(String passwordId) {
        this.passwordId = passwordId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getHasSpecialCharacters() {
        return hasSpecialCharacters;
    }

    public void setHasSpecialCharacters(Boolean hasSpecialCharacters) {
        this.hasSpecialCharacters = hasSpecialCharacters;
    }

    public Boolean getHasNumericCharacters() {
        return hasNumericCharacters;
    }

    public void setHasNumericCharacters(Boolean hasNumericCharacters) {
        this.hasNumericCharacters = hasNumericCharacters;
    }

    public Boolean getHasUpperCaseCharacters() {
        return hasUpperCaseCharacters;
    }

    public void setHasUpperCaseCharacters(Boolean hasUpperCaseCharacters) {
        this.hasUpperCaseCharacters = hasUpperCaseCharacters;
    }

    public Boolean getHasLowerCaseCharacters() {
        return hasLowerCaseCharacters;
    }

    public void setHasLowerCaseCharacters(Boolean hasLowerCaseCharacters) {
        this.hasLowerCaseCharacters = hasLowerCaseCharacters;
    }

}
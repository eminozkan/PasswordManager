package password.manager.business.password;

public class PasswordGenerateOptions {

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

    public PasswordGenerateOptions setLength(int length) {
        this.length = length;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public PasswordGenerateOptions setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPasswordId() {
        return passwordId;
    }

    public PasswordGenerateOptions setPasswordId(String passwordId) {
        this.passwordId = passwordId;
        return this;
    }

    public boolean getHasSpecialCharacters() {
        return hasSpecialCharacters;
    }

    public PasswordGenerateOptions setHasSpecialCharacters(boolean hasSpecialCharacters) {
        this.hasSpecialCharacters = hasSpecialCharacters;
        return this;
    }

    public boolean getHasNumericCharacters() {
        return hasNumericCharacters;
    }

    public PasswordGenerateOptions setHasNumericCharacters(boolean hasNumericCharacters) {
        this.hasNumericCharacters = hasNumericCharacters;
        return this;
    }

    public boolean getHasUpperCaseCharacters() {
        return hasUpperCaseCharacters;
    }

    public PasswordGenerateOptions setHasUpperCaseCharacters(boolean hasUpperCaseCharacters) {
        this.hasUpperCaseCharacters = hasUpperCaseCharacters;
        return this;
    }

    public boolean getHasLowerCaseCharacters() {
        return hasLowerCaseCharacters;
    }

    public PasswordGenerateOptions setHasLowerCaseCharacters(boolean hasLowerCaseCharacters) {
        this.hasLowerCaseCharacters = hasLowerCaseCharacters;
        return this;
    }
}
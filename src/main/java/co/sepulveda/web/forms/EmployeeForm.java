package co.sepulveda.web.forms;

/**
 *
 * @author Carlos Sepulveda <ca.sepulveda.sanchez@gmail.com>
 */
public class EmployeeForm extends Form {

    private String name;
    private String email;
    private String personalId;
    private String phone;
    private String password;
    private String role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean validate() {
        return isNotEmpty(name)
                && isNotEmpty(email)
                && isNotEmpty(personalId)
                && isNotEmpty(phone)
                && isNotEmpty(password)
                && isNotEmpty(role)
                && ("admin".equals(role) || "employee".equals(role));
    }

}

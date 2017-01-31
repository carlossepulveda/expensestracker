package co.sepulveda.web.forms;

/**
 *
 * @author Carlos Sepulveda <ca.sepulveda.sanchez@gmail.com>
 */
public class TripForm extends Form {

    private String name;
    private String employeePersonalId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeePersonalId() {
        return employeePersonalId;
    }

    public void setEmployeePersonalId(String employeePersonalId) {
        this.employeePersonalId = employeePersonalId;
    }

    @Override
    public boolean validate() {
        return isNotEmpty(name)
                && isNotEmpty(employeePersonalId);
    }
}

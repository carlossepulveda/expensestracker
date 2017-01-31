package co.sepulveda.web.forms;

import co.sepulveda.core.trip.Expense;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Carlos Sepulveda <ca.sepulveda.sanchez@gmail.com>
 */
public class TripResponse {

    private long id;
    private long employeeId;
    private String employeePersonId;
    private String name;
    private Date creationTime;
    private String status;
    private Date endTime;
    private Set<Expense> expenses = new HashSet();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Set<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
    }

    public String getEmployeePersonId() {
        return employeePersonId;
    }

    public void setEmployeePersonId(String employeePersonId) {
        this.employeePersonId = employeePersonId;
    }
}

package co.sepulveda.core.trip;

import co.sepulveda.core.employee.Employee;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Carlos Sepulveda <ca.sepulveda.sanchez@gmail.com>
 */
@Entity
@Table(name = "trip")
public class Trip implements Serializable {

    public final static String STATUS_OPEN = "open";
    public final static String STATUS_CLOSED = "closed";
    private long id;
    private Employee employee;
    private String name;
    private Date creationTime;
    private String status;
    private Date endTime;
    private Set<Expense> expenses = new HashSet<>();

    @Id
    @Column(name = "id")
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    @ManyToOne
    @JoinColumn(insertable = true, referencedColumnName = "id", nullable = true, name = "employee_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @NotNull
    @Column(name = "name")
    @Length(max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "creation_time")
    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    @NotNull
    @Column(name = "status")
    @Length(max = 10)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Expense.class, orphanRemoval=true)
    @Cascade({CascadeType.ALL})
    @JoinColumn(name = "trip_id")
    public Set<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
    }

    public void addExpense(Expense expense) {
        if (expenses.contains(expense)) {
            expenses.remove(expense);
        }
        expenses.add(expense);
    }
}

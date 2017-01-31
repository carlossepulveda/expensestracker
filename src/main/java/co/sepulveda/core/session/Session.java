package co.sepulveda.core.session;

import co.sepulveda.core.employee.Employee;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 *
 * @author Carlos Sepulveda <ca.sepulveda.sanchez@gmail.com>
 */
@Entity
@Table(name = "sessions_", uniqueConstraints = @UniqueConstraint(columnNames = "token"))
public class Session implements Serializable {

    private long id;
    private String token;
    private Employee employee;

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
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
}

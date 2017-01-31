package co.sepulveda.core.trip;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Carlos Sepulveda <ca.sepulveda.sanchez@gmail.com>
 */
@Entity
@Table(name = "expense")
public class Expense implements Serializable {

    private long id;
    private String tag;
    private float amount;

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
    @Column(name = "tag")
    @Length(max = 15)
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Column(name = "amount")
    @NotNull
    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}

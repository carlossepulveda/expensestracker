package co.sepulveda.web.forms;

/**
 *
 * @author Carlos Sepulveda <ca.sepulveda.sanchez@gmail.com>
 */
public class ExpenseForm extends Form {

    private String tag;
    private float amount;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public boolean validate() {
        return isNotEmpty(tag) && amount > 0;
    }
}

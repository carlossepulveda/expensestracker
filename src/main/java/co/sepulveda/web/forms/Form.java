package co.sepulveda.web.forms;

/**
 *
 * @author cas
 */
public abstract class Form {

    public abstract boolean validate();

    protected boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}

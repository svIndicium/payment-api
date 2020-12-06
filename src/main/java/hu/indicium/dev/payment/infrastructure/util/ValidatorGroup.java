package hu.indicium.dev.payment.infrastructure.util;

import java.util.List;

public class ValidatorGroup<T> implements Validator<T> {

    private final List<Validator<T>> validators;

    public ValidatorGroup(List<Validator<T>> validators) {
        this.validators = validators;
    }

    @Override
    public void validate(T entity) {
        for (Validator<T> validator : validators) {
            validator.validate(entity);
        }
    }
}

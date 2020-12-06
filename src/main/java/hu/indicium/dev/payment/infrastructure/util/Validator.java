package hu.indicium.dev.payment.infrastructure.util;

public interface Validator<T> {
    void validate(T entity);
}

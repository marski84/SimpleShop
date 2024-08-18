package org.localhost.simpleshop.facade.backoffice.order.exceptions;

public class OrderIdCannotBeNullException extends RuntimeException {
    public OrderIdCannotBeNullException(String message) {
        super(message);
    }
}

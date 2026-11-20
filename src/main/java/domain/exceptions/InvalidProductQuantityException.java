package domain.exceptions;

public class InvalidProductQuantityException extends ProductDomainException {
    public InvalidProductQuantityException(String message) {
        super(message);
    }
}

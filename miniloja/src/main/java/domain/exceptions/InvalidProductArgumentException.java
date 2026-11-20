package domain.exceptions;

public class InvalidProductArgumentException extends ProductDomainException {
    public InvalidProductArgumentException(String message) {
        super(message);
    }
}

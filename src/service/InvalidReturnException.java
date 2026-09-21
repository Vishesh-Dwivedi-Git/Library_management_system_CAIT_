package service;

public class InvalidReturnException extends Exception {
    public InvalidReturnException(String isbn) {
        super("Only issued books can be returned for ISBN: " + isbn);
    }
}

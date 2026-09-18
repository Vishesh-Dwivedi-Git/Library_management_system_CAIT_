package service;

public class DuplicateBookException extends Exception {
    public DuplicateBookException(String isbn) {
        super("A book with ISBN " + isbn + " already exists.");
    }
}
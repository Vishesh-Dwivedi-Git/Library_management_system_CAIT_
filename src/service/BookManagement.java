package service;

import model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookManagement {

    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) throws DuplicateBookException {
        if (searchByIsbn(book.getIsbn()) != null) {
            throw new DuplicateBookException(book.getIsbn());
        }
        books.add(book);
    }

    public void updateBook(String isbn, String title, String author,
                            String publisher, int yearPublished,
                            String genre, int totalCopies, double price) throws BookNotFoundException {

        Book book = searchByIsbn(isbn);

        if (book == null) {
            throw new BookNotFoundException(isbn);
        }

        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setYearPublished(yearPublished);
        book.setGenre(genre);
        book.setTotalCopies(totalCopies);
        book.setPrice(price);
    }

    public void deleteBook(String isbn) throws BookNotFoundException {
        Book book = searchByIsbn(isbn);

        if (book == null) {
            throw new BookNotFoundException(isbn);
        }

        books.remove(book);
    }

    public List<Book> searchByTitle(String title) {
        List<Book> results = new ArrayList<>();

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(book);
            }
        }

        return results;
    }

    public List<Book> searchByAuthor(String author) {
        List<Book> results = new ArrayList<>();

        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                results.add(book);
            }
        }

        return results;
    }

    public Book searchByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equalsIgnoreCase(isbn)) {
                return book;
            }
        }

        return null;
    }

    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        for (Book book : books) {
            System.out.println(book);
        }
    }

    public boolean checkAvailability(String isbn) throws BookNotFoundException {
        Book book = searchByIsbn(isbn);

        if (book == null) {
            throw new BookNotFoundException(isbn);
        }

        return book.isAvailable();
    }

    public List<Book> getBooks() {
        return books;
    }
}

package model;

import java.util.Objects;

/**
 * Represents a book in the library system.
 * Contains all book-related attributes and methods.
 */
public class Book {

    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int yearPublished;
    private String genre;
    private int totalCopies;
    private int availableCopies;

    // Default constructor
    public Book() {
    }

    // Parameterized constructor
    public Book(String isbn, String title, String author, String publisher,
                int yearPublished, String genre, int totalCopies) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.yearPublished = yearPublished;
        this.genre = genre;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies; // all copies available initially
    }

    // Getters and Setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    /**
     * Checks if at least one copy of this book is available for borrowing.
     */
    public boolean isAvailable() {
        return availableCopies > 0;
    }

    /**
     * Decreases available copies by one when a book is issued.
     * Returns true if successful, false if no copies are available.
     */
    public boolean issueOneCopy() {
        if (availableCopies > 0) {
            availableCopies--;
            return true;
        }
        return false;
    }

    /**
     * Increases available copies by one when a book is returned.
     * Returns true if successful, false if all copies are already available.
     */
    public boolean returnOneCopy() {
        if (availableCopies < totalCopies) {
            availableCopies++;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", yearPublished=" + yearPublished +
                ", genre='" + genre + '\'' +
                ", totalCopies=" + totalCopies +
                ", availableCopies=" + availableCopies +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}

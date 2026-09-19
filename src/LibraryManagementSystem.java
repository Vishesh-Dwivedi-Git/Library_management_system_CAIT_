import model.Book;
import model.Librarian;
import model.Member;
import model.Transaction;
import model.TransactionStatus;
import service.BookManagement;
import service.BookNotFoundException;
import service.TransactionManagement;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class LibraryManagementSystem {

    private final Scanner scanner = new Scanner(System.in);
    private final BookManagement bookManagement = new BookManagement();
    private final TransactionManagement transactionManagement =
            new TransactionManagement(bookManagement);
    private final List<Member> members = new ArrayList<>();
    private final Librarian librarian = new Librarian(
            "L1", "Main Librarian", "library@example.com", "0000000000",
            "EMP001", "Circulation", 0.0);

    public static void main(String[] args) {
        new LibraryManagementSystem().run();
    }

    private void run() {
        loadDefaultData();
        System.out.println("=== Library Management System ===");
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            try {
                switch (choice) {
                    case 1:
                        addBook();
                        break;
                    case 2:
                        updateBook();
                        break;
                    case 3:
                        deleteBook();
                        break;
                    case 4:
                        searchBooks();
                        break;
                    case 5:
                        bookManagement.displayAllBooks();
                        break;
                    case 6:
                        checkAvailability();
                        break;
                    case 7:
                        addMember();
                        break;
                    case 8:
                        displayMembers();
                        break;
                    case 9:
                        issueBook();
                        break;
                    case 10:
                        returnBook();
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception exception) {
                System.out.println("Operation failed: " + exception.getMessage());
            }
        }

        scanner.close();
        System.out.println("Thank you for using the Library Management System.");
    }

    private void loadDefaultData() {
        try {
            loadBooksFromFile("data/books.txt");
            loadMembersFromFile("data/members.txt");
            System.out.println("Default books and members loaded.");
        } catch (Exception exception) {
            System.out.println("Could not load default data: "
                    + exception.getMessage());
        }
    }

    private void loadBooksFromFile(String fileName) throws IOException, Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] values = line.split(",", -1);
                if (values.length != 8) {
                    throw new IOException("Invalid book data at line " + lineNumber);
                }

                Book book = new Book(
                        values[0].trim(), values[1].trim(), values[2].trim(),
                        values[3].trim(), Integer.parseInt(values[4].trim()),
                        values[5].trim(), Integer.parseInt(values[6].trim()),
                        Double.parseDouble(values[7].trim()));
                bookManagement.addBook(book);
            }
        }
    }

    private void loadMembersFromFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] values = line.split(",", -1);
                if (values.length != 6) {
                    throw new IOException("Invalid member data at line "
                            + lineNumber);
                }

                addMember(new Member(
                        values[0].trim(), values[1].trim(), values[2].trim(),
                        values[3].trim(), values[4].trim(), values[5].trim()));
            }
        }
    }

    private void printMenu() {
        System.out.println("\n1. Add book");
        System.out.println("2. Update book");
        System.out.println("3. Delete book");
        System.out.println("4. Search books");
        System.out.println("5. Display all books");
        System.out.println("6. Check book availability");
        System.out.println("7. Add member");
        System.out.println("8. Display members");
        System.out.println("9. Issue book");
        System.out.println("10. Return book");
        System.out.println("0. Exit");
    }

    private void addBook() throws Exception {
        System.out.println("\n--- Add Book ---");
        String isbn = readText("ISBN: ");
        String title = readText("Title: ");
        String author = readText("Author: ");
        String publisher = readText("Publisher: ");
        int year = readInt("Year published: ");
        String genre = readText("Genre: ");
        int copies = readPositiveInt("Total copies: ");
        double price = readNonNegativeDouble("Price: ");

        bookManagement.addBook(new Book(isbn, title, author, publisher, year,
                genre, copies, price));
        System.out.println("Book added successfully.");
    }

    private void updateBook() throws Exception {
        String isbn = readText("ISBN of book to update: ");
        String title = readText("New title: ");
        String author = readText("New author: ");
        String publisher = readText("New publisher: ");
        int year = readInt("New year published: ");
        String genre = readText("New genre: ");
        int copies = readPositiveInt("New total copies: ");
        double price = readNonNegativeDouble("New price: ");

        bookManagement.updateBook(isbn, title, author, publisher, year, genre,
                copies, price);
        System.out.println("Book updated successfully.");
    }

    private void deleteBook() throws BookNotFoundException {
        bookManagement.deleteBook(readText("ISBN of book to delete: "));
        System.out.println("Book deleted successfully.");
    }

    private void searchBooks() {
        System.out.println("1. Search by title");
        System.out.println("2. Search by author");
        int choice = readInt("Search option: ");
        String query = readText("Search text: ");
        List<Book> results;

        if (choice == 1) {
            results = bookManagement.searchByTitle(query);
        } else if (choice == 2) {
            results = bookManagement.searchByAuthor(query);
        } else {
            System.out.println("Invalid search option.");
            return;
        }

        if (results.isEmpty()) {
            System.out.println("No matching books found.");
        } else {
            for (Book book : results) {
                System.out.println(book);
            }
        }
    }

    private void checkAvailability() throws BookNotFoundException {
        String isbn = readText("ISBN: ");
        Book book = bookManagement.searchByIsbn(isbn);
        boolean available = bookManagement.checkAvailability(isbn);
        System.out.println("Available: " + available + " ("
                + book.getAvailableCopies() + "/" + book.getTotalCopies()
                + " copies)");
    }

    private void addMember() {
        String id = readText("Member ID: ");
        if (findMember(id) != null) {
            System.out.println("A member with this ID already exists.");
            return;
        }

        String name = readText("Name: ");
        String email = readText("Email: ");
        String phone = readText("Phone number: ");
        String membershipId = readText("Membership ID: ");
        String membershipType = readText("Membership type: ");
        addMember(new Member(id, name, email, phone, membershipId,
                membershipType));
        System.out.println("Member added successfully.");
    }

    private void addMember(Member member) {
        members.add(member);
    }

    private void displayMembers() {
        if (members.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }
        for (Member member : members) {
            System.out.println(member);
        }
    }

    private void issueBook() throws Exception {
        Member member = requireMember(readText("Member ID: "));
        Book book = bookManagement.searchByIsbn(readText("Book ISBN: "));
        if (book == null) {
            throw new BookNotFoundException("unknown ISBN");
        }

        Transaction transaction = transactionManagement.requestBorrow(member, book);
        transactionManagement.approveBorrow(transaction, librarian);
        member.borrowBook(book.getIsbn());
        System.out.println("Book issued successfully.");
        System.out.println("Transaction ID: " + transaction.getTransactionId());
        System.out.println("Due date: " + transaction.getDueDate());
    }

    private void returnBook() throws Exception {
        String memberId = readText("Member ID: ");
        String isbn = readText("Book ISBN: ");
        Member member = requireMember(memberId);
        Transaction transaction = findIssuedTransaction(memberId, isbn);
        if (transaction == null) {
            System.out.println("No issued transaction was found.");
            return;
        }

        transactionManagement.requestReturn(transaction);
        transactionManagement.approveReturn(transaction, librarian, member);
        System.out.println("Book returned successfully.");
        if (transaction.getFineAmount() > 0) {
            System.out.printf("Fine: %.2f%n", transaction.getFineAmount());
        }
    }

    private Member requireMember(String id) throws Exception {
        Member member = findMember(id);
        if (member == null) {
            throw new Exception("Member not found: " + id);
        }
        return member;
    }

    private Member findMember(String id) {
        for (Member member : members) {
            if (member.getId().equalsIgnoreCase(id)) {
                return member;
            }
        }
        return null;
    }

    private Transaction findIssuedTransaction(String memberId, String isbn) {
        for (Transaction transaction : transactionManagement.getTransactions()) {
            if (transaction.getMemberId().equalsIgnoreCase(memberId)
                    && transaction.getIsbn().equalsIgnoreCase(isbn)
                    && transaction.getStatus() == TransactionStatus.ISSUED) {
                return transaction;
            }
        }
        return null;
    }

    private String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readText(prompt));
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private int readPositiveInt(String prompt) {
        while (true) {
            int value = readInt(prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("Value must be greater than zero.");
        }
    }

    private double readNonNegativeDouble(String prompt) {
        while (true) {
            try {
                double value = Double.parseDouble(readText(prompt));
                if (value >= 0) {
                    return value;
                }
            } catch (NumberFormatException exception) {
                // Ask again below when the input is not numeric.
            }
            System.out.println("Please enter a non-negative number.");
        }
    }
}

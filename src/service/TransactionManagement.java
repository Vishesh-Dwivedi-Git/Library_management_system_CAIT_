package service;

import model.Book;
import model.Librarian;
import model.Member;
import model.Transaction;
import model.TransactionStatus;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TransactionManagement {

    private List<Transaction> transactions = new ArrayList<>();
    private BookManagement bookManagement;

    public TransactionManagement(BookManagement bookManagement) {
        this.bookManagement = bookManagement;
    }

    public Transaction requestBorrow(Member member, Book book) throws BookNotAvailableException, InvalidMemberException {
        if (!member.isActive()) {
            throw new InvalidMemberException("Member is not active.");
        }
        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Book is not currently available: " + book.getIsbn());
        }

        Transaction transaction = new Transaction(UUID.randomUUID().toString(), member.getId(), book.getIsbn());
        transactions.add(transaction);
        return transaction;
    }

    public void approveBorrow(Transaction transaction, Librarian librarian) throws Exception {
        if (transaction.getStatus() != TransactionStatus.PENDING_ISSUE) {
            throw new Exception("Transaction is not pending issue.");
        }

        Book book = bookManagement.searchByIsbn(transaction.getIsbn());
        if (book != null && book.issueOneCopy()) {
            transaction.setStatus(TransactionStatus.ISSUED);
            transaction.setIssueDate(LocalDate.now());
            transaction.setDueDate(LocalDate.now().plusDays(14));
        } else {
            throw new BookNotAvailableException("Book is no longer available.");
        }
    }

    public void requestRenewal(Transaction transaction) throws Exception {
        if (transaction.getStatus() != TransactionStatus.ISSUED) {
            throw new Exception("Only issued books can be renewed.");
        }
        
        LocalDate now = LocalDate.now();
        if (now.isAfter(transaction.getDueDate().plusDays(1))) {
            throw new Exception("Renewal request must be before due date + 1 day.");
        }

        transaction.setStatus(TransactionStatus.PENDING_RENEWAL);
    }

    public void approveRenewal(Transaction transaction, Librarian librarian) throws Exception {
        if (transaction.getStatus() != TransactionStatus.PENDING_RENEWAL) {
            throw new Exception("Transaction is not pending renewal.");
        }
        transaction.setDueDate(transaction.getDueDate().plusDays(14));
        transaction.setStatus(TransactionStatus.ISSUED);
    }

    public void rejectRenewal(Transaction transaction, Librarian librarian) throws Exception {
        if (transaction.getStatus() != TransactionStatus.PENDING_RENEWAL) {
            throw new Exception("Transaction is not pending renewal.");
        }
        transaction.setDueDate(LocalDate.now().plusDays(2));
        transaction.setStatus(TransactionStatus.ISSUED);
    }

    public void requestReturn(Transaction transaction) throws InvalidReturnException {
        if (transaction.getStatus() != TransactionStatus.ISSUED) {
            throw new InvalidReturnException("Only issued books can be returned.");
        }
        
        transaction.setReturnDate(LocalDate.now());
        transaction.setStatus(TransactionStatus.PENDING_RETURN);
    }

    public void approveReturn(Transaction transaction, Librarian librarian, Member member) throws Exception {
        if (transaction.getStatus() != TransactionStatus.PENDING_RETURN) {
            throw new Exception("Transaction is not pending return.");
        }

        LocalDate returnDate = transaction.getReturnDate();
        LocalDate dueDate = transaction.getDueDate();
        
        Book book = bookManagement.searchByIsbn(transaction.getIsbn());
        if (book == null) {
            throw new Exception("Book not found in system.");
        }

        if (returnDate.isAfter(dueDate.plusDays(1))) {
            long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
            double fine = daysLate * 2.5;
            double maxFine = book.getPrice() * 0.40;
            if (fine > maxFine) {
                fine = maxFine;
            }
            transaction.setFineAmount(fine);
        }

        book.returnOneCopy();
        member.returnBook(book.getIsbn());
        transaction.setStatus(TransactionStatus.RETURNED);
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }
}

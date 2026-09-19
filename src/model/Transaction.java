package model;

import java.time.LocalDate;

public class Transaction {
    private String transactionId;
    private String memberId;
    private String isbn;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private TransactionStatus status;
    private double fineAmount;

    public Transaction(String transactionId, String memberId, String isbn) {
        this.transactionId = transactionId;
        this.memberId = memberId;
        this.isbn = isbn;
        this.status = TransactionStatus.PENDING_ISSUE;
        this.fineAmount = 0.0;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }
}

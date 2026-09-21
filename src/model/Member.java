package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a library member who can borrow and return books.
 * Extends Person to inherit common person attributes.
 */
public class Member extends Person {

    private String membershipId;
    private String membershipType;   // e.g., "STANDARD", "PREMIUM"
    private boolean isActive;
    private List<String> borrowedBookIsbns; // tracks ISBNs of currently borrowed books

    // Default constructor
    public Member() {
        super();
        this.isActive = true;
        this.borrowedBookIsbns = new ArrayList<>();
    }

    // Parameterized constructor
    public Member(String id, String name, String email, String phoneNumber,
                  String membershipId, String membershipType) {
        super(id, name, email, phoneNumber);
        this.membershipId = membershipId;
        this.membershipType = membershipType;
        this.isActive = true;
        this.borrowedBookIsbns = new ArrayList<>();
    }

    // Getters and Setters
    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<String> getBorrowedBookIsbns() {
        return borrowedBookIsbns;
    }

    public void setBorrowedBookIsbns(List<String> borrowedBookIsbns) {
        this.borrowedBookIsbns = borrowedBookIsbns;
    }

    /**
     * Adds a book ISBN to this member's borrowed list.
     */
    public void borrowBook(String isbn) {
        borrowedBookIsbns.add(isbn);
    }

    /**
     * Removes a book ISBN from this member's borrowed list.
     * Returns true if the book was found and removed, false otherwise.
     */
    public boolean returnBook(String isbn) {
        return borrowedBookIsbns.remove(isbn);
    }

    /**
     * Returns the number of books currently borrowed by this member.
     */
    public int getBorrowedBookCount() {
        return borrowedBookIsbns.size();
    }

    @Override
    public String getRole() {
        return "MEMBER";
    }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                // ", membershipId='" + membershipId + '\'' +
                ", membershipType='" + membershipType + '\'' +
                ", isActive=" + isActive +
                ", borrowedBooks=" + borrowedBookIsbns.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Member member = (Member) o;
        return Objects.equals(membershipId, member.membershipId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), membershipId);
    }
}

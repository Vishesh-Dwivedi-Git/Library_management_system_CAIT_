# Member 1 - Explanation Guide
## Core Classes and OOP Structure

---

## 📁 Project Structure

```
src/
 └── model/
      ├── Person.java        ← Abstract base class (Parent)
      ├── Member.java         ← Child class (extends Person)
      ├── Librarian.java      ← Child class (extends Person)
      └── Book.java           ← Standalone class
```

---

## 🔗 Class Relationship Diagram

```
            ┌──────────────┐
            │   Person     │  ← Abstract Class (cannot be created directly)
            │──────────────│
            │ - id         │
            │ - name       │
            │ - email      │
            │ - phoneNumber│
            │──────────────│
            │ + getRole()  │  ← Abstract method
            │ + toString() │
            │ + equals()   │
            │ + hashCode() │
            └──────┬───────┘
                   │
          ┌────────┴────────┐
          │ (extends)       │ (extends)
          ▼                 ▼
   ┌──────────────┐  ┌──────────────┐
   │   Member     │  │  Librarian   │
   │──────────────│  │──────────────│
   │ - membershipId│  │ - employeeId │
   │ - memberType │  │ - department │
   │ - isActive   │  │ - salary     │
   │ - borrowedBooks│ │──────────────│
   │──────────────│  │ + getRole()  │
   │ + borrowBook()│  └──────────────┘
   │ + returnBook()│
   │ + getRole()  │
   └──────────────┘

   ┌──────────────┐
   │    Book      │  ← Separate class (no inheritance)
   │──────────────│
   │ - isbn       │
   │ - title      │
   │ - author     │
   │ - publisher  │
   │ - year       │
   │ - genre      │
   │ - totalCopies│
   │ - availCopies│
   │──────────────│
   │ + isAvailable()  │
   │ + issueOneCopy() │
   │ + returnOneCopy()│
   └──────────────┘
```

---

## 🧠 OOP Concepts Used (With Code Examples)

---

### 1. ENCAPSULATION (Data Hiding)

**What:** Keep fields `private`, access them through `public` getters and setters.

**Why:** Protects data from being changed incorrectly from outside the class.

```java
// Fields are PRIVATE — no one can access directly
private String name;
private String email;

// Access only through PUBLIC methods
public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}
```

**Real example:**
```java
Member m = new Member();
// m.name = "John";        ← NOT ALLOWED (private)
m.setName("John");          // ← CORRECT way
System.out.println(m.getName());  // prints: John
```

---

### 2. INHERITANCE (Parent-Child Relationship)

**What:** A child class gets all fields and methods from the parent class automatically.

**Why:** Avoids writing the same code in both Member and Librarian.

```java
// PARENT class
public abstract class Person {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
}

// CHILD class — gets id, name, email, phoneNumber for FREE
public class Member extends Person {
    private String membershipId;   // Member's own extra field
}

// ANOTHER CHILD — also gets id, name, email, phoneNumber
public class Librarian extends Person {
    private String employeeId;     // Librarian's own extra field
}
```

**What `extends` does:**
```
Person has:         id, name, email, phoneNumber
Member has:         id, name, email, phoneNumber + membershipId, membershipType, ...
Librarian has:      id, name, email, phoneNumber + employeeId, department, salary
```

---

### 3. ABSTRACTION (Hiding Complex Details)

**What:** An `abstract` class cannot be created directly. It forces child classes to implement certain methods.

**Why:** We never want a generic "Person" — a person is always either a Member or a Librarian.

```java
// ABSTRACT class — you CANNOT do: new Person()
public abstract class Person {

    // ABSTRACT method — no body here, children MUST implement it
    public abstract String getRole();
}

// Member MUST provide its own getRole()
public class Member extends Person {
    @Override
    public String getRole() {
        return "MEMBER";
    }
}

// Librarian MUST provide its own getRole()
public class Librarian extends Person {
    @Override
    public String getRole() {
        return "LIBRARIAN";
    }
}
```

**What happens if you try:**
```java
Person p = new Person();    // ❌ ERROR — cannot instantiate abstract class
Person p = new Member();    // ✅ WORKS — Member is a concrete (non-abstract) class
```

---

### 4. POLYMORPHISM (Same Method, Different Behavior)

**What:** The same method name behaves differently depending on the object type.

**Why:** You can treat all persons the same way, but each one responds differently.

```java
Person p1 = new Member("1", "Alice", "alice@mail.com", "123", "M001", "STANDARD");
Person p2 = new Librarian("2", "Bob", "bob@mail.com", "456", "E001", "Main", 50000);

// Same method call — different output!
System.out.println(p1.getRole());   // prints: MEMBER
System.out.println(p2.getRole());   // prints: LIBRARIAN
```

---

## 📝 Key Methods Explained

---

### Constructors

**What:** Special methods that run when you create an object using `new`.

```java
// DEFAULT constructor — creates an empty object
public Book() {
}

// PARAMETERIZED constructor — creates an object with values
public Book(String isbn, String title, String author, String publisher,
            int yearPublished, String genre, int totalCopies) {
    this.isbn = isbn;
    this.title = title;
    this.author = author;
    // ... and so on
}
```

**Usage:**
```java
// Using default constructor
Book b1 = new Book();
b1.setTitle("Java Basics");

// Using parameterized constructor — faster!
Book b2 = new Book("978-0-13-468599-1", "Java Basics", "John", "Pearson", 2023, "Education", 5);
```

**`super()` in child class:**
```java
public Member(String id, String name, String email, String phoneNumber,
              String membershipId, String membershipType) {
    super(id, name, email, phoneNumber);  // ← calls Person's constructor
    this.membershipId = membershipId;      // ← sets Member's own fields
    this.membershipType = membershipType;
}
```

---

### toString()

**What:** Converts an object to a readable string. Called automatically by `System.out.println()`.

```java
@Override
public String toString() {
    return "Book{" +
            "isbn='" + isbn + '\'' +
            ", title='" + title + '\'' +
            ", author='" + author + '\'' +
            '}';
}
```

**Usage:**
```java
Book b = new Book("978-01", "Java Basics", "John", "Pearson", 2023, "Education", 5);
System.out.println(b);
// Output: Book{isbn='978-01', title='Java Basics', author='John', ...}

// Without toString(), it would print something ugly like: model.Book@1a2b3c
```

---

### equals() and hashCode()

**What:** `equals()` checks if two objects are "the same". `hashCode()` generates a number used by HashMaps.

**Why:** By default, Java checks if two objects are the same *in memory*. We want to check by *value* instead.

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;                          // same object in memory
    if (o == null || getClass() != o.getClass()) return false;  // null or different type
    Book book = (Book) o;
    return Objects.equals(isbn, book.isbn);              // same ISBN = same book
}

@Override
public int hashCode() {
    return Objects.hash(isbn);    // must match what equals() uses
}
```

**Usage:**
```java
Book b1 = new Book("978-01", "Java Basics", "John", "Pearson", 2023, "Education", 5);
Book b2 = new Book("978-01", "Java Basics", "John", "Pearson", 2023, "Education", 3);

System.out.println(b1.equals(b2));  // true — same ISBN
System.out.println(b1 == b2);       // false — different objects in memory
```

**Rule:** If you override `equals()`, you MUST also override `hashCode()`. They must use the same fields.

---

## 🔑 What Each Class Uses for equals/hashCode

| Class     | Compared By      | Why                                    |
|-----------|------------------|----------------------------------------|
| Person    | `id`             | Each person has a unique ID            |
| Member    | `id` + `membershipId` | ID from Person + membership number |
| Librarian | `id` + `employeeId`   | ID from Person + employee number   |
| Book      | `isbn`           | ISBN is the universal book identifier  |

---

## 🧪 Quick Test You Can Run

Create this file to test all your classes:

```java
// Save as: src/TestMember1.java
import model.*;

public class TestMember1 {
    public static void main(String[] args) {

        // --- Test Book ---
        Book b1 = new Book("978-01", "Java Basics", "John", "Pearson", 2023, "Education", 5);
        System.out.println("=== Book ===");
        System.out.println(b1);                      // toString()
        System.out.println("Available: " + b1.isAvailable());  // true
        b1.issueOneCopy();
        System.out.println("After issue, available copies: " + b1.getAvailableCopies()); // 4

        // --- Test Member ---
        Member m1 = new Member("1", "Alice", "alice@mail.com", "1234567890", "M001", "STANDARD");
        System.out.println("\n=== Member ===");
        System.out.println(m1);                      // toString()
        System.out.println("Role: " + m1.getRole()); // MEMBER
        m1.borrowBook("978-01");
        System.out.println("Borrowed books: " + m1.getBorrowedBookCount()); // 1

        // --- Test Librarian ---
        Librarian lib = new Librarian("2", "Bob", "bob@mail.com", "9876543210", "E001", "Main Hall", 50000);
        System.out.println("\n=== Librarian ===");
        System.out.println(lib);                     // toString()
        System.out.println("Role: " + lib.getRole()); // LIBRARIAN

        // --- Test Polymorphism ---
        System.out.println("\n=== Polymorphism ===");
        Person p1 = m1;    // Member stored as Person
        Person p2 = lib;   // Librarian stored as Person
        System.out.println(p1.getRole());  // MEMBER
        System.out.println(p2.getRole());  // LIBRARIAN

        // --- Test equals ---
        System.out.println("\n=== Equals ===");
        Book b2 = new Book("978-01", "Java Basics", "John", "Pearson", 2023, "Education", 3);
        System.out.println("Same book? " + b1.equals(b2));   // true (same ISBN)
        System.out.println("Same object? " + (b1 == b2));    // false

        System.out.println("\n✅ All Member 1 classes working!");
    }
}
```

**To compile and run:**
```
javac src/model/*.java src/TestMember1.java
java -cp src TestMember1
```

---

## 📌 Summary Table

| Feature          | Person       | Member          | Librarian       | Book          |
|------------------|-------------|-----------------|-----------------|---------------|
| Type             | Abstract     | Concrete        | Concrete        | Concrete      |
| Inherits from    | —           | Person          | Person          | —             |
| Key field        | id          | membershipId    | employeeId      | isbn          |
| Constructors     | 2           | 2               | 2               | 2             |
| toString()       | ✅          | ✅ (overridden) | ✅ (overridden) | ✅            |
| equals()         | ✅ (by id)  | ✅ (by id+memId)| ✅ (by id+empId)| ✅ (by isbn)  |
| hashCode()       | ✅          | ✅              | ✅              | ✅            |
| Abstract method  | getRole()   | implements it   | implements it   | N/A           |

---

## 💬 Common Viva / Presentation Questions

**Q: Why is Person abstract?**
A: Because we never want to create a plain "Person". Every person in our system is either a Member or a Librarian. Making it abstract forces this rule.

**Q: Why use `super()` in constructors?**
A: To pass values to the parent class (Person). Since Person's fields are private, the child class can't set them directly — it calls `super()` to let Person set its own fields.

**Q: Why override equals() and hashCode()?**
A: By default, Java compares objects by memory address (`==`). We override them to compare by meaningful values like ISBN or membership ID. `hashCode()` must be overridden too because collections like HashMap depend on it.

**Q: What is polymorphism in your project?**
A: We can store a Member or Librarian in a Person variable. When we call `getRole()`, Java automatically picks the correct version based on the actual object type.

**Q: Why does Book not extend Person?**
A: A Book is not a Person. Inheritance means "is-a" relationship. A Member IS a Person, but a Book IS NOT a Person. Book is an independent entity.

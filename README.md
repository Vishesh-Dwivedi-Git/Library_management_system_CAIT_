# Library Management System

A simple menu-driven Library Management System written using core Java. The
application demonstrates object-oriented programming, collections, file
input, service classes, and custom exception handling.

## Project Structure

```text
Library_management_system_CAIT_
├── data/
│   ├── books.txt
│   └── members.txt
├── src/
│   ├── LibraryManagementSystem.java
│   ├── model/
│   │   ├── Book.java
│   │   ├── Librarian.java
│   │   ├── Member.java
│   │   ├── Person.java
│   │   ├── Transaction.java
│   │   └── TransactionStatus.java
│   └── service/
│       ├── BookManagement.java
│       ├── TransactionManagement.java
│       └── custom exception classes
└── README.md
```

## Requirements

- Java Development Kit (JDK) 8 or later
- A command prompt or terminal

No external libraries or build tools are required.

## How to Run

Open a terminal in the project root:

```text
D:\Apprenticeship\CAIT - Software Skills\Java Programs\Quarter 1\Library_management_system_CAIT_
```

Compile all source files:

```cmd
mkdir out
javac -d out src\LibraryManagementSystem.java src\model\*.java src\service\*.java
```

Run the program:

```text
java -cp out LibraryManagementSystem
```

Run the program from the project root. The application looks for
`data/books.txt` and `data/members.txt` using relative paths.

## Default Data Files

Default books and members are loaded automatically when the application
starts. The files are read using `BufferedReader`.

### `data/books.txt`

Each data row contains:

```text
isbn,title,author,publisher,year,genre,total copies,price
```

Example:

```text
1,Effective Java,Joshua Bloch,Addison-Wesley,2018,Programming,3,45.00
```

### `data/members.txt`

Each data row contains:

```text
id,name,email,phone,membership id,membership type
```

Example:

```text
M001,Aarav Sharma,aarav@example.com,9876543210,LIB001,STANDARD
```

Blank lines and lines beginning with `#` are ignored in both files. Commas
are used as separators, so values should not contain commas.

The librarian is currently created directly in
`LibraryManagementSystem.java`. The default librarian is used when a book
issue or return is approved.

## Menu Options

| Option | Operation | Description |
|---:|---|---|
| 1 | Add book | Reads book details and adds a new book. ISBNs must be unique. |
| 2 | Update book | Updates the details of an existing book using its ISBN. |
| 3 | Delete book | Removes an existing book using its ISBN. |
| 4 | Search books | Searches books by title or author. Searches are case-insensitive and support partial text. |
| 5 | Display all books | Displays every book and its total and available copies. |
| 6 | Check availability | Displays whether a book has an available copy. |
| 7 | Add member | Registers a new member using a member ID. |
| 8 | Display members | Displays all registered members. |
| 9 | Issue book | Checks the member and book, creates a transaction, and approves the issue. |
| 10 | Return book | Finds the member's issued transaction, requests a return, and approves it. |
| 0 | Exit | Closes the application. |

## Borrowing and Returning

When a book is issued:

1. The member must exist and be active.
2. The book must have at least one available copy.
3. A transaction is created.
4. The librarian approves the transaction.
5. One available copy is reduced.
6. The due date is set to 14 days after the issue date.

When a book is returned:

1. The member and issued transaction must be found.
2. A return request is created.
3. The librarian approves the return.
4. The available copy count is increased.
5. The book ISBN is removed from the member's borrowed-book list.

Late returns are charged at `2.5` per late day, with the fine limited to
40 percent of the book price. The service class also contains request,
approval, and rejection methods for renewal, although renewal is not
currently included as a menu option.

## Class Explanation

### `model.Person`

`Person` is an abstract base class for people in the library. It stores
common fields:

- ID
- Name
- Email
- Phone number

It declares the abstract `getRole()` method, which is implemented by its
subclasses.

### `model.Member`

`Member` extends `Person`. It stores membership details, an active/inactive
status, and a list of ISBNs currently borrowed by the member. Its main
operations are:

- `borrowBook()`
- `returnBook()`
- `getBorrowedBookCount()`
- `getRole()`

### `model.Librarian`

`Librarian` extends `Person` and stores employee ID, department, and salary.
The application currently creates one default librarian in the main class.
The librarian is passed to transaction approval methods.

### `model.Book`

`Book` stores ISBN, title, author, publisher, publication year, genre, total
copies, available copies, and price. It controls copy availability through:

- `isAvailable()`
- `issueOneCopy()`
- `returnOneCopy()`

### `model.Transaction`

`Transaction` records an issue or return operation. It stores the transaction
ID, member ID, ISBN, issue date, due date, return date, status, and fine.

### `model.TransactionStatus`

This enum represents the state of a transaction:

```text
PENDING_ISSUE
ISSUED
PENDING_RENEWAL
PENDING_RETURN
RETURNED
REJECTED
```

### `service.BookManagement`

`BookManagement` owns the book collection and provides book operations:

- Add
- Update
- Delete
- Search by title
- Search by author
- Search by ISBN
- Check availability
- Display all books

### `service.TransactionManagement`

`TransactionManagement` owns the transaction collection and applies borrowing,
renewal, and return rules. It uses `BookManagement` to update book copy
counts.

### `LibraryManagementSystem`

This is the application entry point. It is responsible for:

- Displaying the menu
- Reading input using `Scanner`
- Loading default data using `BufferedReader`
- Calling the service methods
- Displaying success and error messages
- Keeping the in-memory member list for the current run

The menu class does not directly implement borrowing rules; those rules
remain in the service classes.

## OOP Concepts Demonstrated

### Encapsulation

Class fields are private and accessed through getters and setters. Book and
member behavior is performed through methods rather than changing fields
directly.

### Inheritance

`Member` and `Librarian` both extend `Person`, so common person data and
behavior are reused.

### Abstraction

`Person` is abstract and defines the common `getRole()` operation without
allowing a generic `Person` object to be created.

### Method Overriding and Polymorphism

Both `Member` and `Librarian` override `getRole()` and `toString()`. A
`Person` reference can refer to either subclass and call the appropriate
overridden method.

### Constructors

The model classes provide default and parameterized constructors. The
parameterized constructors are used while loading data and adding records.

## Collections Used

The application uses `ArrayList`:

- `BookManagement` stores `List<Book>`
- `TransactionManagement` stores `List<Transaction>`
- `Member` stores borrowed ISBNs in `List<String>`
- `LibraryManagementSystem` stores `List<Member>`

`ArrayList` is suitable for this student project because records are added
and searched in memory. Search methods return a separate result list, and
transactions are exposed to the menu through an unmodifiable list.

## Exception Handling

The application uses checked custom exceptions for common invalid operations:

- `DuplicateBookException`: an ISBN already exists.
- `BookNotFoundException`: an ISBN cannot be found.
- `BookNotAvailableException`: no copy is available.
- `InvalidMemberException`: a member is not active.
- `InvalidReturnException`: a return is requested for a transaction that is
  not currently issued.

The menu catches operation errors and displays a readable message instead of
terminating the program. Input methods also repeatedly ask for valid integer
and non-negative numeric values.

File loading reports missing or malformed data files before continuing to the
menu. Each data row is validated for the expected number of comma-separated
fields.

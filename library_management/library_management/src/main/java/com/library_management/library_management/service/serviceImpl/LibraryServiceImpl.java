package com.library_management.library_management.service.serviceImpl;

import com.library_management.library_management.model.Book;
import com.library_management.library_management.model.User;
import com.library_management.library_management.repository.BookRepository;
import com.library_management.library_management.repository.UserRepository;
import com.library_management.library_management.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryServiceImpl implements LibraryService {


    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    // Constructor-based dependency injection for BookRepository
    public LibraryServiceImpl(BookRepository bookRepository,UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository=userRepository;
    }

    /**
     * Retrieves a list of books available in the library.
     * Only books with available copies are included in the list.
     *
     * @return List of available books
     */

    @Override
    public List<Book> viewAvailableBooks() {
        return bookRepository.findAll().stream()
                .filter(book -> book.getAvailableCopies() > 0)
                .collect(Collectors.toList());
    }

    /**
     * Allows a user to borrow a book from the library if they meet the borrowing conditions.
     *
     * Conditions:
     * - User cannot borrow more than 2 books at a time.
     * - User cannot borrow more than 1 copy of the same book.
     * - The library must have at least one copy of the book available.
     *
     * @param userId ID of the user
     * @param bookId ID of the book to be borrowed
     * @return Success or error message based on the borrowing outcome
     */

    @Override
    public String borrowBook(Long userId, Long bookId) {
        // Fetch the user and book entities from the database using their IDs
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        // Check if the user has already borrowed this book (only 1 copy per user)
        if (user.getBorrowedBooks().contains(book)) {
            return "Book already borrowed by the user";
        }
        // Ensure there is at least one copy of the book available in the library
        if (book.getTotalCopies() <= 0) {
            return "No copies available";
        }
        // Update book's availability and add the book to the user's borrowed list

        book.setTotalCopies(book.getTotalCopies() - 1);
        user.getBorrowedBooks().add(book);
        // Persist the updated states of the book and user entities in the database

        bookRepository.save(book);
        userRepository.save(user);

        return "Book borrowed successfully";
    }


}

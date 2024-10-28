package com.library_management.library_management.serviceImpl;

import com.library_management.library_management.model.Book;
import com.library_management.library_management.model.User;
import com.library_management.library_management.repository.BookRepository;
import com.library_management.library_management.repository.UserRepository;
import com.library_management.library_management.service.serviceImpl.LibraryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

//@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)

public class LibraryServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LibraryServiceImpl libraryService;

    private Book book;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setTotalCopies(2);

        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setBorrowedBooks(new ArrayList<>());
    }

    @Test
    void testViewAvailableBooks() {
        // Arrange
        book.setAvailableCopies(1);
        List<Book> books = List.of(book);
        when(bookRepository.findAll()).thenReturn(books);

        // Act
        List<Book> availableBooks = libraryService.viewAvailableBooks();

        // Assert
        assertEquals(1, availableBooks.size());
        assertEquals("Test Book", availableBooks.get(0).getTitle());
    }

    @Test
    void testBorrowBookSuccess() {
        // Arrange
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        // Act
        String result = libraryService.borrowBook(user.getId(), book.getId());

        // Assert
        assertEquals("Book borrowed successfully", result);
        assertEquals(1, user.getBorrowedBooks().size());
        assertEquals(1, book.getTotalCopies());
        verify(bookRepository, times(1)).save(book);
        verify(userRepository, times(1)).save(user);
    }


    @Test
    void testBorrowBookAlreadyBorrowed() {
        // Arrange
        user.getBorrowedBooks().add(book); // User has already borrowed this book
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        // Act
        String result = libraryService.borrowBook(user.getId(), book.getId());

        // Assert
        assertEquals("Book already borrowed by the user", result);
    }

    @Test
    void testBorrowBookNoCopiesAvailable() {
        // Arrange
        book.setTotalCopies(0); // No copies available
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        // Act
        String result = libraryService.borrowBook(user.getId(), book.getId());

        // Assert
        assertEquals("No copies available", result);
    }

    @Test
    void testReturnBookSuccess() {
        // Arrange
        user.getBorrowedBooks().add(book); // User has borrowed this book
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        // Act
        String result = libraryService.returnBook(user.getId(), book.getId());

        // Assert
        assertEquals("Book returned successfully", result);
        assertEquals(0, user.getBorrowedBooks().size());
        assertEquals(3, book.getTotalCopies()); // Ensure copies are incremented back
        verify(bookRepository, times(1)).save(book);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testReturnBookNotInBorrowedList() {
        // Arrange
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        // Act
        String result = libraryService.returnBook(user.getId(), book.getId());

        // Assert
        assertEquals("Book not in borrowed list", result);
    }

    @Test
    void testBorrowBookUserNotFound() {
        // Arrange
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        // Act & Assert
        try {
            libraryService.borrowBook(user.getId(), book.getId());
        } catch (IllegalArgumentException ex) {
            assertEquals("User not found", ex.getMessage());
        }
    }

    @Test
    void testBorrowBookNotFound() {
        // Arrange
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.empty());

        // Act & Assert
        try {
            libraryService.borrowBook(user.getId(), book.getId());
        } catch (IllegalArgumentException ex) {
            assertEquals("Book not found", ex.getMessage());

        }
    }
}

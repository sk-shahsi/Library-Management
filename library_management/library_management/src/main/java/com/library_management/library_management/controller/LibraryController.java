package com.library_management.library_management.controller;

import com.library_management.library_management.model.Book;
import com.library_management.library_management.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
public class LibraryController {
    private final LibraryService libraryService;
    // Constructor-based dependency injection for LibraryService

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }
    /**
     * API endpoint to view all available books in the library.
     * Only books with available copies are returned.
     *
     * @return List of available books
     */

    @GetMapping("/books")
    public ResponseEntity<List<Book>> viewAvailableBooks() {
        List<Book> books = libraryService.viewAvailableBooks();
        return ResponseEntity.ok(books);
    }
    /**
     * API endpoint to allow a user to borrow a book from the library.
     *
     * @param userId ID of the user
     * @param bookId ID of the book to be borrowed
     * @return Response message indicating success or failure of the borrowing action
     */

    @PostMapping("/borrow/{userId}/{bookId}")
    public ResponseEntity<String> borrowBook(@PathVariable Long userId, @PathVariable Long bookId) {
        String result = libraryService.borrowBook(userId, bookId);
        return ResponseEntity.ok(result);
    }
}

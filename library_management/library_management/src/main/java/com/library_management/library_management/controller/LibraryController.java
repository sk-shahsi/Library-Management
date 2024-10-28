package com.library_management.library_management.controller;

import com.library_management.library_management.model.Book;
import com.library_management.library_management.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

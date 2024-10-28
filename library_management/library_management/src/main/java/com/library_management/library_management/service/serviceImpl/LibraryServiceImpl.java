package com.library_management.library_management.service.serviceImpl;

import com.library_management.library_management.model.Book;
import com.library_management.library_management.repository.BookRepository;
import com.library_management.library_management.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    private final BookRepository bookRepository;

    // Constructor-based dependency injection for BookRepository
    public LibraryServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
}

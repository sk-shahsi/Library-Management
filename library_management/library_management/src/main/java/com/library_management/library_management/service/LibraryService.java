package com.library_management.library_management.service;

import com.library_management.library_management.model.Book;

import java.util.List;

public interface LibraryService {
    List<Book> viewAvailableBooks();
}

package org.tus.libraryservice.service;

import org.tus.libraryservice.dto.BooksDto;
import org.tus.libraryservice.entity.Books;

import java.util.List;

public interface IBooksService {
    void addBook(BooksDto booksDto);

    List<BooksDto> findAllBooks();

    BooksDto fetchBook(String bookName);

    boolean updateBook(String bookName, BooksDto booksDto);

    boolean deleteBook(String bookName);

}

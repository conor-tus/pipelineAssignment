package org.tus.libraryservice.mapper;

import org.tus.libraryservice.dto.BooksDto;
import org.tus.libraryservice.dto.CheckedBooksDto;
import org.tus.libraryservice.entity.Books;

import java.time.LocalDateTime;

public class BooksMapper {
    public static BooksDto mapToBooksDto(Books books,BooksDto booksDto) {
        booksDto.setBookId(books.getBookId());
        booksDto.setBookName(books.getBookName());
        booksDto.setAuthor(books.getAuthor());
        booksDto.setPageCount(books.getPageCount());
        return booksDto;
    }

    public static Books mapToBooks(BooksDto booksDto,Books books) {
        if (booksDto.getBookName() != null) {books.setBookName(booksDto.getBookName());}
        if (booksDto.getAuthor() != null) { books.setAuthor(booksDto.getAuthor());}
        if (booksDto.getPageCount()!= null) { books.setPageCount(booksDto.getPageCount());}
        return books;
    }
}

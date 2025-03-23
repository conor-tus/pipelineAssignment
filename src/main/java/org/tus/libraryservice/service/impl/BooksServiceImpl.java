package org.tus.libraryservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.tus.libraryservice.dto.BooksDto;
import org.tus.libraryservice.entity.Books;
import org.tus.libraryservice.entity.LibraryUser;
import org.tus.libraryservice.exception.ResourceNotFoundException;
import org.tus.libraryservice.mapper.BooksMapper;
import org.tus.libraryservice.mapper.LibraryUserMapper;
import org.tus.libraryservice.repository.BooksRepository;
import org.tus.libraryservice.service.IBooksService;

import java.time.LocalDateTime;
import java.util.List;

import static org.tus.libraryservice.mapper.BooksMapper.mapToBooksDto;

@Service
@AllArgsConstructor
public class BooksServiceImpl implements IBooksService {

    private BooksRepository booksRepository;

    @Override
    public void addBook(BooksDto booksDto) {
        Books books = BooksMapper.mapToBooks(booksDto, new Books());
        booksRepository.save(books);
    }

    public List<BooksDto> findAllBooks() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<Books> books = booksRepository.findAll(pageable);

        return books.stream()
                .map(book -> mapToBooksDto(book, new BooksDto()))
                .toList();
    }

    @Override
    public BooksDto fetchBook(String bookName) {
        Books books = booksRepository.findBookByBookName(bookName).orElseThrow(
                () -> new ResourceNotFoundException("Books","BookName",bookName)
        );
        BooksDto booksDto = mapToBooksDto(books, new BooksDto());
        booksDto.setBookId(books.getBookId());
        return booksDto;
    }

    @Override
    public boolean updateBook(String bookName, BooksDto booksDto) {
        boolean isUpdated = false;
        if(booksDto != null) {
            Books book = booksRepository.findBookByBookName(bookName).orElseThrow(
                    () -> new ResourceNotFoundException("Books","bookName",bookName)
            );
            Books bookRecord = BooksMapper.mapToBooks(booksDto, book);
            booksRepository.save(bookRecord);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteBook(String bookName) {
        Books books = booksRepository.findBookByBookName(bookName).orElseThrow(
                () -> new ResourceNotFoundException("LibraryUser","username",bookName)
        );
        booksRepository.delete(books);
        return true;
    }
}

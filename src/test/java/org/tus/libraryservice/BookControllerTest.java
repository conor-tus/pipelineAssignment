package org.tus.libraryservice;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.tus.libraryservice.controller.BookController;
import org.tus.libraryservice.dto.BooksDto;
import org.tus.libraryservice.dto.ResponseDto;
import org.tus.libraryservice.entity.Books;
import org.tus.libraryservice.mapper.BooksMapper;
import org.tus.libraryservice.repository.BooksRepository;
import org.tus.libraryservice.service.IBooksService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.tus.libraryservice.constants.BooksConstants.BOOK_CREATED_MESSAGE_201;

@SpringBootTest

public class BookControllerTest {

    @Autowired BookController bookController;
    @MockitoBean BooksRepository booksRepository;
    @MockitoBean IBooksService booksService;


    @Test
    void testMapToBooksLogic() {
        BooksDto booksDto = new BooksDto();
        booksDto.setBookName("Book 1");
        booksDto.setAuthor("Author 1");
        booksDto.setPageCount("2");
        Books books = BooksMapper.mapToBooks(booksDto, new Books());

        assertEquals(0,books.getBookId());
        assertEquals("Book 1",books.getBookName());
        assertEquals("Author 1",books.getAuthor());
        assertEquals("2",books.getPageCount());

    }
    
    @Test
    public void addBook() {
        Books book = new Books();
        book.setBookName("Book 1");
        book.setAuthor("Author 1");
        book.setPageCount("2");

        BooksDto booksDto = new BooksDto();
        booksDto.setBookName("Book 1");
        booksDto.setAuthor("Author 1");
        booksDto.setPageCount("2");

        when(booksRepository.save(any())).thenReturn(book);
        ResponseEntity response = bookController.createBook(booksDto);
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        ResponseDto responseBody = (ResponseDto) response.getBody();
        assert responseBody != null;
        assertEquals(BOOK_CREATED_MESSAGE_201, responseBody.getStatusMessage());
        //verify(booksRepository, new Times(1)).save(captor.capture());

    }




}

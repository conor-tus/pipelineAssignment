package org.tus.libraryservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tus.libraryservice.constants.BooksConstants;
import org.tus.libraryservice.dto.BooksDto;
import org.tus.libraryservice.dto.ResponseDto;
import org.tus.libraryservice.service.IBooksService;

import java.util.List;


@RestController
@RequestMapping(path="/api", produces= MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    private final IBooksService iBooksService;

    @Autowired
    public BookController(IBooksService iBooksService) {
        this.iBooksService = iBooksService;
    }

    @GetMapping("sayHello")
    public String sayHello() {
        return "Hello World!";
    }

    @PostMapping("/book")
    public ResponseEntity<ResponseDto> createBook(@RequestBody BooksDto booksDto) {
        iBooksService.addBook(booksDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(BooksConstants.STATUS_201, BooksConstants.BOOK_CREATED_MESSAGE_201));
    }

    @GetMapping("/book")
    public ResponseEntity<List<BooksDto>> getAllBooks() {
        List<BooksDto> booksDto = iBooksService.findAllBooks();
        return ResponseEntity.status(HttpStatus.OK).body(booksDto);
    }

    @GetMapping("/book/{bookName}")
    public ResponseEntity<BooksDto> getBook(@PathVariable String bookName) {
        BooksDto booksDto = iBooksService.fetchBook(bookName);
        return ResponseEntity.status(HttpStatus.OK).body(booksDto);
    }

    @PutMapping("/book/{bookName}")
    public ResponseEntity<ResponseDto> updateBook(@PathVariable String bookName, @RequestBody BooksDto booksDto) {
        boolean isUpdated = iBooksService.updateBook(bookName,booksDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(BooksConstants.STATUS_200, BooksConstants.BOOK_UPDATED_MESSAGE_200));
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(BooksConstants.STATUS_500, BooksConstants.MESSAGE_500));
        }
    }

    @DeleteMapping("/book/{bookName}")
    public ResponseEntity<ResponseDto> deleteBook(@PathVariable String bookName) {

        boolean isDeleted = iBooksService.deleteBook(bookName);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(BooksConstants.STATUS_200, BooksConstants.BOOK_DELETED_MESSAGE_200));
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(BooksConstants.STATUS_500, BooksConstants.MESSAGE_500));
        }

    }
}

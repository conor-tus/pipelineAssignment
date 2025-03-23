package org.tus.libraryservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tus.libraryservice.constants.BooksConstants;
import org.tus.libraryservice.dto.CheckedBooksDto;
import org.tus.libraryservice.dto.LibraryUserDto;
import org.tus.libraryservice.dto.ResponseDto;
import org.tus.libraryservice.service.ILibraryUserService;

import java.util.List;

@RestController
@RequestMapping(path="/api", produces= MediaType.APPLICATION_JSON_VALUE)
@Validated
public class LibraryUserController {

    private final ILibraryUserService iLibraryUserService;

    @Autowired
    public LibraryUserController(ILibraryUserService iLibraryUserService) {
        this.iLibraryUserService = iLibraryUserService;
    }

    @PostMapping("/user")
    public ResponseEntity<ResponseDto> createNewUser(@Valid @RequestBody LibraryUserDto libraryUserDto) {
        iLibraryUserService.addLibraryUser(libraryUserDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(BooksConstants.STATUS_201, BooksConstants.LIBRARY_USER_CREATED_MESSAGE_201));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<LibraryUserDto> getLibraryUserDetails(@PathVariable int id, @RequestParam(required = false) boolean isReversed) {
        LibraryUserDto libraryUserDto = iLibraryUserService.fetchUserById(id,isReversed);
        return ResponseEntity.status(HttpStatus.OK).body(libraryUserDto);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @PathVariable int id, @RequestBody LibraryUserDto libraryUserDto) {
        boolean isUpdated = iLibraryUserService.updateLibraryUser(id,libraryUserDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(BooksConstants.STATUS_200, BooksConstants.LIBRARY_USER_UPDATED_MESSAGE));
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(BooksConstants.STATUS_500, BooksConstants.MESSAGE_500));
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<ResponseDto> deleteAccount(@PathVariable int id) {
        boolean isDeleted = iLibraryUserService.deleteLibraryUserById(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(BooksConstants.STATUS_200, BooksConstants.LIBRARY_USER_DELETED_MESSAGE));
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(BooksConstants.STATUS_500, BooksConstants.MESSAGE_500));
        }
    }

    @PostMapping("/user/{userId}/checkout/{bookName}")
    public ResponseEntity<CheckedBooksDto> checkoutBook(@PathVariable int userId, @PathVariable String bookName,@RequestBody LibraryUserDto libraryUserDto) {
        CheckedBooksDto checkedBooksDto = iLibraryUserService.checkoutLibraryBook(userId, bookName, libraryUserDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(checkedBooksDto);
    }

    @GetMapping("/user/{userid}/checkout/")
    public ResponseEntity<List<CheckedBooksDto>> getCheckedBooks(@PathVariable int userid) {
        List<CheckedBooksDto> usersBooks = iLibraryUserService.fetchUsersCheckedOutBooks(userid);
        if (usersBooks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(usersBooks);
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(usersBooks);
        }
    }

    @GetMapping("/user/{userid}/checkout/{checkedBookid}")
    public ResponseEntity<CheckedBooksDto> getCheckedBook(@PathVariable int userid, @PathVariable int checkedBookid) {
        CheckedBooksDto checkedBook = iLibraryUserService.fetchCheckedOutBook(userid,checkedBookid);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(checkedBook);
    }

    @PutMapping("/user/{userid}/checkout/{checkedBookid}")
    public ResponseEntity<ResponseDto> updateCheckoutBook(@PathVariable int userid,@PathVariable int checkedBookid,@RequestBody CheckedBooksDto checkedBooksDto) {
        boolean isUpdated = iLibraryUserService.updateCheckedOutBook(userid,checkedBookid, checkedBooksDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(BooksConstants.STATUS_200, BooksConstants.CHECKED_BOOK_UPDATED_MESSAGE));
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(BooksConstants.STATUS_500, BooksConstants.MESSAGE_500));
        }
    }

    @DeleteMapping("/user/{userid}/checkout/{checkedBookid}")
    public ResponseEntity<ResponseDto> deleteCheckoutBook(@PathVariable int userid,@PathVariable int checkedBookid) {
        boolean isUpdated = iLibraryUserService.deleteCheckedOutBook(userid,checkedBookid);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(BooksConstants.STATUS_200, BooksConstants.CHECKED_BOOK_DELETED_MESSAGE));
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(BooksConstants.STATUS_500, BooksConstants.MESSAGE_500));
        }
    }

}

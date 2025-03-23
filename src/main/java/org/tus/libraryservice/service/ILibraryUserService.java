package org.tus.libraryservice.service;

import org.tus.libraryservice.dto.CheckedBooksDto;
import org.tus.libraryservice.dto.LibraryUserDto;

import java.util.List;

public interface ILibraryUserService {
    void addLibraryUser(LibraryUserDto libraryUserDto);

    CheckedBooksDto checkoutLibraryBook(int libraryUserId, String bookName, LibraryUserDto libraryUserDto);

    LibraryUserDto fetchUserByUsername(String username);

    LibraryUserDto fetchUserById(int id, boolean isReversed);

    boolean updateLibraryUser(int id,LibraryUserDto libraryUserDto);

    boolean deleteLibraryUserByUsername(String username);

    boolean deleteLibraryUserById(int id);

    List<CheckedBooksDto> fetchUsersCheckedOutBooks(int id);

    CheckedBooksDto fetchCheckedOutBook(int userid, int checkedBookId);

    boolean updateCheckedOutBook(int userid, int checkedBookId, CheckedBooksDto checkedBooksDto);

    boolean deleteCheckedOutBook(int userid, int checkedBookId);
}

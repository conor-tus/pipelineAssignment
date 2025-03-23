package org.tus.libraryservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.tus.libraryservice.dto.CheckedBooksDto;
import org.tus.libraryservice.dto.LibraryUserDto;
import org.tus.libraryservice.entity.Books;
import org.tus.libraryservice.entity.CheckedBooks;
import org.tus.libraryservice.entity.LibraryUser;
import org.tus.libraryservice.exception.ResourceAlreadyExistsException;
import org.tus.libraryservice.exception.ResourceNotFoundException;
import org.tus.libraryservice.mapper.CheckedBooksMapper;
import org.tus.libraryservice.mapper.LibraryUserMapper;
import org.tus.libraryservice.repository.BooksRepository;
import org.tus.libraryservice.repository.CheckedBooksRepository;
import org.tus.libraryservice.repository.LibraryUserRepository;
import org.tus.libraryservice.service.ILibraryUserService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.tus.libraryservice.mapper.CheckedBooksMapper.mapToCheckedBooksDto;


@Service
@AllArgsConstructor
public class LibraryUserServiceImpl implements ILibraryUserService {

    private BooksRepository booksRepository;
    private LibraryUserRepository libraryUserRepository;
    private CheckedBooksRepository checkedBooksRepository;

    @Override
    public void addLibraryUser(LibraryUserDto libraryUserDto) {
        LibraryUser libraryUser = LibraryUserMapper.mapToLibraryUser(libraryUserDto, new LibraryUser());
        Optional<LibraryUser> optionalLibraryUser = libraryUserRepository.findLibraryUserByUsername(libraryUserDto.getUsername());
        if (optionalLibraryUser.isPresent()) {
            throw new ResourceAlreadyExistsException("Customer already registered with given username "+libraryUserDto.getUsername());
        }
        libraryUserRepository.save(libraryUser);
    }

    @Override
    public CheckedBooksDto checkoutLibraryBook(int libraryUserId, String bookName, LibraryUserDto libraryUserDto) {
        libraryUserRepository.findByLibraryUserId(libraryUserId).orElseThrow(
                () -> new ResourceNotFoundException("LibraryUser","libraryUserId",String.valueOf(libraryUserId))
        );

        LibraryUser libraryUser = libraryUserRepository.findLibraryUserByUsername(libraryUserDto.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("LibraryUser","username",libraryUserDto.getUsername())
        );

        Books book = booksRepository.findBookByBookName(bookName).orElseThrow(
                () -> new ResourceNotFoundException("Book","bookName",bookName)
        );

        CheckedBooks checkedBook = new CheckedBooks();
        checkedBook.setBook_name(bookName);
        checkedBook.setLibraryUserId(libraryUser.getLibraryUserId());
        checkedBook.setCheckedStatus("ON_TIME");
        checkedBooksRepository.save(checkedBook);
        return mapToCheckedBooksDto(checkedBook, new CheckedBooksDto());
    }

    @Override
    public LibraryUserDto fetchUserById(int id, boolean isReversed) {
        LibraryUser libraryUser = libraryUserRepository.findByLibraryUserId(id).orElseThrow(
                () -> new ResourceNotFoundException("LibraryUser","libraryUserId",String.valueOf(id))
        );

        List<CheckedBooks> usersCheckBooks = checkedBooksRepository.findByLibraryUserId(libraryUser.getLibraryUserId());

        List<CheckedBooksDto> checkedBooksDtos;
        if (isReversed) {
            checkedBooksDtos = usersCheckBooks.stream()
                    .map(checkedBookRecord -> mapToCheckedBooksDto(checkedBookRecord, new CheckedBooksDto()))
                    .sorted(Comparator.comparing(CheckedBooksDto::getCheckoutDate).reversed())
                    .toList();
        }
        else{
            checkedBooksDtos = usersCheckBooks.stream()
                    .map(checkedBookRecord -> mapToCheckedBooksDto(checkedBookRecord, new CheckedBooksDto()))
                    .toList();
        }

        LibraryUserDto libraryUserDto = LibraryUserMapper.mapToLibraryUserDto(libraryUser, new LibraryUserDto());
        libraryUserDto.setLibrary_user_id(libraryUser.getLibraryUserId());
        libraryUserDto.setCheckedBooks(checkedBooksDtos);
        return libraryUserDto;
    }

    @Override
    public LibraryUserDto fetchUserByUsername(String username) {

        LibraryUser libraryUser = libraryUserRepository.findLibraryUserByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("LibraryUser","username",username)
        );

        List<CheckedBooks> usersCheckBooks = checkedBooksRepository.findByLibraryUserId(libraryUser.getLibraryUserId());

        List<CheckedBooksDto> checkedBooksDtos = usersCheckBooks.stream()
                .map(checkedBookRecord -> mapToCheckedBooksDto(checkedBookRecord, new CheckedBooksDto()))
                .sorted(Comparator.comparing(CheckedBooksDto::getCheckoutDate).reversed())
                .toList();

        LibraryUserDto libraryUserDto = LibraryUserMapper.mapToLibraryUserDto(libraryUser, new LibraryUserDto());
        libraryUserDto.setLibrary_user_id(libraryUser.getLibraryUserId());
        libraryUserDto.setCheckedBooks(checkedBooksDtos);
        return libraryUserDto;
    }

    @Override
    public boolean updateLibraryUser(int id, LibraryUserDto libraryUserDto) {
        boolean isUpdated = false;
        if(libraryUserDto != null) {
            LibraryUser libraryUser = libraryUserRepository.findByLibraryUserId(id).orElseThrow(
                    () -> new ResourceNotFoundException("LibraryUser","username",libraryUserDto.getUsername())
            );
            LibraryUserMapper.mapToLibraryUser(libraryUserDto, libraryUser);
            libraryUserRepository.save(libraryUser);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteLibraryUserById(int id) {
        LibraryUser libraryUser = libraryUserRepository.findByLibraryUserId(id).orElseThrow(
                () -> new ResourceNotFoundException("LibraryUser","libraryUserId",String.valueOf(id))
        );

        List<CheckedBooks> usersCheckBooks = checkedBooksRepository.findByLibraryUserId(libraryUser.getLibraryUserId());

        if(!usersCheckBooks.isEmpty()) {
            checkedBooksRepository.deleteAll(usersCheckBooks);
        }

        libraryUserRepository.delete(libraryUser);
        return true;
    }

    @Override
    public boolean deleteLibraryUserByUsername(String username) {
        LibraryUser libraryUser = libraryUserRepository.findLibraryUserByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("LibraryUser","username",username)
        );
        libraryUserRepository.delete(libraryUser);
        return true;
    }

    @Override
    public List<CheckedBooksDto> fetchUsersCheckedOutBooks(int id) {
        LibraryUser libraryUser = libraryUserRepository.findByLibraryUserId(id).orElseThrow(
                () -> new ResourceNotFoundException("LibraryUser","libraryUserId",String.valueOf(id))
        );

        List<CheckedBooks> usersCheckBooks = checkedBooksRepository.findByLibraryUserId(libraryUser.getLibraryUserId());

        if(usersCheckBooks.isEmpty()) {
            throw new ResourceNotFoundException("LibraryUser","libraryUserId",String.valueOf(id));
        }
        else {
            return usersCheckBooks.stream()
                    .map(checkedBookRecord -> mapToCheckedBooksDto(checkedBookRecord, new CheckedBooksDto()))
                    .toList();
        }
    }

    @Override
    public CheckedBooksDto fetchCheckedOutBook(int userid, int checkedBookid) {
        LibraryUser libraryUser = libraryUserRepository.findByLibraryUserId(userid).orElseThrow(
                () -> new ResourceNotFoundException("LibraryUser","libraryUserId",String.valueOf(userid))
        );

        CheckedBooks userCheckedBook = checkedBooksRepository.findByCheckedBookId(checkedBookid);

        if(userCheckedBook == null) {
            throw new ResourceNotFoundException("CheckedBook","checkedBookId",String.valueOf(checkedBookid));
        }
        else {
            return mapToCheckedBooksDto(userCheckedBook, new CheckedBooksDto());
        }
    }

    @Override
    public boolean updateCheckedOutBook(int userid, int checkedBookId, CheckedBooksDto checkedBooksDto) {
        boolean isUpdated = false;
        CheckedBooks userCheckedBook = checkedBooksRepository.findByCheckedBookId(checkedBookId);

        if(userCheckedBook == null) {
            throw new ResourceNotFoundException("CheckedBook", "checkedBookId", String.valueOf(checkedBookId));
        }
        else{
            CheckedBooksMapper.mapToCheckedBooks(checkedBooksDto, userCheckedBook);
            checkedBooksRepository.save(userCheckedBook);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteCheckedOutBook(int userid, int checkedBookId) {
        LibraryUser libraryUser = libraryUserRepository.findByLibraryUserId(userid).orElseThrow(
                () -> new ResourceNotFoundException("LibraryUser","libraryUserId",String.valueOf(userid))
        );

        CheckedBooks usersCheckBooks = checkedBooksRepository.findByCheckedBookId(checkedBookId);

        if(usersCheckBooks == null) {
            throw new ResourceNotFoundException("CheckedBook", "checkedBookId", String.valueOf(checkedBookId));
        }

        checkedBooksRepository.delete(usersCheckBooks);
        return true;
    }
}

package org.tus.libraryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tus.libraryservice.entity.LibraryUser;

import java.util.Optional;

@Repository
public interface LibraryUserRepository extends JpaRepository<LibraryUser, Long> {

    Optional<LibraryUser> findByLibraryUserId(int libraryUserId);

    Optional<LibraryUser> findLibraryUserByUsername(String libraryUserName);

}

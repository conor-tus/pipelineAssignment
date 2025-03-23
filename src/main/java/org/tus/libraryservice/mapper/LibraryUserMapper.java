package org.tus.libraryservice.mapper;

import org.tus.libraryservice.dto.LibraryUserDto;
import org.tus.libraryservice.entity.LibraryUser;

public class LibraryUserMapper {

    public static LibraryUserDto mapToLibraryUserDto(LibraryUser libraryUser, LibraryUserDto libraryUserDto) {
        libraryUserDto.setUsername(libraryUser.getUsername());
        libraryUserDto.setEmail(libraryUser.getEmail());
        libraryUserDto.setMobile_number(libraryUser.getMobileNumber());
        return libraryUserDto;
    }

    public static LibraryUser mapToLibraryUser(LibraryUserDto libraryUserDto,LibraryUser libraryUser) {
        if (libraryUserDto.getUsername() != null) {libraryUser.setUsername(libraryUserDto.getUsername());}
        if (libraryUserDto.getEmail() != null) { libraryUser.setEmail(libraryUserDto.getEmail());}
        if (libraryUserDto.getMobile_number()!= null) { libraryUser.setMobileNumber(libraryUserDto.getMobile_number());}
        return libraryUser;

    }

}

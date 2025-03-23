package org.tus.libraryservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class LibraryUserDto {

    private int library_user_id;
    @NotEmpty(message = "Username cannot be null or empty")
    @Size(min = 5, max = 30, message = "Username should be between 5 and 30 characters long")
    private String username;
    @NotEmpty(message = "email cannot be null or empty")
    @Email(message = "email is not a valid email address")
    private String email;
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobile_number;
    private List<CheckedBooksDto> checkedBooks;

}

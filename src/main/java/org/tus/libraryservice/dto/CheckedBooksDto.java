package org.tus.libraryservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CheckedBooksDto {
    private int checkedBookId;
    private String bookName;
    private int libraryUserId;
    private String checkedStatus;
    private String checkoutDate;
}

package org.tus.libraryservice.dto;

import lombok.Data;

@Data
public class BooksDto {

    private int BookId;
    private String bookName;
    private String author;
    private String pageCount;

}

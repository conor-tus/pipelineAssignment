package org.tus.libraryservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.tus.libraryservice.controller.BookController;
import org.tus.libraryservice.dto.BooksDto;
import org.tus.libraryservice.entity.Books;
import org.tus.libraryservice.repository.BooksRepository;
import org.tus.libraryservice.service.IBooksService;

import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import static org.tus.libraryservice.constants.BooksConstants.*;
import static org.tus.libraryservice.mapper.BooksMapper.mapToBooksDto;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerMockMvcTest {

    @Autowired BookController bookController;
    @MockitoBean BooksRepository booksRepository;
    @MockitoBean IBooksService booksService;
    @Autowired private MockMvc mockMvc;

    @Test
    public void findAllBooks() throws Exception {
        List<Books> books = IntStream.range(0, 10)
                .mapToObj(i -> new Books(i,"Title " + i, "Author " + i,"i"))
                .toList();


        List<BooksDto> booksDtoList = books.stream()
                .map(book -> mapToBooksDto(book, new BooksDto()))
                .toList();


        when(booksService.findAllBooks()).thenReturn(booksDtoList);
        this.mockMvc.perform(get("/api/book")).andDo(print()).andExpect(status().isOk())
                        .andExpect(jsonPath("$.length()",is(10)));
    }

    @Test
    public void findASpecificBook() throws Exception {
        BooksDto books = new BooksDto();
        books.setBookName("1984");
        books.setAuthor("George Orwell");
        books.setPageCount("328");
        books.setBookId(1);

        when(booksService.fetchBook("1984")).thenReturn(books);
        this.mockMvc.perform(get("/api/book/1984")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()",is(4)))
                .andExpect(jsonPath("$.bookName",is("1984")))
                .andExpect(jsonPath("$.author",is("George Orwell")))
                .andExpect(jsonPath("$.pageCount",is("328")));
    }


//    @Test void updateBook() throws Exception {
//        BooksDto books = new BooksDto();
//        books.setBookName("1984");
//        books.setAuthor("George Orwell");
//        books.setPageCount("328");
//        books.setBookId(1);
//
//        when(booksService.updateBook("1984",books)).thenReturn(true);
//        this.mockMvc.perform(put("/api/book/1984").contentType(MediaType.APPLICATION_JSON)
//                        .content("{" +
//                                "\"pageCount\":\"1\"," +
//                                "\"author\":\"conor\"" +
//                                "}"))
//                .andDo(print()).andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()",is(2)))
//                .andExpect(jsonPath("$.statusCode",is("200")))
//                .andExpect(jsonPath("$.statusMessage",is(BOOK_UPDATED_MESSAGE_200)));
//    }

    @Test void deleteBook() throws Exception {
        when(booksService.deleteBook("Dracula")).thenReturn(true);
        this.mockMvc.perform(delete("/api/book/Dracula"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()",is(2)))
                .andExpect(jsonPath("$.statusCode",is("200")))
                .andExpect(jsonPath("$.statusMessage",is(BOOK_DELETED_MESSAGE_200)));
    }

    @Test void createANewBook() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        BooksDto newBook = new BooksDto();
        newBook.setBookName("Conors Book");
        newBook.setAuthor("conor");
        newBook.setPageCount("1");

        String jsonString = mapper.writeValueAsString(newBook);

        this.mockMvc.perform(post("/api/book").contentType(MediaType.APPLICATION_JSON).content("{" +
                        "\"bookName\":\"" + newBook.getBookName() + "\"," +
                        "\"author\":\"" + newBook.getAuthor() + "\"," +
                        "\"pageCount\":" + newBook.getPageCount() +
                        "}"))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.length()",is(2)))
                .andExpect(jsonPath("$.statusCode",is("201")))
                .andExpect(jsonPath("$.statusMessage",is(BOOK_CREATED_MESSAGE_201)));
    }

    @Test
    void createTheSameBook() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        BooksDto newBook = new BooksDto();
//
//        newBook.setBookName("Ulysses");
//        newBook.setAuthor("James Joyce");
//        newBook.setPageCount("730");
//        Optional<BooksDto> booksOptional = Optional.of(newBook);
//
//        String jsonString = mapper.writeValueAsString(newBook);
//        when(booksRepository.findBookByBookName("Ulysses")).thenReturn(booksOptional);
//
//        this.mockMvc.perform(post("/api/book").contentType(MediaType.APPLICATION_JSON).content("{" +
//                        "\"bookName\":\"" + newBook.getBookName() + "\"," +
//                        "\"author\":\"" + newBook.getAuthor() + "\"," +
//                        "\"pageCount\":" + newBook.getPageCount() +
//                        "}"))
//                .andDo(print()).andExpect(status().isConflict())
//                .andExpect(jsonPath("$.length()",is(4)))
//                .andExpect(jsonPath("$.errorCode",is("CONFLICT")))
//                .andExpect(jsonPath("$.errorMessage",is("Book already exists")));
    }

//    @Test
//    void getABookThatDoesNotExist() throws Exception {
//        BooksDto books = new BooksDto();
//        when(booksService.fetchBook("Guiness book of records")).thenReturn(books);
//        this.mockMvc.perform(get("/api/book/GuinessBookOfRecords")).andDo(print()).andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.length()",is(4)))
//                .andExpect(jsonPath("$.apiPath",is("uri=/api/book/GuinessBookOfRecords")))
//                .andExpect(jsonPath("$.errorCode",is("NOT_FOUND")))
//                .andExpect(jsonPath("$.errorMessage",is("Resource Books not found using BookName: GuinessBookOfRecords was not found")));
//    }

}

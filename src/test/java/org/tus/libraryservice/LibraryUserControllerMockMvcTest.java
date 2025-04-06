package org.tus.libraryservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.tus.libraryservice.controller.LibraryUserController;
import org.tus.libraryservice.dto.LibraryUserDto;
import org.tus.libraryservice.repository.LibraryUserRepository;
import org.tus.libraryservice.service.ILibraryUserService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.tus.libraryservice.constants.BooksConstants.BOOK_CREATED_MESSAGE_201;
import static org.tus.libraryservice.constants.BooksConstants.LIBRARY_USER_CREATED_MESSAGE_201;

@SpringBootTest
@AutoConfigureMockMvc
public class LibraryUserControllerMockMvcTest {

    @Autowired LibraryUserController libraryController;
    @MockitoBean LibraryUserRepository libraryRepository;
    @MockitoBean ILibraryUserService libraryService;
    @Autowired private MockMvc mockMvc;

    @Test
    void addALibararyUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        LibraryUserDto newUser = new LibraryUserDto();
        newUser.setUsername("conor_library");
        newUser.setEmail("conor@example.com");
        newUser.setMobile_number("1234567890");

        String jsonString = mapper.writeValueAsString(newUser);

        this.mockMvc.perform(post("/api/user").contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.length()",is(2)))
                .andExpect(jsonPath("$.statusCode",is("201")))
                .andExpect(jsonPath("$.statusMessage",is(LIBRARY_USER_CREATED_MESSAGE_201)));
    }



    @Test
    void addAlibraryUserWithAnInvalidPhoneNumber() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        LibraryUserDto newUser = new LibraryUserDto();
        newUser.setUsername("conor_library");
        newUser.setEmail("conor@example.com");
        newUser.setMobile_number("123456789");

        String jsonString = mapper.writeValueAsString(newUser);

        this.mockMvc.perform(post("/api/user").contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andDo(print()).andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.length()",is(1)))
                .andExpect(jsonPath("$.mobile_number",is("Mobile number must be 10 digits")));
    }

    @Test
    void addLibraryUserWithAnInvalidEmailAddress() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        LibraryUserDto newUser = new LibraryUserDto();
        newUser.setUsername("conor_library");
        newUser.setEmail("conorexample.com");
        newUser.setMobile_number("1234567890");

        String jsonString = mapper.writeValueAsString(newUser);

        this.mockMvc.perform(post("/api/user").contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andDo(print()).andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.length()",is(1)))
                .andExpect(jsonPath("$.email",is("email is not a valid email address")));

    }


    @Test
    void UpdateLibraryUser() {

    }

    @Test
    void UpdateLibraryUserThatDoesNotExist() {

    }

    @Test
    void deleteLibraryUser() {

    }

    @Test
    void DeleteALibraryUserThatDoesNotExist() {

    }

    @Test
    void checkoutAbookForALibraryUser() {

    }

    @Test
    void getUsersCheckoutOutbooks() {

    }

    @Test
    void getASpecificCheckoutOutRecordForALibraryUser() {

    }

    @Test
    void updateASpecificCheckoutOutRecordForALibraryUser() {

    }

    @Test
    void deleteASpecificCheckoutOutRecordForALibraryUser() {

    }
}

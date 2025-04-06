package org.tus.libraryservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.tus.libraryservice.controller.LibraryUserController;
import org.tus.libraryservice.dto.LibraryUserDto;
import org.tus.libraryservice.entity.LibraryUser;
import org.tus.libraryservice.repository.LibraryUserRepository;
import org.tus.libraryservice.service.ILibraryUserService;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class LibraryAppIT {
    @Autowired LibraryUserController libraryController;
    @MockitoBean LibraryUserRepository libraryRepository;
    @MockitoBean ILibraryUserService booksService;
    @Autowired private MockMvc mockMvc;

    @Test
    void addALibararyUserWithTheSameAccount() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        LibraryUserDto newUser = new LibraryUserDto();
        newUser.setUsername("admin_user");
        newUser.setEmail("conor@example.com");
        newUser.setMobile_number("1234567890");

        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LibraryUserDto> entity = new HttpEntity<>(newUser, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/api/user", entity, String.class);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void UpdateLibraryUser() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        LibraryUserDto newUser = new LibraryUserDto();
        newUser.setUsername("admin_user");
        newUser.setEmail("conor@example.com");
        newUser.setMobile_number("1234567890");

        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LibraryUserDto> entity = new HttpEntity<>(newUser, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/api/user", entity, String.class);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
}

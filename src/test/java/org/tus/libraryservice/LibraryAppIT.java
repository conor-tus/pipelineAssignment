package org.tus.libraryservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.tus.libraryservice.dto.LibraryUserDto;
import org.tus.libraryservice.entity.LibraryUser;
import org.tus.libraryservice.repository.LibraryUserRepository;
import org.tus.libraryservice.service.ILibraryUserService;

import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class LibraryAppIT {

    @Autowired LibraryUserRepository libraryUserRepository;

    @Test
    @Sql({"/schema.sql"})
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
    @Sql({"/schema.sql"})
    void UpdateLibraryUser() throws JsonProcessingException {
        String newUser = "{" +
                "\"username\":\"admin_user\"," +
                "\"email\":\"conor@gmail.com\"," +
                "\"mobileNumber\":\"0987654321\"}";

        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(newUser, headers);
        restTemplate.put("http://localhost:8080/api/user/1", entity, String.class);

        Optional<LibraryUser> updatedUser = libraryUserRepository.findByLibraryUserId(1);
        assertTrue(updatedUser.isPresent());
        assertTrue(newUser.contains(updatedUser.get().getUsername()));
        assertTrue(newUser.contains(updatedUser.get().getEmail()));
        assertTrue(newUser.contains(updatedUser.get().getMobileNumber()));

    }


    @Test
    @Sql({"/schema.sql"})
    void CheckoutALibraryBook() throws JsonProcessingException {
        String newUser = "{" +
                "\"username\":\"admin_user\"}";

        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //checckout a book
        HttpEntity<String> entity = new HttpEntity<>(newUser, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/api/user/1/checkout/Frankenstein", entity, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());


        //validate book is present under a users profile
        HttpHeaders getHeaders = new HttpHeaders();
        getHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> getResponse = restTemplate.getForEntity("http://localhost:8080/api/user/1",String.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());

        assertTrue(getResponse.getBody().contains("admin_user"));
        assertTrue(getResponse.getBody().contains("conor@gmail.com"));
        assertTrue(getResponse.getBody().contains("0987654321"));
        assertTrue(getResponse.getBody().contains("checkedBooks"));
        assertTrue(getResponse.getBody().contains("Frankenstein"));
        assertTrue(getResponse.getBody().contains("ON_TIME"));

    }
}

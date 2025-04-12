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
import org.tus.libraryservice.controller.LibraryUserController;
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
}

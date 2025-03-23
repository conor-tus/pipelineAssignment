package org.tus.libraryservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LibraryUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="library_user_id")
    private int libraryUserId;
    private String username;
    private String email;
    @Column(name="mobile_number")
    private String mobileNumber;
    private String books;

}

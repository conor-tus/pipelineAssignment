package org.tus.libraryservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CheckedBooks extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="checked_book_id")
    private int checkedBookId;
    @Column(name="library_user_id")
    private int libraryUserId;
    @Column(name="checked_status")
    private String checkedStatus;
    private String book_name;

}

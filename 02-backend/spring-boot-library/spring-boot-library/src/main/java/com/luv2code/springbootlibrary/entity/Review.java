package com.luv2code.springbootlibrary.entity;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "review")
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column( name = "user_email")
    private String userEmail;

    @Column( name = "date")
    @CreationTimestamp
    private Date date;

    @Column( name = "rating")
    private double rating;

    @Column( name = "book_id")
    private Long bookId;

    @Column ( name = "review_description")
    private String reviewDescription;
}

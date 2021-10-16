package com.example.h2example.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tutorials")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Tutorial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "published")
    private boolean published;

}

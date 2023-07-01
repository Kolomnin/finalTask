package com.example.finaltask.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserAvatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String contentType;
    @Lob
    private byte [] bytes;
    @OneToOne(optional = true)
//    @JoinColumn(referencedColumnName = "user_id")
    private User user;



}

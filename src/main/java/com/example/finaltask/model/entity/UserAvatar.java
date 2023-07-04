package com.example.finaltask.model.entity;

import lombok.*;
import org.hibernate.annotations.Type;

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
    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte [] bytes;
    @OneToOne(optional = true)
//    @JoinColumn(referencedColumnName = "user_id")
    private User user;



}

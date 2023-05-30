package com.example.finaltask.model.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String createdAt;
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ads_id", nullable = false)
    @ToString.Exclude
    private Ads ads;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User authorId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Comment comment = (Comment) o;
        return getId() != null && Objects.equals(getId(), comment.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
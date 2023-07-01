package com.example.finaltask.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "ads")

/**
 * При добавлении анотации @EqualsAndHashCode преобразуется код методов equals и hashCode
 */

public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer price;
    private String title;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User authorId;
    @ToString.Exclude
    @OneToOne(mappedBy = "ads", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "adsImage_id",referencedColumnName = "id")
    private AdsImage image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ads ads = (Ads) o;
        return Objects.equals(id, ads.id) && Objects.equals(price, ads.price) && Objects.equals(title, ads.title) && Objects.equals(description, ads.description) && Objects.equals(authorId, ads.authorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, title, description, authorId);
    }
}
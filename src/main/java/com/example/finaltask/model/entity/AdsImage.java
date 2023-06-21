package com.example.finaltask.model.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "AdsImage")
public class AdsImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private String filePath;
//    private long fileSize;
//    private String mediaType;
    @Lob
    private byte[] image;
    @OneToOne(optional = true)
    @JoinColumn(name = "ads_id")
    private Ads ads;

    @OneToOne(optional = true)
    @JoinColumn(referencedColumnName = "id")
    private User user;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AdsImage adsImage = (AdsImage) o;
        return getId() != null && Objects.equals(getId(), adsImage.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
package com.melodymarket.domain.show.entity;

import com.melodymarket.domain.theater.entity.TheaterEntity;
import com.melodymarket.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "shows")
public class ShowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String showName;
    @Column
    private String director;
    @Column
    private String reserveStartDate;

    @Column
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "theater_id")
    private TheaterEntity theater;

    @Builder
    public ShowEntity(String showName, String director, String reserveStartDate, Integer price, UserEntity user, TheaterEntity theater) {
        this.showName = showName;
        this.director = director;
        this.reserveStartDate = reserveStartDate;
        this.price = price;
        this.user = user;
        this.theater = theater;
    }
}

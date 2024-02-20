package com.melodymarket.domain.show.entity;

import com.melodymarket.domain.theater.entity.Theater;
import com.melodymarket.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "shows")
public class Show {
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
    private User user;

    @OneToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;

    @Builder
    public Show(String showName, String director, String reserveStartDate, Integer price, User user, Theater theater) {
        this.showName = showName;
        this.director = director;
        this.reserveStartDate = reserveStartDate;
        this.price = price;
        this.user = user;
        this.theater = theater;
    }
}

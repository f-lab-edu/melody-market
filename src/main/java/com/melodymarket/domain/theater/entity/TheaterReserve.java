package com.melodymarket.domain.theater.entity;

import com.melodymarket.domain.show.entity.ShowSchedule;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "theater_reserve")
public class TheaterReserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "theaterReserve", cascade = CascadeType.ALL)
    private ShowSchedule showSchedule;
    @Column
    private String reserveDate;

    @Builder
    public TheaterReserve(ShowSchedule showSchedule, String reserveDate) {
        this.showSchedule = showSchedule;
        this.reserveDate = reserveDate;
    }
}

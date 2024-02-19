package com.melodymarket.domain.theater.entity;

import com.melodymarket.domain.show.entity.ShowScheduleEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "theater_reserve")
public class TheaterReserveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "theaterReserve", cascade = CascadeType.ALL)
    private ShowScheduleEntity showSchedule;
    @Column
    private String reserveDate;

    @Builder
    public TheaterReserveEntity(ShowScheduleEntity showSchedule, String reserveDate) {
        this.showSchedule = showSchedule;
        this.reserveDate = reserveDate;
    }
}

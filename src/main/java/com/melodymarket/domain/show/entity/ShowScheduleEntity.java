package com.melodymarket.domain.show.entity;

import com.melodymarket.domain.theater.entity.TheaterReserveEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "show_schedule")
public class ShowScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "show_id")
    private ShowEntity showList;
    @Column
    private String showDate;

    @OneToOne
    @JoinColumn(name = "theater_reserve_id")
    private TheaterReserveEntity theaterReserve;
    @ManyToOne
    @JoinColumn(name = "show_seat_id")
    private ShowSeatEntity showSeat;

    @Builder
    public ShowScheduleEntity(ShowEntity showList, String showDate, TheaterReserveEntity theaterReserve, ShowSeatEntity showSeat) {
        this.showList = showList;
        this.showDate = showDate;
        this.theaterReserve = theaterReserve;
        this.showSeat = showSeat;
    }
}

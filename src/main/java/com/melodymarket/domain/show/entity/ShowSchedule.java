package com.melodymarket.domain.show.entity;

import com.melodymarket.domain.theater.entity.TheaterReserve;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "show_schedule")
public class ShowSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show showList;
    @Column
    private String showDate;

    @OneToOne
    @JoinColumn(name = "theater_reserve_id")
    private TheaterReserve theaterReserve;
    @ManyToOne
    @JoinColumn(name = "show_seat_id")
    private ShowSeat showSeat;

    @Builder
    public ShowSchedule(Show showList, String showDate, TheaterReserve theaterReserve, ShowSeat showSeat) {
        this.showList = showList;
        this.showDate = showDate;
        this.theaterReserve = theaterReserve;
        this.showSeat = showSeat;
    }
}

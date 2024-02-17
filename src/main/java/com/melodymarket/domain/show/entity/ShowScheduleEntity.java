package com.melodymarket.domain.show.entity;

import com.melodymarket.domain.theater.entity.TheaterReserveEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "show_schedule")
public class ShowScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="show_id")
    private ShowEntity showList;
    @Column
    private String showDate;

    @OneToOne
    @JoinColumn(name="theater_reserve_id")
    private TheaterReserveEntity theaterReserve;
    @ManyToOne
    @JoinColumn(name="show_seat_id")
    private ShowSeatEntity showSeat;

}

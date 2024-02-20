package com.melodymarket.domain.show.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "show_seat")
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "showSeat")
    private List<ShowSchedule> showSchedule;
    @Column
    private Long seatId;

    @Builder
    public ShowSeat(List<ShowSchedule> showSchedule, Long seatId) {
        this.showSchedule = showSchedule;
        this.seatId = seatId;
    }
}

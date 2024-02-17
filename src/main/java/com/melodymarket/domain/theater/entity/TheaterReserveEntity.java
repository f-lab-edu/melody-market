package com.melodymarket.domain.theater.entity;

import com.melodymarket.domain.show.entity.ShowScheduleEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "theater_reserve")
public class TheaterReserveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "theaterReserve",cascade = CascadeType.ALL)
    private ShowScheduleEntity showSchedule;
    @Column
    private String reserveDate;
}

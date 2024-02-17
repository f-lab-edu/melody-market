package com.melodymarket.domain.theater.entity;

import com.melodymarket.application.theater.dto.TheaterSeatDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "seat")
public class TheaterSeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "theater_room_id")
    private TheaterRoomEntity theaterRoom;
    @Column
    private Integer seatFloor;
    @Column
    private Integer seatRow;
    @Column
    private Integer seatNumber;

    public static List<TheaterSeatEntity> from(List<TheaterSeatDto> seatDtos) {
        return seatDtos.stream()
                .map(seatDto -> TheaterSeatEntity.builder()
                        .seatFloor(seatDto.getSeatFloor())
                        .seatRow(seatDto.getSeatRow())
                        .seatNumber(seatDto.getSeatNumber())
                        .build())
                .collect(Collectors.toList());
    }

}

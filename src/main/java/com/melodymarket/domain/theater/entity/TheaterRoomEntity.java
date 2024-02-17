package com.melodymarket.domain.theater.entity;

import com.melodymarket.application.theater.dto.TheaterRoomDto;
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
@Table(name = "theater_room")
public class TheaterRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "theater_id")
    private TheaterEntity theater;
    @Column
    private String name;
    @OneToMany(mappedBy = "theaterRoom", cascade = CascadeType.ALL)
    private List<TheaterSeatEntity> seats;

    public static List<TheaterRoomEntity> from(List<TheaterRoomDto> theaterRoomDto) {
        return theaterRoomDto.stream()
                .map(roomDto -> TheaterRoomEntity.builder()
                        .name(roomDto.getRoomName())
                        .seats(TheaterSeatEntity.from(roomDto.getSeatData()))
                        .build())
                .collect(Collectors.toList());
    }

}

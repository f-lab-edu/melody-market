package com.melodymarket.domain.theater.entity;

import com.melodymarket.application.theater.dto.TheaterRoomDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
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

    protected void setTheater(TheaterEntity theater) {
        this.theater = theater;
    }

    @Builder
    public TheaterRoomEntity(TheaterEntity theater, String name, List<TheaterSeatEntity> seats) {
        this.theater = theater;
        this.name = name;
        this.seats = seats;
    }

    public static List<TheaterRoomEntity> from(List<TheaterRoomDto> theaterRoomDto) {
        return theaterRoomDto.stream()
                .map(roomDto -> TheaterRoomEntity.builder()
                        .name(roomDto.getRoomName())
                        .seats(TheaterSeatEntity.from(roomDto.getSeatData()))
                        .build())
                .toList();
    }

    public void associateRoomsWithSeats() {
        this.getSeats().forEach(seat -> seat.setTheaterRoom(this));
    }

}

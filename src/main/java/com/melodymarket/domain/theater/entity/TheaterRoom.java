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
public class TheaterRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;
    @Column
    private String name;

    @OneToMany(mappedBy = "theaterRoom", cascade = CascadeType.ALL)
    private List<TheaterSeat> seats;

    protected void setTheater(Theater theater) {
        this.theater = theater;
    }

    @Builder
    public TheaterRoom(Theater theater, String name, List<TheaterSeat> seats) {
        this.theater = theater;
        this.name = name;
        this.seats = seats;
    }

    public static List<TheaterRoom> from(List<TheaterRoomDto> theaterRoomDto) {
        return theaterRoomDto.stream()
                .map(roomDto -> TheaterRoom.builder()
                        .name(roomDto.getRoomName())
                        .seats(TheaterSeat.from(roomDto.getSeatData()))
                        .build())
                .toList();
    }

    public void associateRoomsWithSeats() {
        this.getSeats().forEach(seat -> seat.setTheaterRoom(this));
    }

}

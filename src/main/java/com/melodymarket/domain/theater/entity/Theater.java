package com.melodymarket.domain.theater.entity;

import com.melodymarket.application.theater.dto.TheaterDto;
import com.melodymarket.domain.show.entity.Show;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "theater")
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String location;
    @OneToOne(mappedBy = "theater")
    private Show showList;
    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TheaterRoom> rooms;

    @Builder
    public Theater(String name, String location, Show showList, List<TheaterRoom> rooms) {
        this.name = name;
        this.location = location;
        this.showList = showList;
        this.rooms = rooms;
    }

    public static Theater from(TheaterDto theaterDto) {
        return Theater.builder()
                .name(theaterDto.getName())
                .location(theaterDto.getLocation())
                .rooms(TheaterRoom.from(theaterDto.getRoomsInfo()))
                .build();
    }

    public void associateTheaterWithRooms() {
        this.getRooms().forEach(room -> room.setTheater(this));
    }
}

package com.melodymarket.domain.theater.entity;

import com.melodymarket.application.theater.dto.TheaterDto;
import com.melodymarket.domain.show.entity.ShowEntity;
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
public class TheaterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String location;
    @OneToOne(mappedBy = "theater")
    private ShowEntity showList;
    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TheaterRoomEntity> rooms;

    @Builder
    public TheaterEntity(String name, String location, ShowEntity showList, List<TheaterRoomEntity> rooms) {
        this.name = name;
        this.location = location;
        this.showList = showList;
        this.rooms = rooms;
    }

    public static TheaterEntity from(TheaterDto theaterDto) {
        return TheaterEntity.builder()
                .name(theaterDto.getName())
                .location(theaterDto.getLocation())
                .rooms(TheaterRoomEntity.from(theaterDto.getRoomsInfo()))
                .build();
    }

    public void associateTheaterWithRooms() {
        this.getRooms().forEach(room -> room.setTheater(this));
    }
}

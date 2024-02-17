package com.melodymarket.domain.theater.entity;

import com.melodymarket.application.theater.dto.TheaterDto;
import com.melodymarket.domain.show.entity.ShowEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @OneToMany(mappedBy = "theater",cascade = CascadeType.ALL)
    private List<TheaterRoomEntity> rooms;
    public static TheaterEntity from(TheaterDto theaterDto) {
        return TheaterEntity
                .builder()
                .name(theaterDto.getName())
                .location(theaterDto.getLocation())
                .rooms(TheaterRoomEntity.from(theaterDto.getRoomsInfo()))
                .build();
    }
}

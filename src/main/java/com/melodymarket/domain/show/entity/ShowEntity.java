package com.melodymarket.domain.show.entity;

import com.melodymarket.domain.theater.entity.TheaterEntity;
import com.melodymarket.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "shows")
public class ShowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String showName;
    @Column
    private String director;
    @Column
    private String reserveStartDate;

    @Column
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne
    @JoinColumn(name="theater_id")
    private TheaterEntity theater;

}

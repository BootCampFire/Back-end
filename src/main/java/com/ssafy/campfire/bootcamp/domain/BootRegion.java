package com.ssafy.campfire.bootcamp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name ="boot_region")
public class BootRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bootcamp_id")
    @Column(nullable = false)
    private Bootcamp bootcampId;


    @ManyToOne(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private Region region;
}
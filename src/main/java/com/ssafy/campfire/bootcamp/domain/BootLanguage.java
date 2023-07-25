package com.ssafy.campfire.bootcamp.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name ="boot_language")
public class BootLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bootcamp_id")
    private Bootcamp bootcamp;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lenguage_id")
    private Language lenguage;
}

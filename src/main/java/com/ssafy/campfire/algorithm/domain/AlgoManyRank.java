package com.ssafy.campfire.algorithm.domain;

import com.ssafy.campfire.bootcamp.domain.Bootcamp;
import com.ssafy.campfire.user.domain.User;
import com.ssafy.campfire.utils.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "algo_many_rank")
public class AlgoManyRank extends BaseEntity {
    /*
    id : pk
    user : 사용자
    bootcamp : 부트캠프
    algorithm: 알고리즘
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bootcamp_id")
    Bootcamp bootcamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "algorithm_id")
    Algorithm algorithm;
}
package com.ssafy.campfire.algorithm.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class AlgorithmResult {
    private String bojId;
    private String result;
    private String solveDate;

}
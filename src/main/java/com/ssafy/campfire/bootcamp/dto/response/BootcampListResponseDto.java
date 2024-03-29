package com.ssafy.campfire.bootcamp.dto.response;

import com.ssafy.campfire.bootcamp.domain.Bootcamp;
import com.ssafy.campfire.bootcamp.domain.Language;
import com.ssafy.campfire.bootcamp.domain.Region;
import com.ssafy.campfire.bootcamp.domain.Track;
import jdk.jshell.Snippet;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Builder
public record BootcampListResponseDto(
         Long id,
         String name, //부캠 이름 
         Boolean cost, //수강료
         Boolean support, //지원금
         Boolean hasCodingtest, //코테 유무
         String onOff, //온/오프
         String startDate,
         String endDate,
         String imgUrl,
         Integer reviewCnt,
         Double score, //평점
         List<Track> tracks,
         List<Region> regions
) {
    public static BootcampListResponseDto of(Optional<Bootcamp> bootcamp, Optional<List<Track>> tracks, Optional<List<Region>> regions) {
        return BootcampListResponseDto.builder()
                .id(bootcamp.get().getId())
                .name(bootcamp.get().getName())
                .cost(bootcamp.get().getCost() == null || bootcamp.get().getCost() == 0.0)
                .support(bootcamp.get().getSupport())
                .hasCodingtest(bootcamp.get().getHasCodingtest())
                .onOff(bootcamp.get().getOnOff())
                .startDate((bootcamp.get().getStartDate() == null) ? null: bootcamp.get().getStartDate()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .endDate((bootcamp.get().getEndDate() == null) ? null: bootcamp.get().getEndDate()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .imgUrl(bootcamp.get().getImgUrl())
                .reviewCnt(bootcamp.get().getReviewCnt())
                .score(bootcamp.get().getTotalScore() / (double) bootcamp.get().getReviewCnt())
                .tracks(tracks.get())
                .regions(regions.get())
                .build();
    }
}

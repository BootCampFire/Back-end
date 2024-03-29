package com.ssafy.campfire.bootcamp.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.campfire.bootcamp.domain.Bootcamp;
import com.ssafy.campfire.bootcamp.domain.Track;
import com.ssafy.campfire.bootcamp.repository.CustomBootTrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import static com.ssafy.campfire.bootcamp.domain.QBootTrack.bootTrack;
import static com.ssafy.campfire.bootcamp.domain.QTrack.track;


import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomBootTrackRepositoryImpl implements CustomBootTrackRepository {
    private final JPAQueryFactory queryFactory;
    @Override
    public Optional<List<Track>> getBootTracksByBootcampId(Long bootcampId) {
        List<Track> trackList = (List<Track>) queryFactory.select(track)
                .from(bootTrack)
                .where(bootTrack.bootcamp.id.eq(bootcampId))
                .fetch();
        return Optional.ofNullable(trackList);
    }

    @Override
    public void deleteByBootcampId(Long bootcampId) {
        queryFactory.delete(bootTrack)
                .where(bootTrack.bootcamp.id.eq(bootcampId))
                .execute();
    }

    @Override
    public Boolean existsBootTrackByBootcampAndTrack(Long bootcampId, Long trackId) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(bootTrack)
                .where(
                        bootTrack.bootcamp.id.eq(bootcampId),
                        bootTrack.track.id.eq(trackId)
                )
                .fetchFirst();
        return fetchOne != null;
    }
}

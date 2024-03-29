package com.ssafy.campfire.bootcamp.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.campfire.bootcamp.domain.Language;
import com.ssafy.campfire.bootcamp.repository.CustomBootLanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.ssafy.campfire.bootcamp.domain.QBootLanguage.bootLanguage;
import static com.ssafy.campfire.bootcamp.domain.QBootTrack.bootTrack;
import static com.ssafy.campfire.bootcamp.domain.QLanguage.language;

@Repository
@RequiredArgsConstructor
public class CustomBootLanguageRepositoryImpl implements CustomBootLanguageRepository {
    private final JPAQueryFactory queryFactory;
    @Override
    public Optional<List<Language>> getBootLanguagesByBootcampId(Long bootcampId) {
        List<Language> languageList = (List<Language>) queryFactory.select(language)
                .from(bootLanguage)
                .where(bootLanguage.bootcamp.id.eq(bootcampId))
                .fetch();
        return Optional.ofNullable(languageList);
    }

    @Override
    public void deleteByBootcampId(Long bootcampId) {
        queryFactory.delete(bootLanguage)
                .where(bootLanguage.bootcamp.id.eq(bootcampId))
                .execute();
    }

    @Override
    public Boolean existsBootLanguageByBootcampAndLanguage(Long bootcampId, Long languageId) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(bootLanguage)
                .where(
                        bootLanguage.bootcamp.id.eq(bootcampId),
                        bootLanguage.language.id.eq(languageId)
                )
                .fetchOne();
        return fetchOne != null;
    }

}

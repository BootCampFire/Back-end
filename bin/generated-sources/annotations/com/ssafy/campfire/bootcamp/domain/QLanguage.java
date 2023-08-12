package com.ssafy.campfire.bootcamp.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLanguage is a Querydsl query type for Language
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLanguage extends EntityPathBase<Language> {

    private static final long serialVersionUID = -30442633L;

    public static final QLanguage language = new QLanguage("language");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QLanguage(String variable) {
        super(Language.class, forVariable(variable));
    }

    public QLanguage(Path<? extends Language> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLanguage(PathMetadata metadata) {
        super(Language.class, metadata);
    }

}


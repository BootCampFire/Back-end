package com.ssafy.campfire.board.domain;

import com.ssafy.campfire.board.domain.dto.BoardUpdate;
import com.ssafy.campfire.bootcamp.domain.Bootcamp;
import com.ssafy.campfire.category.domain.Category;
import com.ssafy.campfire.comment.domain.Comment;
import com.ssafy.campfire.likes.domain.Likes;
import com.ssafy.campfire.user.domain.User;
import com.ssafy.campfire.utils.domain.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    /**
     * id : pk
     * user : 글쓴이
     * category : 글 카테고리
     * bootcamp : 글쓴이의 소속 부캠
     * comments : 달린 댓글들
     * title : 글 제목
     * content : 글 내용
     * anonymous : 익명
     * commentCnt : 댓글 수
     * likeCnt : 좋아요 수
     * views : 조회수
     * maxRef : 마지막 댓글 ref
     * */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bootcamp_id")
    private Bootcamp bootcamp;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean anonymous;

    private Integer commentCnt;

    private Integer likeCnt;

    private Integer view;

    private Integer maxRef;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Likes> likes = new HashSet<>();

    public Board(String title, String content, Boolean anonymous){
        this.title = title;
        this.content = content;
        this.anonymous = anonymous;
        this.commentCnt = 0;
        this.likeCnt = 0;
        this.view = 0;
        this.maxRef = 0;
        this.createdDate = LocalDateTime.now().plusHours(9);;
    }

    public void setCategory(User user, Category category) {
        this.category = category;
        this.bootcamp = user.getBootcamp();
    }

    public void writeBy(User user){
        this.user = user;
    }

    public void minusLikes(Likes likes) {
        this.likes.remove(likes);
    }

    public void addView() {
        this.view++;
    }

    public void addLikeCnt() {
        this.likeCnt++;
    }

    public void minusLikeCnt() {
        this.likeCnt--;
    }

    public void addMaxRef() {
        this.maxRef++;
    }

    public void addCommentCnt() {
        this.commentCnt++;
    }

    public void minusCommentCnt() {
        this.commentCnt--;
    }

    public void update(BoardUpdate boardUpdate) {
        this.title = boardUpdate.title();
        this.content = boardUpdate.content();
        this.anonymous = boardUpdate.anonymous();
        this.updatedDate = LocalDateTime.now().plusHours(9);
    }
}

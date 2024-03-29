package com.ssafy.campfire.comment.domain;

import com.ssafy.campfire.board.domain.Board;
import com.ssafy.campfire.board.domain.dto.BoardUpdate;
import com.ssafy.campfire.category.domain.Category;
import com.ssafy.campfire.comment.domain.dto.CommentUpdate;
import com.ssafy.campfire.user.domain.User;
import com.ssafy.campfire.utils.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    /**
     * id : 댓글 pk
     * user : 댓글 작성자
     * board : 게시글
     * content : 댓글 내용
     * anonymous : 익명여부
     * ref : 몇번째댓글
     * refOrder : 순서
     * maxRefOrder : 마지막 대댓글
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean anonymous;

    @Column(nullable = false)
    private Integer ref;

//    @Column(nullable = false)
    private Integer refOrder;

    private Integer maxRefOrder;

    public void addMaxRefOrder() {
        this.maxRefOrder++;
    }

    public Comment(String content, Boolean anonymous){
        this.content = content;
        this.anonymous = anonymous;
        this.maxRefOrder = 0;
        this.createdDate = LocalDateTime.now().plusHours(9);
    }

    public void writeBy(User user, Board board){
        this.user = user;
        this.board = board;
    }

    public void setOrder(int ref, int refOrder){
        this.ref = ref;
        this.refOrder = refOrder;
    }

    public void update(CommentUpdate commentUpdate) {
        this.content = commentUpdate.content();
        this.anonymous = commentUpdate.anonymous();
        this.updatedDate = LocalDateTime.now().plusHours(9);
    }

}

package com.ssafy.campfire.comment.dto.response;

import com.ssafy.campfire.comment.domain.Comment;
import com.ssafy.campfire.user.domain.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record CommentReadResponse(

        /**
         * id : 댓글 pk
         * user : 댓글 작성자
         * bootcamp : 작성자 부캠
         * content : 댓글 내용
         * ref : 댓글 number
         * refOrder : 댓글 순서
         * isWriter : 작성자인지 여부
         * isLoginUser : 로그인 유저가 쓴 댓글인지
         * createdDate : 작성일
         */

        Long id,
        String user,
        String bootcamp,
        String content,
        Integer ref,
        Integer refOrder,
        Boolean isWriter,
        Boolean isLoginUser,
        String createdDate
) {
    public static CommentReadResponse of(Comment comment, User user){

        Boolean isWriter = false;
        if(comment.getBoard().getUser().getId() == comment.getUser().getId()) {
            isWriter = true;
        }

        Boolean isLoginUser = false;
        if(user.getId() == comment.getUser().getId()) {
            isLoginUser = true;
        }

        if(comment.getAnonymous()) {
            return new CommentReadResponse(
                    comment.getId(),
                    "익명",
                    "익명의 캠프",
                    comment.getContent(),
                    comment.getRef(),
                    comment.getRefOrder(),
                    isWriter,
                    isLoginUser,
                    comment.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            );
        }
        return new CommentReadResponse(
                comment.getId(),
                comment.getUser().getNickname(),
                comment.getUser().getBootcamp().getName(),
                comment.getContent(),
                comment.getRef(),
                comment.getRefOrder(),
                isWriter,
                isLoginUser,
                comment.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    public static CommentReadResponse of(Comment comment){

        Boolean isLoginUser = false;
        Boolean isWriter = false;
        if(comment.getBoard().getUser().getId() == comment.getUser().getId()) {
            isWriter = true;
        }

        if(comment.getAnonymous()) {
            return new CommentReadResponse(
                    comment.getId(),
                    "익명",
                    "익명의 캠프",
                    comment.getContent(),
                    comment.getRef(),
                    comment.getRefOrder(),
                    isWriter,
                    isLoginUser,
                    comment.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            );
        }
        return new CommentReadResponse(
                comment.getId(),
                comment.getUser().getNickname(),
                comment.getUser().getBootcamp().getName(),
                comment.getContent(),
                comment.getRef(),
                comment.getRefOrder(),
                isWriter,
                isLoginUser,
                comment.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }
}

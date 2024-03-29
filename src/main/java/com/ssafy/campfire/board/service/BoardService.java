package com.ssafy.campfire.board.service;

import com.ssafy.campfire.board.domain.Board;
import com.ssafy.campfire.board.dto.request.BoardCreateRequest;
import com.ssafy.campfire.board.dto.request.BoardUpdateRequest;
import com.ssafy.campfire.board.dto.response.BoardCreateResponse;
import com.ssafy.campfire.board.dto.response.BoardReadResponse;
import com.ssafy.campfire.board.dto.response.BoardUpdateResponse;
import com.ssafy.campfire.board.dto.response.UserBoardListResponse;
import com.ssafy.campfire.board.repository.BoardRepository;
import com.ssafy.campfire.category.domain.Category;
import com.ssafy.campfire.category.repository.CategoryRepository;
import com.ssafy.campfire.category.service.CategoryService;
import com.ssafy.campfire.likes.repository.LikesRepository;
import com.ssafy.campfire.user.domain.User;
import com.ssafy.campfire.user.repository.UserRepository;
import com.ssafy.campfire.utils.dto.response.GlobalPageResponseDto;
import com.ssafy.campfire.utils.error.enums.ErrorMessage;
import com.ssafy.campfire.utils.error.exception.custom.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LikesRepository likesRepository;

    private final CategoryService categoryService;

    public BoardCreateResponse save(Long userId, BoardCreateRequest request) {

        Long categoryId = request.categoryId();

        if(categoryId==9)
            categoryId = categoryService.getUserBootCampCategoryId(userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorMessage.USER_NOT_FOUND));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BusinessException(ErrorMessage.CATEGORY_NOT_FOUND));

        Board board = request.toEntity();
        board.setCategory(user, category);
        board.writeBy(user);
        Board savedPost = boardRepository.save(board);

        return BoardCreateResponse.from(savedPost);
    }

    /**
     * slice
     */
    @Transactional(readOnly = true)
    public Slice<UserBoardListResponse> getUserBoard(Long userId, Pageable pageable){

        Slice<UserBoardListResponse> slice = boardRepository
                .getUserBoard(userId, pageable);

        return slice;
    }

    /**
     * page
     */
//    @Transactional(readOnly = true)
//    public GlobalPageResponseDto<UserBoardListResponse> getUserBoard(Long userId, Pageable pageable){
//
//        Page<UserBoardListResponse> page = boardRepository
//                .getUserBoard(userId, pageable)
//                .map(UserBoardListResponse::of);
//
//        return GlobalPageResponseDto.of(page);
//    }

    @Transactional(readOnly = true)
    public BoardReadResponse getBoard(Long boardId, Long userId){
        boardRepository.getByIdFetchJoin(boardId)
                .orElseThrow(() -> new BusinessException((ErrorMessage.BOARD_NOT_FOUND)));

        boardRepository.updateView(boardId);

        Board board = boardRepository.getByIdFetchJoin(boardId)
                .orElseThrow(() -> new BusinessException((ErrorMessage.BOARD_NOT_FOUND)));

        Boolean hasLike = false;
        Boolean isWriter = false;

        if(userId != null){
            hasLike = likesRepository.hasLikeByUserId(boardId, userId);

            if(userId == board.getUser().getId()) {
                isWriter = true;
            }
        }

        String bootCampName = board.getBootcamp().getName();
        String userNickName = board.getUser().getNickname();

        if(board.getAnonymous()){
            bootCampName = "익명의 캠프";
            userNickName = "익명";
        }

        return BoardReadResponse.from(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                bootCampName,
                userNickName,
                isWriter,
                board.getCommentCnt(),
                board.getLikeCnt(),
                board.getView(),
                hasLike,
                board.getCreatedDate()
        );
    }

    public BoardUpdateResponse update(Long boardId,
                                      BoardUpdateRequest request) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BusinessException(ErrorMessage.BOARD_NOT_FOUND));

        board.update(request.toDto());

        return new BoardUpdateResponse(
                board.getId(),
                board.getCategory().getName().getMessage(),
                board.getUser().getNickname(),
                board.getTitle(),
                board.getContent(),
                board.getAnonymous()
        );
    }

    public Long delete(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BusinessException(ErrorMessage.BOARD_NOT_FOUND));
        boardRepository.delete(board);
        return board.getId();
    }

}

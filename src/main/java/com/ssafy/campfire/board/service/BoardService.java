package com.ssafy.campfire.board.service;

import com.ssafy.campfire.board.domain.Board;
import com.ssafy.campfire.board.dto.request.BoardCreateRequest;
import com.ssafy.campfire.board.dto.request.BoardUpdateRequest;
import com.ssafy.campfire.board.dto.response.BoardCreateResponse;
import com.ssafy.campfire.board.dto.response.BoardUpdateResponse;
import com.ssafy.campfire.board.repository.BoardRepository;
import com.ssafy.campfire.category.domain.Category;
import com.ssafy.campfire.category.repository.CategoryRepository;
import com.ssafy.campfire.user.domain.User;
import com.ssafy.campfire.user.repository.UserRepository;
import com.ssafy.campfire.utils.error.enums.ErrorMessage;
import com.ssafy.campfire.utils.error.exception.custom.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public BoardCreateResponse save(Long userId, BoardCreateRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorMessage.USER_NOT_FOUND));
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new BusinessException(ErrorMessage.CATEGORY_NOT_FOUND));

        Board board = request.toEntity();
        board.setCategory(user, category);
        board.writeBy(user);
        Board savedPost = boardRepository.save(board);

        return BoardCreateResponse.from(savedPost);
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

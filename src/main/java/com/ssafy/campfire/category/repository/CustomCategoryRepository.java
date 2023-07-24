package com.ssafy.campfire.category.repository;

import com.ssafy.campfire.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomCategoryRepository {
    List<Board> getLatestFiveBoard(Long categoryId);
    Page<Board> getBoardByNewest(Long categoryId, Pageable pageable);
    Page<Board> getBoardByLike(Long categoryId, Pageable pageable);
    Page<Board> getBoardByView(Long categoryId, Pageable pageable);
}

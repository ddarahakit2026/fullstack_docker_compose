package com.example.fullstack.board;

import com.example.fullstack.board.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByIdxDesc();
}

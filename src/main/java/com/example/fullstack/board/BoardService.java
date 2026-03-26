package com.example.fullstack.board;

import com.example.fullstack.board.model.Board;
import com.example.fullstack.board.model.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;


    public List<BoardDto.BoardRes> list() {
        List<Board> boardList = boardRepository.findAllByOrderByIdxDesc();
        return boardList.stream().map(BoardDto.BoardRes::from).toList();
    }

    public BoardDto.BoardRes reg(BoardDto.RegReq dto) {
        Board entity = boardRepository.save(dto.toEntity());

        return BoardDto.BoardRes.from(entity);
    }

    public BoardDto.BoardRes read(Long idx) {
        Board entity = boardRepository.findById(idx).orElseThrow();
        return BoardDto.BoardRes.from(entity);
    }
}

package com.example.fullstack.board;

import com.example.fullstack.board.model.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(originPatterns = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public ResponseEntity list() {
        List<BoardDto.BoardRes> result = boardService.list();
        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/{idx}")
    public ResponseEntity read(@PathVariable Long idx) {
        BoardDto.BoardRes result = boardService.read(idx);
        return ResponseEntity.status(200).body(result);
    }

    @PostMapping("/reg")
    public ResponseEntity reg(
            @RequestBody BoardDto.RegReq dto) {
        BoardDto.BoardRes result = boardService.reg(dto);
        return ResponseEntity.ok(result);
    }

}

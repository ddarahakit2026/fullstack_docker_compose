package com.example.fullstack.board.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class BoardDto {
    @Getter
    @Setter
    public static class RegReq {
        private String title;
        private String contents;

        public Board toEntity() {
            return Board.builder()
                    .title(title)
                    .contents(contents)
                    .build();
        }
    }


    @Builder
    @Getter
    public static class BoardRes {
        private Long idx;
        private String title;
        private String contents;

        public static BoardRes from(Board entity) {

            return BoardRes.builder()
                    .idx(entity.getIdx())
                    .title(entity.getTitle())
                    .contents(entity.getContents())
                    .build();
        }
    }
}

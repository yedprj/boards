package com.samin.boards.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachDTO extends CommonDTO{

    // 파일번호(pk)
    private Long idx;

    // 게시글 번호 (PK)
    private Long boardIdx;

    //원본 파일명
    private String originalName;

    // 저장 파일명
    private String saveName;

    // 파일 크기
    private long size;
}

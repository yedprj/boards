package com.samin.boards.service;

import com.samin.boards.domain.AttachDTO;
import com.samin.boards.domain.BoardDTO;
import com.samin.boards.paging.Criteria;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {

    public boolean registerBoard(BoardDTO params);

    public boolean registerBoard(BoardDTO params, MultipartFile[] files);

    public BoardDTO getBoardDetail(Long idx);

    public boolean deleteBoard(Long idx);

    public List<BoardDTO> getBoardList(BoardDTO params);

    public List<AttachDTO> getAttachFileList(Long boardIdx);

    public AttachDTO getAttachDetail(Long idx);
}

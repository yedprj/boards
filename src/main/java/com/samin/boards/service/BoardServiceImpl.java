package com.samin.boards.service;

import com.samin.boards.domain.AttachDTO;
import com.samin.boards.domain.BoardDTO;
import com.samin.boards.mapper.AttachMapper;
import com.samin.boards.mapper.BoardMapper;
import com.samin.boards.paging.Criteria;
import com.samin.boards.paging.PaginationInfo;
import com.samin.boards.util.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private AttachMapper attachMapper;

    @Autowired
    private FileUtils fileUtils;

    @Override
    public boolean registerBoard(BoardDTO params) {
        int queryResult = 0;

        if (params.getIdx() == null) {
            queryResult = boardMapper.insertBoard(params);
        } else {
            queryResult = boardMapper.updateBoard(params);

            if ("Y".equals(params.getChangeYn())) {
                attachMapper.deleteAttach(params.getIdx());

                if (!CollectionUtils.isEmpty(params.getFileIdxs())) {
                    attachMapper.undeleteAttach(params.getFileIdxs());
                }
            }
        }

        return (queryResult > 0);
    }

    @Override
    public boolean registerBoard(BoardDTO params, MultipartFile[] files) {
        int queryResult = 1;

        if (!registerBoard(params)) {
            return false;
        }

        List<AttachDTO> fileList = fileUtils.uploadFiles(files, params.getIdx());
        if (!CollectionUtils.isEmpty(fileList)) {
            queryResult = attachMapper.insertAttach(fileList);
            if (queryResult < 1) {
                queryResult = 0;
            }
        }
        return (queryResult > 0);
    }

    @Override
    public BoardDTO getBoardDetail(Long idx) {
        return boardMapper.selectBoardDetail(idx);
    }

    @Override
    public boolean deleteBoard(Long idx) {
        int queryResult = 0;

        BoardDTO board = boardMapper.selectBoardDetail(idx);

        if (board != null && "N".equals(board.getDeleteYn())) {
            queryResult = boardMapper.deleteBoard(idx);
        }
        return (queryResult == 1)? true : false;
    }

    @Override
    public List<BoardDTO> getBoardList(BoardDTO params) {
        List<BoardDTO> boardList = Collections.emptyList();

        int boardTotalCount = boardMapper.selectBoardTotalCount(params);

        PaginationInfo paginationInfo = new PaginationInfo(params);
        paginationInfo.setTotalRecordCount(boardTotalCount);

        params.setPaginationInfo(paginationInfo);

        if (boardTotalCount > 0) {
            boardList = boardMapper.selectBoardList(params);
        }
        return boardList;
    }

    @Override
    public List<AttachDTO> getAttachFileList(Long boardIdx) {

        int fileTotalCount = attachMapper.selectAttachTotalCount(boardIdx);
        if (fileTotalCount < 1) {
            return Collections.emptyList();
        }
        return attachMapper.selectAttachList(boardIdx);
    }

    @Override
    public AttachDTO getAttachDetail(Long idx) {
        return attachMapper.selectAttachDetail(idx);
    }
}

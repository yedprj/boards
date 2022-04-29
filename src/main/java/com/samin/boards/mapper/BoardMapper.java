package com.samin.boards.mapper;

import com.samin.boards.domain.BoardDTO;
import com.samin.boards.paging.Criteria;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface BoardMapper {

    public int insertBoard(BoardDTO params);

    public BoardDTO selectBoardDetail(Long idx);

    public int updateBoard(BoardDTO params);

    public int deleteBoard(Long idx);

    public List<BoardDTO> selectBoardList(BoardDTO params);

    public int selectBoardTotalCount(BoardDTO params);
}

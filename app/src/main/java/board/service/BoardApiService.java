package board.service;

import board.domain.Board;
import board.network.request.BoardApiRequest;
import board.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BoardApiService {

    private final BoardRepository boardRepository;

    public BoardApiService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Board create(BoardApiRequest boardApiRequest) {
        Board board = Board.builder()
                .title(boardApiRequest.getTitle())
                .content(boardApiRequest.getContent())
                .createdAt(LocalDateTime.now())
                .createdBy("mocha")
                .deleted(false)
                .build();
        return boardRepository.save(board);
    }

    public List<Board> readAll() {
        return boardRepository.findAll();
    }

    public Board read(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(NoSuchElementException::new);
    }

    public Board update(Long boardId, BoardApiRequest boardApiRequest) {
        return boardRepository.findById(boardId)
                .map(board -> {
                    board.setTitle(boardApiRequest.getTitle());
                    board.setContent(boardApiRequest.getContent());
                    board.setModifiedAt(LocalDateTime.now());
                    board.setModifiedBy("mocha");
                    return board;
                })
                .map(newBoard -> boardRepository.save(newBoard))
                .orElseThrow(NoSuchElementException::new);
    }

    public Board delete(Long boardId) {
        return boardRepository.findById(boardId)
                .map(board -> {
                    board.setDeleted(true);
                    return board;
                })
                .map(deletedBoard -> boardRepository.save(deletedBoard))
                .orElseThrow(NoSuchElementException::new);
    }
}

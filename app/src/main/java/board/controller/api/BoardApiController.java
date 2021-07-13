package board.controller.api;

import board.domain.Board;
import board.network.request.BoardApiRequest;
import board.service.BoardApiService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardApiController {

    private final BoardApiService boardApiService;

    public BoardApiController(BoardApiService boardApiService) {
        this.boardApiService = boardApiService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Board create(@RequestBody BoardApiRequest boardApiRequest) {
        return boardApiService.create(boardApiRequest);
    }

    @GetMapping
    public List<Board> readAll() {
        return boardApiService.readAll();
    }

    @GetMapping("/{boardId}")
    public Board read(@PathVariable Long boardId) {
        return boardApiService.read(boardId);
    }

    @PatchMapping("/{boardId}")
    public Board update(@PathVariable Long boardId, @RequestBody BoardApiRequest boardApiRequest) {
        return boardApiService.update(boardId, boardApiRequest);
    }

    @DeleteMapping("/{boardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Board delete(@PathVariable Long boardId) {
        return boardApiService.delete(boardId);
    }
}

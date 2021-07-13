package board.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards")
public class BoardApiController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create() {

    }

    @GetMapping
    public void readAll() {

    }

    @GetMapping("/{boardId}")
    public void read(@PathVariable Long boardId) {

    }

    @PatchMapping("/{boardId}")
    public void update(@PathVariable Long boardId) {

    }

    @DeleteMapping("/{boardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long boardId) {

    }
}

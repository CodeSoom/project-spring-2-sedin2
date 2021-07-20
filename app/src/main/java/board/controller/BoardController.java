package board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/boards")
public class BoardController {

    @GetMapping
    public String getBoardViewPage() {
        return "board/view";
    }

    @GetMapping("/form")
    public String getBoardForm() {
        return "board/form";
    }
}

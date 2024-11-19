package com.melly.myweb.board.free;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardFreeController {
    @Autowired
    private  IBoardFreeService boardFreeService;

    @GetMapping("/boardlist")
    private String boardlist(){
        return "board/boardList";
    }
}

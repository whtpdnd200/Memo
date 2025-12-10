package com.devwork.memo.memo;

import com.devwork.memo.memo.service.MemoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/memo")
public class MemoController {

    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @GetMapping("/list")
    public String list(Model model
                       , HttpSession session) {

        long userId = (Long)session.getAttribute("userId");

        model.addAttribute("memoList", memoService.getMemoList(userId));
        return "memo/list";
    }

    @GetMapping("/write")
    public String writeForm() {

        return "memo/form";
    }

    @GetMapping("/detail")
    public String detail(
            @RequestParam long id
            , Model model) {

        model.addAttribute("memo", memoService.getMemo(id));
        return "memo/detail";
    }
}

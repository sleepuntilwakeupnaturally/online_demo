package com.ljx.eduservice.controller;

import com.ljx.commonutils.R;
import com.ljx.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
// 解决跨域问题
//@CrossOrigin
public class EduLoginController {

    @Autowired
    EduTeacherService eduTeacherService;

    // login
    @PostMapping("login")
    public R login(){
        return R.ok().data("token", "admin");
    }

    // info
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles", "[admin]").data("name", "weh").data("avatar", "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F201901%2F26%2F20190126233946_cnmrj.thumb.1000_0.gif&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1651733173&t=ebd93203350290937e3036cda309a7ce");
    }
}

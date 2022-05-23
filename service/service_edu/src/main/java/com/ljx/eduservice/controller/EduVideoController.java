package com.ljx.eduservice.controller;


import com.ljx.commonutils.R;
import com.ljx.eduservice.client.VodClient;
import com.ljx.eduservice.entity.EduChapter;
import com.ljx.eduservice.entity.EduVideo;
import com.ljx.eduservice.service.EduVideoService;
import com.ljx.servicebase.ex.GuliException;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-07
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    @Autowired
    VodClient vodClient;

    // 添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);

        return R.ok();
    }

    // 根据id查小节
    @GetMapping("getVideoInfo/{videoId}")
    public R getVideoInfo(@PathVariable("videoId") String videoId){
        EduVideo eduVideo = videoService.getById(videoId);
        return R.ok().data("video", eduVideo);
    }

    // 修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }

    // 删除小节
    // 后面修养完善
    @DeleteMapping("deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable("videoId") String videoId){
        // 根据小节id 获取视频id
        EduVideo eduVideo = videoService.getById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();
        // 判断小节是否有视频id
        if (!StringUtils.isEmpty(videoSourceId)){
            // 根据视频id实现远程调用实现视频调用
            R r = vodClient.removeAlyVideo(videoSourceId);
            if (r.getCode() == 20001){
                throw new GuliException(20001, "删除视频失败");
            }
        }
        boolean b = videoService.removeById(videoId);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }

    }
}


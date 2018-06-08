package com.nuist.faxe.web.controller;

import com.nuist.faxe.common.controller.message.ResponseMessage;
import com.nuist.faxe.web.service.AudioSegmentationService;
import com.nuist.faxe.web.vo.SegmentationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分段Controller
 *
 * @author ZhouXiang
 **/
@RestController
@RequestMapping("audio/segmentation")
public class AudioSegmentationController {

    @Autowired
    private AudioSegmentationService service;

    /**
     * userId 切分音频 name（6位字符串+.mp3）
     * @param name
     * @return
     * @throws Exception
     */
    @CrossOrigin
    @GetMapping("/execute/{userId}/{name}")
    public ResponseMessage audioSegmentation(@PathVariable(value = "userId") String userId,
                                                @PathVariable(value = "name") String name,
                                                   @RequestParam(value = "cut", defaultValue = "false") String cut,
                                                        @RequestParam(value = "selectValue") Integer selectValue) throws Exception{
        boolean cutResult = cut.equals("true");
        service.audioSegmentation(userId, name, cutResult, selectValue);
        return ResponseMessage.ok();
    }

    @CrossOrigin
    @GetMapping("/getSegmentationInfo/{userId}/{name}")
    public ResponseMessage<SegmentationInfo> getInfo(@PathVariable(value = "userId") String userId,
                                                        @PathVariable(value = "name") String name){
        return ResponseMessage.ok(service.getSegmentationInfo(userId, name));
    }
}

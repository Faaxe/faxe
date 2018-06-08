package com.nuist.faxe.web.controller;

import com.google.common.collect.Lists;
import com.nuist.faxe.common.controller.message.ResponseMessage;
import com.nuist.faxe.common.utils.ParamConfig;
import com.nuist.faxe.web.domain.AudioFile;
import com.nuist.faxe.web.domain.UploadInfo;
import com.nuist.faxe.web.domain.User;
import com.nuist.faxe.web.service.UploadInfoService;
import com.nuist.faxe.web.service.UserService;
import com.nuist.faxe.web.vo.UploadInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 上传信息Controller
 *
 * @author ZhouXiang
 **/
@RestController
@RequestMapping("/audio/path")
public class UploadInfoController {

    @Autowired
    private UploadInfoService uploadInfoService;

    @Autowired
    private UserService userService;

    /**
     * 根据 uploadId 查询一条记录
     * @param id
     * @return UploadInfoVO
     */
    @CrossOrigin
    @GetMapping("/{userId}/{id}")
    public ResponseMessage<UploadInfoVO> getSingle(@PathVariable(value = "userId") String userId,
                                                        @PathVariable(value = "id") String id){
        User user = userService.getUserById(userId);
        UploadInfo result = uploadInfoService.getSingle(id);
        if(user.getRole().equals(ParamConfig.ROLE_ADMIN)
                || result.getUserId().equals(userId)){
            //entity => model
            return ResponseMessage.ok(entity2SimpleModel(result));
        }else{
            return ResponseMessage.error("This is not your audio!");
        }
    }

    /**
     * id为userId 的 User 删除 id为id的AudioUploadInfo
     * @param userId
     * @param id
     * @return UploadInfoVO
     */
    @CrossOrigin
    @DeleteMapping("/{userId}/{id}")
    public ResponseMessage<Boolean> deleteSingle(@PathVariable(value = "userId") String userId,
                                                   @PathVariable(value = "id") String id){
        ResponseMessage responseMessage = getSingle(userId, id);
        if(200 == responseMessage.getStatus()){
            boolean result = uploadInfoService.deleteSingle(id);
            return ResponseMessage.ok(result);
        }else{
            return responseMessage;
        }
    }
    /**
     * 根据userId 查询所有记录
     * 若userId为管理员则记录全部返回
     * @return
     */
    @CrossOrigin
    @GetMapping("/list/{userId}")
    public ResponseMessage<List<UploadInfoVO>> list(@PathVariable String userId){
        List<UploadInfoVO> list = Lists.newArrayList();
        User user = userService.getUserById(userId);
        uploadInfoService.list().forEach(item -> {
            if(user.getRole().equals(ParamConfig.ROLE_ADMIN)
                    || item.getUserId().equals(userId)){
                //将转化为VO的对象添加到list中
                list.add(entity2Model(item));
            }
        });
        return ResponseMessage.ok(list);
    }

    /**
     * entity 转化为 model
     * @param result
     * @return model
     */
    private UploadInfoVO entity2Model(UploadInfo result) {
        UploadInfoVO vo = new UploadInfoVO();
        vo.setId(result.getId());
        vo.setAudioId(result.getAudioId());
        vo.setUploadTime(result.getUploadTime());
        AudioFile file = result.getAudioFile();
        if(null != file){
            vo.setAudioOldName(file.getOldName());
            vo.setAudioPath(file.getPath());
        }
        vo.setUserId(result.getUserId());
        User user = result.getUser();
        if(null != user){
            vo.setUserName(user.getUserName());
        }
        return vo;
    }

    private UploadInfoVO entity2SimpleModel(UploadInfo result) {
        UploadInfoVO vo = new UploadInfoVO();
        AudioFile file = result.getAudioFile();
        if(null != file){
            vo.setAudioOldName(file.getOldName());
            String path = file.getPath();
            //转化为相对路径 eg. upload/A1b.mp3
            vo.setAudioPath(path.substring(14));
        }
        return vo;
    }
}

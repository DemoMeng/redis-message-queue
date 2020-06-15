package com.rmq.web.controller;

import com.rmq.web.common.HttpConstants;
import com.rmq.web.common.MsgListStatus;
import com.rmq.web.common.ServiceResult;
import com.rmq.web.service.DataServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author mqz
 * @description
 * @since 2020/6/15
 */
@RestController
@RequestMapping(value="/web")
@Api(tags = "web")
public class WebController {

    @Autowired
    private DataServiceImpl dataService;

    @GetMapping(value = "/getAllTopics")
    @ApiOperation(value = "获取所有注册的topics",notes = "获取所有注册的topics")
    public ServiceResult getAllTopics() {
        ServiceResult result = null;
        Set<String> data = dataService.getAllTopics();
        if(data != null) {
            result = new ServiceResult(HttpConstants.RESUTL_OK, "");
            result.setData(data);
        }else {
            result = new ServiceResult(HttpConstants.RESUTL_FAIL, HttpConstants.CODE_EXCEPTION, true);
        }
        return result;
    }

    @GetMapping(value = "/getAllGroups")
    @ApiOperation(value = "获取所有注册的groups",notes = "获取所有注册的groups")
    public ServiceResult getAllGroups() {
        ServiceResult result = null;
        Set<String> data = dataService.getAllGroups();
        if(data != null) {
            result = new ServiceResult(HttpConstants.RESUTL_OK, "");
            result.setData(data);
        }else {
            result = new ServiceResult(HttpConstants.RESUTL_FAIL, HttpConstants.CODE_EXCEPTION, true);
        }
        return result;
    }

    @GetMapping(value = "/getTopicGroups/{topic}")
    @ApiOperation(value = "获取topic下的分组",notes = "获取topic下的分组")
    public ServiceResult getTopicGroups(@PathVariable("topic") String topic) {
        ServiceResult result = null;
        Set<String> data = dataService.getTopicGroups(topic);
        if(data != null) {
            result = new ServiceResult(HttpConstants.RESUTL_OK, "");
            result.setData(data);
        }else {
            result = new ServiceResult(HttpConstants.RESUTL_FAIL, HttpConstants.CODE_EXCEPTION, true);
        }
        return result;
    }

    @GetMapping(value = "/getGroupTopics/{group}")
    @ApiOperation(value = "获取分组监听的topic",notes = "获取分组监听的topic")
    public ServiceResult getGroupTopics(@PathVariable("group") String group) {
        ServiceResult result = null;
        Set<String> data = dataService.getGroupTopics(group);
        if(data != null) {
            result = new ServiceResult(HttpConstants.RESUTL_OK, "");
            result.setData(data);
        }else {
            result = new ServiceResult(HttpConstants.RESUTL_FAIL, HttpConstants.CODE_EXCEPTION, true);
        }
        return result;
    }

    @GetMapping(value = "/getMsgList/{topic}/{group}")
    @ApiOperation(value = "获取消息队列",notes = "获取消息队列")
    public ServiceResult getMsgList(@PathVariable("topic") String topic, @PathVariable("group") String group) {
        ServiceResult result = null;
        MsgListStatus data = dataService.getMsgList(topic, group);
        if(data != null) {
            result = new ServiceResult(HttpConstants.RESUTL_OK, "");
            result.setData(data);
        }else {
            result = new ServiceResult(HttpConstants.RESUTL_FAIL, HttpConstants.CODE_EXCEPTION, true);
        }
        return result;
    }

    @GetMapping(value = "/getAllMsgList")
    @ApiOperation(value = "获取所有消息队列",notes = "获取所有消息队列")
    public ServiceResult getAllMsgList() {
        ServiceResult result = null;
        List<MsgListStatus> data = dataService.getAllMsgList();
        if(data != null) {
            result = new ServiceResult(HttpConstants.RESUTL_OK, "");
            result.setData(data);
        }else {
            result = new ServiceResult(HttpConstants.RESUTL_FAIL, HttpConstants.CODE_EXCEPTION, true);
        }
        return result;
    }


    @GetMapping(value = "/changeConsumerStatus/{topic}/{group}/{status}")
    @ApiOperation(value = "开启/关闭消费者",notes = "开启/关闭消费者")
    public ServiceResult changeConsumerStatus(@PathVariable("topic") String topic, @PathVariable("group") String group, @PathVariable("status") int status) {
        ServiceResult serviceResult = null;
        int result = dataService.changeConsumerStatus(topic, group, status);
        if(result == 1) {
            serviceResult = new ServiceResult(HttpConstants.RESUTL_OK, "操作成功");
        }else {
            serviceResult = new ServiceResult(HttpConstants.RESUTL_FAIL, HttpConstants.CODE_EXCEPTION, true);
        }
        return serviceResult;
    }





}

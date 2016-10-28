package com.zoufanqi.controller;

import com.zoufanqi.consts.ConstDB;
import com.zoufanqi.entity.Tree;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.result.ResultBuilder;
import com.zoufanqi.result.ResultJson;
import com.zoufanqi.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 树
 * Created by vanki on 16/10/28.
 */
@Controller
@RequestMapping("/tree")
public class TreeController extends BaseSessionController {
    @Autowired
    private TreeService treeService;

    @ResponseBody
    @RequestMapping(value = "/add.json.login", method = RequestMethod.POST)
    public ResultJson add(Long parentId, Integer type, Long targetId, String title,
                          Boolean isPublic, String description) throws ZouFanqiException {
        return this.treeService.add(parentId, this.getUserId(), type, targetId, title, isPublic, description);
//        return ResultBuilder.build();
    }

    @ResponseBody
    @RequestMapping(value = "/updateById.json.login", method = RequestMethod.POST)
    public ResultJson updateById(Long id, Long parentId, String title, Boolean isPublic,
                                 String description) throws ZouFanqiException {
        return this.treeService.updateById(this.getUserId(), id, parentId, title, isPublic, description);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteById.json.login", method = RequestMethod.POST)
    public ResultJson deleteById(Long id) throws ZouFanqiException {
        return this.treeService.deleteById(this.getUserId(), id);
    }

    @ResponseBody
    @RequestMapping(value = "/getRootList.json", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultJson getRootList(Long userId) throws ZouFanqiException {
        boolean isLogin = false;
        if (userId == null) {
            userId = this.justGetUserId();
            isLogin = true;
        }
        List<Tree> list = this.treeService.getListByParentId(userId, ConstDB.DEFAULT_PARENT_ID, isLogin);
        return ResultBuilder.build(list);
    }

    @ResponseBody
    @RequestMapping(value = "/getListByParentId.json", method = RequestMethod.GET)
    public ResultJson getListByParentId(Long userId, Long parentId) throws ZouFanqiException {
        boolean isLogin = false;
        if (userId == null) {
            userId = this.justGetUserId();
            isLogin = true;
        }
        List<Tree> list = this.treeService.getListByParentId(userId, parentId, isLogin);
        return ResultBuilder.build(list);
    }
}

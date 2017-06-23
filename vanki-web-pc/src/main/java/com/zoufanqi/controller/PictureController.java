package com.zoufanqi.controller;

import com.zoufanqi.consts.ConstService;
import com.zoufanqi.entity.Picture;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.param.common.Page;
import com.zoufanqi.result.ResultBuilder;
import com.zoufanqi.result.ResultJson;
import com.zoufanqi.service.PictureService;
import com.zoufanqi.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by vanki on 2017/6/22.
 */
@Controller
@RequestMapping("/picture")
public class PictureController extends BaseController {
    @Autowired
    private PictureService pictureService;

    @ResponseBody
    @RequestMapping("/getPage.json")
    public ResultJson getPage(Integer useType, Integer pageNo, Integer pageSize, Integer navNum)
            throws ZouFanqiException {
        Page<Picture> page = this.pictureService.getPage(this.getUserId(), useType, pageNo, pageSize, navNum);
        List<Picture> list;
        if (page == null || (list = page.getData()) == null || list.isEmpty()) return ResultBuilder.build(page);

        String path;
        for (Picture pic : list) {
            path = pic.getPath();
            if (StringUtil.isEmpty(path)) continue;
            path = ConstService.imageDomain + path;
            pic.setPath(path);
        }

        return ResultBuilder.build(page);
    }
}

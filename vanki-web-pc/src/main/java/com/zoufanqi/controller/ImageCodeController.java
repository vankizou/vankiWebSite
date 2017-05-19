package com.zoufanqi.controller;

import com.zoufanqi.consts.ConstService;
import com.zoufanqi.result.ResultBuilder;
import com.zoufanqi.result.ResultJson;
import com.zoufanqi.status.EnumStatusCode;
import com.zoufanqi.utils.EncryptUtil;
import com.zoufanqi.utils.StringUtil;
import com.zoufanqi.utils.imagecode.ImageCodeMathCalcUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * 图片验证
 * Created by vanki on 2017/5/18.
 */
@Controller
@RequestMapping("/imageCode")
public class ImageCodeController extends BaseController {

    @ResponseBody
    @RequestMapping(value = "/getImageCode.json", method = RequestMethod.GET)
    public ResultJson getImageCode(Integer width, Integer height) throws IOException {
        return ResultBuilder.build(getImageCodeBase64(width, height));
    }

    /**
     * 验证图片验证码，成功1，失败0
     *
     * @param code
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/validate.json", method = RequestMethod.GET)
    public ResultJson validateImageCodeUrl(String code) {
        return validate(code) ? ResultBuilder.build() : ResultBuilder.buildError(EnumStatusCode.IMAGE_CODE_VALIDATE_FAIL);
    }

    public String getImageCodeBase64(Integer width, Integer height) throws IOException {
        width = width == null ? 220 : width;
        height = height == null ? 45 : height;
        ImageCodeMathCalcUtil imageCode = new ImageCodeMathCalcUtil(width, height);
        String val = imageCode.getValue();
        val = EncryptUtil.md5(val);
        this.setCookie(ConstService.COOKIE_IMAGE_CODE_VALUE, val, ConstService.COOKIE_IMAGE_CODE_VALUE_TIME);
        return imageCode.writeBASE64();
    }

    public boolean validate(String code) {
        if (StringUtil.isEmpty(code)) return false;

        String cookieVal = this.getCookie(ConstService.COOKIE_IMAGE_CODE_VALUE);
        if (StringUtil.isEmpty(cookieVal)) return false;

        if (cookieVal.equals(EncryptUtil.md5(code))) return true;

        return false;
    }
}

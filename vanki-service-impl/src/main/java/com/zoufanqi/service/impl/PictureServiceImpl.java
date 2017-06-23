package com.zoufanqi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zoufanqi.consts.ConstDB;
import com.zoufanqi.consts.ConstService;
import com.zoufanqi.consts.EnumRedisKey;
import com.zoufanqi.entity.Picture;
import com.zoufanqi.entity.PictureExample;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.mapper.PictureMapper;
import com.zoufanqi.param.common.Page;
import com.zoufanqi.service.PictureService;
import com.zoufanqi.service.redis.RedisTemplate;
import com.zoufanqi.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("pictureService")
public class PictureServiceImpl implements PictureService {
    @Autowired
    private PictureMapper pictureMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public int add(Picture pic) {
        if (pic == null || !pic.validate()) return -1;

        pic.setCrateDatetime(new Date());
        pic.setIsDel(ConstDB.ISDEL_FALSE);

        int status = this.pictureMapper.insertSelective(pic);
        if (status > 0) this.redisTemplate.del(EnumRedisKey.TIME_IMAGE_USER_PAGE_.name() + pic.getUserId());

        return status;
    }

    @Override
    public Picture getById(Long id) throws ZouFanqiException {
        if (StringUtil.isNotId(id)) return null;

        PictureExample example = new PictureExample();
        example.createCriteria().andIdEqualTo(id).andIsDelEqualTo(ConstDB.ISDEL_FALSE);

        List<Picture> list = this.pictureMapper.selectByExample(example);

        if (list == null || list.isEmpty()) return null;
        return list.get(0);
    }

    @Override
    public Picture getByUUID(String uuid) throws ZouFanqiException {
        if (StringUtil.isEmpty(uuid)) return null;

        PictureExample example = new PictureExample();
        example.createCriteria().andUuidEqualTo(uuid).andIsDelEqualTo(ConstDB.ISDEL_FALSE);

        List<Picture> list = this.pictureMapper.selectByExample(example);

        if (list == null || list.isEmpty()) return null;
        return list.get(0);
    }

    @Override
    public Page<Picture> getPage(Long loginUserId, Integer useType, Integer pageNo, Integer pageSize, Integer navNum) throws ZouFanqiException {
        if (StringUtil.isNotId(loginUserId)) return null;
        if (useType == null) useType = ConstDB.Picture.USE_TYPE_NOTE;

        Page page = new Page(pageNo, pageSize, 0, navNum);

        String key = EnumRedisKey.TIME_IMAGE_USER_PAGE_.name() + loginUserId;
        String mapKey = new StringBuffer()
                .append(page.getNum()).append("_")
                .append(page.getPageSize()).append("_")
                .append(page.getNavNum()).toString();
        String redisInfo = this.redisTemplate.get(key);
        JSONObject map = null;

        outIf:
        if (StringUtil.isNotEmpty(redisInfo)) {
            if (ConstService.REDIS_DEFAULT_INFO.equals(redisInfo)) return null;

            map = JSON.parseObject(redisInfo);
            String info = map.getString(mapKey);

            if (StringUtil.isEmpty(info)) break outIf;
            if (ConstService.REDIS_DEFAULT_INFO.equals(info)) return null;

            Page<Picture> redisPage = JSON.parseObject(info, Page.class);
            redisPage.setData(JSON.parseArray(String.valueOf(redisPage.getData()), Picture.class));
            return redisPage;
        }

        PictureExample example = new PictureExample();
        example.createCriteria()
                .andUserIdEqualTo(loginUserId)
                .andIsDelEqualTo(ConstDB.ISDEL_FALSE)
                .andUseTypeEqualTo(useType);

        int count = this.pictureMapper.countByExample(example);
        page = new Page(pageNo, pageSize, count, navNum);

        example.setOffset(page.getStartRow());
        example.setRows(page.getPageSize());
        example.setOrderByClause("ID DESC");

        List<Picture> list = this.pictureMapper.selectByExample(example);
        if (map == null) map = new JSONObject();

        if (list == null || list.isEmpty()) {
            map.put(mapKey, ConstService.REDIS_DEFAULT_INFO);
            page = null;
        } else {
            page.setData(list);
            map.put(mapKey, page);
        }
        this.redisTemplate.setex(key, EnumRedisKey.TIME_IMAGE_USER_PAGE_.getTime(), JSON.toJSONString(map));
        return page;
    }
}
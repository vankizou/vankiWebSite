package com.zoufanqi.service.impl;

import com.zoufanqi.consts.ConstDB;
import com.zoufanqi.entity.Tree;
import com.zoufanqi.entity.TreeExample;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.mapper.TreeMapper;
import com.zoufanqi.result.ResultBuilder;
import com.zoufanqi.result.ResultJson;
import com.zoufanqi.service.TreeService;
import com.zoufanqi.status.StatusCode;
import com.zoufanqi.status.VankiCode;
import com.zoufanqi.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("treeService")
public class TreeServiceImpl implements TreeService {
    @Autowired
    private TreeMapper treeMapper;

    /**
     * 添加树
     *
     * @param parentId
     * @param userId
     * @param type
     * @param title
     * @param isPublic
     * @param description
     *
     * @return <br />
     * 1001 父节点数据不存在
     * 20001 不是文件夹, 不能添加文件
     * 20002 根节点必须为文件夹
     *
     * @throws ZouFanqiException
     */
    @Override
    public ResultJson add(Long parentId, Long userId, Integer type, Long targetId, String title,
                          Boolean isPublic, String description) throws ZouFanqiException {
        if (StringUtil.isNotAutoId(userId) || StringUtil.isEmpty(title))
            return ResultBuilder.buildError404();
        if (type != null && StringUtil.isNotAutoId(targetId)) return ResultBuilder.buildError404();

        if (parentId != null && parentId != ConstDB.DEFAULT_PARENT_ID) {
            Tree parent = this.getById(parentId, true);
            if (parent == null) return ResultBuilder.buildError(StatusCode.PARENT_ID_NOT_EXIST);
            // 不是文件夹不能添加文件
            if (parent.getType() != null && type != null) return ResultBuilder.buildError(VankiCode.Tree.NOT_FOLDER);
        } else {
            parentId = ConstDB.DEFAULT_PARENT_ID;
        }
        if (parentId == ConstDB.DEFAULT_PARENT_ID && type != null)
            return ResultBuilder.buildError(VankiCode.Tree.ROOT_MUST_FOLDER);

        if (isPublic == null) isPublic = true;

        Tree tree = new Tree();
        tree.setIsDel(ConstDB.ISDEL_FALSE);
        tree.setCreateDatetime(new Date());
        tree.setCountTarget(0);
        tree.setIsOpen(ConstDB.Tree.IS_OPEN_NO);

        tree.setParentId(parentId);
        tree.setUserId(userId);
        tree.setType(type);
        tree.setTitle(title);
        tree.setIsPublic(isPublic ? ConstDB.Tree.IS_PUBLIC_YES : ConstDB.Tree.IS_PUBLIC_NO);
        tree.setDescription(description);

        int status = this.treeMapper.insertSelective(tree);

        if (status > 0) return ResultBuilder.build();
        return ResultBuilder.buildErrorDB();
    }

    /**
     * @param loginUserId
     * @param id
     * @param parentId
     * @param title
     * @param isPublic
     * @param description
     *
     * @return <br />
     * 1000 数据不存在
     * 1002 不是本人操作
     *
     * @throws ZouFanqiException
     */
    @Override
    public ResultJson updateById(Long loginUserId, Long id, Long parentId, String title, Boolean isPublic,
                                 String description) throws ZouFanqiException {
        if (StringUtil.isNotAutoId(loginUserId) || StringUtil.isNotAutoId(id)) return ResultBuilder.buildError404();

        if (StringUtil.isNotAutoId(parentId) && StringUtil.isEmpty(title) && isPublic == null)
            return ResultBuilder.build();

        Tree old = this.getById(id, true);
        if (old == null) return ResultBuilder.buildError(StatusCode.ID_NOT_EXIST);
        if (old.getUserId() != loginUserId) return ResultBuilder.buildError(StatusCode.DATA_NOT_YOURSELF);

        Tree tree = new Tree();
        tree.setId(id);
        tree.setParentId(parentId);
        tree.setTitle(title);
        tree.setIsPublic(isPublic ? ConstDB.Tree.IS_PUBLIC_YES : ConstDB.Tree.IS_PUBLIC_NO);

        int status = this.treeMapper.updateByPrimaryKeySelective(tree);
        if (status > 0) return ResultBuilder.build();
        return ResultBuilder.buildErrorDB();
    }

    /**
     * @param id
     * @param isOpen
     * @param countTarget
     *
     * @return
     *
     * @throws ZouFanqiException
     */
    @Override
    public int autoUpdateById(Long id, Boolean isOpen, Integer countTarget) throws ZouFanqiException {
        if (StringUtil.isNotAutoId(id)) return -1;
        if (isOpen == null && countTarget == null) return 1;
        Tree tree = new Tree();
        tree.setId(id);
        tree.setIsOpen(isOpen != null && isOpen ? ConstDB.Tree.IS_OPEN_YES : ConstDB.Tree.IS_OPEN_NO);
        tree.setCountTarget(countTarget);
        return this.treeMapper.updateByPrimaryKeySelective(tree);
    }

    /**
     * @param loginUserId
     * @param id
     *
     * @return 1002 不是本人操作
     *
     * @throws ZouFanqiException
     */
    @Override
    public ResultJson deleteById(Long loginUserId, Long id) throws ZouFanqiException {
        if (StringUtil.isNotAutoId(id)) return ResultBuilder.build();

        Tree old = this.getById(id, true);
        if (old == null) return ResultBuilder.build();

        if (StringUtil.isNotAutoId(loginUserId) || old.getUserId() != loginUserId)
            return ResultBuilder.buildError(StatusCode.DATA_NOT_YOURSELF);

        Tree tree = new Tree();
        tree.setId(id);
        tree.setIsDel(ConstDB.ISDEL_TRUE);

        this.treeMapper.updateByPrimaryKeySelective(tree);

        return ResultBuilder.build();
    }

    @Override
    public Tree getById(Long id, boolean isLogin) throws ZouFanqiException {
        if (StringUtil.isNotAutoId(id)) return null;

        TreeExample example = new TreeExample();
        TreeExample.Criteria c = example.createCriteria();
        c.andIdEqualTo(id).andIsDelEqualTo(ConstDB.ISDEL_FALSE);
        /**
         * 不是本人, 则只获取公开数据
         */
        if (!isLogin) c.andIsPublicEqualTo(ConstDB.Tree.IS_PUBLIC_YES);

        List<Tree> list = this.treeMapper.selectByExample(example);
        if (list == null || list.isEmpty()) return null;
        return list.get(0);
    }

    @Override
    public List<Tree> getListByParentId(Long userId, Long parentId, boolean isLogin) throws ZouFanqiException {
        if (StringUtil.isNotAutoId(parentId)) return null;

        TreeExample example = new TreeExample();
        TreeExample.Criteria c = example.createCriteria();
        c.andIsDelEqualTo(ConstDB.ISDEL_FALSE).
                andParentIdEqualTo(parentId).
                andUserIdEqualTo(userId);
        /**
         * 不是本人, 则只获取公开数据
         */
        if (!isLogin) c.andIsPublicEqualTo(ConstDB.Tree.IS_PUBLIC_YES);

        return this.treeMapper.selectByExample(example);
    }
}
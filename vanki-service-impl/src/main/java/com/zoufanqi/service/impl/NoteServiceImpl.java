package com.zoufanqi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zoufanqi.consts.ConstDB;
import com.zoufanqi.consts.ConstService;
import com.zoufanqi.consts.EnumRedisKey;
import com.zoufanqi.entity.Note;
import com.zoufanqi.entity.NoteDetail;
import com.zoufanqi.entity.NoteExample;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.mapper.NoteMapper;
import com.zoufanqi.param.common.Page;
import com.zoufanqi.result.ResultBuilder;
import com.zoufanqi.result.ResultJson;
import com.zoufanqi.service.NoteDetailService;
import com.zoufanqi.service.NoteService;
import com.zoufanqi.service.redis.RedisTemplate;
import com.zoufanqi.status.EnumStatusCode;
import com.zoufanqi.utils.StringUtil;
import com.zoufanqi.vo.NoteTreeVo;
import com.zoufanqi.vo.NoteViewVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("noteService")
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteMapper noteMapper;
    @Autowired
    private NoteDetailService noteDetailService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ResultJson add(Note note, List<NoteDetail> noteDetailList) throws ZouFanqiException {
        if (note == null ||
                StringUtil.isNotId(note.getUserId()) ||
                note.getTitle() == null ||
                StringUtil.isEmpty(note.getTitle().trim()))
            return ResultBuilder.buildParamError();

        if (note.getTitle().length() > ConstService.NOTE_TITLE_LEN_MAX)
            return ResultBuilder.buildError(EnumStatusCode.NOTE_TITLE_OVER_FLOW_MAX_LEN);

        if (note.getType() == null) {
            note.setType(ConstDB.Note.TYPE_MARKDOWN);
        }
        if (note.getSequence() == null) note.setSequence(ConstDB.FIRST_SEQUENCE);
        if (note.getParentId() == null) note.setParentId(ConstDB.DEFAULT_PARENT_ID);

        if (StringUtil.notEquals(note.getParentId(), ConstDB.DEFAULT_PARENT_ID)) {
            Note parent = this.getById(note.getParentId());
            if (parent == null || !note.getUserId().equals(parent.getUserId()))
                return ResultBuilder.buildError(EnumStatusCode.DB_PARENT_NOT_FOUND);
            if (note.getSecret() == null) {
                if (parent.getSecret() == ConstDB.Note.SECRET_PWD) {
                    note.setSecret(ConstDB.Note.SECRET_PWD);
                    note.setPassword(parent.getPassword());
                } else if (parent.getSecret() == ConstDB.Note.SECRET_PRIVATE) {
                    note.setSecret(ConstDB.Note.SECRET_PRIVATE);
                }
            }
        }
        if (note.getSecret() == null) {
            note.setSecret(ConstDB.Note.SECRET_OPEN);
        } else if (note.getSecret() != ConstDB.Note.SECRET_PWD) {
            note.setPassword(null);
        }

        Date date = new Date();
        note.setUpdateDatetime(date);
        note.setCreateDatetime(date);
        note.setIsDel(ConstDB.ISDEL_FALSE);
        note.setViewNum(0L);
        note.setCountNote(0);
        this.setDefaultStatus(note);

        int count = addOrUpdateNoteDetail(note.getId(), note.getUserId(), noteDetailList);
        if (count != -1) note.setCountNoteContent(count);

        int status = this.noteMapper.insertSelective(note);
        if (status > 0) {
            /**
             * 更新父节点笔记数量
             */
            Integer countNote = this.countNote(note.getParentId());
            if (countNote != null) {
                Note uParent = new Note();
                uParent.setId(note.getParentId());
                uParent.setCountNote(++countNote);
                this.updateById(note.getUserId(), uParent, null, true);
            }

            this.redisTemplate.del(EnumRedisKey.TIME_NOTE_PAGE_TREE_.name() + note.getUserId());
            this.redisTemplate.del(EnumRedisKey.TIME_NOTE_PAGE_HOME.name());
            return ResultBuilder.build(note);
        }
        throw new ZouFanqiException(EnumStatusCode.DB_ERROR);
    }

    @Override
    public ResultJson updateById(Long loginUserId, Note note, List<NoteDetail> noteDetailList, boolean isSystem) throws ZouFanqiException {
        if (note == null || StringUtil.isNotId(note.getId()) || StringUtil.isNotId(loginUserId))
            return ResultBuilder.buildParamError();

        Note old = this.getById(note.getId());
        if (old == null) return ResultBuilder.buildError(EnumStatusCode.DB_NOT_FOUND);

        if (StringUtil.notEquals(old.getUserId(), loginUserId))
            return ResultBuilder.buildError(EnumStatusCode.DB_DATA_NOT_YOURS);

        if (null != note.getTitle() &&
                StringUtil.isEmpty(note.getTitle().trim())) {
            note.setTitle(null);
        }
        if (note.getTitle() != null && note.getTitle().length() > ConstService.NOTE_TITLE_LEN_MAX)
            return ResultBuilder.buildError(EnumStatusCode.NOTE_TITLE_OVER_FLOW_MAX_LEN);

        note.setUserId(null);
        note.setCreateDatetime(null);

        if (!isSystem) {
            note.setType(null);
            note.setViewNum(null);
            note.setCountNote(null);
            note.setCountNoteContent(null);
            note.setIsDel(null);
            note.setUpdateDatetime(new Date());
            this.setDefaultStatus(note);
        }

        if (note.getSecret() != null &&
                note.getSecret() != ConstDB.Note.SECRET_PWD &&
                StringUtil.isNotEmpty(old.getPassword())) {
            note.setPassword("");
        }

        int countNoteDetail = this.addOrUpdateNoteDetail(note.getId(), loginUserId, noteDetailList);
        if (countNoteDetail > -1) note.setCountNoteContent(countNoteDetail);

        int status = this.noteMapper.updateByPrimaryKeySelective(note);
        if (status > 0) {
            if (StringUtil.isId(note.getParentId()) &&
                    StringUtil.notEquals(note.getParentId(), old.getParentId())) {
                /**
                 * 更新旧父节点笔记数量
                 */

                if (StringUtil.notEquals(old.getParentId(), ConstDB.DEFAULT_PARENT_ID)) {
                    int s = updateCountNote(loginUserId, old.getParentId(), -1);
                    if (s <= 0) throw new ZouFanqiException(EnumStatusCode.DB_PARENT_NOT_FOUND);
                }
                /**
                 * 更新新父节点笔记数量
                 */
                if (StringUtil.notEquals(note.getParentId(), ConstDB.DEFAULT_PARENT_ID)) {
                    int s = updateCountNote(loginUserId, note.getParentId(), 1);
                    if (s <= 0) throw new ZouFanqiException(EnumStatusCode.DB_PARENT_NOT_FOUND);
                }
            }
            if (!isSystem) this.redisTemplate.del(EnumRedisKey.TIME_NOTE_PAGE_HOME.name());
            this.redisTemplate.del(EnumRedisKey.TIME_NOTE_.name() + note.getId());
            this.redisTemplate.del(EnumRedisKey.TIME_NOTE_PAGE_TREE_.name() + loginUserId);
            return ResultBuilder.build(countNoteDetail > -1 ? countNoteDetail : null);
        } else {
            return ResultBuilder.buildDBError();
        }
    }

    /**
     * 设置默认审核状态
     *
     * @param note
     */
    private void setDefaultStatus(Note note) {
        if (note == null) return;
//        note.setStatus(ConstDB.Note.STATUS_PASS);
        note.setStatus(ConstDB.Note.STATUS_ALL_PASS);
    }

    /**
     * 更新笔记的下级笔记数量
     *
     * @param loginUserId
     * @param parentId
     * @param changeNum
     *
     * @return
     *
     * @throws ZouFanqiException
     */
    private int updateCountNote(Long loginUserId, Long parentId, Integer changeNum) throws ZouFanqiException {
        if (StringUtil.isNotId(loginUserId) || StringUtil.isNotId(parentId)) return -1;
        if (parentId == ConstDB.DEFAULT_PARENT_ID) return 1;

        Note pNote = this.getByIdInRedis(loginUserId, parentId);
        if (pNote == null || StringUtil.notEquals(loginUserId, pNote.getUserId())) return 0;

        Integer pcn = this.countNote(parentId);
        pcn = pcn == null ? 0 : pcn;
        pcn += changeNum;
        if (pcn < 0) pcn = 0;

        pNote.setCountNote(pcn);
        this.updateById(loginUserId, pNote, null, true);
        return 1;
    }

    private int addOrUpdateNoteDetail(Long noteId, Long userId, List<NoteDetail> noteDetailList) throws ZouFanqiException {
        if (StringUtil.isNotId(noteId) || StringUtil.isNotId(userId) ||
                noteDetailList == null || noteDetailList.isEmpty()) return -1;

        List<Long> uDetailIdList = new ArrayList<>();
        for (NoteDetail detail : noteDetailList) {
            if (StringUtil.isNotId(detail.getId())) continue;
            uDetailIdList.add(detail.getId());
        }
        this.noteDetailService.deleteByNoteId(userId, noteId, uDetailIdList);

        int count = 0;
        ResultJson result;

        for (NoteDetail detail : noteDetailList) {
            if (detail == null) continue;

            if (detail.getContent() != null && "".equals(detail.getContent())) {
                this.noteDetailService.deleteById(detail.getId());
                continue;
            }

            detail.setNoteId(noteId);
            detail.setUserId(userId);

            if (StringUtil.isId(detail.getId())) {
                result = this.noteDetailService.updateById(detail);
            } else {
                result = this.noteDetailService.add(detail);
            }
            if (result != null && result.getCode() == EnumStatusCode.SUCCESS.getCode()) count++;
        }
        this.redisTemplate.del(EnumRedisKey.TIME_NOTE_DETAIL_LIST_.name() + noteId);
        return count;
    }

    @Override
    public ResultJson deleteById(Long loginUserId, Long id) throws ZouFanqiException {
        Note old = this.getById(id);
        if (old == null) return ResultBuilder.build();

        old.setIsDel(ConstDB.ISDEL_TRUE);

        ResultJson result = this.updateById(loginUserId, old, null, true);
        if (result.getCode() != EnumStatusCode.SUCCESS.getCode()) return ResultBuilder.build();

        // 更新父节点的数据
        int s = updateCountNote(loginUserId, old.getParentId(), -1);
        if (s <= 0) throw new ZouFanqiException(EnumStatusCode.DB_PARENT_NOT_FOUND);

        this.redisTemplate.del(EnumRedisKey.TIME_NOTE_PAGE_TREE_.name() + id);
        return ResultBuilder.build();
    }

    /**
     * 获取笔记详情
     *
     * @param loginUserId
     * @param id
     * @param password
     *
     * @return
     *
     * @throws ZouFanqiException
     */
    @Override
    public NoteViewVo getNoteVoById(Long loginUserId, Long id, String password) throws ZouFanqiException {
        Note note = this.getByIdInRedis(loginUserId, id);
        if (note == null) return null;
        Integer secret = note.getSecret();
        secret = secret == null ? ConstDB.Note.SECRET_OPEN : secret;

        if (StringUtil.notEquals(note.getUserId(), loginUserId)) {
            if (!(note.getStatus() == ConstDB.Note.STATUS_PASS || note.getStatus() == ConstDB.Note.STATUS_ALL_PASS))
                return null;
            if (secret == ConstDB.Note.SECRET_PRIVATE) return null;
            if (secret == ConstDB.Note.SECRET_PWD && StringUtil.notEquals(note.getPassword(), password)) {
                Note pwdNote = new Note();
                pwdNote.setId(id);
                pwdNote.setTitle(note.getTitle());

                NoteViewVo noteVo = new NoteViewVo();
                noteVo.setIsNeedPwd(1);
                noteVo.setNote(pwdNote);
                return noteVo;
            }
        }

        NoteViewVo vo = new NoteViewVo();
        vo.setNote(note);
        vo.setNoteDetailList(this.noteDetailService.getListByNoteId(note.getId()));

        return vo;
    }

    /**
     * 笔记树
     *
     * @param loginUserId
     * @param userId
     * @param parentId
     * @param deep        获取展开节点的深度
     *
     * @return
     *
     * @throws ZouFanqiException
     */
    @Override
    public List<NoteTreeVo> getTreeList(Long loginUserId, Long userId, Long parentId, Integer deep) throws ZouFanqiException {
        if (StringUtil.isNotId(loginUserId) && StringUtil.isNotId(userId)) return null;
        if (StringUtil.isNotId(userId)) userId = loginUserId;
        if (deep == null) deep = 0;
        if (deep > 3) return null;
        final int h = 5;    // 横向找最多打开的节点数
        int hCount = 0;

        boolean isMine = false;
        if (loginUserId != null && StringUtil.equals(userId, loginUserId)) {
            isMine = true;
        }

        int pageNo = 1;
        final int pageSize = 200;
        final int navNum = 10;

        Integer count = null;
        boolean flag = true;
        List<NoteTreeVo> list = null;
        List<Note> temp;

        do {
            Page<Note> page = this.getTreePage(loginUserId, userId, parentId, pageNo, pageSize, navNum);
            if (page == null || (temp = page.getData()) == null || temp.isEmpty()) break;
            if (count == null || list == null) {
                count = page.getRowCount();
                list = new ArrayList<>(count);
            }

            /**
             * 获取展开的数据
             */
            for (Note note : temp) {
                NoteTreeVo vo = new NoteTreeVo();

                if (isMine && hCount < h && note.getCountNote() != null &&
                        note.getCountNote() != 0 && this.isNoteOpenedInRedis(userId, note.getId())) {
                    hCount++;
                    List<NoteTreeVo> subVoList = this.getTreeList(loginUserId, userId, note.getId(), ++deep);
                    vo.setSubNoteVoList(subVoList);
                }
                vo.setNote(note);
                list.add(vo);
                vo = null;
            }
            if (temp.size() < pageSize) break;
            pageNo++;
            temp = null;
        } while (flag);
        return list;
    }

    @Override
    public Page<Note> getTreePage(Long loginUserId, Long userId, Long parentId,
                                  Integer pageNo, Integer pageSize, Integer navNum) throws ZouFanqiException {
        if (StringUtil.isNotId(loginUserId) && StringUtil.isNotId(userId)) return null;
        if (StringUtil.isNotId(userId)) userId = loginUserId;
        if (StringUtil.isNotId(parentId)) parentId = ConstDB.DEFAULT_PARENT_ID;

        Page page = new Page(pageNo, pageSize, 0, navNum);

        int flag = 0;
        if (StringUtil.isId(loginUserId) && StringUtil.equals(loginUserId, userId)) {
            flag = 1;   // 自己打开的树与别人打开的树是不一样的
        }

        String key = EnumRedisKey.TIME_NOTE_PAGE_TREE_.name() + userId;
        String mapKey = new StringBuffer().append(flag).append("_").append(parentId).append("_").
                append(page.getNum()).append("_").
                append(page.getPageSize()).append("_").
                append(page.getNavNum()).toString();
        String redisInfo = this.redisTemplate.get(key);
        JSONObject map = null;

        outIf:
        if (StringUtil.isNotEmpty(redisInfo)) {
            if (ConstService.REDIS_DEFAULT_INFO.equals(redisInfo)) return null;

            map = JSON.parseObject(redisInfo);
            String info = map.getString(mapKey);

            if (StringUtil.isEmpty(info)) break outIf;
            if (ConstService.REDIS_DEFAULT_INFO.equals(info)) return null;

            Page<Note> redisPage = JSON.parseObject(info, Page.class);
            redisPage.setData(JSON.parseArray(String.valueOf(redisPage.getData()), Note.class));
            return redisPage;
        }

        NoteExample example = new NoteExample();
        NoteExample.Criteria c = example.createCriteria();
        c.andIsDelEqualTo(ConstDB.ISDEL_FALSE);
        c.andParentIdEqualTo(parentId);
        c.andUserIdEqualTo(userId);

        if (!userId.equals(loginUserId)) {  // 不是本人私有的不能获取
            List<Integer> secretTypeList = new ArrayList<>();
            secretTypeList.add(ConstDB.Note.SECRET_OPEN);
            secretTypeList.add(ConstDB.Note.SECRET_PWD);

            c.andStatusEqualTo(ConstDB.Note.STATUS_PASS);
            c.andSecretIn(secretTypeList);

            NoteExample.Criteria c2 = example.or();
            c2.andIsDelEqualTo(ConstDB.ISDEL_FALSE);
            c2.andParentIdEqualTo(parentId);
            c2.andUserIdEqualTo(userId);

            c2.andStatusEqualTo(ConstDB.Note.STATUS_ALL_PASS);
            c2.andSecretIn(secretTypeList);
        }

        int count = this.noteMapper.countByExample(example);

        page = new Page(pageNo, pageSize, count, navNum);

        example.setOffset(page.getStartRow());
        example.setRows(page.getPageSize());
        example.setOrderByClause("COUNT_NOTE DESC, TITLE ASC");


        List<Note> list = this.noteMapper.selectByExample(example);

        if (map == null) map = new JSONObject();

        if (list == null || list.isEmpty()) {
            map.put(mapKey, ConstService.REDIS_DEFAULT_INFO);
            this.redisTemplate.setex(key, EnumRedisKey.TIME_NOTE_PAGE_TREE_.getTime(), JSON.toJSONString(map));
            return null;
        } else {
            page.setData(list);
            map.put(mapKey, page);
            this.redisTemplate.setex(key, EnumRedisKey.TIME_NOTE_PAGE_TREE_.getTime(), JSON.toJSONString(map));
            return page;
        }
    }

    @Override
    public Page<Note> getHomePage(Integer pageNo, Integer pageSize, Integer navNum) throws ZouFanqiException {
        Page page = new Page(pageNo, pageSize, 0, navNum);

        String mapKey = new StringBuffer().
                append(page.getNum()).append("_").
                append(page.getPageSize()).append("_").
                append(page.getNavNum()).toString();
        String redisInfo = this.redisTemplate.get(EnumRedisKey.TIME_NOTE_PAGE_HOME.name());
        JSONObject map = null;

        outIf:
        if (StringUtil.isNotEmpty(redisInfo)) {
            if (ConstService.REDIS_DEFAULT_INFO.equals(redisInfo)) return null;

            map = JSON.parseObject(redisInfo);
            String info = map.getString(mapKey);

            if (StringUtil.isEmpty(info)) break outIf;
            if (ConstService.REDIS_DEFAULT_INFO.equals(info)) return null;

            Page<Note> redisPage = JSON.parseObject(info, Page.class);
            redisPage.setData(JSON.parseArray(String.valueOf(redisPage.getData()), Note.class));
            return redisPage;
        }

        NoteExample example = new NoteExample();
        NoteExample.Criteria c = example.createCriteria();
        c.andIsDelEqualTo(ConstDB.ISDEL_FALSE);
        c.andSecretEqualTo(ConstDB.Note.SECRET_OPEN);
        c.andStatusEqualTo(ConstDB.Note.STATUS_ALL_PASS);
//        c.andStatusEqualTo(ConstDB.Note.STATUS_PASS);
        c.andCountNoteContentGreaterThan(0);

        int count = this.noteMapper.countByExample(example);

        page = new Page(pageNo, pageSize, count, navNum);

        example.setOffset(page.getStartRow());
        example.setRows(page.getPageSize());
        example.setOrderByClause("VIEW_NUM DESC,UPDATE_DATETIME DESC");

        List<Note> list = this.noteMapper.selectByExample(example);

        if (map == null) map = new JSONObject();

        if (list == null || list.isEmpty()) {
            map.put(mapKey, ConstService.REDIS_DEFAULT_INFO);
            this.redisTemplate.setex(EnumRedisKey.TIME_NOTE_PAGE_HOME.name(),
                    EnumRedisKey.TIME_NOTE_PAGE_HOME.getTime(), JSON.toJSONString(map));
            return null;
        } else {
            page.setData(list);
            map.put(mapKey, page);
            this.redisTemplate.setex(EnumRedisKey.TIME_NOTE_PAGE_HOME.name(),
                    EnumRedisKey.TIME_NOTE_PAGE_HOME.getTime(), JSON.toJSONString(map));
            return page;
        }
    }

    @Override
    public void updateNoteViewNumInRedis(Long noteId, long currViewNum) throws ZouFanqiException {
        if (StringUtil.isNotId(noteId)) return;
        this.redisTemplate.hset(EnumRedisKey.MAP_NOTE_VIEW_NUM.name(), String.valueOf(noteId), String.valueOf(currViewNum));
    }

    @Override
    public long getNoteViewNumInRedis(Long noteId) throws ZouFanqiException {
        if (StringUtil.isNotId(noteId)) return 0;
        String numStr = this.redisTemplate.hget(EnumRedisKey.MAP_NOTE_VIEW_NUM.name(), String.valueOf(noteId));
        return numStr == null ? 0 : Long.valueOf(numStr);
    }

    /**
     * 打开的笔记目录集合
     *
     * @param userId
     * @param noteId
     *
     * @return
     *
     * @throws ZouFanqiException
     */
    @Override
    public boolean isNoteOpenedInRedis(Long userId, Long noteId) throws ZouFanqiException {
        if (StringUtil.isNotId(userId) || StringUtil.isNotId(noteId) ||
                StringUtil.equals(ConstDB.DEFAULT_PARENT_ID, noteId)) return false;
        return this.redisTemplate.sismember(EnumRedisKey.S_OPENED_NOTE_ID_.name() + userId, String.valueOf(noteId));
    }

    /**
     * 关闭笔记目录
     *
     * @param userId
     * @param noteId
     *
     * @throws ZouFanqiException
     */
    @Override
    public void closeNoteInRedis(Long userId, Long noteId) throws ZouFanqiException {
        if (StringUtil.isNotId(userId) || StringUtil.isNotId(noteId) ||
                StringUtil.equals(ConstDB.DEFAULT_PARENT_ID, noteId)) return;
        this.redisTemplate.srem(EnumRedisKey.S_OPENED_NOTE_ID_.name() + userId, String.valueOf(noteId));
    }

    /**
     * 打开笔记目录
     *
     * @param userId
     * @param noteId
     *
     * @throws ZouFanqiException
     */
    public void openNoteInRedis(Long userId, Long noteId) throws ZouFanqiException {
        if (StringUtil.isNotId(userId) || StringUtil.isNotId(noteId) ||
                StringUtil.equals(ConstDB.DEFAULT_PARENT_ID, noteId)) return;
        this.redisTemplate.sadd(EnumRedisKey.S_OPENED_NOTE_ID_.name() + userId, String.valueOf(noteId));
    }

    @Override
    public Note getByIdInRedis(Long loginUserId, Long id) throws ZouFanqiException {
        if (StringUtil.isNotId(id) || StringUtil.equals(id, ConstDB.DEFAULT_PARENT_ID)) return null;

        String redisKey = EnumRedisKey.TIME_NOTE_.name() + id;
        String redisInfo = this.redisTemplate.get(redisKey);

        long redisViewNum = this.getNoteViewNumInRedis(id);

        if (StringUtil.isNotEmpty(redisInfo)) {
            if (ConstService.REDIS_DEFAULT_INFO.equals(redisInfo)) return null;

            Note note = JSON.parseObject(redisInfo, Note.class);

            if (StringUtil.isNotId(loginUserId) || StringUtil.notEquals(loginUserId, note.getUserId()))
                this.updateNoteViewNumInRedis(id, ++redisViewNum);

            note.setViewNum(redisViewNum);
            return note;
        }

        NoteExample example = new NoteExample();
        example.createCriteria().andIsDelEqualTo(ConstDB.ISDEL_FALSE).andIdEqualTo(id);

        List<Note> list = this.noteMapper.selectByExample(example);
        if (list == null || list.isEmpty()) {
            this.redisTemplate.setex(redisKey, EnumRedisKey.TIME_NOTE_.getTime(), ConstService.REDIS_DEFAULT_INFO);
            return null;
        }
        Note note = list.get(0);

        /**
         * 更新viewNum
         */
        long dbViewNum = note.getViewNum() == null ? 0 : note.getViewNum();

        if (redisViewNum > dbViewNum) {
            Note uNote = new Note();
            uNote.setId(id);
            uNote.setViewNum(redisViewNum);
            this.updateById(note.getUserId(), uNote, null, true);
        } else {
            note.setViewNum(dbViewNum);
            this.updateNoteViewNumInRedis(id, dbViewNum);
        }
        this.redisTemplate.setex(redisKey, EnumRedisKey.TIME_NOTE_.getTime(), JSON.toJSONString(note));

        return note;
    }

    private Note getById(Long id) throws ZouFanqiException {
        if (StringUtil.isNotId(id) || StringUtil.equals(id, ConstDB.DEFAULT_PARENT_ID)) return null;
        NoteExample example = new NoteExample();
        example.createCriteria().andIsDelEqualTo(ConstDB.ISDEL_FALSE).andIdEqualTo(id);

        List<Note> list = this.noteMapper.selectByExample(example);
        return list.get(0);
    }

    private Integer countNote(Long parentId) {
        if (StringUtil.isNotId(parentId) || parentId == ConstDB.DEFAULT_PARENT_ID) return null;

        NoteExample example = new NoteExample();
        example.createCriteria().
                andIsDelEqualTo(ConstDB.ISDEL_FALSE).
                andParentIdEqualTo(parentId);

        return this.noteMapper.countByExample(example);
    }
}
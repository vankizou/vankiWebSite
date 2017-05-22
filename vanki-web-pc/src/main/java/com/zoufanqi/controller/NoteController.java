package com.zoufanqi.controller;

import com.zoufanqi.consts.ConstDB;
import com.zoufanqi.entity.Note;
import com.zoufanqi.entity.User;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.param.common.Page;
import com.zoufanqi.result.ResultBuilder;
import com.zoufanqi.result.ResultJson;
import com.zoufanqi.service.NoteService;
import com.zoufanqi.service.UserService;
import com.zoufanqi.status.EnumStatusCode;
import com.zoufanqi.utils.DateUtil;
import com.zoufanqi.utils.StringUtil;
import com.zoufanqi.vo.NoteHomeVo;
import com.zoufanqi.vo.NoteTreeVo;
import com.zoufanqi.vo.NoteViewVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 笔记
 * Created by vanki on 16/10/26.
 */
@Controller
@RequestMapping("/note")
public class NoteController extends BaseController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/edit/{id}.htm")
    public ModelAndView editHtml(@PathVariable("id") Long id) throws ZouFanqiException {
        Long loginUserId = this.getUserId();
        ModelAndView mv = new ModelAndView("note/edit");
        NoteViewVo noteVo = this.noteService.getNoteVoById(loginUserId, id, null);
        if (noteVo == null) throw new ZouFanqiException(EnumStatusCode.NOT_FOUND);
        mv.addObject("noteVo", noteVo);
        return mv;
    }

    @RequestMapping(value = "/view/{id}.html")
    public ModelAndView viewHtml(@PathVariable("id") Long id, String password) throws ZouFanqiException {
        Long loginUserId = this.justGetUserId();
        ModelAndView mv = new ModelAndView("note/view");
        NoteViewVo noteVo = this.noteService.getNoteVoById(loginUserId, id, password);
        if (noteVo == null) throw new ZouFanqiException(EnumStatusCode.NOT_FOUND);

        // 父节点数据
        noteVo.setParentNote(this.noteService.getByIdInRedis(null, noteVo.getNote().getParentId()));
        // 用户
        noteVo.setUser(this.userService.getById(noteVo.getNote().getUserId()));
        noteVo.setUpdateDatetimeStr(DateUtil.formatDate(noteVo.getNote().getUpdateDatetime()));

        mv.addObject("noteVo", noteVo);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/add.data", method = RequestMethod.POST)
    public ResultJson add(NoteViewVo noteVo) throws ZouFanqiException {
        if (noteVo == null || noteVo.getNote() == null) return ResultBuilder.buildParamError();
        noteVo.getNote().setUserId(this.getUserId());
        return this.noteService.add(noteVo.getNote(), noteVo.getNoteDetailList());
    }

    /**
     * @param noteVo
     *
     * @return <br />
     * 1000 数据不存在
     * 1002 不是本人操作
     *
     * @throws ZouFanqiException
     */
    @ResponseBody
    @RequestMapping(value = "/updateById.data", method = RequestMethod.POST)
    public ResultJson updateById(NoteViewVo noteVo) throws ZouFanqiException {
        Long loginUserId = this.getUserId();
        return this.noteService.updateById(loginUserId, noteVo.getNote(), noteVo.getNoteDetailList(), false);
    }

    /**
     * isOpen = 0
     *
     * @param id
     *
     * @return
     *
     * @throws ZouFanqiException
     */
    @ResponseBody
    @RequestMapping(value = "/closeNote.data", method = RequestMethod.POST)
    public ResultJson closeNote(Long id) throws ZouFanqiException {
        this.noteService.closeNoteInRedis(this.getUserId(), id);
        return ResultBuilder.build();
    }

    /**
     * isOpen = 1
     *
     * @param id
     *
     * @return
     *
     * @throws ZouFanqiException
     */
    @ResponseBody
    @RequestMapping(value = "/openNote.data", method = RequestMethod.POST)
    public ResultJson openNote(Long id) throws ZouFanqiException {
        this.noteService.openNoteInRedis(this.getUserId(), id);
        return ResultBuilder.build();
    }

    @ResponseBody
    @RequestMapping(value = "/deleteById.data", method = RequestMethod.POST)
    public ResultJson deleteById(Long id) throws ZouFanqiException {
        Long loginUserId = this.getUserId();
        return this.noteService.deleteById(loginUserId, id);
    }

    @ResponseBody
    @RequestMapping(value = "/getNoteVoById.json", method = RequestMethod.GET)
    public ResultJson getNoteVoById(Long id, String password) throws ZouFanqiException {
        NoteViewVo vo = this.noteService.getNoteVoById(this.justGetUserId(), id, password);
        if (vo == null) return ResultBuilder.buildError(EnumStatusCode.DB_NOT_FOUND);
        if (vo.getIsNeedPwd() != null && vo.getIsNeedPwd() == 1)
            return ResultBuilder.buildError(EnumStatusCode.NOTE_PASSWORD_ERROR);
        vo.setUpdateDatetimeStr(DateUtil.formatDate(vo.getNote().getUpdateDatetime()));
        // 父节点数据
        vo.setParentNote(this.noteService.getByIdInRedis(null, vo.getNote().getParentId()));
        // 用户
        vo.setUser(this.userService.getById(vo.getNote().getUserId()));

        return ResultBuilder.build(vo);
    }

    @ResponseBody
    @RequestMapping(value = "/getPageForHome.json")
    public ResultJson getHomePage(Integer pageNo, Integer pageSize, Integer navNum) throws ZouFanqiException {
        Page<Note> page = this.noteService.getHomePage(pageNo, pageSize, navNum);
        if (page == null) return ResultBuilder.build();
        List<Note> noteList = page.getData();

        Page<NoteHomeVo> pageReturn = new Page<>();
        Page.copyData(pageReturn, page);
        List<NoteHomeVo> returnList = new ArrayList<>();
        pageReturn.setData(returnList);

        if (noteList == null || noteList.isEmpty()) return ResultBuilder.build(pageReturn);

        Map<Long, User> userTempMap = new HashMap<>();
        Map<Long, Note> parentNoteMap = new HashMap<>();
        NoteHomeVo noteHomeVo;
        User user;
        for (Note note : noteList) {
            noteHomeVo = new NoteHomeVo();
            noteHomeVo.setNote(note);

            /**
             * 添加用户信息
             */
            user = userTempMap.get(note.getUserId());
            if (user != null) {
                noteHomeVo.setUser(user);
                returnList.add(noteHomeVo);
            } else {
                user = this.userService.getById(note.getUserId());
                if (user == null) continue;
                noteHomeVo.setUser(user);
                returnList.add(noteHomeVo);
                userTempMap.put(note.getUserId(), user);
            }

            /**
             * 添加父笔记信息
             */
            if (StringUtil.equals(note.getParentId(), ConstDB.DEFAULT_PARENT_ID)) continue;
            Note pNote = parentNoteMap.get(note.getParentId());
            if (pNote != null) {
                noteHomeVo.setParentNote(pNote);
                continue;
            }
            pNote = this.noteService.getByIdInRedis(null, note.getParentId());
            if (pNote == null) continue;
            noteHomeVo.setParentNote(pNote);
            parentNoteMap.put(note.getParentId(), pNote);
        }
        return ResultBuilder.build(pageReturn);
    }

    @ResponseBody
    @RequestMapping(value = "/getTreeList.json")
    public ResultJson getTreeList(Long userId, Long parentId) throws ZouFanqiException {
        Long loginUserId = this.justGetUserId();
        List<NoteTreeVo> list = this.noteService.getTreeList(loginUserId, userId, parentId, 0);
        return ResultBuilder.build(list);
    }
}

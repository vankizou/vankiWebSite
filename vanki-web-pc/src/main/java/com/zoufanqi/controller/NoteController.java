package com.zoufanqi.controller;

import com.zoufanqi.entity.Note;
import com.zoufanqi.entity.User;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.param.common.Page;
import com.zoufanqi.result.ResultBuilder;
import com.zoufanqi.result.ResultJson;
import com.zoufanqi.service.NoteService;
import com.zoufanqi.service.UserService;
import com.zoufanqi.status.EnumStatusCode;
import com.zoufanqi.vo.NoteHomeVo;
import com.zoufanqi.vo.NoteVo;
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
        NoteVo noteVo = this.noteService.getNoteVoById(loginUserId, id, null);
        if (noteVo == null) throw new ZouFanqiException(EnumStatusCode.NOT_FOUND);
        mv.addObject("noteVo", noteVo);
        return mv;
    }

    @RequestMapping(value = "/view/{id}.html")
    public ModelAndView viewHtml(@PathVariable("id") Long id, String password) throws ZouFanqiException {
        Long loginUserId = this.justGetUserId();
        ModelAndView mv = new ModelAndView("note/view");
        NoteVo noteVo = this.noteService.getNoteVoById(loginUserId, id, password);
        if (noteVo == null) throw new ZouFanqiException(EnumStatusCode.NOT_FOUND);
//        if (noteVo.getNote() == null) throw new ZouFanqiException(EnumStatusCode.NOTE_PASSWORD_ERROR);
        mv.addObject("noteVo", noteVo);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/add.data", method = RequestMethod.POST)
    public ResultJson add(NoteVo noteVo) throws ZouFanqiException {
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
    public ResultJson updateById(NoteVo noteVo) throws ZouFanqiException {
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
        NoteVo vo = this.noteService.getNoteVoById(this.justGetUserId(), id, password);
        if (vo == null) return ResultBuilder.buildError(EnumStatusCode.DB_NOT_FOUND);
        if (vo.getIsNeedPwd() != null && vo.getIsNeedPwd() == 1)
            return ResultBuilder.buildError(EnumStatusCode.NOTE_PASSWORD_ERROR);
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
        NoteHomeVo noteHomeVo;
        User user;
        for (Note note : noteList) {
            noteHomeVo = new NoteHomeVo();
            noteHomeVo.setNote(note);

            user = userTempMap.get(note.getUserId());
            if (user != null) {
                noteHomeVo.setUser(user);
                returnList.add(noteHomeVo);
                continue;
            }
            user = this.userService.getById(note.getUserId());
            if (user == null) continue;
            noteHomeVo.setUser(user);
            returnList.add(noteHomeVo);
            userTempMap.put(note.getUserId(), user);
        }
        return ResultBuilder.build(pageReturn);
    }

    @ResponseBody
    @RequestMapping(value = "/getList.json")
    public ResultJson getList(Long userId, Long parentId) throws ZouFanqiException {
        Long loginUserId = this.justGetUserId();
        List<NoteVo> list = this.noteService.getTreeList(loginUserId, userId, parentId, 0);
        return ResultBuilder.build(list);
    }
}

package com.zoufanqi.controller;

import com.zoufanqi.consts.ConstDB;
import com.zoufanqi.entity.Note;
import com.zoufanqi.entity.NoteDetail;
import com.zoufanqi.entity.User;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.param.common.Page;
import com.zoufanqi.result.ResultBuilder;
import com.zoufanqi.result.ResultJson;
import com.zoufanqi.service.NoteService;
import com.zoufanqi.service.UserService;
import com.zoufanqi.status.EnumStatusCode;
import com.zoufanqi.utils.DateUtil;
import com.zoufanqi.utils.ExceptionUtil;
import com.zoufanqi.utils.StringUtil;
import com.zoufanqi.utils.TemplateUtil;
import com.zoufanqi.vo.NoteHomeVo;
import com.zoufanqi.vo.NoteTreeVo;
import com.zoufanqi.vo.NoteViewVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
    private static final Logger LOG = LoggerFactory.getLogger(NoteController.class);
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
        noteVo.setCreateDatetimeStr(DateUtil.formatDate(noteVo.getNote().getCreateDatetime()));
        noteVo.setUpdateDatetimeStr(DateUtil.formatDate(noteVo.getNote().getUpdateDatetime()));

        mv.addObject("noteVo", noteVo);
        return mv;
    }


    @ResponseBody
    @RequestMapping(value = "/download.json")
    public ResultJson downloadNote(Long id, String password) throws ZouFanqiException, IOException {
        if (StringUtil.isNotId(id)) return ResultBuilder.buildParamError();

        List<String> noteTempList = TemplateUtil.getExportNoteTempList();
        if (noteTempList == null) return ResultBuilder.buildError();

        NoteViewVo noteViewVo = this.noteService.getNoteVoById(this.justGetUserId(), id, password);
        List<NoteDetail> detailList = noteViewVo.getNoteDetailList();
        if (noteViewVo != null && noteViewVo.getIsNeedPwd() != null && noteViewVo.getIsNeedPwd() == 1)
            return ResultBuilder.buildError(EnumStatusCode.NOTE_PASSWORD_ERROR);
        if (detailList == null || detailList.isEmpty())
            return ResultBuilder.buildError(EnumStatusCode.NOTE_EXPORT_CONTENT_IS_EMPTY);

        return ResultBuilder.build();
    }

    @RequestMapping(value = "/doDownload.json")
    public ResultJson doDownloadNote(Long id, String password) throws ZouFanqiException, UnsupportedEncodingException {
        if (StringUtil.isNotId(id)) return null;

        List<String> noteTempList = TemplateUtil.getExportNoteTempList();
        if (noteTempList == null) return null;

        NoteViewVo noteViewVo = this.noteService.getNoteVoById(this.justGetUserId(), id, password);
        List<NoteDetail> detailList = noteViewVo.getNoteDetailList();
        if (noteViewVo != null && noteViewVo.getIsNeedPwd() != null && noteViewVo.getIsNeedPwd() == 1) return null;
        if (detailList == null || detailList.isEmpty()) return null;

        String title = noteViewVo.getNote().getTitle();

        StringBuffer htmlContent = new StringBuffer();

        for (String noteTemp : noteTempList) {
            if (TemplateUtil.key_exportNoteTemp_title.startsWith(noteTemp)) {
                htmlContent.append(title);
            } else if (TemplateUtil.key_exportNoteTemp_content.startsWith(noteTemp)) {
                htmlContent.append(detailList.get(0).getContent());
            } else {
                htmlContent.append(noteTemp);
            }
        }
        try {
            byte[] byteArr = htmlContent.toString().getBytes();
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"" +
                    new String(title.getBytes("UTF-8"), "ISO-8859-1") + ".html\"");
            response.addHeader("Content-Length", "" + byteArr.length);
            response.setContentType("application/octet-stream;charset=UTF-8");
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());

            outputStream.write(byteArr);

            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            LOG.error(ExceptionUtil.getExceptionAllMsg(e));
            return ResultBuilder.buildError();
        }
        return ResultBuilder.build();

        /*String htmlContentStr = new String(htmlContent.toString().getBytes("UTF-8"), "ISO-8859-1");
        byte[] body = htmlContentStr.getBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", new String(title.getBytes(), "ISO-8859-1") + ".html");

        return new ResponseEntity<>(body, headers, HttpStatus.CREATED);*/
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
        vo.setCreateDatetimeStr(DateUtil.formatDate(vo.getNote().getCreateDatetime()));
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

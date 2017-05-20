/**
 * Created by vanki on 16/10/27.
 */

var vankiEditor;
var height = $(window).height() - 138;
$(function () {
    var startContent = $('#j_content').html();

    if (c_isNeedPwd == ConstDB.Note.isNeedPwdYes) {
        fnGetNoteVo(c_noteId);
    } else if (!startContent) {
        $('#j_empty_content').show();
        $('.j_note_info').hide();
    } else {
        fnInitVankiEditor();
        $('#j_empty_content').hide();
        $('.j_note_info').show();
    }
});

function fnGetNoteVo(noteId, msg) {
    if (!msg) msg = "请输入密码";
    var pwd = prompt(msg);
    if (pwd == null) return;//fnGetNoteVo(noteId);
    var params = {
        "id": noteId,
        "password": pwd
    };
    var fnSucc = function (data) {
        var val = "";
        if (data['noteDetailList'] && data['noteDetailList'][0]) {
            val = data['noteDetailList'][0]['content'];
        }
        if (val) {
            $('#j_empty_content').hide();
            $('.j_note_info').show();

            /**
             * 密码的不能一次性获取完数据
             */
            if (data['parentNote']) {
                $('#j_note_info_parent_title').html(data['parentNote']['title']);
            }
            if (data['user']) {
                $('#j_note_info_user_alias').html(data['user']['alias']);
            }
            $('#j_note_info_update_datetime').html(data['updateDatetimeStr']);
            $('#j_note_info_user_url').attr('href', '/user/' + data['user']['id'] + '.html');

            if (!vankiEditor) fnInitVankiEditor(val);
        } else {
            $('#j_empty_content').show();
            $('.j_note_info').hide();
        }
    };
    var fnFail = function (data) {
        fnGetNoteVo(noteId, "密码输入错误，请重新输入");
    };
    vankiAjax(ConstAjaxUrl.Note.getById, params, fnSucc, fnFail);
}

function fnInitVankiEditor(val) {
    vankiEditor = editormd("vanki-editormd-view-note", {
        width: "100%",
        height: height,
        syncScrolling: "single",
        path: "/statics/third/markdown/lib/",
        toolbarIcons: [],
        onload: function () {
            this.previewing();
            if (val) this.setValue(val);
            $('.fa-close').hide();
        }
    });
}
/**
 * Created by vanki on 16/10/27.
 */

var vankiEditor;
var height = $(window).height() - 88;
$(function () {
    var startContent = $('#j_content').html();

    if (c_isNeedPwd == ConstDB.Note.isNeedPwdYes) {
        fnGetNoteVo(c_noteId);
    } else if (!startContent) {
        $('#j_empty_content').show();
    } else {
        fnInitVankiEditor();
        $('#j_empty_content').hide();
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
            if (!vankiEditor) fnInitVankiEditor(val);
        } else {
            $('#j_empty_content').show();
        }
    };
    var fnFail = function (data) {
        fnGetNoteVo(noteId, "密码输入错误，请重新输入");
    };
    vankiAjax(ConstAjaxUrl.Note.getById, params, fnSucc, fnFail);
}

function fnInitVankiEditor(val) {
    vankiEditor = editormd("vanki-editormd-view-note", {
        width: "80%",
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
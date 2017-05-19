/**
 * Created by vanki on 2017/2/22.
 */

var vankiEditor;
var height = $(window).height() - 45;
$(function () {
    var fnSaveOrUpdateNote = function (noteId, noteContentId, noteContent) {

        if (noteId) {
            /**
             * 更新
             */
            var noteTitle = $('#j_note_title').val();
            var noteDesc = $('#j_note_description').val();
            var noteKeyword = $('#j_note_keyword').val();
            if (isValFalse(noteTitle)) {
                vankiMsgAlertAutoClose("标题不能为空!", 2000);
                return;
            }

            var ncJsonArr = [{
                id: noteContentId,
                type: ConstDB.NoteContent.typeText,
                sequence: 1,
                content: noteContent
            }];
            var params = {
                id: noteId,
                title: noteTitle,
                description: noteDesc,
                keyword: noteKeyword,
                noteContentJsonArrStr: JSON.stringify(ncJsonArr)
            };

            var fnSucc = function () {
                vankiMsgAlertAutoClose("保存成功!", 1000);
            };
            var fnFail = function (data) {
                var code = data['code'];
                if (code == ConstStatusCode.CODE_1000[0]) {
                    vankiMsgAlert("该笔记已删除!")
                } else if (code == ConstStatusCode.CODE_1002[0]) {
                    vankiMsgAlert("请勿操作他人笔记!");
                }
            };
            vankiAjax(ConstAjaxUrl.Note.updateById, params, fnSucc, fnFail);
        } else {
            /**
             * 添加
             */
        }

    }

    vankiEditor = editormd("vanki-editormd-edit-note", {
        width: "100%",
        height: height,
        syncScrolling: "single",
        path: "/statics/markdown/lib/",
        toolbarIcons: [
            "undo", "redo", "|",
            "bold", "del", "italic", "quote", "ucwords", "uppercase", "lowercase", "|",
            "h1", "h2", "h3", "h4", "h5", "h6", "|",
            "list-ul", "list-ol", "hr", "|",
            "code", "preformatted-text", "code-block", "table", "datetime", "emoji", "html-entities", "pagebreak", "|",
            "goto-line", "watch", "preview", "fullscreen", "clear", "search", "|",
            "help", "info"
        ],
        onload: function () {
            $('.fa-close').hide();
            var btnLi = '<li><a href="javascript:;" id="J_save_note" title="保存笔记" unselectable="on">保存</a></li>';
            $('.editormd-menu').append(btnLi);

            $('#J_save_note').click(function () {
                if (!vankiMsgConfirm("点击确定保存笔记")) return;

                var noteId = $('#J_note_id').html();
                var noteContentId = $('#J_note_content_id').html();
                var noteContent = $('#J_content').val();
                fnSaveOrUpdateNote(noteId, noteContentId, noteContent);
            });
        }
    });


});
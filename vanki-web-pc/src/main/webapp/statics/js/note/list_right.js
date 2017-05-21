/**
 * Created by vanki on 2017/4/15.
 */

var vankiEditor;
var height = $(window).height() - 116;
$(function () {
    /**
     * 初始化markdown工具
     * @type {string}
     */
    var initStr = "### 欢迎使用奇奇笔记！\r\n" +
        "\r\n" +
        "> 功能清单\r\n" +
        ">> 1.奇奇笔记支持Markdown，有经验的朋友可以无缝介入，无经验的小伙伴也能很快上手\r\n" +
        "\r\n" +
        ">> 2.在列表右击可得到相应操作\r\n" +
        "\r\n" +
        ">> 3.双击打开笔记\r\n" +
        "\r\n" +
        ">> 4.笔记是通过标题来自动排序的\r\n" +
        "\r\n" +
        ">> 5.目录也是能成为一个笔记的哦！\r\n" +
        "\r\n" +
        ">> 6.通过拖拽可更换笔记目录\r\n" +
        "\r\n" +
        "------------\r\n" +
        "\r\n" +
        "> 注意点\r\n" +
        ">> 1.公开的笔记会上主页的哦，所以请将笔记做的棒棒哒，才倍有面子！\r\n" +
        "\r\n" +
        ">> 2.目录设置私密不会影响旗下笔记公开或加密，反之设置私密或加密的目录下若有公开笔记是会上主页的\r\n" +
        "\r\n" +
        "------------\r\n" +
        "\r\n" +
        "> **记录生活，记录点滴！**\r\n" +
        ">> **你的笔记，大家的财富！**\r\n" +
        "\r\n" +
        "> **http://www.qiqinote.com**\r\n";
    vankiEditor = editormd("vanki-editormd-view-note", {
        width: "100%",
        height: height,
        syncScrolling: "single",
        path: "/statics/third/markdown/lib/",
        toolbarIcons: [
            "undo", "redo", "|",
            "bold", "del", "italic", "quote", "ucwords", "uppercase", "lowercase", "|",
            "h1", "h2", "h3", "h4", "h5", "h6", "|",
            "list-ul", "list-ol", "hr", "|",
            "code", "preformatted-text", "code-block", "table", "datetime", "html-entities", "pagebreak", "|",
            "goto-line", "watch", "preview", "fullscreen", "clear", "search", "|",
            "info"
        ],
        onload: function () {
            this.previewing();
            hideMarkdownCloseIcon();
            this.setValue(initStr);
        },
        onpreviewed: function () {
            showEdit();
        },
        onpreviewing: function () {
            showView();
        }
    });

    /**
     * 点击编辑
     */
    $('#j_note_edit').bind('click', function () {
        vankiEditor.previewed();
    });

    /**
     * 点击保存
     */
    $('#j_note_save').bind('click', function () {
        var noteId = $('#j_curr_note_id').val();
        var title = $('#j_note_info_edit_title').val();
        var secretType = $('#j_note_info_edit_secret').val();
        var keyword = $('#j_note_info_edit_keyword').val();
        var content = $('#j_note_content').val();
        var contentId = $('#j_curr_note_detail_id').val();

        var params = {
            "note.id": noteId,
            "note.title": title,
            "note.secret": secretType,
            "note.password": currPwd,
            "note.keyword": keyword,
            "noteDetailList[0].id": contentId,
            "noteDetailList[0].content": content
        };
        var fnSucc = function () {
            vankiEditor.previewing();
            hideMarkdownCloseIcon();
            vankiMsgAlertAutoClose("保存成功");

            /**
             * 更新树节点的信息
             */
            var node = tree.getSelectedNodes()[0];
            noteSecretTypeJson[noteId] = secretType;
            node.name = title;

            tree.updateNode(node);
            updateViewTitle(title);
            updateDiyDom(node, 0);
        };
        vankiAjax(ConstAjaxUrl.Note.updateById, params, fnSucc);
    });

    $('#j_note_info_edit_secret').bind('change', function () {
        var val = $(':checked').val();
        if (val == ConstDB.Note.secretPwd) {
            currPwd = prompt("请输入密码", currPwd);
            if (currPwd == null) return;
        }
        var viewSecretStr = getViewSecretStr(val, currPwd);
        $('#j_note_info_secret').html(viewSecretStr);
    })
});


var currPwd;    // 密码
function viewNote(noteId, password, isNotAsync) {
    var params = {
        "id": noteId,
        "password": password
    }
    var context;
    var fnSucc = function (data) {
        if (!data || data.isNeedPwd == 1) {
            vankiMsgAlertAutoClose("密码错误");
            return;
        }
        var noteDetailList = data['noteDetailList'];
        var val = "";
        if (noteDetailList && noteDetailList[0]) {
            $('#j_curr_note_detail_id').val(noteDetailList[0]['id']);

            if (!(val = noteDetailList[0]['content'])) val = "";
        } else {
            $('#j_curr_note_detail_id').val("");
        }
        vankiEditor.setValue(val);

        buildViewNoteCommonInfo(data['note']);

        history.pushState(null, null, "/note/view/" + noteId + ".html");

        context = true;
    };
    vankiAjax(ConstAjaxUrl.Note.getById, params, fnSucc, null, null, isNotAsync);
    return context;
}

/**
 * 查看笔记时该笔记公共信息
 * @param note
 */
function buildViewNoteCommonInfo(note) {
    if (!note) return;

    if (!vankiEditor.state.preview) vankiEditor.previewing();
    showView();
    hideMarkdownCloseIcon();

    if (c_myUserId && c_myUserId == c_noteUserId) {
        $('#j_note_edit').parent().show();

    } else {
        $('#j_note_edit').parent().hide();
    }

    $('#j_curr_note_id').val(note['id']);

    // 标题
    var originTitle = note['title'];
    updateViewTitle(originTitle);

    // 私密
    var pwd = note['password'];
    var secret = note['secret'];
    var secretStr = getViewSecretStr(secret, pwd);

    $('#j_note_info_secret').html(secretStr);
    $('#j_note_info_edit_secret').val(secret);

    // 关键词
    var keyword = note['keyword'];
    if (!keyword) keyword = "";
    $('#j_note_info_keyword').html(keyword);
    $('#j_note_info_edit_keyword').val(keyword);

    // 浏览数
    var viewNum = note['viewNum'];
    $('#j_note_info_viewNum').html(viewNum);
    $('#j_note_info_edit_viewNum').html(viewNum);
}

/**
 * 显示标题更新
 * @param originTitle
 */
function updateViewTitle(originTitle) {
    var title = originTitle;
    if (originTitle.length > 48) title = originTitle.substring(0, 48) + "...";
    $('#j_note_info_title').html('<label title="' + originTitle + '">' + title + '</label>');
    $('#j_note_info_edit_title').val(originTitle);
}

function getViewSecretStr(secretType, pwd) {
    var secretStr = "";
    switch (Number(secretType)) {
        case ConstDB.Note.secretPwd:
            secretStr = '密码访问&nbsp;<i class="fa fa-question-circle-o" style="cursor: pointer;" title="密码：(' + pwd + ')"></i>';
            currPwd = pwd;
            break;
        case ConstDB.Note.secretClose:
            secretStr = "仅自己可见";
            break;
        default:
            secretStr = "公开";
    }
    return secretStr;
}

function showView() {
    $('.note_content_common_edit').hide();
    $('.note_content_common_view').show();
}

function showEdit() {
    $('.note_content_common_view').hide();
    $('.note_content_common_edit').show();
}

function hideMarkdownCloseIcon() {
    $('.fa-close').hide();
}
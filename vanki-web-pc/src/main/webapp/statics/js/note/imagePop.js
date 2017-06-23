/**
 * Created by vanki on 2017/6/21.
 */

$(function () {
    $('.j_page_prev_next').click(function () {
        getHistoryImageData($(this).attr('val'));
    });

    $('#j_page_jump').click(function () {
        var val = $('#j_page_jump_val').val();
        if (val && /^\d+$/.test(val)) getHistoryImageData(val);
    });
});

function getHistoryImageData(pageNo, pageSize, navNum) {
    if (!pageNo) pageNo = 1;
    if (!pageSize) pageSize = 10;

    var params = {
        useType: ConstDB.Picture.useTypeNote,
        pageNo: pageNo,
        pageSize: pageSize,
        navNum: navNum
    };
    var fnSucc = function (data) {
        var pageLast = 0;   // 尾页
        var pagePrev = 0;   // 上一页
        var pageNext = 0;   // 下一页
        var pageCurr = 0;   // 本页
        var pageJump = 0;   // 跳转页
        if (data) {
            pageLast = data['last'];
            pagePrev = data['prev'];
            pageNext = data['next'];
            pageCurr = data['num'];
            pageJump = pageCurr + 3;
            pageJump = pageJump > pageLast ? pageLast : pageJump;
        }
        $('#j_page_info').html(pageCurr + "/" + pageLast);
        $('#j_page_previous').attr('val', pagePrev);
        $('#j_page_next').attr('val', pageNext);
        $('#j_page_jump_val').val(pageJump);

        var node = '';
        var datas = data['data'];
        for (var i in datas) {
            var d = datas[i];

            var title = d["name"];
            var path = d["path"];

            node += '<img width="100px" height="100px" src="' + path + '" title="' + title + '"/>'
        }
        $("#j_historyImageData").children().remove();
        $("#j_historyImageData").append(node);

        /**
         * 给图片添加点击事件
         */
        $("#j_historyImageData img").click(function () {
            var src = $(this).attr("src");

            if (!src) {
                vankiLayerMsgFailCha("图片链接错误");
                return;
            }
            src = "\r\n![](" + src + ")";
            vankiEditor.appendMarkdown(src);
        });
    };
    vankiAjax(ConstAjaxUrl.Picture.getPage, params, fnSucc);
}

var uploadImageLayerIndex;
function createAddImagePop() {
    $(function () {
        uploadImageLayerIndex = layer.open({
            type: 1,
            area: ["600px", "500px"],
            title: "添加图片",
            closeBtn: 2,
            shade: false,
            skin: "color:red",
            shadeClose: true,
            // offset: "l",
            content: $("#j_imagePop")
        });
    });
    getHistoryImageData();

    if (!isUploadImageFunInited) initUploadImageFun();
}

var isUploadImageFunInited = false;
function initUploadImageFun() {
    isUploadImageFunInited = true;
    var fnImageUploadSucc = function (data) {
        var succFn = function (data) {
            for (i in data) {
                var src = "\r\n![](" + data[i].path + ")";
                vankiEditor.appendMarkdown(src);
            }
            vankiLayerMsgSuccGou("已添加" + data.length + "张图片");
        };
        if (uploadImageLayerIndex) layer.close(uploadImageLayerIndex);
        vankiParseResponseData(data, succFn);
    };
    vankiUploadImageMulti(ConstDB.Picture.useTypeNote, "j_imageUploadForm", "j_images", fnImageUploadSucc, null, 20, 5);

    $("#j_imageUploadForm").change(function () {
        $("#j_imageUploadForm").submit();
    });
}

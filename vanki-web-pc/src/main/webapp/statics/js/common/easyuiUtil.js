/**
 * Created by vanki on 16/10/28.
 */

var openEasyUIWin = function (divId, width, height, isModal) {
    $('#' + divId).show();
    if (isModal) isModal = false;
    $('#' + divId).window({
        width: width,
        height: height,
        modal: isModal,
        shadow: true,
        collapsible: false
    });
}

var closeEasyUIWin = function (divId) {
    $('#' + divId).window('close');
}

var openEasyUIDialog = function (divId, width, height, okFun, failFun) {
    $('#' + divId).width(width).height(height).css('padding', '5px');
    $('#' + divId).show();
    $('#' + divId).dialog({
        buttons: [{
            text: "确定",
            iconCls: "icon-ok",
            handler: okFun
        }, {
            text: "取消",
            iconCls: "icon-cancel",
            handler: function () {
                if (failFun) failFun();
                $('#' + divId).dialog('close');
            }
        }]
    });
    $('#' + divId).dialog('open');
}

var closeEasyUIDialog = function (divId) {
    $('#' + divId).dialog('close');
}
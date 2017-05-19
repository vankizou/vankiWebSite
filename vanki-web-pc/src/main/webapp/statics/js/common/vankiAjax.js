/**
 * Created by vanki on 16/10/28.
 */

var vankiAjax = function (ajaxInfo, ajaxParams, successFun, failFun, ajaxContext, isNotAsync) {
    $.ajax({
        url: ajaxInfo[0],
        type: ajaxInfo[1],
        dataType: ajaxInfo[2],
        data: ajaxParams,
        async: !isNotAsync,

        success: function (data) {
            operateMyAjaxData(data, ajaxContext, successFun, failFun);
        }
    })
}

var operateMyAjaxData = function (data, ajaxContext, successFun, failFun) {
    if (typeof data == 'string') data = eval('(' + data + ')');
    var code = data['code'];
    if (code == ConstStatusCode.CODE_200[0]) {
        if (successFun) successFun(data['data'], ajaxContext);
    } else {
        if (code == '404') {
            window.location = ConstAjaxUrl.Root.error404[0];
            return;
        }
        var errInfo = ConstStatusCode["CODE_" + code];
        if (errInfo) vankiMsgAlertAutoClose(errInfo[1], 3000);

        if (failFun) failFun(data, ajaxContext);
    }
}
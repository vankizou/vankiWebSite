/**
 * Created by vanki on 16/10/28.
 */

var myAjax = function (ajaxInfo, ajaxParams, ajaxContext, successFun, failFun) {
    $.ajax({
        url: ajaxInfo[0],
        type: ajaxInfo[1],
        dataType: ajaxInfo[2],
        data: ajaxParams,
        success: function (data) {
            operateMyAjaxData(data, ajaxContext, successFun, failFun);
        }
    })
}

var operateMyAjaxData = function (data, ajaxContext, successFun, failFun) {
    if (data['code'] == '200') {
        if (successFun) successFun(data['data'], ajaxContext);
    } else {
        if (failFun) failFun(data, ajaxContext);
    }
}
/**
 * Created by vanki on 2017/2/25.
 */
$(function () {
    //显示弹出层
    $('#j_login_register').click(function () {
        $.blockUI({
            message: $('#j_win_loginRegister'),
            css: {width: '500px'},
            onOverlayClick: $.unblockUI
        });
    });
});
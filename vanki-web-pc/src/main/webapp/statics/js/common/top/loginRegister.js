/**
 * Created by vanki on 2017/5/17.
 */

$(function () {
    $('.j_change').click(function () {
        var val = $(this).html();

        if (val == '登录') {
            $('#j_register').hide();
            $('#j_login').show();
        } else {
            $('#j_login').hide();
            $('#j_register').show();
            fnGetImageCode();
        }
    });

    $('#j_login_id, #j_login_pwd').keyup(function (event) {
        if (event.keyCode == 13) fnLogin();
    });

    $('#j_register input').keyup(function (event) {
        if (event.keyCode == 13) fnRegister();
    });

    /**
     * 登录
     */
    $('#j_login_submit').click(fnLogin);

    /**
     * 注册
     */
    $('#j_register_submit').click(fnRegister);


    $('#j_reg_imagecode_img').click(function () {
        fnGetImageCode();
    });

    var fnLogin = function () {
        var account = $('#j_login_id').val();
        var pwd = $('#j_login_pwd').val();

        if (!account) {
            vankiMsgAlertAutoClose("请输入帐户名");
            return;
        }
        if (!pwd) {
            vankiMsgAlertAutoClose("请输入密码");
            return;
        }
        var params = {
            account: account,
            password: pwd
        };
        var fnSucc = function () {
            window.location = ConstAjaxUrl.User.userHomeHtml_login;
        };
        vankiAjax(ConstAjaxUrl.User.login, params, fnSucc);
    };

    var fnRegister = function () {
        var alias = $('#j_reg_alias').val();
        var pwd = $('#j_reg_pwd').val();
        var confirmPwd = $('#j_reg_confirm_pwd').val();
        var findPwdValidation = $('#j_reg_find_pwd_validation').val();
        var imageCodeVal = $('#j_reg_imagecode').val();

        if (!alias) {
            vankiMsgAlertAutoClose("请输入昵称");
            return;
        }
        if (!pwd) {
            vankiMsgAlertAutoClose("请输入密码");
            return;
        }
        if (!confirmPwd) {
            vankiMsgAlertAutoClose("请输入确认密码");
            return;
        }
        if (pwd != confirmPwd) {
            vankiMsgAlertAutoClose("两次密码输入不一致");
            return;
        }
        if (!findPwdValidation) {
            vankiMsgAlertAutoClose("密码验证符很重要，请勿必填写和牢记");
            return;
        }
        if (!imageCodeVal) {
            vankiMsgAlertAutoClose("请计算图片验证码的值");
            return;
        }
        var params = {
            alias: alias,
            password: pwd,
            findPwdValidation: findPwdValidation,
            imageCode: imageCodeVal
        };
        var fnMsgClose = function () {
            window.location = ConstAjaxUrl.User.userHomeHtml_login;
        };
        var fnSucc = function (data) {
            if ($('#j_win_loginRegister').attr('is_pop_win')) $.unblockUI();
            $('#j_login').hide();
            $('#j_register').hide();

            var msg = "恭喜！帐号注册成功！您的登录ID为：<span style='color:red; font-size:20px; font-weight: 700;'>" + data.id + "</span>";
            vankiMsgAlert(msg, "帐号注册成功提示", fnMsgClose);
        };
        var fnFail = function (data) {
            if (data['code'] != ConstStatusCode.CODE_620[0]) return;
            $('#j_reg_imagecode_img').attr('src', 'data:image/png;base64,' + data['data']);
        };
        vankiAjax(ConstAjaxUrl.User.register, params, fnSucc, fnFail);
    };

    /*==========图片验证码===========*/
    var fnGetImageCode = function () {
        var flag = $('#j_win_loginRegister').attr('is_pop_win');
        var params = {
            width: flag ? 170 : 220,
            height: flag ? 45 : 45
        };
        var fnSucc = function (imageCode) {
            $('#j_reg_imagecode_img').attr('src', 'data:image/png;base64,' + imageCode);
        };
        vankiAjax(ConstAjaxUrl.ImageCode.getImageCode, params, fnSucc);
    };
});


/**
 * Created by vanki on 2017/2/25.
 */
var c_win;
$(function () {
    $('#j_login_register').mouseup(function () {
        if (c_win) return;

        var loginFlag = 1;
        var registerFlag = 2;

        var node = '<div class="row col-xs-12">';
        node += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
        node += '<label class="radio-inline"><input type="radio" name="loginOrRegister" value="' + loginFlag + '" checked> 登录 </label>';
        node += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
        node += '<label class="radio-inline"><input type="radio" name="loginOrRegister" value="' + registerFlag + '" > 注册 </label>';
        node += '<hr style="height:1px;border:none;border-top:1px solid #555555;" />';
        node += buildLoginNode();
        node += buildRegisterNode();
        node += '</div>';

        var fnOk = function () {
            var checkedVal = $('input:radio[name="loginOrRegister"]:checked').val();

            if (checkedVal == loginFlag) {
                /**
                 * 登录
                 */
                var phone = $('#j_login_phone').val();
                var password = $('#j_login_password').val();
                if (!isPhone(phone)) {
                    vankiMsgAlertAutoClose("手机号不正确");
                    return false;
                }
                if (isValFalse(password)) {
                    vankiMsgAlertAutoClose("密码不能为空");
                    return false;
                }
                var params = {
                    account: phone,
                    password: password
                };
                var fnSucc = function () {
                    vankiMsgAlertAutoClose("登录成功");
                    location.reload();
                };
                var fnFail = function (data) {
                };
                vankiAjax(ConstAjaxUrl.User.login, params, fnSucc, fnFail);
            } else if (checkedVal == registerFlag) {
                /**
                 * 注册
                 */
                var phone = $('#j_register_phone').val();
                if (!isPhone(phone)) {
                    vankiMsgAlertAutoClose("手机号不正确");
                    return false;
                }
                var alias = $('#j_register_alias').val();
                if (isValFalse(alias)) {
                    vankiMsgAlertAutoClose("昵称不能为空");
                    return false;
                }
                var password = $('#j_register_password').val();
                var confirmPassword = $('#j_register_password_confirm').val();
                if (isValFalse(password) ||
                    isValFalse(confirmPassword)) {
                    vankiMsgAlertAutoClose("密码与确认密码不能为空");
                    return false;
                }
                if (password != confirmPassword) {
                    vankiMsgAlertAutoClose("两次密码输入不一致");
                    return false;
                }
                var sex = $('input:radio[name="sex"]:checked').val();

                var params = {
                    phone: phone,
                    password: password,
                    alias: alias,
                    sex: sex,
                };
                var fnSucc = function () {
                    vankiMsgAlertAutoClose("注册成功");
                    location.reload();
                };
                var fnFail = function (data) {
                    var code = data['code'];

                    if (code == ConstStatusCode.CODE_10012[0]) {
                        vankiMsgAlertAutoClose(ConstStatusCode.CODE_10012[1]);
                        return;
                    }
                    vankiMsgAlertAutoClose("注册失败, 别找茬, 自己找原因")
                };
                vankiAjax(ConstAjaxUrl.User.register, params, fnSucc, fnFail);
                return true;
            }
        };

        var fnClose = function () {
            c_win = null;
        };
        var winAttr = {
            title: '登录•注册',
            width: 300,
            height: 286,
            onok: fnOk,
            onclose: fnClose,
        };
        c_win = vankiMsgWindow(node, winAttr);

        /**
         * 登录, 注册切换
         */
        $('#j_register').hide();
        $('input:radio[name="loginOrRegister"]').change(function () {
            var checkedVal = $(this).val();
            if (checkedVal == loginFlag) {
                $('#j_login').show();
                $('#j_register').hide();
            } else if (checkedVal == registerFlag) {
                $('#j_login').hide();
                $('#j_register').show();
            }
        });
    });
});

function buildLoginNode() {
    var node = '<div id="j_login">';
    node += '<br />';
    node += '<br />';
    node += '<div class="row">';
    node += '<div class="col-xs-4">手机号: </div>';
    node += '<div class="col-xs-8"><input class="form-control" id="j_login_phone" value=""/></div>';
    node += '</div>';
    node += '<br />';
    node += '<br />';
    node += '<br />';
    node += '<div class="row">';
    node += '<div class="col-xs-4">密&nbsp;&nbsp;&nbsp;&nbsp;码: </div>';
    node += '<div class="col-xs-8"><input class="form-control" id="j_login_password" type="password" value=""/></div>';
    node += '</div>';
    node += '</div>';
    return node;
}


function buildRegisterNode() {
    var node = '<div id="j_register" style="margin-left:10px;">';
    node += '<div class="row">'
    node += '<div class="col-xs-4">昵&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称:&nbsp;</div>';
    node += '<div class="col-xs-8"><input class="form-control input-sm" id="j_register_alias" value="" placeholder=""/><br /></div>';
    node += '</div>';
    node += '<div class="row">';
    node += '<div class="col-xs-4">手&nbsp;&nbsp;机&nbsp;&nbsp;号:&nbsp;</div>';
    node += '<div class="col-xs-8"><input class="form-control input-sm" type="text" id="j_register_phone" value=""/><br /></div>';
    node += '</div>';
    node += '<div class="row">';
    node += '<div class="col-xs-4">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码:&nbsp;</div>';
    node += '<div class="col-xs-8"><input class="form-control input-sm" id="j_register_password" type="password" value=""/><br /></div>';
    node += '</div>';
    node += '<div class="row">';
    node += '<div class="col-xs-4">确认密码:&nbsp;</div>';
    node += '<div class="col-xs-8"><input class="form-control input-sm" id="j_register_password_confirm" type="password" value=""/><br /></div>';
    node += '</div>';
    node += '<div class="row">';
    node += '<div class="col-xs-4">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:&nbsp;&nbsp;&nbsp;&nbsp;</div>';
    node += '<div class="col-xs-8">'
    node += '<label class="radio-inline"><input class="col-xs-4" name="sex" type="radio" value="' + ConstDB.User.sexMale + '" checked/>&nbsp;男</label>';
    node += '<label class="radio-inline"><input class="col-xs-4" name="sex" type="radio" value="' + ConstDB.User.sexFemale + '"/>&nbsp;女</label>';
    node += '</div>';
    node += '</div>';
    return node;
}

/**
 * Created by vanki on 16/10/28.
 */

var ConstAjaxUrl = {
    ImageCode: {
        getImageCode: ["/imageCode/getImageCode.json"],
        validate: ["/imageCode/validate.json"]
    },

    Note: {
        editHtm: ['/note/edit/${id}.htm', '${id}'],
        viewHtml: ['/note/view/${id}.html', '${id}'],

        add: ['/note/add.data', 'POST', 'JSON'],
        updateById: ['/note/updateById.data', 'POST', 'JSON'],
        closeNote: ['/note/closeNote.data', 'POST', 'JSON'],
        openNote: ['/note/openNote.data', 'POST', 'JSON'],
        deleteById: ['/note/deleteById.data', 'POST', 'JSON'],
        getById: ['/note/getNoteVoById.json', 'GET', 'JSON'],
        getPageForHome: ['/note/getPageForHome.json', 'GET', 'JSON'],
        getList: ['/note/getList.json', 'GET', 'JSON']
    },

    Root: {
        logout: ['/logout.data'],
        error404: ['/404.html'],
    },

    User: {
        userHomeHtml_login: ["/user/home.html"],
        userHomeHtml: ["/user/${userId}.html", '${userId}'],
        register: ['/user/register.json', 'POST', 'JSON'],
        login: ['/user/login.json', 'GET', 'JSON'],
    },
}
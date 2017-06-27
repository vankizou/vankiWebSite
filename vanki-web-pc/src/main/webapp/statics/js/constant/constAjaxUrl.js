/**
 * Created by vanki on 16/10/28.
 */

var ConstAjaxUrl = {
    Image: {
      uploadMulti: ["/image/uploadMulti.json"],
    },

    ImageCode: {
        getImageCode: ["/imageCode/getImageCode.json"],
        validate: ["/imageCode/validate.json"]
    },

    Note: {
        editHtm: ['/note/edit/${id}.htm', '${id}'],
        viewHtml: ['/note/${id}.html', '${id}'],
        download: ['/note/download.json'],
        doDownload: ['/note/doDownload.json'],

        add: ['/note/add.data', 'POST', 'JSON'],
        updateById: ['/note/updateById.data', 'POST', 'JSON'],
        closeNote: ['/note/closeNote.data', 'POST', 'JSON'],
        openNote: ['/note/openNote.data', 'POST', 'JSON'],
        deleteById: ['/note/deleteById.data', 'POST', 'JSON'],
        getById: ['/note/getNoteVoById.json', 'GET', 'JSON'],
        getPageForHome: ['/note/getPageForHome.json', 'GET', 'JSON'],
        getTreeList: ['/note/getTreeList.json', 'GET', 'JSON']
    },

    Picture: {
        getPage: ['/picture/getPage.json', 'GET', 'json']
    },

    Root: {
        error404: ['/error/404.html'],
    },

    User: {
        logout: ['/user/logout.html'],
        userHomeHtml_login: ["/user/home.html"],
        userHomeHtml: ["/user/${userId}.html", '${userId}'],
        register: ['/user/register.json', 'POST', 'JSON'],
        login: ['/user/login.json', 'GET', 'JSON'],
    },
}
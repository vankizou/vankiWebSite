/**
 * Created by vanki on 16/10/27.
 */

$(function () {
    var noteTree = [{
        "id": 1,
        "text": "Folder1",
        "iconCls": "icon-save",
        "children": [{
            "text": "File1",
            "checked": true
        }, {
            "text": "Books",
            "state": "open",
            "vankiParams": {
                "haha": "sldfjsdkf"
            },
            "children": [{
                // "id":7,
                "text": "PhotoShop",
                "checked": true,
                "state": "closed"
            }, {
                "id": 8,
                "text": "Sub Bookds",
                "state": "closed",
                "children": []
            }]
        }]
    }, {
        "text": "Languages",
        "state": "closed",
        "children": [{
            "text": "Java"
        }, {
            "text": "C#"
        }, {
            "text": "C12112111"
        }, {
            "text": "C2222222222"
        }]
    }];

    $('#VJ_note_tree').tree({
        // data: [],
        dnd: true,
        animate: true,
        lines: true,
        url: ConstAjaxUrl.tree.getRootList[0],
        loadFilter: function (data) {
            operateMyAjaxData(data, null, success_getRootList);
        },
        onDrop: function (target, source, point) {
            if (point == 'append') {

            } else if (point == 'top' || point == 'bottom') {

            } else {
                // 不支持的操作
            }
            // debugger;
        },
        onDblClick: function (node) {
            console.info(node.attributes);
            console.info(node.vankiParams);
            toggle();
        }
    });

    var success_getRootList = function (data) {
        if (!data) {
            $('#VJ_add_tree_root').show();
            return;
        }
        /**
         * 有数据返回
         */
        $('#VJ_add_tree_root').hide();
    }

    /**
     * 添加第一个树节点
     */
    $('#VJ_add_tree_root').click(function () {
        var addTreeRoot = function () {
            var rootTitle = $('#VJ_tree_title').val();
            if (!rootTitle) {
                $.messager.alert('错误信息', "标题不能为空", 'error');
                return;
            }
            var rootDesc = $('#VJ_tree_description').val();
            var rootIsPublic = $('input[name="VJ_isPublic"]:checked').val();
            var ajaxParam = {
                title: rootTitle,
                isPublic: rootIsPublic,
                description: rootDesc
            }
            var succFun = function (data) {
                console.info(data);
                closeEasyUIDialog('VJ_win_add_tree_note');
                reload();
            }
            myAjax(ConstAjaxUrl.tree.add, ajaxParam, null, succFun);

        }
        openEasyUIDialog('VJ_win_add_tree_note', 200, 180, addTreeRoot);
    });
});


function reload() {
    var node = $('#VJ_note_tree').tree('getSelected');
    if (node) {
        $('#VJ_note_tree').tree('reload', node.target);
    } else {
        $('#VJ_note_tree').tree('reload');
    }
}
function getChildren() {
    var node = $('#VJ_note_tree').tree('getSelected');
    if (node) {
        var children = $('#VJ_note_tree').tree('getChildren', node.target);
    } else {
        var children = $('#VJ_note_tree').tree('getChildren');
    }
    var s = '';
    for (var i = 0; i < children.length; i++) {
        s += children[i].text + ',';
    }
    alert(s);
}
function getChecked() {
    var nodes = $('#VJ_note_tree').tree('getChecked');
    var s = '';
    for (var i = 0; i < nodes.length; i++) {
        if (s != '') s += ',';
        s += nodes[i].text;
    }
    alert(s);
}
function getSelected() {
    var node = $('#VJ_note_tree').tree('getSelected');
    alert(node.text);
}
function collapse() {
    var node = $('#VJ_note_tree').tree('getSelected');
    $('#VJ_note_tree').tree('collapse', node.target);
}
function expand() {
    var node = $('#VJ_note_tree').tree('getSelected');
    $('#VJ_note_tree').tree('expand', node.target);
}
function collapseAll() {
    var node = $('#VJ_note_tree').tree('getSelected');
    if (node) {
        $('#VJ_note_tree').tree('collapseAll', node.target);
    } else {
        $('#VJ_note_tree').tree('collapseAll');
    }
}
function expandAll() {
    var node = $('#VJ_note_tree').tree('getSelected');
    if (node) {
        $('#VJ_note_tree').tree('expandAll', node.target);
    } else {
        $('#VJ_note_tree').tree('expandAll');
    }
}
function append() {
    var node = $('#VJ_note_tree').tree('getSelected');
    /*$('#VJ_note_tree').tree('append', {
     parent: (node ? node.target : null),
     data: [{
     text: 'new1',
     checked: true
     }, {
     text: 'new2',
     state: 'closed',
     children: [{
     text: 'subnew1'
     }, {
     text: 'subnew2'
     }]
     }]
     });*/
}
function remove() {
    var node = $('#VJ_note_tree').tree('getSelected');
    $('#VJ_note_tree').tree('remove', node.target);
}
function update() {
    var node = $('#VJ_note_tree').tree('getSelected');
    if (node) {
        node.text = '<span style="font-weight:bold">new text</span>';
        node.iconCls = 'icon-save';
        $('#VJ_note_tree').tree('update', node);
    }
}
function isLeaf() {
    var node = $('#VJ_note_tree').tree('getSelected');
    var b = $('#VJ_note_tree').tree('isLeaf', node.target);
}
function toggle() {
    var node = $('#VJ_note_tree').tree('getSelected');
    var b = $('#VJ_note_tree').tree('toggle', node.target);
}
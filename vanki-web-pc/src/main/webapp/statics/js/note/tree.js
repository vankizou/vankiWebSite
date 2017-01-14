/**
 * Created by vanki on 16/10/27.
 */

$(function () {
    var initNoteTree = function () {
        /**
         * 树
         */
        $('#VJ_note_tree').tree({
            data: [],
            dnd: true,
            animate: true,
            lines: true,
            /*url: ConstAjaxUrl.tree.getListByParentId[0] + "?parentId=" + currentParentId,
             loadFilter: function (data) {
             var initTreeData = [];
             operateMyAjaxData(data, initTreeData, success_getRootList);
             console.info(initTreeData);
             return initTreeData;
             },*/
            onDrop: function (target, source, point) {
                if (point == 'append') {

                } else if (point == 'top' || point == 'bottom') {

                } else {
                    // 不支持的操作
                }
                // debugger;
            },
            onDblClick: function (node) {
                var vankiParams = node.vankiParams;

                console.info($('#VJ_note_tree').tree('getSelected').target);

                // 打开或关闭
                $('#VJ_note_tree').tree('toggle', $('#VJ_note_tree').tree('getSelected').target);
            }
        });

        /**
         * 初始化树数据
         * @param dataArr
         */
        var success_getRootList = function (dataArr) {
            if (!dataArr || dataArr.length == 0) {
                $('#VJ_add_tree_root').show();
                return;
            }
            /**
             * 有数据返回
             */
            // $('#VJ_add_tree_root').hide();

            var initTreeData = [];
            for (var i = 0, len = dataArr.length; i < len; i++) {
                var data = dataArr[i];
                var treeNoteData = {};

                treeNoteData.id = "nodeId" + i;

                var add = '<span onclick="subNode(' + data.id + ');" currentNodeId="' + data.id + '" title="删除" style="cursor:wait;">  -  </span>';
                var sub = '<span onclick="addNode(' + data.id + ');" currentNodeId="' + data.id + '" title="添加" style="cursor:wait;">  +  </span>';


                if (data.type) {    // 文件
                    treeNoteData.text = data.title + " (" + data.countTarget + ")" + sub;
                    treeNoteData.state = 'open';
                } else if (!data.type && data.countTarget == 0) {   // 文件夹, 文件为空
                    treeNoteData.text = data.title + " (" + data.countTarget + ")" + add + sub;
                    treeNoteData.state = 'closed';
                } else {
                    treeNoteData.text = data.title + " (" + data.countTarget + ")" + add + sub;
                    if (data.isOpen) {  // 文件夹, 有文件
                        treeNoteData.state = 'open';
                    } else {
                        treeNoteData.state = 'closed';
                    }
                }
                treeNoteData.vankiParams = {
                    parentId: data.parentId,
                    id: data.id
                }
                initTreeData[i] = treeNoteData;
            }
            $('#VJ_note_tree').tree('append', {
                data: initTreeData
            });
        }
        myAjax(ConstAjaxUrl.tree.getRootList, null, null, success_getRootList);
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
            var rootIsPublic = $('input[name="VJ_isPublic"]:checked').val();
            var rootDesc = $('#VJ_tree_description').val();
            var ajaxParam = {
                title: rootTitle,
                isPublic: rootIsPublic,
                description: rootDesc
            }
            var succFun = function (data) {
                closeEasyUIDialog('VJ_dialog_add_tree_note');
                initNoteTree();
            }
            myAjax(ConstAjaxUrl.tree.add, ajaxParam, null, succFun);
        }
        openEasyUIDialog('VJ_dialog_add_tree_note', 260, 210, addTreeRoot);
    });


    initNoteTree();
});

/**
 * 删除树节点
 * @param treeId
 */
var subNode = function (treeId) {
    if (!treeId) return;
    var selectedNode = $('#VJ_note_tree').tree('getSelected');
    if (!selectedNode) return;
    $.messager.confirm('确认删除', "是否确认删除?", function (r) {
        if (r) {
            var deleteByIdParams = {id: treeId}
            var deleteByIdSuccFun = function (data) {
                $('#VJ_note_tree').tree('remove', selectedNode.target);
            }
            myAjax(ConstAjaxUrl.tree.deleteById, deleteByIdParams, null, deleteByIdSuccFun);
        }
    });
};

/**
 * 添加树节点
 * @param treeId
 */
var addNode = function (treeId) {
    if (!treeId) return;
    var selectedNode = $('#VJ_note_tree').tree('getSelected');
    if (!selectedNode) return;

    $('#VJ_FOLDER_OR_FILE_DIV').show();

    var chooseNodeTypeFun = function () {
        // 0文件, 1文件夹
        var nodeType = $('input[name="VJ_FOLDER_OR_FILE"]:checked').val();
        if (nodeType == 0) {

        } else if (nodeType == 1) {
            closeEasyUIDialog('VJ_dialog_choose_node_type');
            var addFolderNodeFun = function () {
                var title = $('#VJ_tree_title').val();
                if (!title) {
                    $.messager.alert('错误信息', "标题不能为空", 'error');
                    return;
                }
                var isPublic = $('input[name="VJ_isPublic"]:checked').val();
                var desc = $('#VJ_tree_description').val();
                var ajaxParam = {
                    parentId: treeId,
                    title: title,
                    isPublic: isPublic,
                    description: desc
                }
                var succFun = function (data) {
                    closeEasyUIDialog('VJ_dialog_add_tree_note');
                    initNoteTree();
                }
                myAjax(ConstAjaxUrl.tree.add, ajaxParam, null, succFun);
            }
            openEasyUIDialog('VJ_dialog_add_tree_note', 260, 210, addFolderNodeFun);
        }
    }
    openEasyUIDialog('VJ_dialog_choose_node_type', 250, 150, chooseNodeTypeFun);
}


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
    var noteTree = [{
        "id": 1,
        "text": 1,
        "state": "closed"
    }]


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
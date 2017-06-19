/**
 * Created by vanki on 2017/6/19.
 */

/*$(function () {
    changeFooterMarginTop();
});*/

function changeFooterMarginTop(correctHeight) {
    if (!correctHeight) correctHeight = 0;

    var viewHeight = $(window).height();    // 可视区域高
    var totalHeight = $(document).height();    // 总高
    var distTopHeight = $("#copyright").offset().top;   // copyright与顶部高
    var height = $('#copyright').outerHeight(true);     // copyright高

    if (totalHeight > viewHeight) {
        $('#copyright').css("margin-top", 0);
        return;
    }

    /*if (totalHeight > distTopHeight)
        $('#copyright').css("margin-top", totalHeight - distTopHeight - height + correctHeight);
    else
        $('#copyright').css("margin-top", 0);*/
    $('#copyright').css("margin-top", viewHeight - distTopHeight - height + correctHeight);
}

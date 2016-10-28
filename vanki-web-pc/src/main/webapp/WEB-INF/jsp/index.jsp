<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Expires" content="0"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Pragma" content="no-cache"/>
<link rel="shortcut icon" href="statics/images/favicon.ico" type="image/x-icon" />
<title>爱上直播</title>
<base href="<%=basePath%>">

<style>/*公共样式*/
body{margin:0; padding:0; list-style:0; font-family:"Microsoft Yahei","微软雅黑",arial,"宋体",sans-serif; background-color:#ffffff; font-size:12px;}
ul,li,p,input,button,h2,h3,h4,h5{ margin:0; padding:0; list-style:none; border:0;}
image{vertical-align:top;border:none;margin:0;padding:0;}
a{text-decoration:none;color:#000;}
.clear{clear:both;}
/*背景图*/
body{width:100%;height:100%;background-image:url(statics/images/l_bg_img.jpg);background-repeat:no-repeat;background-size:100% 100%;} 
.main{width:1317px;height:828px;margin:0 auto;margin-top:50px;position:relative;}
.main a{display:block;width:250px;height:60px;position:absolute;}
.main a.iphone{left:294px;top:447px;}
.main a.android{left:294px;top:574px;}
.main .video{position:absolute;right:26px;top:98px;width:355px; height:632px; display:block;}
.main .l-m-i{ border:0px; display:block;}

/*设置屏幕的宽960像素*/
.w960{}
.w960 .main{width:960px;}
.w960 .main .l-m-i{width:960px;}
.w960 .main .video{width: 259px;right:21px; height:461px;top:72px;}
.w960 .main a.iphone{width:189px; height:51px;left: 214px;top: 320px;}
.w960 .main a.android{width:189px; height:51px;left: 214px;top: 414px;}
.w960 .v_box .bottom{bottom: 23px;}
.w960 .v_box .top{top: 4px;}
/**/
.v_box{width:100%; height:100%; position:relative; display:block;}
.v_box .top{width: 100%;
top: 2px;
display: block;position:absolute;z-index:99;}
.v_box .bottom{width: 100%;
bottom: 28px;
display: block; position:absolute;z-index:99;}
.v_object{width:100%; height:100%; position:absolute;z-index:1; display:block;}
</style>
<script language="javascript" src="statics/js/jquery-1.8.3.min.js"></script>
<script>
$(document).ready(function(){
	var w=$(window).width();
	var h=$(window).height();
	w=parseInt(w);
	h=parseInt(h);
	if(w<=1400){
		$('body').addClass('w960');
		
	}else{
		$('body').removeClass('w960');
		
	}
});
</script>
</head>
<body class="w960">
<div class="main">
	<img src="statics/images/l_main_img.jpg" border="0" class="l-m-i"/>
    <a href="${iosUrl}" class="iphone"></a>
    <a href="${androidUrl}" class="android"></a>
    <!-- 代码 开始 -->
    <div class="video">
		<div class="v_box">
			<img src="statics/images/l_top.png?v=1.0" border="0" class="top"/>
			<img src="statics/images/l_bottom_1.png" border="0" class="bottom"/>
			<div class="v_object">
			 
			<video src="statics/images/video.mp4" height="100%" width="100%" autoplay poster="statics/images/w-bg-1.jpg" preload="auto" webkit-playsinline >
</video>
			
			</div>
		</div>
    </div>
	<!-- 代码 结束 -->
</div>

</body>
</html>

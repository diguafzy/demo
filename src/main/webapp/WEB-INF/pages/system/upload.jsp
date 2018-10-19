<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>文件上传</title>

    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/webuploader.css" />
    <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css" />
</head>
<body>
<div class="container" style="width: initial;">
	<div class="container" style="text-align: center">
	    <form:form action="system/upload/add" id="webupload" class="form-horizontal" method="POST" role="form">
	        	<input type="hidden" id="filenames" name="filenames"/>
	    </form:form>
        <div class='row form-group'>
            <label class="col-md-2 control-label" style="text-align:right">文件:</label>
            <div class="col-md-10">
                <!--用来存放item-->  
                <div id="thelist" class="row"></div>  
                <div id="filePicker">选择文件</div>
                <button id="uploadbtn" class="btn btn-sm btn-success">提交</button>
            </div>
        </div>
	</div>
</div>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script type="text/javascript" src="script/jquery-3.2.1.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script type="text/javascript" src="script/bootstrap.min.js"></script>
<script type="text/javascript" src="script/webuploader.js"></script>
<script type="text/javascript">
$(function() {
	uploader = WebUploader.create({
	    auto: true,
	    pick: '#filePicker',
	    swf: 'script/Uploader.swf',
	    server: 'system/upload/save',
	    accept: {
	         title: 'Images',
	         extensions: 'gif,jpg,jpeg,bmp,png,doc,docx,xls,xlsx,dwg',
	         mimeTypes: 'image/*'
	     },
	     method:'POST'
	});
	
	// 当有文件添加进来的时候  
	uploader.on( 'fileQueued', function( file ) {  // webuploader事件.当选择文件后，文件被加载到文件队列中，触发该事件。等效于 uploader.onFileueued = function(file){...} ，类似js的事件定义。  
	    var $li = $(  
	            '<div id="' + file.id + '" class="col-md-2">' +  
	                '<img>' +  
	                '<div class="info">' + file.name + '</div>' +  
	                '<input type="hidden" id="filename'+file.id+'" value="">' +
	            '</div>'  
	            ),  
	        $img = $li.find('img');  
	    var $btns = $('<div class="file-panel">' +
	        '<span class="cancel fa fa-trash-o" >删除</span></div>').appendTo($li);
	    $li.on('mouseenter', function () {
	        $btns.stop().animate({height: 20});
	    });
	    $li.on('mouseleave', function () {
	        $btns.stop().animate({height: 0});
	    });
	    $btns.on('click', 'span', function () {
	        var index = $(this).index();
	        switch (index) {
	            case 0:
	                uploader.removeFile(file);
	                removeFile(file);
	                return;
	        }
	    });
	
	    // $("#thelist")为容器jQuery实例  
	    if(!$("#thelist").hasClass("thumbnail")) $("#thelist").addClass("thumbnail");
	    $("#thelist").append( $li );  
	
	    // 创建缩略图  
	    // 如果为非图片文件，可以不用调用此方法。  
	    // thumbnailWidth x thumbnailHeight 为 100 x 100  
	    uploader.makeThumb( file, function( error, src ) {   //webuploader方法  
	        if ( error ) {  
	            $img.replaceWith('<span>附件文档不能预览</span>');  
	            return;  
	        }  
	
	        $img.attr( 'src', src );  
	    }, 100, 100 );  
	});  
	// 文件上传过程中创建进度条实时显示。  
	uploader.on( 'uploadProgress', function( file, percentage ) {  
	    var $li = $( '#'+file.id ),  
	        $percent = $li.find('.progress span');  
	
	    // 避免重复创建  
	    if ( !$percent.length ) {  
	        $percent = $('<p class="progress"><span></span></p>')  
	                .appendTo( $li )  
	                .find('span');  
	    }  
	
	    $percent.css( 'width', percentage * 100 + '%' );  
	});  
	
	// 文件上传成功，给item添加成功class, 用样式标记上传成功。  
	uploader.on( 'uploadSuccess', function( file, response) {
	    $( '#'+file.id ).addClass('upload-state-done');
	    if(response.picturename) $("#filename"+file.id).val(response.picturename);
	    if(response.picturename) $("#filenames").val($("#filenames").val()+","+response.picturename);
	});  
	
	// 文件上传失败，显示上传出错。  
	uploader.on( 'uploadError', function( file ) {  
	    var $li = $( '#'+file.id ),  
	        $error = $li.find('div.error');  
	
	    // 避免重复创建  
	    if ( !$error.length ) {  
	        $error = $('<div class="error"></div>').appendTo( $li );  
	    }  
	
	    $error.text('上传失败');  
	});  
	
	// 完成上传完了，成功或者失败，先删除进度条。  
	uploader.on( 'uploadComplete', function( file ) {  
	    $( '#'+file.id ).find('.progress').remove();  
	});  
	$("#uploadbtn").on( 'click', function() {  
		$("#webupload").submit();  
	});  
	
	// 负责view的销毁
    function removeFile( file ) {
        var $li = $('#'+file.id);
        $.ajax({
            type: "GET",
            url: 'system/upload/delete',
            data: {
            	filename:$("#filename"+file.id).val()
            },
            dataType:'json',
            cache: false,
            success: function(data){
                var $li = $('#'+file.id);
                $li.off().find('.file-panel').off().end().remove();
                if($("#thelist").find("div").length == 0) $("#thelist").removeClass("thumbnail");
            }
        });
    }
})
</script>
</body>
</html>
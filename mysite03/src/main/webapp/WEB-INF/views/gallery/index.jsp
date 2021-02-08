<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<%pageContext.setAttribute( "newLine", "\n" );%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/gallery.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/lightbox.css" rel="stylesheet" type="text/css">
<link href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-3.5.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/lightbox.js"></script>
<script src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">
$(function(){
	let load = function() {
		$.ajax({
			url: "${pageContext.request.contextPath}/api/gallery",
			type: "GET",
			success: function (response) {
				if(response.result != 'success'){
					console.error(response.message);
					return;
				}
				
				let image = response.data;
				$('div#gallery ul li').remove();
				for(index in image) {
					html = '<li>' +
					'<a href="${pageContext.request.contextPath}' + image[index].imageURL + '" data-lightbox="gallery" class="image" style="background-image:url(${pageContext.request.contextPath}' + image[index].imageURL + ')">&nbsp;</a>' +
					'<a href="' + image[index].no + '" class="del-button" title="삭제">삭제</a>' + '</li>';
					
					$('div#gallery ul').prepend(html);
				}
	    	},
	    	error: function(xhr, status, e){
				console.log(status + ':' + e);
			}
		});
	}
	
	load();
	
	// 업로드 다이알로그
	var dialogUpload = $( "#dialog-upload-form" ).dialog({
		autoOpen: false,
		height: 280,
		width: 300,
		modal: true,
		buttons: {
			"업로드": function() {
				if($('#input-file').val() == '') {
					$('.validateTips.normal').text('업로드할 파일을 선택해주세요.');
					return;
				}
				
				let form = new FormData($('#fileupload-form')[0])
				
				console.log(form);
				
				$.ajax({
					url: "${pageContext.request.contextPath}/api/gallery",
					type: "POST",
					data: form,
				    enctype: 'multipart/form-data',
					processData: false,
				    contentType: false,
				    cache: false,
				    success: function (response) {
				    	console.log(response);
						if(response.result != 'success'){
							console.error(response.message);
							return;
						}
				    },
				    error: function(xhr, status, e){
						console.log(status + ':' + e);
					}
				})
				dialogUpload.dialog("close");
			},
			"취소" : function() {
				dialogUpload.dialog("close");
			}
		},
		close: function() {
			$('.validateTips.normal').text('이미지와 간단한 코멘트를 입력해 주세요.');
			$( "#dialog-upload-form form" ).get(0).reset();
			setTimeout(load, 500);
		}
	});
		
	$("#upload-image").click(function(event) {
		event.preventDefault();
		dialogUpload.dialog("open");
	});
	
	$("submit#input-button").click(function(event) {
		event.preventDefault();
	})
	
	$("body").on("click", "a.del-button", function(event) {
		event.preventDefault();
		
		no = $(this).attr('href');
		$.ajax({
			url: "${pageContext.request.contextPath}/api/gallery/" + no,
			type: "DELETE",
		    success: function (response) {
				if(response.result != 'success'){
					console.error(response.message);
					return;
				}
				setTimeout(load, 500);
	    	},
	    	error: function(xhr, status, e){
				console.log(status + ':' + e);
			}
		})
	}) 
});	
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="gallery">
				<div>
					<h1>갤러리</h1>
					<a href="" id="upload-image">이미지 올리기</a>
				</div>
				<ul>																																				
				</ul>	
			</div>

			<div id="dialog-upload-form" title="이미지 업로드" style="display:none">
  				<p class="validateTips normal">이미지와 간단한 코멘트를 입력해 주세요.</p>
  				<form id="fileupload-form"
  					  action="${pageContext.request.contextPath }/gallery/upload" 
  					  method="post"
  					  enctype="multipart/form-data">
					<label>코멘트</label>
					<input type="text" id="input-comments" name="comment" value="">
					<label>이미지</label>
					<input type="file" id="input-file" name="file">
					<input type="submit" id="input-button" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="gallery"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>
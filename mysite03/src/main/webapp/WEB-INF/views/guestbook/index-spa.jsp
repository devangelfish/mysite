<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-spa.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-3.5.1.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js"></script>
<script>
let startNo = 0;
let isEnd = false;

const listTemplate = new EJS({
	url: '${pageContext.request.contextPath }/assets/js/ejs/list-template.ejs'
});

const listItemTemplate = new EJS({
	url: '${pageContext.request.contextPath }/assets/js/ejs/list-item-template.ejs'
});

/* guestbook spa application */
const messageBox = function(title, message, callback){
	$("#dialog-message")
		.attr('title', title)
		.dialog({
			modal: true,
			buttons: {
				"확인": function(){
					$(this).dialog('close');
				}
			},
			close: callback || function(){}
		});
	
	$("#dialog-message p")
		.height($("#dialog-message").height())
		.width($("#dialog-message").width())
		.html(message.replace(/\n/gi, '<br>'));
}

const fetchList = function(){
	if(isEnd) {
		return;
	}
	$.ajax({
		url: '${pageContext.request.contextPath }/api/guestbook/list/' + startNo,
		async: true,
		type: 'get',
		dataType: 'json',
		data: '',
		success: function(response){
			if(response.result != 'success'){
				console.error(response.message);
				return;
			}
			
			// detect end
			if(response.data.length < 3){
				isEnd = true;
				$('#btn-fetch').prop('disabled', true);
				return;
			}
			
			// rendreing
			console.log(response);
			const html = listTemplate.render(response);
			$("#list-guestbook").append(html);
			
			
			// startNo = response.data[response.data.length-1]["no"];
			startNo = $('#list-guestbook li').last().data('no') || 0;
		},
		error: function(xhr, status, e){
			console.log(status + ':' + e);
		}
	});	
}

$(function(){
	const dialogDelete = $("#dialog-delete-form").dialog({
		width: 300,
		height: 200,
		autoOpen: false,
		model: true,
		buttons: {
			"삭제": function() {
				const password = $('#password-delete').val();
				const no = $('#hidden-no').val();
				$.ajax({
					url: '${pageContext.request.contextPath }/api/guestbook/delete/' + no,
					async: true,
					type: 'delete',
					dataType: 'json',
					data: 'password=' + password,
					success: function(response){
						console.log(response);
						if(response.result != 'success'){
							console.error(response.message);
							return;
						}
						
						if(response.data != -1) {
							$('#list-guestbook li[data-no=' + response.data + ']').remove();
							dialogDelete.dialog('close');
							return;
						}
						
						// 비밀번호가 틀린 경우
						$('#dialog-delete-form p.validateTips.error').show();
					},
					error: function(xhr, status, e){
						console.log(status + ':' + e);
					}
				});
			},
			"취소": function() {
				$(this).dialog('close');
			}
		},
		close: function() {
			$('#password-delete').val('');
			$('#hidden-no').val('');
			$('#dialog-delete-form p.validateTips.error').hide();
		}
	})
	
	// 버튼 이벤트(test)
	$('#btn-fetch').click(fetchList);
	
	// 입력폼 submit 이벤트
	$('#add-form').submit(function(event){
		event.preventDefault();
		
		vo = {};
		// validation
		vo.name = $('#input-name').val();
		vo.password = $('#input-password').val();
		vo.message = $('#tx-message').val();
		
		// post
		$.ajax({
			url: '${pageContext.request.contextPath }/api/guestbook/add',
			async: true,
			type: 'post',
			dataType: 'json',
			data: JSON.stringify(vo),
			contentType: 'application/json',
			success: function(response){
				if(response.result != 'success'){
					console.error(response.message);
					return;
				}
				
				const html = listItemTemplate.render(response.data);
				$('#list-guestbook').prepend(html);
				
				// form reset
				$('#add-form')[0].reset();
			},
			error: function(xhr, status, e){
				console.log(status + ':' + e);
			}
		});	
	});
	
	// 창 스크롤 이벤트
	$(window).scroll(function(){
		const $window = $(this);
		const $document = $(document);
		
		const scrollTop = $window.scrollTop(); 
		const windowHeight = $window.height();
		const documentHeight = $document.height();
		
		if(windowHeight + scrollTop + 10 > documentHeight){
			fetchList();
		}
	});
	
	// 삭제 버튼 click 이벤트
	// Live Event: 존재하지 않는 element의 이벤트 핸들러를 미리 세팅하는 것
	// delegation(위임, document)
	$(document).on('click', '#list-guestbook li a', function(event){
		event.preventDefault();
		
		const no = $(this).data('no');
		$('#hidden-no').val(no);
		dialogDelete.dialog('open');
	});
	
	// 첫번쨰 리스트 가져오기
	fetchList();
	
	// jQuery Plugin Test
	$("#btn-fetch").hello();
	$("#btn-fetch").flash();
});
</script>

<script>
(function($){
	$.fn.hello = function(){
		console.log(this);
		console.log("hello #" + this.attr('title'))
	}
})(jQuery);
(function($){
	$.fn.flash = function(){
		const $this = this;
		let isBlink = false;
		setInterval(function(){
			$this.css('backgroundColor', (isBlink ? '#f00' : '#aaa'));
			isBlink = !isBlink;
		}, 1000);
	}
})(jQuery);


</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-message" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook"></ul>
				<div style='margin:20px 0 0 0'>
					<button id='btn-fetch' title='jQuery plugin'>다음가져오기</button>
				</div>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p></p>
			</div>						
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>
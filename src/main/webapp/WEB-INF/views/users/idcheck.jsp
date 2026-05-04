<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <h2>아이디 중복확인</h2>
  <form action="/Users/DupCheckWindow" method="get">
  <input type="text" name="userid" />
  <input type="submit" value="중복확인" /><br>
  <div id="msg"></div>
  <input type="button" value="사용하기" id="btnClose" />
  </form>
  
  <script>
    const btnCloseEl = document.querySelector('#btnClose') 
    btnCloseEl.addEventListener('click', function() {
    	//alert('ok')
    	const thisUseridEl   = window.document.querySelector('[name=userid]')
    	const parentUseridEl = window.opener.document.querySelector('[name="userid"]')
    	parentUseridEl.value = thisUseridEl.value 
    	window.close();
    })
  </script>
</body>
</html>
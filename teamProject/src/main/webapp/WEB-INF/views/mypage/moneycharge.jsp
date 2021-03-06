<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<link href="resources/css/style-moneycharge.css" rel="stylesheet"/>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
	integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>
<script type="text/javascript">

	/* 충전 후 금액 실시간 반영 */
	$(document).ready(function(){
		$("#radio-div input").on("propertychange change keyup paste input", function(){
			var cmoney = Number("${money}");
			var amoney = Number($(this).val());
			cmoney += amoney;
			/* console.log("cmoney = ", cmoney); */
			/* console.log("amoney = ", amoney); */
			var afterChange = document.getElementById('aftermoney');
			$("input[type=hidden]").val(cmoney);
			/* console.log($("#moneyadd").val()); */
			afterChange.innerHTML = "<div class='aftercharge'>충전 후 금액: " + cmoney + "</div>";
		});
	});
	
	/* 값을 넘겨주면서 창을 닫음 */
	function paymoney() {
			document.subform.submit();	
		/*document.location = "paycomplete?moneyadd="+document.getElementById("moneyadd").value;
			 if (window.opener && !window.opener.closed) {
				window.opener.location = "paymoneycheck?smoney="+smoney; 
				window.close();
			} */
		}
	
	/* 직접입력 시 라디오 버튼 전부 해제 */
	$(function(){
		$("#inputmoney").click(function(){
			$("input[type=radio]").prop("checked",false);
		});
	});
</script>
</head>
<body>
	<div>
		<div id="aftermoney">
			<div class="aftercharge">현재  금액:  ${money}</div>
		</div>
	</div>
	<div>
		<h3 class="chargeinfo">충전하실 금액을 선택해주세요.</h3>
	</div>
	<div id="radio-div">
		<form action="paycomplete" name="subform" method="post">
			<input type="number" id="inputmoney" name="addmoney"
				placeholder="직접입력" class="directinput" onclick="this.value='';">
			<input type="button" class="submitbtn" value="결 제" onclick="paymoney();">
			<input type="radio" id="1000원" value="1000" name="addmoney" class="radio-cir" >
			<label for="1000원">1,000원</label>
			<input type="radio" id="5000원" value="5000" name="addmoney" class="radio-cir">
			<label for="5000원">5,000원</label>
			<input type="radio" id="10000원" value="10000" name="addmoney" class="radio-cir">
			<label for="10000원">10,000원</label>
			<input type="radio" id="20000원" value="20000" name="addmoney" class="radio-cir">
			<label for="20000원">20,000원</label>
			<input type="radio" id="50000원" value="50000" name="addmoney" class="radio-cir">
			<label for="50000원">50,000원</label>
			
			<input type="hidden" name="moneyadd">
		</form>
	</div>
</body>
</html>
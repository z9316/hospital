var emailyzm = '';
var tab1flag = '0';//0=tab11不能打开，1=tab11可以打开
var userid = '';

jQuery(function($){
	
	$("#button1").click(function(){
		if(tab1flag == '0'){
			$("#tab1").css("display","block");
			$("#tab11").css("display","none");
		}else{
			$("#tab1").css("display","none");
			$("#tab11").css("display","block");
		}
		
		$("#tab2").css("display","none");
		$("#tab3").css("display","none");
	});
	$("#button2").click(function(){
		$("#tab1").css("display","none");
		$("#tab11").css("display","none");
		$("#tab2").css("display","block");
		$("#tab3").css("display","none");
	});
	$("#button3").click(function(){
		$("#tab1").css("display","none");
		$("#tab11").css("display","none");
		$("#tab2").css("display","none");
		$("#tab3").css("display","block");
	});
	
	$('#emailsave1').click(function(){
		var username = $('#username1').val();
		var email = $('#email1').val();
		var f = checkemail(email);
		if(username == ''){
			alert('用户名不能为空');
			return ;
		}
		if(f == '0'){
			alert('邮箱格式不正确');
			return ;
		}
		$.post('/hospital/getEmailYZM',{"username":username,"email":email},function(data){
			if(data.state != '1'){
				alert(data.msg);
			}else{
				daojishi(60);
				emailyzm = data.code;
				userid = data.userid;
			}
		});
		
	});
	
	$('#save1').click(function(){
		var yzm1 = $('#emailkey1').val();
		yzm1  = yzm1.toUpperCase();
		var f = checkyzm(yzm1);
		if(f == '0'){
			alert('请输入正确的验证码');
		}else{
			var newcode = hex_md5(yzm1);
			if(emailyzm != '' && newcode == emailyzm){
				tab1flag = '1';
				$("#tab1").css("display","none");
				$("#tab11").css("display","block");
			}
		}
	});
	
	$('#save11').click(function(){
		var p1 = $('#password1').val();
		var np1 = $('#newpassword1').val();
		if(p1 == ''||np1 == ''){
			alert('密码不能为空');
		}else{
			var f1 = checkusernameorpassword(p1);
			var nf1 = checkusernameorpassword(np1);
	        if(f1 == '0' || nf1 == '0'){
	        	alert('密码格式不正确');
	        }else if(f1 != nf1){
	        	alert('两次密码不相同');
	        }else{
	        	p1 = hex_md5(p1);
	        	$.post("/hospital/editpassword",{"userid":userid,"password":p1},function(data){
	        		emailyzm = '';
	        		if(data.state == '0'){
	        			alert(data.msg);
	        			tab1flag = '0';
	        		}else{
	        			window.location.href = '/herb/home';
	        		}
	        	});
	        }
		}
		
	});
	
	$('#save2').click(function(){
		var username = $('#username2').val();
		var email = $('#email2').val();
		var pass2 = $('#key2').val();
		var npass2 = $('#newkey2').val();
		var u2 = checkusernameorpassword(username);
		var e2 = checkemail(email);
		var p2 = checkusernameorpassword(pass2);
		var np2 = checkusernameorpassword(npass2);
		if(u2 == '0'){
			alert('用户名格式不对，请重新输入');
			return ;
		}
		if(e2 == '0'){
			alert('邮箱名不对，请重新输入');
			return ;
		}
		if(p2 == '0' || np2 == '0'){
			alert('密码格式不对，请重新输入');
			return ;
		}
		if(pass2 != npass2){
			alert('两次密码不一致，请重新输入');
			return ;
		}
		var pass = hex_md5(pass2);
		$.post('/hospital/addUser',{"username":username,"email":email,"password":pass},function(data){
			alert(data.msg);
			if(data.state == 1){
				window.location.href = '/herb/home';
			}
		});
		
	});
	
	$('#save3').click(function(){
		var flag = confirm('您确定要注销用户？');
		if(!flag) return ;
		var username = $('#username3').val();
		var pass0 = $('#key3').val();
		var pass = hex_md5(pass0);
        $.post('/hospital/deleteUser',{"username":username,"password":pass},function(data){
			alert(data.msg);
		});
		
	});
	
	
	document.onkeydown=function(ev){
	    var oEvent=ev||event;//获取事件对象(IE和其他浏览器不一样，这里要处理一下浏览器的兼容性event是IE；ev是chrome等)
	    //Esc键的keyCode是27
	    if(oEvent.keyCode==27)
	    {
	       //  alert('按键了');
	    	window.location.href = '/hospital/login';
	    }
	}

});

function checkusernameorpassword(string){
	var reg = /^[0-9a-zA-Z]{6,18}$/;
	if(reg.test(string))
		return "1";
	else
		return "0";
}

function checkyzm(string){
	var reg = /^[0-9a-zA-Z]{3}$/;
	if(reg.test(string))
		return "1";
	else
		return "0";
}

function checkemail(string){
	var reg = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
	if(reg.test(string))
		return "1";
	else
		return "0";
}

function daojishi(time){
	if(time == 0){
		$('#emailsave1').removeAttr("disabled");
		$('#emailsave1').val("发送邮件验证码");
		time = 60;
	}else{
		$('#emailsave1').css("disabled", true);
		$('#emailsave1').val("发送成功，"+time+ "秒后可以重新发送");
		time = time - 1;
	    setTimeout(function() {
		    daojishi(time);
		}, 1000);
	}
}

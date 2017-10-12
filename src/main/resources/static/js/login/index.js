function changeUrl() {
    var rad = Math.floor(Math.random() * Math.pow(10, 8));
  //var url = $("#codevalidate").prop('src');
    //url = url.substr(url.lastIndexOf('/'));
    var url ='/hospital/getYZM';
    url = url+"?rad="+rad;
    $("#codevalidate").prop('src',url);
}

function checkusernameorpassword(string){
	var reg = /^[0-9a-zA-Z]{6,18}$/;
	if(reg.test(string))
		return "1";
	else
		return "0";
	
}

function checkyzm(string){
	var reg = /^[0-9a-zA-Z]{4}$/;
	if(reg.test(string))
		return "1";
	else
		return "0";
	
}

jQuery(function($){
	
	$("#register").click(function(){
		window.location.href = '/hospital/register';
	});
	
	$("input[name='submit']").click(function(){
		var username = $("input[name='username']").val();
		var password = $("input[name='password']").val();
		var yzm = $("input[name='yzm']").val();
		if(username.trim()==''){
			alert('请输入用户名');
			return ;
		}else if(password.trim()==''){
			alert('请输入密码');
			return ;
		}else if(yzm.trim()==''){
			alert('请输入验证码');
			return ;
		}else{
			var r1 = checkusernameorpassword(username);
			if(r1 == '0'){
				alert('用户名格式不正确');
				return ;
			}
			var r2 = checkusernameorpassword(password);
			if(r2 == '0'){
				alert('密码格式不正确');
				return ;
			}
			var r3 = checkyzm(yzm);
			if(r3 == '0'){
				alert('验证码格式不正确');
				return ;
			}
		}
		
		password = hex_md5(password);
		$.post("/hospital/checkUserAndPass",{"username":username,"password":password,"yzm":yzm} , function(data){
			if(data.state != 1){
				alert(data.message);
				changeUrl();
			}else{
				window.location.href = '/herb/home';
			}
		});
	});
	
});

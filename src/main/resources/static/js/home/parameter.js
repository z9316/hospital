
jQuery(function($){
	
	init();

	$('#save').click(function(){
		var text = $('#text').val();
		var scan = $('#scan').val();
		var tc = check(text);
		var sc = check(scan);
		if(tc == '0'){
			alert('上传文件路径格式不对，请重新输入');
			return ;
		}else if(sc == '0'){
			alert('上传文件路径格式不对，请重新输入');
			return ;
		}
		var host = $('#host').val();
		var port = $("#port").val();
		var name = $('#name').val();
		var email = $('#email').val();
		var password = $('#password').val();
		var hc = checkip(host);
		var pc = checkport(port);
		var nc = checkname(name);
		var ec = checkemail(email);
		var pac = checkpassword(password);
		if(hc == '0'){
			alert('服务器名称不对，请重新输入');
			return ;
		}else if(pc == '0'){
			alert('端口名称不对，请重新输入');
			return ;
		}else if(nc == '0'){
			alert('用户名称不对，请重新输入');
			return ;
		}else if(ec == '0'){
			alert('邮箱名称不对，请重新输入');
			return ;
		}else if(pac == '0'){
			alert('密码不对，请重新输入');
			return ;
		}
		$.post('/herb/parameteredit',{'text':text,'scan':scan,'host':host,'port':port,'name':name,'email':email,'password':password},function(data){
			alert(data.msg);
		});
		
	});
	
	document.onkeydown=function(ev){
    var oEvent=ev||event;//获取事件对象(IE和其他浏览器不一样，这里要处理一下浏览器的兼容性event是IE；ev是chrome等)
    //Esc键的keyCode是27
    if(oEvent.keyCode==27)
    {
       //  alert('按键了');
    	window.location.href = '/herb/home';
    }
}

});

function check(string){
	var reg = /^[a-zA-Z]:(\\\\|\/)(\w+(\\\\|\/))*$/; 
	if(reg.test(string))
		return "1";
	else
		return "0";
}

function init(){
	$.post('/herb/parameterview',function(data){
		$('#text').val(data.luencetext);
		$('#scan').val(data.luencescan);
		$('#host').val(data.host);
		$("#port").val(data.port);
		$('#name').val(data.name);
		$('#email').val(data.email);
		$('#password').val(data.password);
	});	
}

function checkemail(string){
	var reg = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
	if(reg.test(string))
		return "1";
	else
		return "0";
}

function checkip(string){
	var reg = /^[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\.?$/;
	if(reg.test(string))
		return "1";
	else
		return "0";
}

function checkport(string){
	var reg = /^[1-9]\d*$/;
	if(reg.test(string))
		return "1";
	else
		return "0";
}

function checkname(string){
	var reg = /^[\u4E00-\u9FA5A-Za-z0-9_]+$/;
	if(reg.test(string))
		return "1";
	else
		return "0";
}

function checkpassword(string){
	var reg = /^[A-Za-z0-9_]+$/;
	if(reg.test(string))
		return "1";
	else
		return "0";
}
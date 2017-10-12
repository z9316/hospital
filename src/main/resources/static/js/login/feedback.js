

jQuery(function($){
	$('#submit').click(function(){
		var content = $('#content').val();
		if(content.trim() == ''){
			alert('输入不能为空');
			return ;
		}
		
		var html = '<p id="red">问：</p><p id="redcontent">'+content+'</p>';
		$('#center').append(html);
		
		$.post("/hospital/feedback",{"content":content},function(data){
		//	alert(data.msg);
			$('#center').append(data.msg);
			$('#content').val('');
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
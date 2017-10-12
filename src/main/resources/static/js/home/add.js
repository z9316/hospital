

jQuery(function($){
	
	$("#button1").click(function(){
		$("#tab1").css("display","block");
		$("#tab2").css("display","none");
	});
	$("#button2").click(function(){
		$("#tab1").css("display","none");
		$("#tab2").css("display","block");
	});
	
	$("#submit1").click(function(){
		writeadd();
	});
	
	$("#submit2").click(function(){
		uploadadd();
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

function writeadd(){
	var herbname = $("#herbname").val();
	var reg = /^[a-zA-Z0-9_\u4e00-\u9fa5]+$/;
	if(!reg.test(herbname)){
		alert('请输入汉字、数字、字母、下划线');
		return ;
	}else if(herbname.length >40){
		alert('输入的长度不能超过四十');
		return ;
	}
	var herbtext = $("#herbtext").val();
    if(herbtext.length>1000){
    	alert('输入的长度不能超过一千');
    	return ;
    }
	//alert(herbtext);
	var str= herbtext;//获取指定id的textarea值
  //  var strs= new Array(); //定义一数组 
    var content = "";
  //  strs=str.split("\n"); //字符分割 
  //  for (var i=0;i<strs.length ;i++ ) 
  //  { 
  //      content += "<p style='text-indent: 2em;'>"+strs[i]+"</p>"; //分割后的字符输出， style='text-indent: 2em;首行缩进
  //  }
    content = herbtext;
    $.post("/herb/writeadd",{"name":herbname,"text":content},function(data){
    	alert(data.msg);
    	if(data.state == "1"){
    		$("#herbname").val(herbname);
    		$("#herbtext").val(herbtext);
    	}
    });
}

function uploadadd(){
	$.ajax({
		url:"/herb/uploadadd",
		 type : "POST",  
		 cache: false,
		 data: new FormData($('#uploadForm')[0]),
		 processData: false,
		 contentType: false, 
	     success : function(data) {  
	    	 alert(data.msg);
	     }
	});
}

var totalpage = 0;
var currentpage = 0;

jQuery(function($){
	
	init();
	
$("#search").click(function(){
	//alert(12);
	var content = $("#content").val();
	if(content.trim() == ''){
		alert('输入内容不得为空！');
	}else{
	
		$.post("/herb/searchcontent",{"newcontent":content,"pagenumber":"1"},function(data){
			$('#lower2').html('');
			currentpage = 1;
			totalpage = data.matchfilecount;
			if(totalpage != 0){
			var text = '';
	        text = text + data.content;
	        $('#lower2').html(text);
			}
		}); 
	}
});

$("#lastpage").click(function(){
	var content = $("#content").val();
	currentpage = currentpage - 1;
	if(currentpage <= 0){
		currentpage = totalpage  ;
	}
	$.post("/herb/searchcontent",{"newcontent":content,"pagenumber":currentpage},function(data){
		$('#lower2').html('');
		if(totalpage != 0){
		totalpage = data.matchfilecount;
		var text = '';
        text = text + data.content;
        $('#lower2').html(text);
		}
	}); 
});

$("#nextpage").click(function(){
	var content = $("#content").val();
	currentpage = currentpage + 1;
	if(currentpage > totalpage){
		currentpage = 1  ;
	}
	$.post("/herb/searchcontent",{"newcontent":content,"pagenumber":currentpage},function(data){
		$('#lower2').html('');
		totalpage = data.matchfilecount;
		if(totalpage != 0){
		var text = '';
        text = text + data.content;
        $('#lower2').html(text);
		}
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

function init(){
	$.post('/herb/createindex',function(data){
		
	});
}
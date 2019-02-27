var _ajax = function(param){
	$.ajax({
		url : param.url,
		method : "post",
		dataType : "json",
		async : param.async ? param.async : false,
		data : param.data,
		headers:{
             "X-Requested-With":"XMLHttpRequest"
        },
		success : function(data){
			if(data.code == 110){
				go_to_login_page();
			}
			console.info("----------------------------------------------");
			console.info(param.url);
			console.info(param.data);
			console.info(data);
			param.success(data);
		},
		error : function(data){
			alert("error = " + JSON.stringify(data));
		}
	});
}

var go_to_login_page = function(){
	var url = document.location.toString();
	if(url.substring(url.length - "login.html".length,url.length) != "login.html"){
		window.top.location.href = "/static/main/login.html";
	}
}


//深度复制
var deep_clone = function(obj) {
	  var str = JSON.stringify(obj)
	  return JSON.parse(str);
	  /*var o = obj instanceof Array ? [] : {};
	  for(var k in obj)
	    o[k] = typeof obj[k] === Object ? deep_clone(obj[k]) : obj[k];
	  return o;*/
}


/**
 * 获取url 中的参数
 */
var getUrlParamfunction = function(paraName){
	var url = document.location.toString();
	var arrObj = url.split("?");

	if(arrObj.length > 1) {
		var arrPara = arrObj[1].split("&");
		var arr;
		for (var i = 0; i < arrPara.length; i++){
			arr = arrPara[i].split("=");
			if (arr != null && arr[0] == paraName){
				return arr[1];
			}
		}
		return "";
	}else{
		return "";
	}
}
//退出登录
var logout = function(){
	_ajax({
		url : controller_logout,
		success : function(result){
			if(result.code == 0){
				window.location.href = "/static/main/login.html";
			}
		}
	});
}
//获得权限
var userRole = function(){
	_ajax({
		url : controller_userRole,
		success : function(result){
			if(result.msg == "success"){

			}
		}
	});
}

/**
 * 自定义前台权限框架
 */
var Perssion = {
	data : "", 
	init : function(){
		 $.ajax({
			 url : controller_userRole,
			 method : "post",
			 dataType :"json",
			 async : false,
			 data : {},
			 success : function(result){
				 if(result.msg == "success"){
					 Perssion.data = result.data;
				 }
			 },
			 error : function(result){
				 
			 }
		 });
	 }
}
Perssion.init();   //权限初始化


var login_user = function(){
	var username = "";
	_ajax({
		url : controller_loginUser,
		async : false,
		success : function(result){
			if(result.code == 0){
				username = result.data;
			}
		}
	});
	return username;
}



//延迟显示，自执行（加载遮罩层），
var delayShow = function(){
	$("body").css("display","block");
}();

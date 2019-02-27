//propertygrid 属性
var propertygrid_config = {"total":4,
						"rows":[
                             {_name :  "color","name":"颜色","value":"黑色","group":"属性设置","editor":{
                           	  type : "combobox",
                           	  options:{
                                     "data":[{"value":"黑色","text":"黑色"},
                                             {"value":"红色","text":"红色"},
                                             {"value":"蓝色","text":"蓝色"}]
                                 }
                             }},
                             {_name :  "font_size","name":"字体大小","value":"12px","group":"属性设置","editor":{
                           	  type : "combobox",
                           	  options:{
                                     "data":[{"value":"12px","text":"12px"},
                                             {"value":"14px","text":"14px"},
                                             {"value":"16px","text":"16px"},
                                             {"value":"18px","text":"18px"},
                                             {"value":"20px","text":"20px"},
                                             {"value":"22px","text":"22px"},
                                             {"value":"24px","text":"24px"},
                                             {"value":"26px","text":"26px"},
                                             {"value":"28px","text":"28px"},
                                             {"value":"30px","text":"30px"}]
                                 }
                             }},
                             {_name :  "align","name":"对齐方式","value":"左对齐","group":"属性设置","editor":{
                           	  type : "combobox",
                           	  options:{
                                     "data":[{"value":"左对齐","text":"左对齐"},
                                             {"value":"右对齐","text":"右对齐"},
                                             {"value":"居中","text":"居中"}]
                                 }
                             }},
                             {_name :  "width","name":"宽(像素)","value":"300","group":"属性设置","editor": "text"},
                             {_name :  "height","name":"高(像素)","value":"150","group":"属性设置","editor":"text"},
                             {_name :  "font_count","name":"字数","value":"100","group":"属性设置","editor":"text"}
                         ]};
var copy_propertygrid_config = function(config){
	return deep_clone(propertygrid_config);
} 
	

// 元素类别 数组
var add_element_type_config =[{
			tab_type: "title_1",
		    text : "正标题",
		    show_type : "one",
		    tab_id : 1,
		    config : {
		    	color : "黑色",
		    	font_size : "25px",
		    	align : "居中",
		    	width : "300",
		    	height : "",
		    	font_count  : "100"
		    },
		    //config : ["黑色","25px","居中","300","","100"],
		},{
			tab_type : "title_2",
		    text : "副标题",
		    show_type : "one",
		    tab_id : 2,
		    config : {
		    	color : "黑色",
		    	font_size : "20px",
		    	align : "居中",
		    	width : "300",
		    	height : "",
		    	font_count  : "100"
		    },
		   // config : ["黑色","20px","居中","300","","100"]
		},{
			tab_type : "title_3",
		    text:"小标题",
		    show_type : "one",
		    tab_id : 3,
		    config : {
		    	color : "黑色",
		    	font_size : "12px",
		    	align : "左对齐",
		    	width : "300",
		    	height : "",
		    	font_count  : "100"
		    }
		    //config : ["黑色","12px","左对齐","300","","100"]
		},{
			tab_type:"input",
		    text :"输入框",
		    show_type : "one",
		    tab_id : 4,
		    config : {
		    	color : "黑色",
		    	font_size : "12px",
		    	align : "左对齐",
		    	width : "300",
		    	height : "",
		    	font_count  : "100"
		    }
		   // config : ["黑色","12px","左对齐","300","","100"]
		},{
			tab_type :"radio",
		    text:"单选框",
		    show_type : "two",
		    tab_id : 5,
		    config : {
		    	color : "黑色",
		    	font_size : "12px",
		    	align : "左对齐",
		    	width : "300",
		    	height : "",
		    	font_count  : "100"
		    }
		    //config : ["黑色","12px","左对齐","300","","100"]
		},{
			tab_type : "checkbox",
		    text :"多选框",
		    show_type : "two",
		    tab_id : 6,
		    config : {
		    	color : "黑色",
		    	font_size : "12px",
		    	align : "左对齐",
		    	width : "300",
		    	height : "",
		    	font_count  : "100"
		    }
		    //config : ["黑色","12px","左对齐","300","","100"]
		},{
			tab_type : "textarea",
		    text : "超文本",
		    show_type : "one",
		    tab_id : 7,
		    config : {
		    	color : "黑色",
		    	font_size : "12px",
		    	align : "左对齐",
		    	width : "300",
		    	height : "100",
		    	font_count  : "1000"
		    }
		    //config : ["黑色","12px","左对齐","300","","100"]
		},{
			tab_type : "combobox",
		    text : "下拉框",
		    show_type  : "two",
		    tab_id : 8,
		    config : {
		    	color : "黑色",
		    	font_size : "12px",
		    	align : "左对齐",
		    	width : "300",
		    	height : "",
		    	font_count  : "100"
		    }
		   // config : ["黑色","12px","左对齐","300","","100"]
		},{
			tab_type:"date",
		    text:"日期",
		    show_type : "three",
		    tab_id : 9,
		    config : {
		    	color : "黑色",
		    	font_size : "12px",
		    	align : "左对齐",
		    	width : "300",
		    	height : "",
		    	font_count  : "100"
		    }
		   //config : ["黑色","12px","左对齐","300","","100"]
		}];

//获取element_type 对象
var get_element_type = function(tab_type){
	for(var i = 0 ; i < add_element_type_config.length;i++){
		var type = add_element_type_config[i];
		if(type["tab_type"] == tab_type){
			return type
		}
	}
	return null;
}

Parser = {};
Parser.tempaltes = {
	"title_1" : "<span tab_type='$tab_type' id='diy_$elmt_id' style='font-size:$font_size; width:100%;color:$color;'>$elmt_name:</span>",
	"title_2" : "<span tab_type='$tab_type' id='diy_$elmt_id' style='font-size:$font_size; width:100%;color:$color'>$elmt_name:</span>",
	"title_3" : "<span tab_type='$tab_type' id='diy_$elmt_id' style='font-size:$font_size; width:100%;color:$color'>$elmt_name:</span>",
	"input"  : '<input tab_type="$tab_type" id="diy_$elmt_id" name="" 	class="easyui-textbox" label="$elmt_name:"'
					+'data-options="required:false" '
					+ ' style="width: 100px;height:25px;color:$color;width:$width;height:$height;">',
	"radio" : "",
	"checkbox" : "",
	"textarea" : '<input id="diy_$elmt_id" tab_type="$tab_type" label="$elmt_name:"  name="" style="width:$width;height:$height;" '
				+'class="easyui-textbox"  data-options="multiline:true">',
	"combobox" : "<input id='diy_$elmt_id' tab_type='$tab_type' label='$elmt_name:' class='easyui-combobox' name=''" +
				"data-options=\"valueField:'id',textField:'text',url:''\" style='width:$width;height:$height;'>",
	"date"	   : "<input id='diy_$elmt_id' tab_type='$tab_type' label='$elmt_name:' type='text' class='easyui-datebox' style='width:$width;height:$height;'>"
};

/*data = {
 *  element_type : 
	parentObj : "",
	config : {}
}
*/
Parser.add = function(data){
	Parser.parse(data);
}
Parser.parse = function(data){
		var template = Parser.tempaltes[data.tab_type];
		var config = Parser._build_propertygrid_data(data.config);
		template = template.replace("$font_size",config.font_size);
		template = template.replace("$color",config.color);
		template = template.replace("$width",config.width);
		template = template.replace("$elmt_name",data.elmt_name);
		template = template.replace("$elmt_id",data.elmt_id);
		template = template.replace("$tab_type",data.tab_type);
		
		var parent_obj = $(data.parent_obj);
		parent_obj.css("text-align",config.align)
		
		switch(data.tab_type){
		case "radio":
			template = "<div id='diy_" + data.elmt_id + "'  tab_type='" + data.tab_type + "'>";
			var str = data.element_data;
			var ds = str.split("|");
			var group = "";
			for(var i = 0 ; i < ds.length;i++){
				group += ds[i] + "_";
			}
			group = group.substring(0,group.length - 1);
			template += data.elmt_name + " : ";
			for(var i = 0 ;i < ds.length;i++){
				template +=  "<input type='radio' value='"+ ds[i] +  "' name='" + group + "'><span>" + ds[i] + " </span>";
			}
			template += "</div>";
			break;
		case "checkbox":
			var str = data.element_data;
			var ds = str.split("|");
			template = "<div id='diy_$elmt_id' tab_type='$tab_type'>$elmt_name : "
			for(var i = 0 ; i < ds.length;i++){
				template += "<input type='checkbox' value='" + ds[i] + "'>" +  ds[i];
			}
			template += "</div>"
			template = template.replace("$elmt_name",data.elmt_name);
			template = template.replace("$elmt_id",data.elmt_id);
			template = template.replace("$tab_type",data.tab_type);
			break;
		case "textarea":
			template = template.replace("$height",config.height);
			break;
		default : 
		}
		
		parent_obj.html("");
		var targetObj = $(template).appendTo(parent_obj);
		if(data.tab_type == "combobox"){
			//var sql = config.sql;
			var str = data.element_data;
			var ds = str.split("|");
			var data = [];
			for(var i = 0 ; i < ds.length;i++){
				data[i] = {
					"id" : ds[i],
					"text" : ds[i]
				};
			}
			$(targetObj).combobox({
				data : data
			});
		}
		parent_obj.attr("font_count",config.font_count)
		$.parser.parse();
}

Parser.exesql = function(){
	
}

Parser.get_color = function(field){
	var color = "black";
	switch(field){
	case "黑色":
		color = "black";
		break;
	case "红色":
		color = "red";
		break;
	case "蓝色":
		color = "blue";
		break;
	}
	return color;
}
Parser.get_align = function(field){
	var align = "left";
	switch(field){
	case "左对齐":
		align = "left";
		break;
	case "右对齐":
		align = "right";
		break;
	case "居中":
		align = "center";
		break;
	}
	return align;
}
Parser._build_propertygrid_data = function(config){
	var result = {};
	result.color = Parser.get_color(config.color);
	result.font_size = config.font_size;
	result.align = Parser.get_align(config.align);
	result.width = config.width + "px";
	if(config.height){
		result.height = config.height + "px";
	}
	result.font_count = config.font_count;
	return result;
}
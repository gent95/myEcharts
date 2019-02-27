Parser = {};
var title_template = "<span  style='font-size:$font_size; width:100%;color:$color;' tab_type='$tab_type' id='diy_$elmt_id' >$elmt_name</span>";
var input_template = "<span  style='font-size:$font_size; width:100%;color:$color;'>$elmt_name:</span>" +
											"<span class='inputval' tab_type='$tab_type' id='diy_$elmt_id'></span>";
Parser.tempaltes = {
	"title_1" : title_template,
	"title_2" : title_template,
	"title_3" : title_template,
	"input"  : input_template,
	"radio" : "",
	"checkbox" : "",
	"textarea" : input_template,
	"combobox" : input_template,
	"date"	   : input_template,
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
		var parent_obj = $(data.parent_obj);
		parent_obj.css("text-align",config.align)

		var tab_type = data.tab_type;
		if(tab_type == "checkbox" || tab_type == "radio"){
            var str = data.element_data;
            var ds = str.split("|");
            template = '<span class="lable">' + data.elmt_name + '</span>:<span class="checkboxs" id="diy_$elmt_id" tab_type="$tab_type">';
            for(var i = 0 ; i < ds.length;i++){
                template += '<span class="checkboxitem" ><input type="checkbox" value="' + ds[i] + '">' + ds[i] + '</span>';
            }
            template += "</span>";
		}else if(tab_type == "textarea"){
            template = template.replace("$height",config.height);
		}

		template = template.replace("$font_size",config.font_size);
		template = template.replace("$color",config.color);
		template = template.replace("$width",config.width);
		template = template.replace("$elmt_name",data.elmt_name);
		template = template.replace("$elmt_id",data.elmt_id);
		template = template.replace("$tab_type",data.tab_type);

		parent_obj.html("");
		var targetObj = $(template).appendTo(parent_obj);
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
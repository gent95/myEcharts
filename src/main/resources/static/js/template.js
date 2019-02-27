
//重新计算选中行的 colspan
var _calculate_td = function(tr){
	var tds = tr.children("td");
	var count = tds.length;
	tds.attr("colspan",12 / count);
}

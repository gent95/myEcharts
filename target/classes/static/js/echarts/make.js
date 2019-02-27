var opt_id = 0;
var editor_option,editor_sql,editor_result,editor_script;
$(function () {
    $("#dg_option").datagrid({
        onSelect:function (index,row) {
            opt_id = row.id;
            $("#dg_sql").datagrid({
                url:"/sql/list",
                queryParams:{opt_id:opt_id}
            })
        }
    });
    editor_sql = editor("e_sql","text/x-plsql");
    editor_option = editor("option_json","application/ld+json");
    editor_result = editor("result","application/ld+json");
    editor_script = editor("option_script","application/ld+json")
    newOption();
    $("#eventJs").combobox({
        onSelect:function (record) {
            eventSelect(record.value,record.text);
        }
    });
});

//编辑Option
function editOption() {
    $('#tt').tabs('select', "面板");
    var row = $("#dg_option").datagrid("getSelected");
    $.post("/option/info",row,function (result) {
        if (result.code == 0){
            $('#fm_option').form("load",row);
            editor_option.setValue("");
            var option = $.parseJSON(result.data.option_json);

            if (null != option.xAxis[0]){
                option.xAxis[0].data = null;
            }

            for (var i = 0; i < option.series.length; i++) {
                if (option.series[i] != null){
                    option.series[i].data = null;
                }
            }
            editor_option.setValue(JSON.stringify(option));
            editor_script.setValue(result.data.option_script);
            jsonSelect(result.data.option_json);
            if (row.y_type == 0){
                $("#y_type").prop("checked","checked");
            }else {
                $("#y_type").removeAttr("checked");
            }
        }else {
            $.messager.alert("提醒",result.msg,"warning");
        }
    });
}

//添加Option
function newOption() {
    $('#tt').tabs('select', "面板");
    $('#fm_option').form("clear");
    editor_option.setValue("");
    editor_script.setValue("");
}

//删除option
function removeOption() {
    $.messager.confirm('确定', '确定删除?', function (r) {
        if (r){
            var row = $("#dg_option").datagrid("getSelected");
            $.post("/option/delete",row,function (result) {
                if (result.code == 0 && result.data){
                    $("#dg_option").datagrid("reload");
                }
            });
        }
    });
}

//预览option
function lookOption() {
    $('#tt').tabs('select', "预览");
    load("正在加载数据...");
    var row = $("#dg_option").datagrid("getSelected");
    $.post("/option/optionJson",row, function (result) {
        $("#look").html("");
        if (result.code == 0){
            disLoad();
            var urlE = 'http://'+location.host+'/static/echarts/look.html?id='+ result.id;
            $("#look").html(
                "<div id='myChart' style='width: 100%;height:380px;'></div> " +
                "<h4><span>提取路径:</span>" +urlE+
                "</h4>"
            );
            var myChart = echarts.init(document.getElementById('myChart'));
            var option = result.data;
            myChart.setOption(option);
            window.onresize = myChart.resize;
        }else {
            disLoad();
            $.messager.alert("提醒",result.msg,"warning");
        }
    });
}

//打开预览窗口
function getGraph(url) {
    alert(url);
}

//json单项配置
var opt;
function jsonSelect(optionV) {
    opt = eval('(' + optionV + ')');
    $("#optionSelect").combobox({
        onSelect:function (record) {
            if (record.id){
                if (record.id == "all"){
                    editor_option.setValue(optionV);
                } else {
                    editor_option.setValue(JSON.stringify(eval('opt.'+record.id)));
                }
            }
        }
    });
}

//点击确认按钮时,将option属性值改入
function option_ok() {
    var param = $("#optionSelect").combobox("getValue");
    eval("opt."+param+" =$.parseJSON(editor_option.getValue())");
    editor_option.setValue(JSON.stringify(opt));
}

//一键重置
function reset() {
    if (option().id == null || option().id == 0 || !option()){
        $.messager.alert("请选中一条记录");
    }
    $.post("/option/reset",{id:option().id},function (result) {
        if (result.code == 0){
            editor_option.setValue(result.data.option_json);
        } else {
            $.messager.alert(result.msg);
        }
    });
}
//启用option
function onOption() {
    var row = $("#dg_option").datagrid("getSelected");
    row.status = 1;
    $.post("/option/update",row,function (result) {
        if (result.code == 0 && result.data){
            $("#dg_option").datagrid("reload");
        }
    });
}

//禁用option
function offOption() {
    var row = $("#dg_option").datagrid("getSelected");
    row.status = 0;
    $.post("/option/update",row,function (result) {
        if (result.code == 0 && result.data){
            $("#dg_option").datagrid("reload");
        }
    });
}

var option = function() {
    return{
        id:$("#option_id").numberbox("getValue"),
        name:$("#option_name").textbox('getValue'),
        remark:$("#option_remark").textbox('getValue'),
        option_json:editor_option.getValue(),
        option_script:editor_script.getValue(),
        y_type:$("#y_type").is(':checked')?0:1
    }
}

//保存Option
function saveOption() {
    $.post("/option/insert",option(),function (result) {
        if (result.code == 0 && result.data){
            $.messager.alert("消息",result.msg,"info");
            $("#dg_option").datagrid("reload");
        }else {
            $.messager.alert("提醒",result.msg);
        }
    });
}

//状态格式化
function statusFmt(val) {
    if (val == 1) {
        return "<span style='color: #00ee00'>启用</span>";
    } else {
        return "<span style='color: red'>禁用</span>";
    }
}


//编辑sql
function editSql() {
    selectPieType();
    $('#tt').tabs('select', "数据");
    var row = $("#dg_sql").datagrid("getSelected");
    $('#fm_sql').form("load",row);
    $.post("/sql/info",row,function (result) {
        if (result.code == 0){
            $("#pie_select").combobox("setValue",result.data.pie_select);
            $("#in_radius").numberbox("setValue",result.data.in_radius);
            $("#out_radius").numberbox("setValue",result.data.out_radius);
            $("#all_radius").numberbox("setValue",result.data.all_radius);
        }
    });
    editor_sql.setValue("\n"+row.sql);
    editor_result.setValue("");
}

//格式化半径列
function radiusFmt(val) {
    if (val == null || val == "" || !val){
        return "*";
    }
    return val;
}

//添加一个sql
function newSql() {
    selectPieType();
    $('#tt').tabs('select', "数据");
    $('#fm_sql').form("clear");
    editor_sql.setValue("");
    editor_result.setValue("");
}

//选择饼图类型
function selectPieType() {
    $("#sql_type").combobox({
        onSelect:function (record) {
            if (record.value == "pie"){
                $("#pie_box").show();
            } else {
                $("#pie_box").hide();
            }

            if (record.value == 'yAis'){
                $("#yType").show();
            } else {
                $("#yType").hide();
            }
        }
    });
    $("#pie_select").combobox({
        onSelect:function (record) {
            if (record.value == "full"){
                $("#full").show();
                $("#empty").hide();
            } else {
                $("#full").hide();
                $("#empty").show();
            }
        }
    });
}

//删除一个Sql
function removeSql() {
    $.messager.confirm('确定', '确定删除?', function (r) {
        if (r){
            var row = $("#dg_sql").datagrid("getSelected");
            $.post("/sql/delete",row,function (result) {
                if (result.code == 0 && result.data){
                    $("#dg_sql").datagrid("reload");
                }
            });
        }
    });
}

//启用sql
function onSql(){
    var row = $("#dg_sql").datagrid("getSelected");
    row.status = 1;
    $.post("/sql/update",row,function (result) {
        if (result.code == 0 && result.data){
            $("#dg_sql").datagrid("reload");
        }
    });
}

//禁用sql
function offSql() {
    var row = $("#dg_sql").datagrid("getSelected");
    row.status = 0;
    $.post("/sql/update",row,function (result) {
        if (result.code == 0 && result.data){
            $("#dg_sql").datagrid("reload");
        }
    });
}

var sql = function () {
    var radius;
    var type = $("#sql_type").combobox("getValue");
    if (type == "pie"){
        var pie_type = $("#pie_select").combobox("getValue");
        if (pie_type == "full"){
            radius = $("#all_radius").numberbox("getValue");
        } else if (pie_type == "empty"){
            radius = $("#in_radius").numberbox("getValue")+","+$("#out_radius").numberbox("getValue");
        } else {
            radius = "";
        }
    }
    return {
        id:$("#sql_id").numberbox("getValue"),
        name:$("#sql_name").textbox("getValue"),
        type:$("#sql_type").combobox("getValue"),
        sql:editor_sql.getValue(),
        opt_id:opt_id,
        sort:$("#sql_sort").numberbox("getValue"),
        radius:radius,
        sql_param:$("#sql_param").textbox("getValue")
    }
}

//保存sql
function saveSql() {
    $.post("/sql/insert",sql(),function (result) {
        if (result.code == 0 && result.data){
            $("#dg_sql").datagrid("reload");
        }else {
            $.messager.alert("提醒",result.msg);
        }
    });
}

//执行sql
function executeSql() {
    var sql = editor_sql.getValue();
    var sql_param = $("#sql_param").textbox("getValue");
    var sqlparams = sql_param.split("&");
    for (var i = 0; i <sqlparams.length ; i++) {
        if (sql.indexOf("@"+sqlparams[i].split("=")[0]) != -1) {
            sql = sql.replace("@"+sqlparams[i].split("=")[0],"\'"+sqlparams[i].split("=")[1]+"\'");
        }
    }
    console.log(sql);
    $.post("/sql/execute",{sql:sql},function (result) {
        if (result.code == 0){
            editor_result.setValue("\t"+result.data);
        } else {
            editor_result.setValue("\t"+result.msg);
        }
    });
}

//图表类型格式话
function typeFmt(val) {
    if (val == "line"){
        return "折线图";
    } else if (val == "pie"){
        return "饼图";
    } else if (val == "bar"){
        return "柱状图";
    } else if (val == "xAis"){
        return "x轴";
    } else if (val == "yAis"){
        return "y轴";
    } else {
        return val;
    }
}

//图表事件触发
function eventSelect(val,text) {
    var fun_str = "myChart.on('"+val+"',[series.line](此参数可省略),function () {alert('"+text+"')});";
    editor_script.setValue(fun_str);
}

//弹出加载层
function load(tip) {
    $("<div class=\"datagrid-mask\"></div>").css({
        display: "block",
        width: "100%",
        height: $(window).height()
    }).appendTo("body");
    $("<div class=\"datagrid-mask-msg\"></div>").html(tip).appendTo("body").css({
        display: "block",
        left: ($(document.body).outerWidth(true) - 190) / 2,
        top: ($(window).height() - 45) / 2
    });
}

//取消加载层
function disLoad() {
    $(".datagrid-mask").remove();
    $(".datagrid-mask-msg").remove();
}

//初始化配置项
// function jsonSelect(json) {
//     var obj = eval(json);
//     var keys =[];
//     for (var prop in obj) {
//       alert(prop)
//     }
//     // console.log(keys);
// }

/**
 * 初始化codeMirror编辑器
 * @param dom
 * @param code
 */
var editor = function (dom,code){
    //根据DOM元素的id构造出一个编辑器
    return CodeMirror.fromTextArea(document.getElementById(dom), {
        //实现Java代码高亮
        mode: code,
        //设置显示行号
        lineNumbers: true,
        //设置主题
        theme: "dracula",
        //在长行时文字是换行(wrap)还是滚动(scroll)，默认为滚动(scroll)
        lineWrapping: "wrap",
        //在选择时是否显示光标，默认为false
        showCursorWhenSelecting: true,
        //光标高度。默认为1
        cursorHeight: 0.85,
        //代码折叠
        lineWrapping: true,
        foldGutter: true,
        gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"],
        // //全屏模式
        fullScreen: false,
        //括号匹配
        matchBrackets: true,
        styleActiveLine: true,
        continueComments: "Enter",
        //智能缩进
        smartIndent : true,
        //智能提示
        extraKeys: {
            "Alt-/": "autocomplete",
            "F11": function (cm) {
                cm.setOption("fullScreen", !cm.getOption("fullScreen"));
            },
            "F7": function autoFormat(editor) {
                var totalLines = editor.lineCount();
                editor.autoFormatRange({line:0, ch:0}, {line:totalLines});
            }
        }
    });
}
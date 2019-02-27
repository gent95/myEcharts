var rows = []; //option列表
var comments = {}; //配置项
var col = []; //配置项名称
var editor_script, editor_option, editor_sql, editor_element, editor_sqldata; //script编辑器
var sqlParamId = null;
var seriesBodyId;
$(function () {
    _init();
    $("#sql_param").textbox("setText", "");
    $.post("/now/echtoption/comments", function (result) {
        if (result.code == 0) {
            comments = result.rows;
            for (var y in comments) {
                col.push(y);
            }
        }
    });
    editor_script = editor("e_series", "application/ld+json");
    editor_option = editor("e_option", "application/ld+json");
    editor_sql = editor("e_sql", "application/ld+json");
    editor_element = editor("e_element", "application/ld+json");
    editor_sqldata = editor("e_sqldata", "application/ld+json");
    $("#fm_title").form("clear");
});

function _init() {
    $.post("/now/echtoption/list", function (result) {
        if (result.code == 0) {
            rows = result.rows;
            init_dg_title();
        } else {
            $.messager.alert("提醒", "数据获取失败");
        }
    });
}

/**
 * 初始化series_sql下拉框
 */
function init_series_sql() {
    var _row = $("#dg_title").datagrid("getSelected");
    $.post("/now/echtsql/list1", {optId: _row.id}, function (result) {
        if (result.code == 0) {
            console.log("sql列表", result.rows);
            $("#series_sql").combobox({
                data: result.rows,
                valueField: "id",
                textField: "describe",
                onSelect: function (record) {
                    $.post("/now/echtsql/getFields", {id: record.id}, function (result) {
                        if (result.code == 0) {
                            console.log("result.data", result.data);
                            $("#series_field").combobox({
                                data: result.data,
                                valueField: "value",
                                textField: "text"
                            });
                        }
                    });
                }
            });
        }
    });
}

/**
 * 添加一个series
 */
function newSeries() {
    var _row = $("#dg_title").datagrid("getSelected");
    if (!_row) {
        $.messager.alert("提醒", "请选中左侧面板中的一条记录", "warning");
        return;
    }
    $("#dlg_series").dialog("open");
    $("#fm_series").form("clear");
    editor_element.setValue("-");
    init_series_sql();
    var comboboxs = [];
    for (var x in config_series) {
        var combobox = {};
        combobox.value = x;
        combobox.name = x;
        comboboxs.push(combobox);
    }
    $("#series_type").combobox({
        data: comboboxs,
        valueField: "value",
        textField: "name",
        onSelect: function (record) {
            editor_element.setValue("-");
            var griphs = ["line", "pie", "bar", "xAxis", "yAxis"];
            var griphs1 = griphs;
            if (griphs.indexOf(record.value) != -1) {
                //先隐藏所有选项
                for (var i = 0; i < griphs.length; i++) {
                    //初始化元素选项配置项
                    $("#" + griphs[i] + "_style").combobox({
                        data: eval("config_series." + griphs[i]),
                        valueField: "value",
                        textField: "text",
                        onSelect: function (record) {
                            editor_element.setValue("-");
                            editor_element.setValue("\t" + JSON.stringify(record.code))
                        }
                    });
                    //隐藏未选中的配置项
                    $('#' + griphs1[i]).hide();
                }
                //显示当前选中项的配置项
                $('#' + record.value).show();
                //将显示项从隐藏数组中剔除
                griphs1.splice(record.value);
            } else {
                //隐藏未选中项的配置项
                for (var i = 0; i < griphs1.length; i++) {
                    $('#' + griphs1[i]).hide();
                }
            }
        }
    });
}

/**
 * 修改series
 */
function editSeries() {
    var series = $("#dg_series").datagrid("getSelected");
    console.log("series", series)
    if (series) {
        newSeries();
        $("#dlg_series").dialog("open");
        var type = "";
        if (series.type == "value" || series.type == "category" || series.type == "log" || series.type == "time") {
            console.log(series.description)
            if (series.description == "x轴") {
                type = "xAxis";
            } else if (series.description == "y轴") {
                type = "yAxis";
            }
        } else {
            type = series.type;
        }
        console.log("type", type)
        $("#series_type").combobox("setValue", type);
        $("#series_type").combobox("setText", type);
        $("#series_name").textbox("setValue", series.name);
        seriesBodyId = series.id;
        console.log("seriesBodyId",seriesBodyId);
        var code = editor_script.getValue();
        editor_element.setValue(code);
        var id = $.parseJSON(code).id;
        $.post("/now/echtsqlelmt/info/elmtid", {elmtId: id}, function (result) {
            if (result.code == 0) {
                $("#series_sql").combobox("setValue", result.info.sqlid);
                var fields = [];
                if (result.info.field.indexOf(",")) {
                    fields = result.info.field.split(",");
                } else {
                    fields.push(result.info.field);
                }
                setTimeout(function () {
                    $("#series_field").combobox("setValues", fields);
                },500);
            }
        });
    } else {
        $.messager.alert("提醒", "请选中一条记录!", "info");
    }
}

/**
 * 判断元素对象是否执行更新操作
 */
function checkIsUpdate(_arr, _obj) {
    try {
        var length = _arr.length;
        for (var i = 0; i < length; i++) {
            if (_arr[i].id == _obj.id) {
                if (i == 0) {
                    _arr.shift(); //删除并返回数组的第一个元素
                }
                else if (i == length - 1) {
                    _arr.pop();  //删除并返回数组的最后一个元素
                }
                else {
                    _arr.splice(i, 1); //删除下标为i的元素
                }
            }
        }
    }catch (e) {
        return _arr;
    }
    return _arr;
}

/**
 * 保存series
 */
function series_save() {
    var _row = $("#dg_title").datagrid("getSelected");
    var type = $("#series_type").combobox("getValue");
    $.post("/now/echtoption/info", {id: _row.id}, function (result) {
        if (result.code == 0) {
            var elmt = $.parseJSON(editor_element.getValue());
            elmt.name = $("#series_name").textbox("getValue");
            elmt.id = seriesBodyId;
            if (!elmt.id) {
                elmt.id = Date.parse(new Date()) + type + elmt.name;
            }
            console.log("elmt",elmt);
            var obj = {id: _row.id};
            var sort;
            switch (type) {
                case "xAxis":
                    var xAxis = eval('(' + result.info.xAxis + ')');
                    if (null == xAxis) {
                        xAxis = [];
                    }
                    xAxis = checkIsUpdate(xAxis, elmt);
                    xAxis.push(elmt);
                    sort = xAxis.length - 1;
                    obj.xAxis = JSON.stringify(xAxis);
                    break;
                case "yAxis":
                    var yAxis = eval('(' + result.info.yAxis + ')');
                    if (null == yAxis) {
                        yAxis = [];
                    }
                    yAxis = checkIsUpdate(yAxis, elmt);
                    yAxis.push(elmt);
                    sort = yAxis.length - 1;
                    obj.yAxis = JSON.stringify(yAxis);
                    break;
                default :
                    var series = eval('(' + result.info.series + ')');
                    if (null == series) {
                        series = [];
                    }
                    console.log("series检查", series);
                    series = checkIsUpdate(series, elmt);
                    series.push(elmt);
                    sort = series.length - 1;
                    obj.series = JSON.stringify(series);
                    break;
            }
            option_update(obj);
            var sql = {
                type: type,
                optid: _row.id,
                sqlid: $("#series_sql").combobox("getValue"),
                sort: sort,
                elmtId: elmt.id,
                field: $("#series_field").combobox("getText")
            };
            console.log("sql对象", sql);
            elmt_sql_add(sql);
        }
    });
}

/**
 * 更新option对象
 */
function option_update(obj) {
    $.post("/now/echtoption/saveObject", obj, function (result) {
        if (result.code == 0) {
            reflash(obj.id);
            _init();
            $("#dlg_series").dialog("close");
            $("#dlg_option").dialog("close");
            $.messager.alert("消息", "操作成功", "info");
        } else {
            $.messager.alert("消息", result.msg, "warning");
        }
    });
}

/**
 * 绑定图标与sql关系
 */
function elmt_sql_add(obj) {
    $.post("/now/echtsqlelmt/save", obj, function (result) {
        if (result.code == 0) {
            console.log("sql_save", "sql保存成功")
        }
    });
}

/**
 * 查询当前选中对象的信息
 * @returns {*}
 */
function reflash(id) {
    $.post("/now/echtoption/info", {id: id}, function (result) {
        if (result.code == 0) {
            init_dg_option(result.info)
        }
    });
}

/**
 * 删除series
 */
function removeSeries() {
    var selectRow = $("#dg_series").datagrid("getSelected");
    var _row = $("#dg_title").datagrid("getSelected");
    if (!selectRow && !_row) {
        $.messager.alert("提醒", "请选中一条记录", "info");
        return;
    }
    var s = $.parseJSON(editor_script.getValue());
    if (selectRow.type == "value" || selectRow.type == "category" || selectRow.type == "log" || selectRow.type == "time") {
        if (selectRow.description == "x轴") {
            selectRow.type = "xAxis";
        } else if (selectRow.description == "y轴") {
            selectRow.type = "yAxis";
        }
    } else {
        selectRow.type = "series";
    }

    $.post("/now/echtoption/selectColumn", {id: _row.id, column: selectRow.type}, function (result) {
        if (result.code == 0) {
            var obj = {id: _row.id};
            switch (selectRow.type) {
                case "xAxis":
                    var xAxis = eval('(' + result.rows.xAxis + ')');
                    for (var i = 0; i < xAxis.length; i++) {
                        if (xAxis[i].id == s.id) {
                            xAxis.splice(i, 1);
                            obj.xAxis = JSON.stringify(xAxis);
                            removeSqlElmt(s.id);
                        }
                    }
                    break;
                case "yAxis":
                    var yAxis = eval('(' + result.rows.yAxis + ')');
                    for (var j = 0; j < yAxis.length; j++) {
                        if (yAxis[j].id == s.id) {
                            yAxis.splice(j, 1);
                            obj.yAxis = JSON.stringify(yAxis);
                            removeSqlElmt(s.id);
                        }
                    }
                    break;
                default:
                    var series = eval('(' + result.rows.series + ')');
                    for (var k = 0; k < series.length; k++) {
                        if (series[k].id == s.id) {
                            series.splice(k, 1);
                            obj.series = JSON.stringify(series);
                            removeSqlElmt(s.id);
                        }
                    }
                    break;
            }
            option_update(obj);
            reflash(_row.id);
        }
    });
}

//删除sql元素关系
function removeSqlElmt(elmtId) {
    $.post("/now/echtsqlelmt/delete",{elmtId:elmtId},function (result) {
        if (result.code = 0){
            console.log("elmtId:"+elmtId,result.msg);
        }
    });
}

/**
 * 保存sql
 */
function sql_save() {
    var _row = $("#dg_title").datagrid("getSelected");
    if (!_row) {
        $.messager.alert("消息", "请选中一条记录", "info");
    }
    var sql = {
        id: $("#sql_id").numberbox("getValue"),
        describe: $("#describe").textbox('getValue'),
        // param:$("#param").textbox('getValue'),
        code: editor_sql.getValue(),
        optId: _row.id
    };

    $.post("/now/echtsql/insert", sql, function (result) {
        if (result.code == 0) {
            $.messager.alert("提醒", result.msg, "info");
            init_dg_sql(_row.id);
        } else {
            $.messager.alert("提醒", result.msg, "warning");
        }
    });
}


/**
 * 添加sql
 */
function newSQL() {
    $("#dlg_sql").dialog("open");
    $("#fm_sql").form("clear");
    editor_sql.setValue(" ");
    init_dg_sqlparam();
}

/**
 * 初始化sql参数表格
 */
function init_dg_sqlparam() {
    var param = $("#sql_param").textbox("getText");
    var rows = [];
    if (param != null && param && param != "") {
        var fields = param.split(",");
        for (var i = 0; i < fields.length; i++) {
            var row = {field: "", value: ""};
            row.field = fields[i];
            rows.push(row);
        }
    }
    $("#dg").datagrid("loadData", {rows: rows});
}

/**
 * 预览执行sql
 */
var sql_code;

function sql_look() {
    var sql = editor_sql.getValue();
    sql_code = sql;
    var param = $("#dg").datagrid("getChanges");
    for (var i = 0; i < param.length; i++) {
        sql = sql.replace(":" + param[i].field, param[i].value);
    }
    editor_sql.setValue(sql);
}

/**
 * 还原sql
 */
function re_sql() {
    editor_sql.setValue(sql_code);
}

/**
 * 执行
 */
function go_sql() {
    var sql = editor_sql.getValue();
    $.post("/now/echtsql/goSQL", {sql: sql}, function (result) {
        if (result.code == 0) {
            $("#dlg_sqlData").dialog("open");
            editor_sqldata.setValue(JSON.stringify(result.data));
        } else {
            alert("执行失败");
        }
    });
}

/**
 * 修改sql
 */
function editSQL() {
    var _sql = $("#dg_sql").datagrid("getSelected");
    console.log("选中的sql", _sql);
    if (_sql) {
        newSQL();
        $("#fm_sql").form("load", _sql);
        editor_sql.setValue("\t" + _sql.code);
    } else {
        $.messager.alert("提醒", "请选中一条记录!", "info");
    }
}

/**
 * 删除sql
 */
function removeSQL() {
    var _sql = $("#dg_sql").datagrid("getSelected");
    var _row = $("#dg_title").datagrid("getSelected");
    if (_sql) {
        $.post("/now/echtsql/update", {id: _sql.id, isDelete: 1}, function (result) {
            if (result.code == 0) {
                $.messager.alert("提醒", "删除成功!", "info");
                init_dg_sql(_row);
            } else {
                $.messager.alert("提醒", "删除失败!", "info");
            }
        });
    } else {
        $.messager.alert("提醒", "请选中一条记录!", "info");
    }
}

/**
 * 初始化左侧标题列表导航
 */
function init_dg_title() {
    var titles = [];
    for (var i = 0; i < rows.length; i++) {
        var row = {
            id: null,
            text: null,
            subtext: null
        };
        var obj = rows[i];
        row.id = obj.id;
        var title = eval('(' + obj.title + ')');
        row.text = title.text;
        row.subtext = title.subtext;
        row.hrefId = obj.hrefId
        titles.push(row)
    }
    $("#dg_title").datagrid({
        data: titles,
        onSelect: function (index, row) {
            $("#sql_param").textbox("setText", "");
            $("#fm_title").form("load", row);
            reflash(row.id);
            init_dg_sql(row.id);
            $.post("/now/echtoptionsqlparam/info", {optid: row.id}, function (result) {
                if (result.code == 0) {
                    sqlParamId = result.data.id;
                    $("#sql_param").textbox("setText", result.data.sqlparam);
                }
            });
        }
    });
}

/**
 * 刷新页面
 */
function add() {
    $.messager.confirm("提醒", "请确保页面数据已经保存", function (check) {
        if (check) {
            location.reload();
        }
    });
}

/**
 * 面板列表添加
 */
function option_save() {
    var col = $("#option_select").combobox("getValue");
    var col_v = editor_option.getValue();
    var id = $("#dg_title").datagrid("getSelected").id;
    var obj = {};
    obj.id = id;

    eval("obj." + col + "=" + JSON.stringify(col_v));
    if (col && col_v) {
        option_update(obj);
    } else {
        $.messager.alert("警告", "未设置添加项或添加项不能为空!", "warning");
    }
}

/**
 * 添加主要面板
 */
function save() {
    var _row = $("#dg_title").datagrid("getSelected");
    var id = 0;
    if (_row) {
        id = _row.id;
    }
    var title = {
        text: $("#text").textbox("getValue"),
        subtext: $("#subtext").textbox("getValue"),
        x:'center'
    };
    var hrefId = $('#hrefId').textbox("getValue");
    if (!hrefId || hrefId.trim() == ""){
        $.messager.alert("提醒","调用ID不能为空","warning");
    } else {
        var obj = {id: id,hrefId:$('#hrefId').textbox("getValue"), title: JSON.stringify(title)};
        option_update(obj);
        saveSQL_Param(id);
    }
}

/**
 * 保存参数
 */
function saveSQL_Param(optId) {
    if (optId) {
        var sp = {
            id: sqlParamId,
            optid:optId,
            sqlparam: $("#sql_param").textbox("getValue")
        };

        $.post("/now/echtoptionsqlparam/save", sp, function (result) {
            if (result.code == 0) {
                reflash(optId);
                sqlParamId = null;
                $.messager.alert("提醒", result.msg, "info");
            }
        });
    } else {
        $.messager.alert("提醒", "请选中一条记录", "info");
    }
}

/**
 * 复用
 */
function reuser() {
    var _row = $("#dg_title").datagrid("getSelected");
    if (!_row) {
        $.messager.alert("提醒", "请选中一条记录", "info");
    } else {
        $.post("/now/echtoption/reuse", {id: _row.id}, function (result) {
            if (result.code == 0) {
                _init();
                $("#dg_title").datagrid("selectRow", 0);
            }
        });
    }
}

/**
 * 初始化sql列表
 */
function init_dg_sql(optId) {
    $.post("/now/echtsql/list1", {optId: optId}, function (result) {
        if (result.code == 0) {
            console.log("当前选中导航的sql列表:", result.rows);
            $("#dg_sql").datagrid({
                data: result.rows,
                onSelect: function (index, row) {
                    editor_script.setValue(row.code);
                }
            });
        }
    });
}

/**
 * 加载面板列表和元素列表
 * @param row
 */
function init_dg_option(row) {
    //得到当前选中的option全部
    var _row = row;
    console.log("当前导航选中对象:", _row);
    //面板列表集合
    var items = [];
    //元素列表集合
    var elements = [];
    for (var x in _row) {
        //过滤掉原数据中的无效项
        if (x == "id" || _row[x] == null || _row[x] == "" || _row[x] == "null" || _row[x] == "[]" || _row[x] == "{}") {
            // eval("delete _row." + x);
        } else {
            console.log("col", x)
            //每一行对象
            var item = {};
            if (x != "series" && x != "yAxis" && x != "xAxis" && x != "hrefId") { //封装面板列表
                item.value = JSON.stringify(_row[x]);
                for (var i = 0; i < col.length; i++) {
                    if (x.toLowerCase() == col[i].toLowerCase()) {
                        item.name = eval("comments." + col[i]);
                    }
                }

                item.col = x;
                //将构建的单行面板对象添加到数组
                items.push(item);
            } else { //封装元素列表
                switch (x) {
                    case "series":
                        var series = eval('(' + _row[x] + ')');
                        for (var i = 0; i < series.length; i++) {
                            series[i].data = [];
                            elements.push(series[i]);
                        }
                        break;
                    case "xAxis":
                        var xAxis = eval('(' + _row[x] + ')');
                        for (var i = 0; i < xAxis.length; i++) {
                            xAxis[i].description = "x轴";
                            xAxis[i].data = [];
                            elements.push(xAxis[i]);
                        }
                        break;
                    case "yAxis":
                        var yAxis = eval('(' + _row[x] + ')');
                        for (var i = 0; i < yAxis.length; i++) {
                            yAxis[i].description = "y轴";
                            yAxis[i].data = [];
                            elements.push(yAxis[i]);
                        }
                        break;
                }
            }
        }
    }

    //加载面板列表
    $("#dg_option").datagrid({
        data: items,
        onSelect: function (index, row) {
            var value = eval('(' + row.value + ')');
            editor_script.setValue(value);
        }
    });
    //加载元素列表
    $("#dg_series").datagrid({
        data: elements,
        onSelect: function (index, row) {
            // delete row.description;
            editor_script.setValue(JSON.stringify(row));
        }
    });
}

/**
 * 初始化面板添加项下拉框
 */
function init_option_select() {
    var no_exit = ["yAxis", "title", "xAxis", "series","id","ID","hrefId","HREFID"];
    $.post("/now/echtoption/comments1", function (result) {
        if (result.code == 0) {
            var o = result.rows;
            for (var oc in option_config) {
                for (var i = 0; i < o.length; i++) {
                    if (oc.toLowerCase() == o[i].col.toLowerCase()) {
                        o[i].col = oc;
                    }
                }
            }

            for (var i = 0; i < o.length; i++) {
                for (var j = 0; j < no_exit.length; j++) {
                    if (o[i].col == no_exit[j] || o[i].com == "y轴") {
                        o.splice(i, 1);
                    }
                }
            }

            $("#option_select").combobox({
                data: o,
                valueField: 'col',
                textField: 'com',
                onSelect: function (record) {
                    editor_option.setValue(" ");
                    for (oc in option_config) {
                        if (oc.toLowerCase() == record.col.toLowerCase()) {
                            var ov = eval("option_config." + oc);
                            if (ov) {
                                editor_option.setValue("\t" + JSON.stringify(ov));
                            } else {
                                editor_option.setValue("");
                            }
                        }
                    }
                }
            });
        }
    });
}

/**
 * 添加Option
 */
function newOption() {
    $('#dlg_option').dialog('open');
    init_option_select();
}

/**
 * 修改Option
 */
function editOption() {
    editor_option.setValue(" ");
    var selectRow = $("#dg_option").datagrid("getSelected");
    if (selectRow) {
        $('#dlg_option').dialog('open');
        console.log("seletRow", selectRow);
        $("#option_select").combobox("setValue", selectRow.col);
        $("#option_select").combobox("setText", selectRow.name);
        var value = eval('(' + selectRow.value + ')');
        editor_option.setValue(value);
    } else {
        $.messager.alert("提醒", "请选中一项进行修改", "warning");
    }

}

/**
 * 删除Option
 */
function removeOption() {
    var selectRow = $("#dg_option").datagrid("getSelected");
    if (selectRow) {
        var _row = $("#dg_title").datagrid("getSelected");
        var obj = {id: _row.id};
        eval("obj." + selectRow.col + "=null");
        option_update(obj);
    } else {
        $.messager.alert("提醒", "请选中一项", "warning");
    }
}

/**
 * 预览
 */
function look() {
    var row = $("#dg_title").datagrid("getSelected");
    window.open("./look1.html?hrefId=" + row.hrefId);
}

/**
 * 清除空格和换行符和tab
 * @param string
 * @returns {*|void|string}
 */
function clearBlank(string) {
    console.log("待过滤字符串:" + string);
    string = string.replace("\n", "");
    string = string.replace("\t", "");
    string = string.replace(" ", "");
    console.log("已过滤字符串:" + string);
    return string;
}

/**
 * 创建编辑器对象
 * @param dom
 * @param code
 */
var editor = function (dom, code) {
    //根据DOM元素的id构造出一个编辑器
    return CodeMirror.fromTextArea(document.getElementById(dom), {
        mode: code,
        //设置显示行号
        lineNumbers: false,
        //设置主题
        theme: "dracula",
        //在长行时文字是换行(wrap)还是滚动(scroll)，默认为滚动(scroll)
        lineWrapping: "wrap",
        //在选择时是否显示光标，默认为false
        showCursorWhenSelecting: true,
        //光标高度。默认为1
        cursorHeight: 0.85,
        //代码折叠
        // lineWrapping: true,
        foldGutter: true,
        gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"],
        // //全屏模式
        fullScreen: false,
        //括号匹配
        matchBrackets: true,
        styleActiveLine: true,
        continueComments: "Enter",
        //智能缩进
        smartIndent: true,
        //智能提示
        extraKeys: {
            "Alt-/": "autocomplete",
            "F11": function (cm) {
                cm.setOption("fullScreen", !cm.getOption("fullScreen"));
            },
            "F7": function autoFormat(editor) {
                var totalLines = editor.lineCount();
                editor.autoFormatRange({line: 0, ch: 0}, {line: totalLines});
            }
        }
    });
}

var editIndex = undefined;

function endEditing() {
    if (editIndex == undefined) {
        return true
    }
    if ($('#dg').datagrid('validateRow', editIndex)) {
        $('#dg').datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}

function onClickCell(index, field) {
    if (editIndex != index) {
        if (endEditing()) {
            $('#dg').datagrid('selectRow', index)
                .datagrid('beginEdit', index);
            var ed = $('#dg').datagrid('getEditor', {index: index, field: field});
            if (ed) {
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            editIndex = index;
        } else {
            setTimeout(function () {
                $('#dg').datagrid('selectRow', editIndex);
            }, 0);
        }
    }
}

function onEndEdit(index, row) {
    $("#dg").datagrid('getEditor', {
        index: index,
        field: 'value'
    });
}

/**
 * 面板配置对象
 */
var option_config = {
    legend: {
        show: true,
        orient:'vertical',
        x:'right',
        y:'center'
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    toolbox: {
        show: true,
        feature: {
            dataZoom: {
                yAxisIndex: 'none'
            },
            dataView: {readOnly: false},
            magicType: {type: ['line', 'bar']},
            restore: {},
            saveAsImage: {}
        }
    },
    polar: {},
    angleAxis: {
        min: 0,
        max: 360,
        interval: 30,
        startAngle: 45
    },
    dataZoom: [
        {
            type: 'slider',
            show: true,
            xAxisIndex: [0],
            start: 1,
            end: 35
        },
        {
            type: 'inside',
            xAxisIndex: [0],
            start: 1,
            end: 35
        }
    ],
    visualMap: [
        {
            type: 'continuous'
        },
        {
            type: 'piecewise'
        }
    ],
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    brush: {
        outOfBrush: {
            color: '#abc'
        },
        brushStyle: {
            borderWidth: 2,
            color: 'rgba(0,0,0,0.2)',
            borderColor: 'rgba(0,0,0,0.5)'
        },
        seriesIndex: [0, 1],
        throttleType: 'debounce',
        throttleDelay: 300,
        geoIndex: 0
    },
    geo: {
        map: 'china',
        label: {
            emphasis: {
                show: false
            }
        },
        itemStyle: {
            normal: {
                areaColor: '#323c48',
                borderColor: '#111'
            },
            emphasis: {
                areaColor: '#2a333d'
            }
        }
    },
    parallel: {
        left: '5%',
        right: '13%',
        bottom: '10%',
        top: '20%',
        parallelAxisDefault: {
            type: 'value',
            name: 'AQI指数',
            nameLocation: 'end',
            nameGap: 20,
            nameTextStyle: {
                fontSize: 12
            }
        }
    },
    singleAxis: [],
    timeline: {
        axisType: 'category',
        realtime: true,
        loop: false,
        autoPlay: true,
        playInterval: 1000,
        data: [
            '2002-01-01', '2003-01-01', '2004-01-01', '2006-01-01', '2007-01-01', '2008-01-01', '2009-01-01', '2010-01-01'
        ]
    },
    axisPointer: {
        link: {xAxisIndex: 'all'},
        label: {
            backgroundColor: '#777'
        }
    },
    calendar: {
        top: 120,
        left: 30,
        right: 30,
        cellSize: ['auto', 13],
        range: '2016',
        itemStyle: {
            normal: {borderWidth: 0.5}
        },
        yearLabel: {show: false}
    },
    dataset: {
        source: [
            {}
        ]
    },
    aria: {
        show: true
    },
    animationDuration: 2000,
    animationDelay: 2000,
    backgroundColor: '#2c343c',
    color: [
        "#FBB367","#80B1D2","#FB8070","#CC99FF","#B0D961",
        "#99CCCC","#BEBBD8","#FFCC99","#8DD3C8","#FF9999",
        "#CCEAC4","#BB81BC","#FBCCEC","#CCFF66","#99CC66",
        "#66CC66","#FF6666","#FFED6F","#ff7f50","#87cefa",
        "#8EC9EB","#FFC125","#FF83FA","#CD96CD","#9932CC"
    ],
    graphic: [
        {
            type: 'image',
            id: 'logo',
            right: 20,
            top: 20,
            z: -10,
            bounding: 'raw',
            origin: [75, 75],
            style: {
                image: 'http://echarts.baidu.com/images/favicon.png',
                width: 150,
                height: 150,
                opacity: 0.4
            }
        },
        {
            type: 'group',
            rotation: Math.PI / 4,
            bounding: 'raw',
            right: 110,
            bottom: 110,
            z: 100,
            children: [
                {
                    type: 'rect',
                    left: 'center',
                    top: 'center',
                    z: 100,
                    shape: {
                        width: 400,
                        height: 50
                    },
                    style: {
                        fill: 'rgba(0,0,0,0.3)'
                    }
                },
                {
                    type: 'text',
                    left: 'center',
                    top: 'center',
                    z: 100,
                    style: {
                        fill: '#fff',
                        text: 'ECHARTS BAR CHART',
                        font: 'bold 26px Microsoft YaHei'
                    }
                }
            ]
        },
        {
            type: 'group',
            left: '10%',
            top: 'center',
            children: [
                {
                    type: 'rect',
                    z: 100,
                    left: 'center',
                    top: 'middle',
                    shape: {
                        width: 190,
                        height: 90
                    },
                    style: {
                        fill: '#fff',
                        stroke: '#555',
                        lineWidth: 2,
                        shadowBlur: 8,
                        shadowOffsetX: 3,
                        shadowOffsetY: 3,
                        shadowColor: 'rgba(0,0,0,0.3)'
                    }
                },
                {
                    type: 'text',
                    z: 100,
                    left: 'center',
                    top: 'middle',
                    style: {
                        fill: '#333',
                        text: [
                            '在这里添加描述文本',
                            '每一项表示一行',
                        ].join('\n'),
                        font: '14px Microsoft YaHei'
                    }
                }
            ]
        }
    ]
};

/**
 * 元素配置对象
 */
var config_series = {
    pie: [
        {
            value: "pie",
            text: "默认样式",
            code: {
                name: '',
                type: 'pie',
                data: []
            }
        },
        {
            value: "pie1",
            text: "环形图-居左",
            code: {
                name: '',
                type: 'pie',
                radius: [20, 110],
                center: ['25%', '50%'],
                roseType: 'radius',
                data: []
            }
        },
        {
            value: "pie2",
            text: "环形图-居右",
            code: {
                name: '',
                type: 'pie',
                radius: [20, 110],
                center: ['50%', '25%'],
                roseType: 'radius',
                data: []
            }
        },
        {
            value: "pie3",
            text: "齿轮图-居中",
            code: {
                name: '',
                type: 'pie',
                radius: '55%',
                center: ['50%', '50%'],
                data: [],
                roseType: 'radius'
            }
        },
        {
            value:"pie4",
            text:"触发式图解(显示详情)",
            code:{
                name:'',
                type:'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: true,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data:[]
            }
        }
    ],
    line: [
        {
            value: "line2",
            text: "默认",
            code: {
                name: '',
                type: 'line',
                data: []
            }
        },
        {
            value: "line1",
            text: "折线(背景色)",
            code: {
                name: '',
                type: 'line',
                stack: '',
                smooth: true,
                areaStyle: {},
                data: []
            }
        },
        {
            value: "line3",
            text: "阈值高亮/平均线",
            code: {
                name: '最高气温',
                type: 'line',
                data: [],
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            }
        },
        {
            value: "line4",
            text: "虚线",
            code: {
                data: [120, 200, 150, 80, 70, 110, 130],
                type: 'line',
                symbol: 'triangle',
                symbolSize: 20,
                lineStyle: {
                    normal: {
                        color: 'green',
                        width: 4,
                        type: 'dashed'
                    }
                },
                itemStyle: {
                    normal: {
                        borderWidth: 3,
                        borderColor: 'yellow',
                        color: 'blue'
                    }
                }
            }
        }
    ],
    bar: [
        {
            value: "bar1",
            text: "默认",
            code: {
                name: '',
                type: 'bar',
                data: [],
                label: {
                    normal: {
                        position: 'center',
                        show: true
                    }
                }
            }
        },
        {
            value: "bar2",
            text: "阀值高亮/平均线",
            code: {
                name: '',
                type: 'bar',
                data: [],
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            }
        }
    ],
    xAxis: [
        {
            value: "xAxis1",
            text: "默认(X坐标全部显示)",
            code: {
                name: '',
                type: 'category',
                data: [],
                axisLabel: {
                    interval:0
                }
            }
        },
        {
            value: "xAxis2",
            text: "值类型",
            code: {
                name: '',
                type: 'value',
                data: []
            }
        }
    ],
    yAxis: [
        {
            value: "yAxis1",
            text: "默认(值类型)",
            code: {
                type: 'value',
                name: '',
                data: []
            }
        },
        {
            value: "yAxis2",
            text: "yAxis2",
            code: {
                type: 'value',
                name: '',
                data: [],
                min: 0,
                max: 250,
                position: 'left',
                axisLabel: {
                    formatter: '{value}'
                }
            }
        }
    ]
};

var scatter = [
    {
        value: "scatter1",
        text: "scatter1",
        code: {
            symbolSize: 20,
            data: [],
            type: 'scatter'
        }
    },
    {
        value: "scatter2",
        text: "scatter2",
        code: {
            symbolSize: 20,
            data: [],
            type: 'scatter'
        }
    }
];


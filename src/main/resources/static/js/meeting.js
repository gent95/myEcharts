var home_url = "http://localhost:8080",
    elementList = "/element/list",
    modelList = "/model/list",
    modelTypeList = "/modelType/list",
    modelDataList = "/modelData/list",
    roomList = "/room/list",
    modelDataSave = "/modelData/save",
    modelDataSearch = "/modelData/search",
    modelSave = "/model/save",
    modelInfo = "/model/info",
    modelDelete = "/model/delete",
    modelNames = "/model/names",
    modelSearch = "/model/search",
    modelCheckRoom = "/model/checkRoom",
    modelHtmlCode = "/model/code",
    modelModelName = "/model/modelName",
    modelLablesDatas = "/model/lablesDatas",
    mmdList = "/mmd/list",
    modelDataInfo = "/modelData/info",
    modelDataDelete = "/modelData/delete",
    doctorGetDeptNames = "/doctor/deptNames",
    districtAllDeptNames = "/district/allDeptNames";

//点击模板列表按钮，打开dialog
function clickShowModelList() {
    $("#search_model").click(function () {
        $("#dlg").dialog("open");
    });
}

//初始化数据
function init_selElement() {
    //初始化模板列表
    init_dlgTable("#datagrid");
    //打开列表弹窗
    clickShowModelList();

    init_model_names();

    init_elements();

    init_rooms();


}

//模板类型下拉列表，数据初始化
function init_model_types() {
    $.getJSON(home_url + modelTypeList, function (result) {
        console.log(result);
        var modelTypes = [{'text': '请选择', 'id': ''}];
        for (var i = 0; i < result.length; i++) {
            modelTypes.push({"text": result[i].motName, "id": "room" + result[i].motId});
        }
        $("#selModelType").combobox("loadData", modelTypes);
    });
}

//初始化科室下拉框
function init_rooms() {
    $.getJSON(home_url + roomList, function (result) {
        console.log(result);
        result = result.data;
        var rooms = [{'text': '请选择', 'id': ''}];
        for (var i = 0; i < result.length; i++) {
            rooms.push({"text": result[i].roomName, "id": "room" + result[i].roomId});
        }
        $("#selRoom").combobox("loadData", rooms);
    });
}

//初始化模板名称下拉框
function init_model_names() {
    $.getJSON(home_url + modelNames, function (result) {
        console.log(result);
        result = result.data;
        var names = [{'text': '请选择', 'id': ''}];
        for (var i = 0; i < result.length; i++) {
            names.push({"text": result[i].modelName, "id": result[i].modelId});
        }
        $("#selModeName").combobox("loadData", names);
    });
}

//初始化元素下拉框
function init_elements() {
    $.getJSON(home_url + elementList, function (result) {
        console.log(result);
        result = result.data;
        var elements = [{'text': '请选择', 'id': ''}];
        for (var i = 0; i < result.length; i++) {
            elements.push({"text": result[i].proName, "id": "htmlType" + result[i].proId});
        }
        $("#selElement").combobox("loadData", elements);
    });
}

//初始化模板列表
function init_dlgTable(target) {
    //初始化表格数据
    $(target).datagrid({
        border: false,
        fitColumns: true,
        singleSelect: true,
        url: home_url + modelList,
        columns: [[
            {field: 'modelId', title: '编号', width: 25},
            {field: 'modelName', title: '模板名称', width: 40},
            {field: 'modelRoom', title: '科室', width: 25},
            {field: 'createTime', title: '创建时间', width: 55},
            {
                field: 'opt', title: '编辑', width: 50,
                formatter: function (value, rec) {
                    var btn = '<a class="editcls" onclick="modeTable_eidt(' + rec.modelId + ')">编辑</a>';
                    return btn;
                }
            },
            {
                field: 'opt1', title: '删除', width: 40,
                formatter: function (value, rec) {
                    var btn = '<a class="editcls1" onclick="modeTable_remove(' + rec.modelId + ')">删除</a>';
                    return btn;
                }
            }
        ]],
        onLoadSuccess: function (data) {
            $('.editcls').linkbutton({text: '编辑', plain: true, iconCls: 'icon-edit'});
            $('.editcls1').linkbutton({text: '删除', plain: true, iconCls: 'icon-edit'});
        }
    });
}

//设置高度自适应
function setHeight() {
    var c = $('#layout');
    var p = c.layout('panel', 'center');	// get the center panel
    var oldHeight = p.panel('panel').outerHeight();
    p.panel('resize', {height: 'auto'});
    var newHeight = p.panel('panel').outerHeight();
    c.layout('resize', {
        height: (c.height() + newHeight - oldHeight)
    });
}

//选中元素下拉框
var panle, element_type;

function selected_element() {
    $("#selElement").combobox({
        onSelect: function (record) {
            switch (record.text) {
                case "文本框":
                    showPanle1(record);
                    break;
                case "多行文本框":
                    showPanle1(record);
                    break;
                case "正标题":
                    showPanle1(record);
                    break;
                case "副标题":
                    showPanle1(record);
                    break;
                case "小标题":
                    showPanle1(record);
                    break;
                case "请选择":
                    showPanle1(record);
                    break;
                default:
                    showPanle2(record);
                    break;
            }
        }
    });
}

//展示文本框面板
function showPanle1(record) {
    $("#panle2").hide();
    $("#panle1").show();
    panle = "#panle1";
    element_type = record.text;
}

//展示列表框面板
function showPanle2(record) {
    $("#panle1").hide();
    $("#panle2").show();
    panle = "#panle2";
    element_type = record.text;
}

//添加元素到内容区
var select_dat = "", select_lables = "";
var tag = false;

function add_element() {
    $("#add_element").click(function () {
        if (tag) {
            initContent();
            tag = false;
        }
        if ($(panle).is(':hidden') != true) {
            var lable = $(panle + " .lable").val();
            select_lables = select_lables + lable + "|";
            if (panle == "#panle2") {
                var data = $(panle + " #data").val();
                select_dat = select_dat + data + "|";
                if (data.search(",") != -1) {
                    data = data.split(",");
                } else {
                    data = data.split("，");
                }
            } else {
                select_dat = select_dat + "***" + "|";
            }
            addElementToContent(data, lable);
        }
    });
}

//初始化content标签内容
function initContent() {
    $("#content").html("<a id=\"add_model\" onclick=\"click_submit()\" data-options=\"iconCls:'icon-ok'\" class=\"easyui-linkbutton subBut\"\n" +
        "               plain=\"true\" outline=\"true\"\n" +
        "               style=\"width:100px;height:20px;margin-top: 10px; margin-left: 80px;\"><span\n" +
        "                    style=\"font-size:16px\">保存模板</span></a>");
    $.parser.parse("#content");

}

//根据元素类型生成元素
var num = 1;

function addElementToContent(data, lable) {
    switch (element_type) {
        case "下拉框":
            data = "''," + data;
            if (data.search(",") != -1) {
                data = data.split(",");
            } else {
                data = data.split("，");
            }
            var element_html = select(data);
            break;
        case "单选按钮":
            var element_html = radio(data);
            break;
        case "多选按钮":
            var element_html = checkBox(data);
            break;
        case "正标题":
            var element_html = "<h1 style='display: inline'>"+lable+"</h1>";
            lable = "";
            break;
        case "副标题":
            var element_html = "<h2 style='display: inline'>"+lable+"</h2>";
            lable = "";
            break;
        case "小标题":
            var element_html = "<h3 style='display: inline'>"+lable+"</h3>";
            lable = "";
            break;
        case "多行文本框":
            var element_html = "";
            element_html = "<br/><textarea type='text' class='input' style='width: 568px; height: 316px; margin-left: 50px;'/>"
            break;
        default:
            var element_html = "";
            element_html = "<input class='input' class='input' type='text'/>";
            break;
    }
    $(".subBut").before(model(lable, element_html));
    //局部渲染
    $.parser.parse("#content");
}

//生成下拉框
var select = function init_select(data) {
    var option = "";
    for (i = 1; i < data.length; i++) {
        option = option + "<option value='" + data[i] + "'>" + data[i] + "</option>"
    }
    return element_html = "<select class='sel' style='width: 40%; height: 30px; border-radius: 3px;'><option>请选择</option>" + option + "</select>";
}

//生成单选按钮
var radio = function init_radio(data) {
    var option = "";
    num++;
    for (i = 0; i < data.length; i++) {
        option = option + "<input type='radio' class='radio' name = '" + num + "' value=" + data[i] + "/><span>" + data[i] + "</span>";
    }
    return option;
}

//生成复选框
var checkBox = function init_checkBox(data) {
    var option = "";
    for (i = 0; i < data.length; i++) {
        option = option + "<input style='margin-left: 10px' class='che' type='checkbox' value=" + data[i] + "/><span>" + data[i] + "</span>";
    }
    return option;
}

//元素添加模块模板
var model = function element_mode(lable, html) {
    var span = "<span class='lable'>" + lable + "</span>";
    //根据是否包含特殊符号决定lable是否后置
    opt= span+html;
    if(lable.indexOf(">>") >= 0){
        span = span.replace(">>","");
        opt= html+span;
    }
    return tmpModel = "<div ondblclick='elementDBC(this)' class = 'din' style='width: 70%; margin-top: 5px;'>"+opt +
        "<a onclick='remove_element(this)' class='easyui-linkbutton doButton'data-options=iconCls:'icon-cancel' plain='true' outline='true' style='width:30px; margin-left: 10px;'></a></div>"
}

//撤销当前按钮
function remove_element(target) {
    $(target).parent().remove();
}

//向上移动元素
function up_element(target) {
    $(target).parent().before($(target).parent());
}

//向下移动元素
function down_element(target) {

}

//监听部门下拉列表
var roomId;

function lins_selRoom() {
    $("#selRoom").combobox({
        onSelect: function (record) {
            roomId = record.id;
        }
    });
}

//点击保存模板按钮时
function click_submit() {
    modHtml_v = $("#content").prop("outerHTML");
    console.log("modHtml_v:" + modHtml_v);
    modRoom_v = roomId.split("m")[1];
    console.log("roomId:" + modRoom_v);
    modType_v = $("#selModelType").val();
    console.log("modType_v:" + modType_v);
    $.ajax({
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        url: home_url + modelSave,
        dataType: "JSON",
        data: JSON.stringify({
            modelName: modType_v,
            modelRoom: modRoom_v,
            modelHtml: modHtml_v,
            modelLables: select_lables,
            modelDatas: select_dat
        }),
        success: function (result) {
            if (result.code == 0) {
                init_dlgTable("datagrid");
                alert("保存成功");
            }
        }
    });
}

//将保存模板按钮替换为保存模板数据按钮
function replaceButton(modelType) {
    var div = $("<input id='submit_form' type='button' value='提交表单' onclick='insert_modelData(" + modelType + ")' class= 'easyui-linkbutton'plain='true' outline='true' style='width:100px;height:30px;margin-top: 10px; margin-left: 80px; border: black solid 2px;'></input>");
    $("#add_model").after(div);
    $('#add_model').remove();
}

//清除模板中标签元素的操作按钮
function remove_doButton() {
    $("#remove_element").remove();
}

//点击表格编辑按钮时，按模板id查询模板信息填充content，并更新左侧模板名称
function modeTable_eidt(id) {
    $.ajax({
        type: 'GET',
        url: home_url + modelInfo + "?modelId=" + id,
        success: function (result) {
            if (result.code == 0) {
                if ($("#content") != null) {
                    $("#content").remove();
                    $("#center .panel-header").after(result.data.modelHtml);
                    getLablesAndDatas(id);
                    tag = true;
                }
            }
        }
    });
}

//获取模板标签的标签名和复选元素的选项
function getLablesAndDatas(modelId) {
    $.ajax({
        type: 'GET',
        url: home_url + modelLablesDatas + "?modelId=" + modelId,
        success: function (result) {
            if (result.code == 0) {
                result = result.lableDatas;
                $("#content>.din").each(function () {
                    if ($(this).attr("class") == "din") {
                        for (var i = 0; i < result.length; i++) {
                            // var tagName = $(this).children(":first").next().tagName;
                            console.log($(this).children(":first").next().attr("class"));
                        }
                    }
                });
            }
        }
    });
}

//初始化模板标签
function modelData_fill(modelLables, modelDatas) {

}

//点击表格删除按钮时，删除选中的模板
function modeTable_remove(id) {
    $.ajax({
        type: 'GET',
        url: home_url + modelDelete + "?modelId=" + id,
        success: function (result) {
            if (result.code == 0) {
                alert("删除成功！");
                init_dlgTable("#datagrid1");
                init_dlgTable("#datagrid");
            }
        }
    });
}

//点击提交表单按钮
var deptName;

function insert_modelData(modeType) {
    var modelData = [{'text': []}, {'radio': []}, {'checkbox': []}, {'select': []},{'textarea': []}];
    $("#content input[type='text']").each(function () {
        if ($(this).val() != "") {
            modelData[0].text.push($(this).val());
        }
        console.log(modelData[0]);
    });

    $("#content input[type='radio']:checked").each(function () {
        if ($(this).val() != "") {
            modelData[1].radio.push($(this).val());
        }
    });

    $("#content input[type='checkbox']:checked").each(function () {
        if ($(this).val() != "") {
            modelData[2].checkbox.push($(this).val());
        }
    });

    $("#content select option:selected").each(function () {
        if ($(this).val() != "") {
            modelData[3].select.push($(this).val());
        }
    });

    $("textarea").each(function () {
        if ($(this).val() != "") {
            modelData[4].textarea.push($(this).val());
        }
    });

    modHtml_v = $("#content").prop("outerHTML");
    console.log("modHtml_v:" + modHtml_v);
    $.ajax({
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        url: home_url + modelDataSave,
        dataType: "JSON",
        data: JSON.stringify({
            modType: modeType,
            modHtml: modHtml_v,
            dataJson: JSON.stringify(modelData),
            deptName: deptName
        }),
        success: function (result) {
            if (result.code == 0) {
                alert("提交成功");
            }
        }
    });
}

//点击查询按钮时
function condition_search(target, init) {
    var roomName = $('#selRoom').combobox('getValue');
    roomId = roomName.split("m")[1];
    var modelName = $('#selModeName').combobox('getText');
    var date = $('#date').datebox('getValue');
    console.log("search========roomId:" + roomId + "modelName:" + modelName + "date:" + date);
    if (date.length < 1) {
        date = null;
    }
    if ($('#selRoom').combobox('getText') == "请选择") {
        roomId = 0;
    }
    if (init == "init") {
        is_url = home_url + modelList;
    } else {
        is_url = home_url + modelSearch + "?modelName=" + modelName + "&createTime=" + date + "&modelRoom=" + roomId;
    }
    console.log("is_url:" + is_url);
    $(target).datagrid({
        border: false,
        fitColumns: true,
        singleSelect: true,
        url: is_url,
        columns: [[
            {field: 'modelId', title: '编号', width: 25},
            {field: 'modelName', title: '模板名称', width: 40},
            {field: 'modelRoom', title: '科室', width: 25},
            {field: 'createTime', title: '创建时间', width: 55},
            {
                field: 'opt', title: '打开', width: 50,
                formatter: function (value, rec) {
                    var btn = '<a class="editcls" onclick="modelTable_open(' + rec.modelId + ')">打开</a>';
                    return btn;
                }
            }
        ]],
        onLoadSuccess: function (data) {
            $('.editcls').linkbutton({text: '打开', plain: true, iconCls: 'icon-edit'});
        }
    });
}

//点击打开按钮，打开模板并使用
function modelTable_open(modelId) {
    $.ajax({
        type: 'GET',
        url: home_url + modelHtmlCode + "?modelId=" + modelId,
        success: function (result) {
            if (result.code == 0) {
                if (result.data != null) {
                    $("#dlg1").html(result.data);
                    $(".doButton").remove();
                    replaceButton(modelId);
                }
                $("#dlg1").dialog("open");
            }
        }
    });
}

//元素双击事件
function elementDBC(target) {
    // $(target).css({"float":"left"})
}

//data页面，模板名称改变时候将模板填充为提交表单
var modelHtml,modelId1;
function modelNameChange() {
    $("#selDistrictdiv").hide();
    $("#selModeName").combobox({
        onSelect: function (record) {
            modelId1 = record.id;
            modelName = record.text;
            if (modelName != "请选择") {
                $.ajax({
                    type: 'GET',
                    contentType: "application/json; charset=utf-8",
                    url: home_url + modelInfo + "?modelId=" + modelId1,
                    dataType: "JSON",
                    success: function (result) {
                        if (result.code == 0) {
                            modelHtml = result.data.modelHtml;
                            if (result.data.modelRoom == 1){
                                $("#selDistrictdiv").show()
                            } else {
                                $("#selDistrictdiv").hide();
                            }
                        }
                    }
                });
            }
        }
    });
}

//fill页面点击添加按钮时触发
function showFile(){
    $("#center").html(modelHtml);
    replaceButton(modelId1);
    replaceFillSign();
    $(".din a").remove();
}

//初始化模板数据列表
function init_modelDataTable(url) {
    $("#datagrid2").datagrid({
        border: false,
        fitColumns: true,
        singleSelect: true,
        url: url,
        columns: [[
            {field: 'modId', title: '编号', width: 25},
            {field: 'modelName', title: '模板名称', width: 40},
            // {field: 'roomName', title: '模板科室', width: 25},
            {field: 'deptName', title: '文件科室', width: 40},
            {field: 'createUser', title: '创建人', width: 55},
            {field: 'createTime', title: '创建时间', width: 55},
            {
                field: 'opt', title: '打开', width: 50,
                formatter: function (value, rec) {
                    var btn = '<a class="editcls" onclick="modelDataTable_open(' + rec.modId + ')">打开</a>';
                    return btn;
                }
            }
            ,
            {
                field: 'opt1', title: '删除', width: 40,
                formatter: function (value, rec) {
                    var btn = '<a class="editcls1" onclick="modeDataTable_remove(' + rec.modId + ')">删除</a>';
                    return btn;
                }
            }
        ]],
        onLoadSuccess: function (data) {
            $('.editcls').linkbutton({text: '打开', plain: true, iconCls: 'icon-edit'});
            $('.editcls1').linkbutton({text: '删除', plain: true, iconCls: 'icon-edit'});
        }
    });
}

//点击打开按钮，打开模板并使用
function modelDataTable_open(modId) {
    $.ajax({
        type: 'GET',
        url: home_url + modelDataInfo + "?modId=" + modId,
        success: function (result) {
            if (result.code == 0) {
                if (result.data != null) {
                    $("#dlg2").dialog("open");
                    $("#dlg2").html(result.data.modHtml);
                    $("#submit_form").remove();
                    fillData(result.data.dataJson);
                }
            }
        }
    });
}

//数据回填
function fillData(dataJson) {
    var data = JSON.parse(dataJson);
    $("input").removeClass();
    var list = $("#content .din input").attr("class", "input");
    for (var i = 0; i < list.length; i++) {
        list[i].value = data[0].text[i];
    }

    $("#content input[type='radio']").each(function (index, element) {
        for (var k = 0; k < data[1].radio.length; k++) {
            if ($(this).next().text() == data[1].radio[k]) {
                element.checked = true;
            }
        }
    });

    $("#content input[type='checkbox']").each(function (index, element) {
        for (var j = 0; j < data[2].checkbox.length; j++) {
            if ($(this).next().text() == data[2].checkbox[j]) {
                console.log($(this).next().text());
                element.checked = true;
            }
        }
    });

    $("#content select option").each(function () {
        for (var l = 0; l < data[3].select.length; l++) {
            if ($(this).val() == data[3].select[l]) {
                console.log($(this).val());
                $(this).attr("selected", "selected");
            }
        }
    });

    var textareaList = $("textarea");
    for (var i = 0; i < textareaList.length; i++) {
        textareaList[i].value = data[4].textarea[i];
    }
}

//点击表格删除按钮时，删除选中的模板
function modeDataTable_remove(id) {
    $.ajax({
        type: 'GET',
        url: home_url + modelDataDelete + "?modId=" + id,
        success: function (result) {
            if (result.code == 0) {
                alert("删除成功！");
                init_modelDataTable();
            }
        }
    });
}

//初始化科室名称列表
function init_selDistrict() {
    $.getJSON(home_url + districtAllDeptNames, function (result) {
        console.log(result);
        if (result.code == 0) {
            result = result.data;
            var deptNames = [{'text': '请选择', 'id': ''}];
            for (var i = 0; i < result.length; i++) {
                deptNames.push({"text": result[i], "id": "district" + i});
            }
            $("#selDistrict").combobox("loadData", deptNames);
        }
    });
}

//改变科室名称列表时触发
function change_selDistrict() {
    $("#selDistrict").combobox({
        onSelect: function (record) {
            deptName = record.text;
        }
    });
}

//替换fill页面中的科室和时间标记符
function replaceFillSign(){
    var el = $("#content");
    el.html(el.html().replace(/@@/ig, deptName));
    // var myDate = new Date();
    // el.html(el.html().replace(/##/ig, myDate));
}

//files页查询按钮触发
var distName;
function modelData_search() {
    var date = $("#mdDate").datebox('getValue');
    var date1 = $("#mdDate1").datebox('getValue');
    url = home_url + modelDataSearch + "?&createTime=" + date +"&endDate="+date1+ "&deptName=" + distName;
    init_modelDataTable(url);
}

//files科室下拉框改变时获取值
function ch_sel() {
    $("#selDistrict").combobox({
        onSelect: function (record) {
            distName = record.text;
        }
    });
}


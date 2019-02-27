function prn1_preview() {
    CreateOneFormPage();
    LODOP.PREVIEW();
};
function prn1_print() {
    CreateOneFormPage();
    LODOP.PRINT();
};
function CreateOneFormPage(){
    LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
    LODOP.PRINT_INIT("医院感染病例报告卡");
    LODOP.SET_PRINT_STYLE("FontSize",18);
    LODOP.SET_PRINT_STYLE("Bold",1);
    //LODOP.ADD_PRINT_TEXT(50,231,260,39,"打印页面部分内容");
    console.info(document.getElementById("box").innerHTML);
    LODOP.ADD_PRINT_HTM(30,0,"100%","100%",document.getElementById("box").innerHTML);
    //LODOP.ADD_PRINT_HTM(70,0,"100%","100%",' <span class="xing" style="margin-left:10px">*</span>医生编号：');
    //LODOP.ADD_PRINT_TEXT(63,38,684,330,document.getElementById("Date_nos").value);
};
//printme();
//setTimeout("prn1_preview();",500);
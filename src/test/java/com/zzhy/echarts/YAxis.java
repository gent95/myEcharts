package com.zzhy.echarts;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YAxis {
    public static void main(String[] args) {
        String sql = "select id a,name b,decode(c,0,0,((1 - c1/c) * 100)) lv,get_v(c1,c,'%') cll from test union all select id,elmtid from echt_sql_elmt";

        sql = "select datano,data_name,c,c1,get_v(c1,c,'%') cll,c2 ,get_v(c2,c,'%') c21 from (\n" +
                "  select \n" +
                "  d.datano,d.data_name,\n" +
                "  count(distinct wi.warnno) c,\n" +
                "  count(distinct cr.warnno)c1,\n" +
                "  count(case when (cr.check_date - wi.warnDate) * 24 * 60 <p.par_val then '1' end)c2\n" +
                "  from district_dict d\n" +
                "  left join (select * from warning_info where\n" +
                "       warndate between to_date(':begin_time','yyyy-mm-dd') and to_date(':end_time 23:59:59','yyyy-mm-dd hh24:mi:ss')\n" +
                "\t   and (systype=':systype' or ':systype' is null) and (pattype=':pattype' or ':pattype' is null) and (distno =':distno' or ':distno' is null)\n" +
                "      and ((sysdate - warndate) * 24 * 60 < ':check_times' or ':check_times' is null)\n" +
                "       --and systype = 1 and pattype = 1\n" +
                "  ) wi on wi.distno=d.datano\n" +
                "  left join (\n" +
                "        select warnno,min(check_date)check_date from check_record c where \n" +
                "              exists(select * from end_warn_type et left join end_warn_type_child cc on cc.type_id = et.type_id where et.type_key='tj_lcqr' and cc.pattype=c.pattype and cc.check_type=c.check_type)\n" +
                "       group by warnno\n" +
                "       ) cr on wi.warnno=cr.warnno,\n" +
                "  (select par_val from parameter_dict where par_key = 'lin_chuang_ji_shi_que_ren_shi_jian')p\n" +
                "  group by d.datano,d.data_name\n" +
                ")t order by c desc";
        String s = sql.substring(StringUtils.indexOf(sql, "select") + 6, StringUtils.indexOf(sql, "from"));
        System.out.println(s);
        Pattern pattern = Pattern.compile("(?<=\\()[^\\)]+");
        Matcher matcher = pattern.matcher(s);
        while(matcher.find()){
            s = matcher.replaceAll("");
        }

        for (String ss:s.split(",")) {
            String field = ss.trim();
            if (StringUtils.contains(field," ")){
                System.out.println(field.substring(field.lastIndexOf(" ")));
            }else {
                System.out.println(field);
            }
        }
    }
}

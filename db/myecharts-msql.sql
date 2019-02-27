/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.104
 Source Server Type    : SQL Server
 Source Server Version : 10501600
 Source Host           : localhost:1433
 Source Catalog        : myecharts
 Source Schema         : dbo

 Target Server Type    : SQL Server
 Target Server Version : 10501600
 File Encoding         : 65001

 Date: 23/11/2018 15:16:07
*/


-- ----------------------------
-- Table structure for chart_option
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[chart_option]') AND type IN ('U'))
	DROP TABLE [dbo].[chart_option]
GO

CREATE TABLE [dbo].[chart_option] (
  [id] int  IDENTITY(1,1) NOT NULL,
  [xAxis] varchar(max) COLLATE Chinese_PRC_CI_AS  NULL,
  [yAxis] varchar(max) COLLATE Chinese_PRC_CI_AS  NULL,
  [series] varchar(max) COLLATE Chinese_PRC_CI_AS  NULL,
  [other] varchar(max) COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[chart_option] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of chart_option
-- ----------------------------
SET IDENTITY_INSERT [dbo].[chart_option] ON
GO

INSERT INTO [dbo].[chart_option] ([id], [xAxis], [yAxis], [series], [other]) VALUES (N'1', N'xAxis:[{type:''category'',axisTick:{alignWithLabel:true},data:[''1月'',''2月'',''3月'',''4月'',''5月'',''6月'',''7月'',''8月'',''9月'',''10月'',''11月'',''12月'']}]', N'yaxis:[{type:''value'',name:''蒸发量'',min:0, max:250, position:''right'', axisline:{ linestyle:{ color:colors[0] } }, axislabel:{ formatter:''{value}ml'' } }, { type:''value'', name:''降水量'', min:0, max:250, position:''right'', offset:80, axisline:{ linestyle:{ color:colors[1] } }, axislabel:{ formatter:''{value}ml'' } }, { type:''value'', name:''温度'', min:0, max:25, position:''left'', axisline:{ linestyle:{ color:colors[2] } }, axislabel:{ formatter:''{value}°c'' } } ]', N'series: [ { name: ''蒸发量'', type: ''pie'', data: [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3], center: [''75%'', ''35%''], radius: ''28%'', z: 100 }, { name: ''降水量'', type: ''bar'', yaxisindex: 1, data: [2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3] }, { name: ''平均温度'', type: ''line'', yaxisindex: 2, data: [2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2] } ]', N'color: colors, tooltip: { trigger: ''axis'', axispointer: { type: ''cross'' } }, grid: { right: ''20%'' }, toolbox: { feature: { dataview: {show: true, readonly: false}, restore: {show: true}, saveasimage: {show: true} } }, legend: { data: [''蒸发量'', ''降水量'', ''平均温度''] }')
GO

SET IDENTITY_INSERT [dbo].[chart_option] OFF
GO


-- ----------------------------
-- Table structure for ECHARTS_OPTION
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ECHARTS_OPTION]') AND type IN ('U'))
	DROP TABLE [dbo].[ECHARTS_OPTION]
GO

CREATE TABLE [dbo].[ECHARTS_OPTION] (
  [NAME] varchar(200) COLLATE Chinese_PRC_CI_AS  NULL,
  [REMARK] varchar(500) COLLATE Chinese_PRC_CI_AS  NULL,
  [STATUS] varchar(1) COLLATE Chinese_PRC_CI_AS DEFAULT ((0)) NULL,
  [CREATE_TIME] varchar(20) COLLATE Chinese_PRC_CI_AS  NULL,
  [OPTION_JSON] text COLLATE Chinese_PRC_CI_AS  NULL,
  [OPTION_SCRIPT] text COLLATE Chinese_PRC_CI_AS  NULL,
  [Y_TYPE] int DEFAULT ((0)) NULL,
  [id] int  IDENTITY(1,1) NOT NULL
)
GO

ALTER TABLE [dbo].[ECHARTS_OPTION] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ECHARTS_OPTION
-- ----------------------------
SET IDENTITY_INSERT [dbo].[ECHARTS_OPTION] ON
GO

INSERT INTO [dbo].[ECHARTS_OPTION] ([NAME], [REMARK], [STATUS], [CREATE_TIME], [OPTION_JSON], [OPTION_SCRIPT], [Y_TYPE], [id]) VALUES (N'测试', N'测试', N'1', N'2018-11-23 14:33:27', N'{"yAxis":[{"type":"value"}],"xAxis":[{"data":[1,2,3,4,5,6,7,8,9,10,11,13,14,15,16],"show":true,"type":"category"}],"calculable":true,"legend":{"orient":"horizontal","data":["测试","X"],"top":30,"left":"left"},"series":[{"data":[1,2,3,4,5,6,7,8,9,10,11,13,14,15,16],"name":"测试","itemStyle":{"normal":{"areaStyle":{"type":"default"}}},"type":"line","smooth":true},null],"toolbox":{"padding":20,"feature":{"saveAsImage":{"show":true,"title":"保存为图片","type":"png","lang":["点击保存"]},"restore":{"show":true,"title":"还原"},"magicType":{"show":true,"title":{"bar":"柱形图切换","stack":"堆积","tiled":"平铺","line":"折线图切换"},"type":["line","bar","stack","tiled"]},"dataView":{"show":true,"readOnly":false,"title":"数据视图","lang":["数据视图","关闭","刷新"]},"mark":{"lineStyle":{"color":"#1e90ff","width":2,"type":"dashed"},"show":true,"title":{"markUndo":"删除辅助线","markClear":"清空辅助线","mark":"辅助线开关"}}},"show":true},"tooltip":{"trigger":"axis"},"title":{"subtext":"测试","x":"center","text":"测试"}}', N'', N'0', N'1')
GO

SET IDENTITY_INSERT [dbo].[ECHARTS_OPTION] OFF
GO


-- ----------------------------
-- Table structure for ECHARTS_SQL
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ECHARTS_SQL]') AND type IN ('U'))
	DROP TABLE [dbo].[ECHARTS_SQL]
GO

CREATE TABLE [dbo].[ECHARTS_SQL] (
  [OPT_ID] int  NULL,
  [NAME] varchar(200) COLLATE Chinese_PRC_CI_AS  NULL,
  [TYPE] varchar(20) COLLATE Chinese_PRC_CI_AS  NULL,
  [SQL] text COLLATE Chinese_PRC_CI_AS  NULL,
  [STATUS] varchar(1) COLLATE Chinese_PRC_CI_AS DEFAULT ((0)) NULL,
  [CREATE_TIME] varchar(20) COLLATE Chinese_PRC_CI_AS  NULL,
  [SORT] int  NULL,
  [RADIUS] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [SQL_PARAM] varchar(500) COLLATE Chinese_PRC_CI_AS  NULL,
  [Y_TYPE] int DEFAULT ((0)) NULL,
  [id] int  IDENTITY(1,1) NOT NULL
)
GO

ALTER TABLE [dbo].[ECHARTS_SQL] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ECHARTS_SQL
-- ----------------------------
SET IDENTITY_INSERT [dbo].[ECHARTS_SQL] ON
GO

INSERT INTO [dbo].[ECHARTS_SQL] ([OPT_ID], [NAME], [TYPE], [SQL], [STATUS], [CREATE_TIME], [SORT], [RADIUS], [SQL_PARAM], [Y_TYPE], [id]) VALUES (N'1', N'测试', N'line', N'
	select id as value from test', N'1', N'2018-11-23 14:36:41', N'1', N'20', N'', N'0', N'1')
GO

INSERT INTO [dbo].[ECHARTS_SQL] ([OPT_ID], [NAME], [TYPE], [SQL], [STATUS], [CREATE_TIME], [SORT], [RADIUS], [SQL_PARAM], [Y_TYPE], [id]) VALUES (N'1', N'X', N'xAis', N'	select id as value from test', N'1', N'2018-11-23 14:54:27', N'2', N'', N'', N'0', N'2')
GO

INSERT INTO [dbo].[ECHARTS_SQL] ([OPT_ID], [NAME], [TYPE], [SQL], [STATUS], [CREATE_TIME], [SORT], [RADIUS], [SQL_PARAM], [Y_TYPE], [id]) VALUES (N'1', N'pie', N'pie', N'	select id as value,name as name from test', N'1', N'2018-11-23 14:57:39', N'3', N'30', N'', N'0', N'3')
GO

SET IDENTITY_INSERT [dbo].[ECHARTS_SQL] OFF
GO


-- ----------------------------
-- Table structure for test
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[test]') AND type IN ('U'))
	DROP TABLE [dbo].[test]
GO

CREATE TABLE [dbo].[test] (
  [id] int  NOT NULL,
  [name] varchar(255) COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[test] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO [dbo].[test]  VALUES (N'1', N'a')
GO

INSERT INTO [dbo].[test]  VALUES (N'2', N'b')
GO

INSERT INTO [dbo].[test]  VALUES (N'3', N'c')
GO

INSERT INTO [dbo].[test]  VALUES (N'4', N'd')
GO

INSERT INTO [dbo].[test]  VALUES (N'5', N'e')
GO

INSERT INTO [dbo].[test]  VALUES (N'6', N'f')
GO

INSERT INTO [dbo].[test]  VALUES (N'7', N'g')
GO

INSERT INTO [dbo].[test]  VALUES (N'8', N'h')
GO

INSERT INTO [dbo].[test]  VALUES (N'9', N'i')
GO

INSERT INTO [dbo].[test]  VALUES (N'10', N'j')
GO

INSERT INTO [dbo].[test]  VALUES (N'11', N'k')
GO

INSERT INTO [dbo].[test]  VALUES (N'13', N'l')
GO

INSERT INTO [dbo].[test]  VALUES (N'14', N'm')
GO

INSERT INTO [dbo].[test]  VALUES (N'15', N'n')
GO

INSERT INTO [dbo].[test]  VALUES (N'16', N'o')
GO


-- ----------------------------
-- Primary Key structure for table chart_option
-- ----------------------------
ALTER TABLE [dbo].[chart_option] ADD CONSTRAINT [PK_chart_option] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ECHARTS_OPTION
-- ----------------------------
ALTER TABLE [dbo].[ECHARTS_OPTION] ADD CONSTRAINT [PK__ECHARTS___3213E83F108B795B] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ECHARTS_SQL
-- ----------------------------
ALTER TABLE [dbo].[ECHARTS_SQL] ADD CONSTRAINT [PK__ECHARTS___3213E83F0DAF0CB0] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table test
-- ----------------------------
ALTER TABLE [dbo].[test] ADD CONSTRAINT [PK__test__3213E83F145C0A3F] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


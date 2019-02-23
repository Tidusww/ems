# 2018-11-02：executed
INSERT INTO `ems`.`condition_item` (`condition_code`, `condition_key`, `condition_desc`, `condition_name`, `condition_placeholder`, `condition_type`, `condition_ext`, `condition_datasource`, `condition_enum`, `status`) VALUES ('ATTENDANCE_STATUS', 'attendanceStatus', '出勤情况', '选择出勤情况', '选择出勤情况', '1', 'width=180', '0', 'com.ly.ems.model.attendance.constant.AttendanceStatusEnum', '1');
INSERT INTO `ems`.`condition_item` (`condition_code`, `condition_key`, `condition_desc`, `condition_name`, `condition_type`, `condition_enum`, `condition_parent_key`, `status`) VALUES ('IMPORT_TEMPLATE_DOWNLOAD', '下载导入模板', 'download', '下载导入模板', '2', 'width=225;type=normal;icon=download', '1', '0');
INSERT INTO `ems`.`condition_config` (`config_code`, `condition_code`, `order_by`, `status`) VALUES ('EMPLOYEE_MANAGE', 'IMPORT_TEMPLATE_DOWNLOAD', '5', '1');
UPDATE `ems`.`condition_config` SET `order_by`='6' WHERE `config_code`='EMPLOYEE_MANAGE' and`condition_code`='IMPORT_BUTTON';
UPDATE `ems`.`condition_item` SET `condition_key`='download', `condition_desc`='下载导入模板', `condition_placeholder`='下载导入模板', `condition_ext`='width=150;type=normal;icon=download', `condition_enum`=NULL, `condition_parent_key`=NULL, `status`='1' WHERE `condition_code`='IMPORT_TEMPLATE_DOWNLOAD';

# 2018-11-26：executed
INSERT INTO `ems`.`condition_config` (`config_code`, `condition_code`, `order_by`, `status`) VALUES ('EMPLOYEE_SELECT', 'EMPLOYEE_NAME', '1', '1');
INSERT INTO `ems`.`condition_config` (`config_code`, `condition_code`, `order_by`, `status`) VALUES ('EMPLOYEE_SELECT', 'QUERY_BUTTON', '99', '1');
INSERT INTO `ems`.`condition_config` (`config_code`, `condition_code`, `order_by`, `status`) VALUES ('PROJECT_SELECT', 'PROJECT_NAME', '1', '1');
INSERT INTO `ems`.`condition_config` (`config_code`, `condition_code`, `order_by`, `status`) VALUES ('PROJECT_SELECT', 'QUERY_BUTTON', '99', '1');

# 2018-12-04：executed
UPDATE `ems`.`condition_item` SET `condition_code`='EXPORT_SALARY_DETAIL' WHERE `condition_code`='EXPORT_BUTTON';
UPDATE `ems`.`condition_config` SET `condition_code`='EXPORT_SALARY_DETAIL' WHERE `config_code`='SALARY_MANAGE' and`condition_code`='EXPORT_BUTTON';
UPDATE `ems`.`condition_item` SET `condition_key`='exportSalaryDetail', `condition_desc`='导出工资明细', `condition_name`='导出工资明细', `condition_placeholder`='导出工资明细' WHERE `condition_code`='EXPORT_SALARY_DETAIL';
INSERT INTO `ems`.`condition_item` (`condition_code`, `condition_key`, `condition_desc`, `condition_name`, `condition_placeholder`, `condition_type`, `condition_ext`, `condition_datasource`, `status`) VALUES ('EXPORT_SALARY_SUMMARY', 'exportSalarySummary', '导出工资汇总', '导出工资汇总', '导出工资汇总', '2', 'width=125;type=normal;icon=download', '0', '1');
INSERT INTO `ems`.`condition_config` (`config_code`, `condition_code`, `order_by`, `status`) VALUES ('SALARY_MANAGE', 'EXPORT_SALARY_SUMMARY', '4', '1');
INSERT INTO `ems`.`condition_item` (`condition_code`, `condition_key`, `condition_desc`, `condition_name`, `condition_placeholder`, `condition_type`, `condition_ext`, `condition_datasource`, `status`) VALUES ('EXPORT_SALARY_DISPATCH', 'exportSalaryDispatch', '导出工资发放表', '导出工资发放表', '导出工资发放表', '2', 'width=125;type=normal;icon=download', '0', '1');
INSERT INTO `ems`.`condition_config` (`config_code`, `condition_code`, `order_by`, `status`) VALUES ('SALARY_MANAGE', 'EXPORT_SALARY_DISPATCH', '5', '1');
UPDATE `ems`.`condition_item` SET `condition_desc`='导出工资汇总表', `condition_name`='导出工资汇总表', `condition_placeholder`='导出工资汇总表', `condition_ext`='width=150;type=normal;icon=download' WHERE `condition_code`='EXPORT_SALARY_SUMMARY';
UPDATE `ems`.`condition_item` SET `condition_desc`='导出工资明细表', `condition_name`='导出工资明细表', `condition_placeholder`='导出工资明细表', `condition_ext`='width=150;type=normal;icon=download' WHERE `condition_code`='EXPORT_SALARY_DETAIL';
UPDATE `ems`.`condition_item` SET `condition_ext`='width=150;type=normal;icon=download' WHERE `condition_code`='EXPORT_SALARY_DISPATCH';
INSERT INTO `ems`.`condition_config` (`config_code`, `condition_code`, `order_by`, `status`) VALUES ('SALARY_MANAGE', 'GROUP_SELECT', '3', '1');
UPDATE `ems`.`condition_config` SET `order_by`='50' WHERE `config_code`='SALARY_MANAGE' and`condition_code`='EXPORT_SALARY_DISPATCH';
UPDATE `ems`.`condition_config` SET `order_by`='49' WHERE `config_code`='SALARY_MANAGE' and`condition_code`='EXPORT_SALARY_SUMMARY';
UPDATE `ems`.`condition_config` SET `order_by`='48' WHERE `config_code`='SALARY_MANAGE' and`condition_code`='EXPORT_SALARY_DETAIL';

# 2018-12-08：executed
INSERT INTO `ems`.`condition_item` (`condition_code`, `condition_key`, `condition_desc`, `condition_name`, `condition_placeholder`, `condition_type`, `condition_ext`, `condition_datasource`, `status`) VALUES ('EXPORT_ATTENDANCE_DETAIL', 'exportAttendanceDetail', '导出出勤明细表', '导出工资明细表', '导出工资明细表', '2', 'width=150;type=normal;icon=download', '0', '1');
INSERT INTO `ems`.`condition_config` (`config_code`, `condition_code`, `order_by`, `status`) VALUES ('ATTENDANCE_MANAGE', 'EXPORT_ATTENDANCE_DETAIL', '50', '1');

# 2018-12-11：executed
INSERT INTO `ems`.`condition_config` (`config_code`, `condition_code`, `order_by`) VALUES ('ATTENDANCE_MANAGE', 'GROUP_SELECT', '2');
UPDATE `ems`.`condition_config` SET `order_by`='49' WHERE `config_code`='ATTENDANCE_MANAGE' and`condition_code`='GENERATE_ATTENDANCE_BUTTON';
UPDATE `ems`.`condition_config` SET `status`='1' WHERE `config_code`='ATTENDANCE_MANAGE' and`condition_code`='GROUP_SELECT';
UPDATE `ems`.`condition_item` SET `condition_name`='导出出勤明细表', `condition_placeholder`='导出出勤明细表' WHERE `condition_code`='EXPORT_ATTENDANCE_DETAIL';

# 2018-12-16: executed
alter table ly_salary_template add column `total_hot_allowance` decimal(8,2) NOT NULL COMMENT '4.总高温津贴=日高温津贴*出勤天数' after `hot_allowance`;
alter table ly_salary_template add column `total_house_fund_allowance` decimal(8,2) NOT NULL COMMENT '3.总住房补贴=日住房补贴*出勤天数' after `hot_allowance`;
alter table ly_salary_template add column `total_social_security_allowance` decimal(8,2) NOT NULL COMMENT '2.总社保补贴=日社保补贴*出勤天数' after `hot_allowance`;
alter table ly_salary_template add column `total_daily_salary` decimal(8,2) NOT NULL COMMENT '1.总日工资=工种的工资标准*出勤天数' after `hot_allowance`;


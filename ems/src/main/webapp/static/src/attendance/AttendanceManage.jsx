import 'css/common.css'
import React from 'react';
import moment from 'moment';
import { Table, message, Modal, Input, InputNumber, DatePicker, Checkbox, Button } from 'antd';
import { ConditionContainer } from 'component/ConditionContainer.jsx';
import {ConditionSelect} from 'component/ConditionSelect.jsx'
import {EditableTable} from 'component/EditableTable.jsx';
import {CommonHelper} from 'core/Common.jsx';

const confirm = Modal.confirm;

class AttendanceManage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentMonth: '', // 当前dataSource数据所属的月份
            monthChanged: true,
            daysInCurrentMonth: 0,
            //Table状态
            isLoading: false,
            selectedRowKeys: [],
            selectedRows: [],
            dataSource: [],
            total: 0,
            dataParam: {
                current: 1,
                pageSize: 5
            },
            downloadVisible: false,
        };
        this.configuration = {
            //提示
            NOT_SELECT_MSG: "请先选择考勤记录",
            OPERATION_SUCCESS_MSG: "操作成功",
            OPERATION_FAILED_MSG: "操作失败，请重试，或与管理员联系",

            //Table相关配置
            conditionConfigCode: "ATTENDANCE_MANAGE",
            keyId: "id",
            selectionType: "radio",
            getUrl: `${_ctx_}/attendance/get`,
            updateUrl: `${_ctx_}/attendance/update`,
            generateUrl: `${_ctx_}/attendance/generate`,
            exportAttendanceDetailUrl: `${_ctx_}/attendance/exportAttendanceDetail`,

        };
        /**
         * Table相关定义
         */
        this.getTableColumns = () => {
            return [
                {title: '月份', dataIndex: 'month', key: 'month', width: 100
                },
                {title: '员工姓名', dataIndex: 'employeeName', key: 'employeeName', width: 100
                },
                {title: '班组', dataIndex: 'groupName', key: 'groupName', width: 100
                },
                {title: '工种', dataIndex: 'jobName', key: 'jobName', width: 100
                },
                {title: '1号', dataIndex: 'attendanceStatus1', key: 'attendanceStatus1', width: 120,
                    render: (text, record, index, args) => {
                        const propName = 'attendanceStatus1';
                        const valuePropName = 'attendanceStatusValue1';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                    return (getFieldDecorator(itemKey, {
                        initialValue: text ? text.toString() : '',
                        rules: [{required: true, message: '请选择出勤情况'}]
                    })(
                        <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                    ));
                }
                },{title: '2号', dataIndex: 'attendanceStatus2', key: 'attendanceStatus2', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus2';
                        const valuePropName = 'attendanceStatusValue2';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '3号', dataIndex: 'attendanceStatus3', key: 'attendanceStatus3', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus3';
                        const valuePropName = 'attendanceStatusValue3';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '4号', dataIndex: 'attendanceStatus4', key: 'attendanceStatus4', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus4';
                        const valuePropName = 'attendanceStatusValue4';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '5号', dataIndex: 'attendanceStatus5', key: 'attendanceStatus5', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus5';
                        const valuePropName = 'attendanceStatusValue5';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '6号', dataIndex: 'attendanceStatus6', key: 'attendanceStatus6', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus6';
                        const valuePropName = 'attendanceStatusValue6';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '7号', dataIndex: 'attendanceStatus7', key: 'attendanceStatus7', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus7';
                        const valuePropName = 'attendanceStatusValue7';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '8号', dataIndex: 'attendanceStatus8', key: 'attendanceStatus8', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus8';
                        const valuePropName = 'attendanceStatusValue8';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '9号', dataIndex: 'attendanceStatus9', key: 'attendanceStatus9', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus9';
                        const valuePropName = 'attendanceStatusValue9';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '10号', dataIndex: 'attendanceStatus10', key: 'attendanceStatus10', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus10';
                        const valuePropName = 'attendanceStatusValue10';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '11号', dataIndex: 'attendanceStatus11', key: 'attendanceStatus11', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus11';
                        const valuePropName = 'attendanceStatusValue11';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '12号', dataIndex: 'attendanceStatus12', key: 'attendanceStatus12', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus12';
                        const valuePropName = 'attendanceStatusValue12';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '13号', dataIndex: 'attendanceStatus13', key: 'attendanceStatus13', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus13';
                        const valuePropName = 'attendanceStatusValue13';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '14号', dataIndex: 'attendanceStatus14', key: 'attendanceStatus14', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus14';
                        const valuePropName = 'attendanceStatusValue14';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '15号', dataIndex: 'attendanceStatus15', key: 'attendanceStatus15', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus15';
                        const valuePropName = 'attendanceStatusValue15';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '16号', dataIndex: 'attendanceStatus16', key: 'attendanceStatus16', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus16';
                        const valuePropName = 'attendanceStatusValue16';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '17号', dataIndex: 'attendanceStatus17', key: 'attendanceStatus17', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus17';
                        const valuePropName = 'attendanceStatusValue17';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '18号', dataIndex: 'attendanceStatus18', key: 'attendanceStatus18', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus18';
                        const valuePropName = 'attendanceStatusValue18';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '19号', dataIndex: 'attendanceStatus19', key: 'attendanceStatus19', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus19';
                        const valuePropName = 'attendanceStatusValue19';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '20号', dataIndex: 'attendanceStatus20', key: 'attendanceStatus20', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus20';
                        const valuePropName = 'attendanceStatusValue20';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '21号', dataIndex: 'attendanceStatus21', key: 'attendanceStatus21', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus21';
                        const valuePropName = 'attendanceStatusValue21';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '22号', dataIndex: 'attendanceStatus22', key: 'attendanceStatus22', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus22';
                        const valuePropName = 'attendanceStatusValue22';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '23号', dataIndex: 'attendanceStatus23', key: 'attendanceStatus23', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus23';
                        const valuePropName = 'attendanceStatusValue23';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '24号', dataIndex: 'attendanceStatus24', key: 'attendanceStatus24', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus24';
                        const valuePropName = 'attendanceStatusValue24';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '25号', dataIndex: 'attendanceStatus25', key: 'attendanceStatus25', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus25';
                        const valuePropName = 'attendanceStatusValue25';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '26号', dataIndex: 'attendanceStatus26', key: 'attendanceStatus26', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus26';
                        const valuePropName = 'attendanceStatusValue26';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '27号', dataIndex: 'attendanceStatus27', key: 'attendanceStatus27', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus27';
                        const valuePropName = 'attendanceStatusValue27';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '28号', dataIndex: 'attendanceStatus28', key: 'attendanceStatus28', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus28';
                        const valuePropName = 'attendanceStatusValue28';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '29号', dataIndex: 'attendanceStatus29', key: 'attendanceStatus29', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus29';
                        const valuePropName = 'attendanceStatusValue29';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: this.canColumnEdit(29), formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '30号', dataIndex: 'attendanceStatus30', key: 'attendanceStatus30', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus30';
                        const valuePropName = 'attendanceStatusValue30';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: this.canColumnEdit(30), formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },{title: '31号', dataIndex: 'attendanceStatus31', key: 'attendanceStatus31', width: 120,
                    render: (text, record, index) => {
                        const propName = 'attendanceStatus31';
                        const valuePropName = 'attendanceStatusValue31';
                        return this.attendanceStatusRender(record, propName, valuePropName);
                    },
                    editable: this.canColumnEdit(31), formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text ? text.toString() : '',
                            rules: [{required: true, message: '请选择出勤情况'}]
                        })(
                            <ConditionSelect conditionCode="ATTENDANCE_STATUS"/>
                        ));
                    }
                },
            ];
        }



    }

    /**
     * 生命周期
     */
    componentDidMount = () => {

    };
    componentWillReceiveProps = (nextProps) => {

    };
    shouldComponentUpdate = (nextProps, nextState) => {
        return true;
    };
    componentWillUpdate = (nextProps, nextState) => {

    };
    componentDidUpdate = (prevProps, prevState) => {

    };
    componentWillUnmount = () => {

    };

    /**
     *  condition container事件
     */
    conditionDidLoad = () => {
        // this.doSearch();
    };
    handleItemChange = (conditionKey, value) => {
        this.state.dataParam[conditionKey] = value;
        if(conditionKey === 'monthSelect') {
            // 当月天数，超过天数的列不许操作
            const date = value.split('-');
            const daysInCurrentMonth = CommonHelper.getDaysInOneMonth(date[0], date[1]);
            this.columns


            console.log("daysInCurrentMonth:%o", daysInCurrentMonth);
            // 防止误修改
            this.setState({monthChanged: true, daysInCurrentMonth: daysInCurrentMonth});
        }
        //

    };
    handleItemPressEnter = (conditionKey) => {
        this.doSearch();
    };
    handleButtonClick = (conditionKey) => {
        switch (conditionKey) {
            case "query":
            {
                this.state.dataParam.current = 1;
                this.doSearch();
                break;
            }
            case 'generateAttendance':
            {
                this.generateAttendanceInfo();
                break;
            }
            case 'exportAttendanceDetail':
            {
                this.doExportAttendanceDetail();
                break;
            }
        }
    };

    /**
     * 导出
     */
    doExportAttendanceDetail = () => {
        this.doExport(this.configuration.exportAttendanceDetailUrl);
    };
    doExport = (targetUrl) => {
        const hide = message.loading('导出中，请稍后...', 0);
        this.setState({isLoading: true});
        $.ajax({
            url: targetUrl,
            type: 'POST',
            data: this.state.dataParam,
            async: true,
            dataType: "json",
            success: (result) => {
                this.setState({isLoading: false});
                hide();
                if (result.success) {
                    const data = result.data;
                    message.success('导出成功，请点击下载', 3);
                    this.showDownloadDialog(data);
                } else {
                    console.log("请求出错");
                    message.error(result.msg, 3);
                }
            },
            error: (result) => {
                this.setState({isLoading: false});
                console.log("请求出错" + result);
                message.error(this.configuration.OPERATION_FAILED_MSG, 3);
            }
        });
    };
    showDownloadDialog = (fileData)=> {
        const downloadUrl = CommonHelper.getNewUrlWithParam(`${_ctx_}/export`, fileData);
        this.setState({downloadVisible: true, downloadUrl: downloadUrl});
    };
    download = ()=> {
        this.refs.ifile.src = this.state.downloadUrl;
    };

    /**
     * Table相关事件
     */
    onPageChange = (current, pageSize) => {
        this.state.dataParam.current = current;
        this.state.dataParam.pageSize = pageSize;
        this.doSearch();
    };
    onSelectionChange = (selectedRowKeys, selectedRows) => {
        this.setState({selectedRowKeys, selectedRows});
    };


    /**
     *  增删查改
     */
    doSearch = () => {
        this.clearSelection();
        this.setState({isLoading: true, monthChanged: false});

        $.ajax({
            url: this.configuration.getUrl,
            type: 'GET',
            data: this.state.dataParam,
            async: true,
            dataType: "json",
            success: (result) => {
                this.setState({isLoading: false});
                if (result.success) {
                    const data = result.data;
                    this.setState({dataSource: data.dataSource, total: data.total, currentMonth: this.state.dataParam['monthSelect']});
                } else {
                    console.log("请求出错");
                    message.info(result.msg, 3);
                }
            },
            error: (result) => {
                this.setState({isLoading: false});
                console.log("请求出错" + result);
                message.error(this.configuration.OPERATION_FAILED_MSG, 3);
            }
        });
    };

    /**
     * 生成考勤数据
     */
    generateAttendanceInfo = () => {
        $.ajax({
            url: this.configuration.generateUrl,
            type: 'POST',
            data: this.state.dataParam,
            async: true,
            dataType: "json",
            success: (result) => {
                this.setState({isLoading: false});
                if (result.success) {
                    message.success(result.msg, 3);
                    this.doSearch();
                } else {
                    console.log("请求出错");
                    message.info(result.msg, 3);
                }
            },
            error: (result) => {
                this.setState({isLoading: false});
                console.log("请求出错" + result);
                message.error(this.configuration.OPERATION_FAILED_MSG, 3);
            }
        });
    };

    canEdit = () => {
        // 没有改变月份条件才能编辑行，不然提交数据时可能会更新错考勤表（根据前端时间）
        return !this.state.monthChanged || this.state.dataParam['monthSelect'] === this.state.currentMonth;
    };
    canColumnEdit = (columnDay) => {
        // 超过月份天数的列不许修改
        return columnDay <= this.state.daysInCurrentMonth;
    };

    doUpdate = (changedData, index) => {
        this.setState({isLoading: true});
        const param = Object.assign({},
            changedData,
            {month: this.state.dataParam.monthSelect});
        $.ajax({
            url: this.configuration.updateUrl,
            type: 'POST',
            data: param,
            async: true,
            dataType: "json",
            success: (result) => {
                this.setState({isLoading: false});
                if (result.success) {
                    message.success(result.msg || this.configuration.OPERATION_SUCCESS_MSG, 3);
                    this.doSearch();
                } else {
                    message.error(result.msg || this.configuration.OPERATION_FAILED_MSG, 5);
                }
            },
            error: (result) => {
                this.setState({isLoading: false});
                message.error(this.configuration.OPERATION_FAILED_MSG, 3);
            }
        });
    }

    attendanceStatusRender = (record, propName, valuePropName) => {
        const text = record[propName];
        const value = record[valuePropName];
        let color = '#000000';
        if(text == 1) {
            color = '#FF0000';
        }
        // console.log(value);
        return (
            <div className="common-flex-center">
                <span style={{color: color}}>{value}</span>
            </div>
        )
    };

    /**
     * 出勤checkbox变化
     * @param row       行,从0开始
     * @param propName      出勤名, e.g:attendanceStatus1
     * @param valuePropName 出勤值名, e.g:attendanceStatusValue1
     * @param newValue  新值, e.g:true
     * @param id        出勤记录的id
     */
    onAttendanceStatusChange = (row, propName, valuePropName, newValue, id) => {
        console.log(`第${row}行 ${propName}-${valuePropName}, 出勤数据变为${newValue}, id:${id}`);
        let dataSource = this.state.dataSource.slice();
        for(let i=0; i<dataSource.length; i++){
            let attendanceRecord = dataSource[i];
            if(attendanceRecord.id == id){
                attendanceRecord[propName] = newValue ? 1 : 0;
                attendanceRecord[valuePropName] = newValue ? '出勤' : '缺勤';
            }
        }
        this.setState(dataSource);
    };
    /**
     * helper method
     */
    clearSelection = () => {
        this.state.selectedRowKeys = [];
        this.state.selectedRows = [];
    };


    render = () => {
        //Table
        const pagination = {
            pageSize: this.state.dataParam.pageSize,
            current: this.state.dataParam.current,
            total: this.state.total,
            onChange: (current, pageSize) => this.onPageChange(current, pageSize),
            showTotal: total => `共 ${this.state.total} 条记录`,
            showSizeChanger: true,
            pageSizeOptions: ['5','10'],
            onShowSizeChange: (current, pageSize) => this.onPageChange(current, pageSize)
        };


        return (
            <div>
                <ConditionContainer
                    configCode={this.configuration.conditionConfigCode}
                    conditionDidLoad={this.conditionDidLoad}
                    onItemChange={this.handleItemChange}
                    onItemPressEnter={this.handleItemPressEnter}
                    onButtonClick={this.handleButtonClick}
                />
                <EditableTable
                    bordered
                    title={()=>`考勤列表`}
                    rowKey={this.configuration.keyId}
                    loading={this.state.isLoading}
                    dataSource={this.state.dataSource}
                    columns={this.getTableColumns()}
                    pagination={pagination}
                    scroll={{x: 4120}}//列的总宽度+62(有选择框)
                    empty
                    rowEdit={this.canEdit()}
                    onSaveRow={(changedData, index) => {
                        console.log('EditableTable 单行编辑，第', index, '行 内容：', changedData);
                        this.doUpdate(changedData, index);
                    }}
                />
                <Modal
                    title='生成文件成功'
                    visible={this.state.downloadVisible}
                    onCancel={()=>{
                        this.setState({downloadVisible:false, downloadUrl: ''})
                    }}
                    footer={null}
                >
                    <Button onClick={this.download}>点击下载</Button>
                </Modal>
                <iframe ref="ifile" style={{display:'none'}}></iframe>
            </div>
        );
    }
}



exports.AttendanceManage = AttendanceManage;
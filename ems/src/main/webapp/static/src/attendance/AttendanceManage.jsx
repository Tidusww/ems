import 'css/common.css'
import React from 'react';
import moment from 'moment';
import { Table, message, Modal, Input, InputNumber, DatePicker, Checkbox } from 'antd';
import { ConditionContainer } from 'component/ConditionContainer.jsx';

const confirm = Modal.confirm;

class AttendanceManage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //Table状态
            isLoading: false,
            selectedRowKeys: [],
            selectedRows: [],
            dataSource: [],
            total: 0,
            dataParam: {
                current: 1,
                pageSize: 50
            }
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
            saveUrl: `${_ctx_}/attendance/save`,
            disableUrl: `${_ctx_}/attendance/generate`,


        };
        /**
         * Table相关定义
         */
        this.columns = [
            {title: '员工姓名', dataIndex: 'employeeName', key: 'employeeName', width: 100
            },
            {title: '班组', dataIndex: 'groupName', key: 'groupName', width: 100
            },
            {title: '工种', dataIndex: 'jobName', key: 'jobName', width: 100
            },
            {title: '1号', dataIndex: 'attendanceStatus1', key: 'attendanceStatus1', width: 50, render: (text, record, index) => {
                let propName = 'attendanceStatus1';
                let valuePropName = 'attendanceStatusValue1';
                let checked = text == 1;
                return (
                    <div className="common-flex-center">
                        <Checkbox
                            onChange={(e)=>this.onAttendanceStatusChange(index, propName, valuePropName, e.target.checked, record.id)}
                            checked={checked}
                        >
                        </Checkbox>
                    </div>
                )
            }},{title: '2号', dataIndex: 'attendanceStatus2', key: 'attendanceStatus2', width: 50
            },{title: '3号', dataIndex: 'attendanceStatus3', key: 'attendanceStatus3', width: 50
            },{title: '4号', dataIndex: 'attendanceStatus4', key: 'attendanceStatus4', width: 50
            },{title: '5号', dataIndex: 'attendanceStatus5', key: 'attendanceStatus5', width: 50
            },{title: '6号', dataIndex: 'attendanceStatus6', key: 'attendanceStatus6', width: 50
            },{title: '7号', dataIndex: 'attendanceStatus7', key: 'attendanceStatus7', width: 50
            },{title: '8号', dataIndex: 'attendanceStatus8', key: 'attendanceStatus8', width: 50
            },{title: '9号', dataIndex: 'attendanceStatus9', key: 'attendanceStatus9', width: 50
            },{title: '10号', dataIndex: 'attendanceStatus10', key: 'attendanceStatus10', width: 50
            },{title: '11号', dataIndex: 'attendanceStatus11', key: 'attendanceStatus11', width: 50
            },{title: '12号', dataIndex: 'attendanceStatus12', key: 'attendanceStatus12', width: 50
            },{title: '13号', dataIndex: 'attendanceStatus13', key: 'attendanceStatus13', width: 50
            },{title: '14号', dataIndex: 'attendanceStatus14', key: 'attendanceStatus14', width: 50
            },{title: '15号', dataIndex: 'attendanceStatus15', key: 'attendanceStatus15', width: 50
            },{title: '16号', dataIndex: 'attendanceStatus16', key: 'attendanceStatus16', width: 50
            },{title: '17号', dataIndex: 'attendanceStatus17', key: 'attendanceStatus17', width: 50
            },{title: '18号', dataIndex: 'attendanceStatus18', key: 'attendanceStatus18', width: 50
            },{title: '19号', dataIndex: 'attendanceStatus19', key: 'attendanceStatus19', width: 50
            },{title: '20号', dataIndex: 'attendanceStatus20', key: 'attendanceStatus20', width: 50
            },{title: '21号', dataIndex: 'attendanceStatus21', key: 'attendanceStatus21', width: 50
            },{title: '22号', dataIndex: 'attendanceStatus22', key: 'attendanceStatus22', width: 50
            },{title: '23号', dataIndex: 'attendanceStatus23', key: 'attendanceStatus23', width: 50
            },{title: '24号', dataIndex: 'attendanceStatus24', key: 'attendanceStatus24', width: 50
            },{title: '25号', dataIndex: 'attendanceStatus25', key: 'attendanceStatus25', width: 50
            },{title: '26号', dataIndex: 'attendanceStatus26', key: 'attendanceStatus26', width: 50
            },{title: '27号', dataIndex: 'attendanceStatus27', key: 'attendanceStatus27', width: 50
            },{title: '28号', dataIndex: 'attendanceStatus28', key: 'attendanceStatus28', width: 50
            },{title: '29号', dataIndex: 'attendanceStatus29', key: 'attendanceStatus29', width: 50
            },{title: '30号', dataIndex: 'attendanceStatus30', key: 'attendanceStatus30', width: 50
            },{title: '31号', dataIndex: 'attendanceStatus31', key: 'attendanceStatus31', width: 50
            },
        ];

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
        }
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
     *  增删查改
     */
    doSearch = () => {
        this.clearSelection();
        this.setState({isLoading: true});

        const _this = this;
        $.ajax({
            url: this.configuration.getUrl,
            type: 'GET',
            data: this.state.dataParam,
            async: true,
            dataType: "json",
            success: function (result) {
                _this.setState({isLoading: false});
                if (result.success) {
                    const data = result.data;
                    _this.setState({dataSource: data.dataSource, total: data.total});
                } else {
                    console.log("请求出错");
                    message.error(result.msg, 3);
                }
            },
            error: function (result) {
                _this.setState({isLoading: false});
                console.log("请求出错" + result);
                message.error(_this.configuration.OPERATION_FAILED_MSG, 3);
            }
        });
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
            pageSizeOptions: ['50','100','200','500'],
            onShowSizeChange: (current, pageSize) => this.onPageChange(current, pageSize)
        };
        const rowSelection = {
            type: this.configuration.selectionType,
            selectedRowKeys: this.state.selectedRowKeys,
            onChange: (selectedRowKeys, selectedRows) => this.onSelectionChange(selectedRowKeys, selectedRows)
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
                <Table
                    bordered
                    title={()=>`考勤列表`}
                    rowKey={this.configuration.keyId}
                    loading={this.state.isLoading}
                    dataSource={this.state.dataSource}
                    columns={this.columns}
                    pagination={pagination}
                    rowSelection={rowSelection}
                    scroll={{x: 1850}}//列的总宽度+62(有选择框)
                />
            </div>
        );
    }
}



exports.AttendanceManage = AttendanceManage;
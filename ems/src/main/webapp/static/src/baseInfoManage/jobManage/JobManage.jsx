// product begin
import React from 'react';
import { Table, Input, Button, message, Modal, Form, Select } from 'antd';
import { ConditionContainer } from 'core/component/ConditionContainer.jsx';
// product end

class JobManage extends React.Component {
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
                pageSize: 10
            }
        };
        this.configuration = {
            //提示
            NOT_SELECT_MSG: "请先选择工种",
            OPERATION_SUCCESS_MSG: "操作成功",
            OPERATION_FAILED_MSG: "操作失败，请重试，或与管理员联系",

            //Table相关配置
            conditionConfigCode: "JOB_MANAGE",
            keyId: "id",
            tableUrl: `${_ctx_}/base/getJobs`,
            selectionType: "radio"

        };
        /**
         * Table相关定义
         */
        this.columns = [
            {title: '班组名称', dataIndex: 'jobName', key: 'jobName', width: 140
            },
            {title: '是否特殊工种', dataIndex: 'isSpec', key: 'isSpec', width: 100
            },
            {title: '工资', dataIndex: 'salary', key: 'salary', width: 75
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
        this.doSearch();
    }
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
            case "export":
            {
                break;
            }
        }
    };

    /**
     * Table相关事件
     */
    onPageChange = (page, pageSize) => {
        this.state.dataParam.current = page;
        this.state.dataParam.pageSize = pageSize;
        this.doSearch();
    };
    onSelectionChange = (selectedRowKeys, selectedRows) => {
        this.setState({selectedRowKeys, selectedRows});
    };

    /**
     *  查询
     */
    doSearch = ()=> {
        this.setState({isLoading: true});

        const _this = this;
        $.ajax({
            url: this.configuration.tableUrl,
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
     *  操作按钮
     */

    /**
     * helper method
     */
    

    render = () => {
        //Table
        const pagination = {
            pageSize: this.state.dataParam.pageSize,
            current: this.state.dataParam.current,
            total: this.state.total,
            onChange: (page, pageSize) => this.onPageChange(page, pageSize),
            showTotal: total => `共 ${this.state.total} 条记录`
        };
        const rowSelection = {
            type: this.configuration.selectionType,
            selectedRowKeys: this.state.selectedRowKeys,
            onChange: (selectedRowKeys, selectedRows) => this.onSelectionChange(selectedRowKeys, selectedRows)
        };

        const modalFooter = [
            <Button size="large" loading={this.state.isSubmitting} onClick={this.handleCancel}>关 闭</Button>,
            <Button type="primary" size="large" disabled={this.state.orderStatus!='订单异常'} loading={this.state.isSubmitting} onClick={this.handleRewriteOrder} >重新回写TMS</Button>
        ];

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
                    title={()=>`工种列表`}
                    rowKey={this.configuration.keyId}
                    loading={this.state.isLoading}
                    dataSource={this.state.dataSource}
                    columns={this.columns}
                    pagination={pagination}
                />
            </div>
        );
    }
}



exports.JobManage = JobManage;
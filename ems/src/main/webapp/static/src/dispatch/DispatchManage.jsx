import React from 'react';
import {Table, message, Modal, Input, InputNumber} from 'antd';
import {ConditionContainer} from 'component/ConditionContainer.jsx';
import {ModalForm} from 'component/ModalForm.jsx'

const confirm = Modal.confirm;

class DispatchManage extends React.Component {
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
            },
            modalForm: {
                form: undefined,
                modalTitle: "",
                modalVisible: false,
                modalWidth: "40%",
                formData: {},
                formDataIdKey: "id",
                isSubmitting: false
            }
        };
        this.configuration = {
            //提示
            NOT_SELECT_MSG: "请先选择分派记录",
            OPERATION_SUCCESS_MSG: "操作成功",
            OPERATION_FAILED_MSG: "操作失败，请重试，或与管理员联系",

            //Table相关配置
            conditionConfigCode: "DISPATCH_MANAGE",
            keyId: "id",
            selectionType: "radio",
            getUrl: `${_ctx_}/dispatch/list`,
            saveUrl: `${_ctx_}/dispatch`,
            disableUrl: `${_ctx_}/dispatch`

        };
        /**
         * Table相关定义
         */
        this.columns = [
            {
                title: '班组名称', dataIndex: 'groupName', key: 'groupName', width: 140
            }, {
                title: '单位名称', dataIndex: 'companyName', key: 'companyName', width: 140
            }, {
                title: '项目名称', dataIndex: 'projectName', key: 'projectName', width: 140
            }, {
                title: '开始时间', dataIndex: 'startDate', key: 'startDate', width: 140
            }, {
                title: '结束时间', dataIndex: 'endDate', key: 'endDate', width: 140
            }, {
                title: '状态', dataIndex: 'enable', key: 'enable', width: 140
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
    onPageChange = (page, pageSize) => {
        this.state.dataParam.current = page;
        this.state.dataParam.pageSize = pageSize;
        this.doSearch();
    };
    onSelectionChange = (selectedRowKeys, selectedRows) => {
        this.setState({selectedRowKeys, selectedRows});
    };
    selectRow = (record) => {
        const selectedRowKeys = [...this.state.selectedRowKeys];
        if (selectedRowKeys.indexOf(record.id) >= 0) {
            selectedRowKeys.splice(selectedRowKeys.indexOf(record.id), 1);
        } else {
            selectedRowKeys.push(record.id);
        }
        this.setState({selectedRowKeys});
    };


    /**
     *  增删查改
     */
    doSearch = () => {
        this.clearSelection();
        this.setState({isLoading: true});

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
                    this.setState({dataSource: data.dataSource, total: data.total});
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

    handleDisable = () => {
        if (this.state.selectedRows.length <= 0) {
            message.info(this.configuration.NOT_SELECT_MSG);
            return;
        }
        confirm({
            title: '确定作废该单位?',
            content: '',
            okText: '确定',
            okType: 'danger',
            cancelText: '取消',
            onOk: this.doDisable,
            onCancel: () => {

            }
        });
    };
    doDisable = () => {
        $.ajax({
            url: this.configuration.disableUrl,
            type: 'POST',
            data: {id: this.state.selectedRowKeys[0]},
            async: true,
            dataType: "json",
            success: (result) => {
                if (result.success) {
                    this.disableSuccess(result);
                } else {
                    this.disableFail(result);
                }
            },
            error: (result) => {
                this.disableFail(result);
            }
        });
    };
    disableSuccess = (result) => {
        message.success(result.msg || this.configuration.OPERATION_SUCCESS_MSG);
        this.state.dataParam.current = 1;
        this.doSearch();
    };
    disableFail = (result) => {
        message.error(result.msg || this.configuration.OPERATION_FAILED_MSG, 3);
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
            onChange: (page, pageSize) => this.onPageChange(page, pageSize),
            showTotal: total => `共 ${this.state.total} 条记录`
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
                    title={()=>`单位列表`}
                    rowKey={this.configuration.keyId}
                    loading={this.state.isLoading}
                    dataSource={this.state.dataSource}
                    columns={this.columns}
                    pagination={pagination}
                    rowSelection={rowSelection}
                    onRow={(record) => ({
                        onClick: () => {
                            this.selectRow(record);
                        },
                    })}
                />
            </div>
        );
    }
}


exports.DispatchManage = DispatchManage;
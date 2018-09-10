/**
 * 外面是Modal, 里面包裹着一个带查询条件的Table
 *
 *
 * 使用方法:
 *      上层组件需要提供:
 *          title, visible, width,
 *          conditionConfigCode,
 *          searchUrl, keyId, column, multiSelect
 *      回调:
 *          handleConfirm                点击了确定
 *          handleCancel                点击了取消
 *
 *  简单例子:


 */

import React from 'react';
import { Modal, Table, Button, message } from 'antd';
import { ConditionContainer } from 'component/ConditionContainer.jsx';

class ModalTable extends React.Component {
    static propTypes = {
        title: React.PropTypes.string,
        visible: React.PropTypes.bool,
        width: React.PropTypes.string,

        // conditionConfigCode: React.PropTypes.string.required,
        // searchUrl: React.PropTypes.string.required,
        // keyId: React.PropTypes.string.required,
        // column: React.PropTypes.array.required,
        // multiSelect: React.PropTypes.bool.required,

        handleConfirm: React.PropTypes.func,
        handleCancel: React.PropTypes.func
    };

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
            OPERATION_SUCCESS_MSG: "操作成功",
            OPERATION_FAILED_MSG: "操作失败，请重试，或与管理员联系"
        };
    }

    /**
     * 生命周期
     */
    componentDidMount = () => {
        console.log("ModalTable DidMount:%o", this.props);
    };
    componentWillReceiveProps = (nextProps) => {
        console.log("ModalTableWillReceiveProps");
    };
    shouldComponentUpdate = (nextProps, nextState) => {
        return true;
    };
    componentWillUpdate = (nextProps, nextState) => {

    };
    componentDidUpdate = (prevProps, prevState) => {
        console.log("ModalTable DidUpdate");
    };
    componentWillUnmount = () => {
        console.log("ModalTable will unmount");
    };

    /**
     * events
     */



    /**
     *  增删查改
     */
    doSearch = () => {
        this.clearSelection();
        this.setState({isLoading: true});

        $.ajax({
            url: this.props.searchUrl,
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

    /**
     * helper method
     */
    clearSelection = () => {
        this.state.selectedRowKeys = [];
        this.state.selectedRows = [];
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

    /**
     * Modal相关
     */
    handleCancel = () => {
        this.props.handleCancel && this.props.handleCancel();
    };
    handleConfirm = () => {
        this.props.handleConfirm && this.props.handleConfirm(this.state.selectedRowKeys, this.state.selectedRows);
    };

    render = () => {
        const modalFooter = [
            <Button size="large" loading={this.state.isLoading} onClick={this.props.handleCancel} key="cancel">取 消</Button>,
            <Button size="large" loading={this.state.isLoading} disabled={!this.state.selectedRowKeys} onClick={this.handleConfirm} key="confirm" type="primary" >确 定</Button>
        ];

        //Table
        const pagination = {
            pageSize: this.state.dataParam.pageSize,
            current: this.state.dataParam.current,
            total: this.state.total,
            onChange: (page, pageSize) => this.onPageChange(page, pageSize),
            showTotal: total => `共 ${this.state.total} 条记录`
        };
        const rowSelection = {
            type: this.props.multiSelect ? "checkbox" : "radio",
            selectedRowKeys: this.state.selectedRowKeys,
            onChange: (selectedRowKeys, selectedRows) => this.onSelectionChange(selectedRowKeys, selectedRows)
        };

        return (
            <Modal
                title={this.props.title}
                closable={false}
                maskClosable={false}
                width={this.props.width}
                visible={this.props.visible}
                footer={modalFooter}
            >
                {this.props.visible && (
                    <div>
                        <ConditionContainer
                            configCode={this.props.conditionConfigCode}
                            conditionDidLoad={this.conditionDidLoad}
                            onItemChange={this.handleItemChange}
                            onItemPressEnter={this.handleItemPressEnter}
                            onButtonClick={this.handleButtonClick}
                        />
                        <Table
                            bordered
                            title={()=>{this.props.title}}
                            rowKey={this.props.keyId}
                            loading={this.state.isLoading}
                            dataSource={this.state.dataSource}
                            columns={this.props.column}
                            pagination={pagination}
                            rowSelection={rowSelection}
                        />
                    </div>
                )}
            </Modal>
        );
    }
}
exports.ModalTable = ModalTable;
// product begin
import React from 'react';
import { Table, Input, Button, message, Modal, Form, Select } from 'antd';
import { ConditionContainer } from '../core/ConditionContainer.jsx';
import { Constants } from '../core/Const.jsx';
import { CommonHelper } from '../core/Common.jsx';
import { OrderDetail } from './OrderDetail.jsx';
// product end

class GroupManage extends React.Component {
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
            NOT_SELECT_MSG: "请先选择订单",
            OPERATION_SUCCESS_MSG: "操作成功",
            OPERATION_FAILED_MSG: "操作失败，请重试，或与管理员联系",

            //Table相关配置
            conditionConfigCode: "ORDER_MANAGE",
            queryUrl: `${_ctx_}/order/getOrders`,
            keyId: "orderId",
            selectionType: "radio",

        };
        /**
         * Table相关定义
         */
        this.columns = [
            {title: '订单号', dataIndex: 'orderNum', key: 'orderNum', width: 250,
                render:(data, record, index) => {
                    return <a onClick={()=>this.showOrderDetail(record)}>{data}</a>;
                }
            },
            {title: '下单时间', dataIndex: 'createDate', key: 'createDate', width: 140
            },
            {
                title: '订单状态', dataIndex: 'status', key: 'status', width: 100
            },
            {title: '支付方式', dataIndex: 'payType', key: 'payType', width: 100
            },
            {title: '城市名', dataIndex: 'cityName', key: 'cityName', width: 75
            },
            {title: '年份', dataIndex: 'yearName', key: 'yearName', width: 75
            },
            {title: '季节', dataIndex: 'seasonName', key: 'seasonName', width: 75
            },
            {title: '校区', dataIndex: 'deptName', key: 'deptName', width: 200
            },
            {title: '科目数', dataIndex: 'subjectsNum', key: 'subjectsNum', width: 75
            },
            {title: '姓名', dataIndex: 'stuMemberName', key: 'stuMemberName', width: 80
            },
            {title: '电话', dataIndex: 'phone', key: 'phone', width: 120
            },
            {title: '学生编号', dataIndex: 'stuNum', key: 'stuNum', width: 100
            },
            {title: '报读金额', dataIndex: 'discountPrice', key: 'discountPrice', width: 75
            },
            {title: '优惠金额', dataIndex: 'couponAmount', key: 'couponAmount', width: 75
            },
            {title: '订单总额', dataIndex: 'orderAmount', key: 'orderAmount', width: 75
            },
            {title: '下单客户端', dataIndex: 'createOrderClient', key: 'createOrderClient', width: 100
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
        const enterKey = ["orderNum", "memberName", "phone", "memberId"];
        if ( enterKey.contains(conditionKey) ) {
            this.doSearch();
        }
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
     *  操作按钮
     */
    /**
     *  查询
     */
    doSearch = ()=> {
        this.setState({isLoading: true});

        const _this = this;
        $.ajax({
            url: this.configuration.queryUrl,
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
                    message.error(_this.configuration.OPERATION_FAILED_MSG, 3);
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
    handleOrderDetail = (orderDetail) => {

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
                    title={()=>`订单管理列表`}
                    rowKey={this.configuration.keyId}
                    loading={this.state.isLoading}
                    dataSource={this.state.dataSource}
                    columns={this.columns}
                    pagination={pagination}
                />
                <Modal
                    width={this.configuration.updateModalWidth}
                    maskClosable={this.configuration.maskClosable}
                    closable={this.configuration.closable}
                    title={`订单: ${this.state.orderDetailTile}`}
                    visible={this.state.isOrderDetailVisible}
                    footer={modalFooter}
                >
                    <OrderDetail
                        orderNum={this.state.orderDetailOrderNum}
                    />
                </Modal>
            </div>
        );
    }
}



exports.GroupManage = GroupManage;
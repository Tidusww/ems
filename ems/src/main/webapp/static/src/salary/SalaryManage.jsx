import React from 'react';
import {Table, message, Modal, Input, InputNumber} from 'antd';
import {ConditionContainer} from 'component/ConditionContainer.jsx';
import {CommonHelper} from 'core/Common.jsx';

const confirm = Modal.confirm;

class SalaryManage extends React.Component {
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
            NOT_SELECT_MSG: "请先选择工资记录",
            OPERATION_SUCCESS_MSG: "操作成功",
            OPERATION_FAILED_MSG: "操作失败，请重试，或与管理员联系",

            //Table相关配置
            conditionConfigCode: "SALARY_MANAGE",
            keyId: "id",
            selectionType: "radio",
            getUrl: `${_ctx_}/salary/get`,
            saveUrl: `${_ctx_}/salary`,
            disableUrl: `${_ctx_}/`,
            generateUrl: `${_ctx_}/salary/generate`,

        };
        /**
         * Table相关定义
         */
        this.columns = [
            {
                title: '员工姓名', dataIndex: 'employeeName', key: 'employeeName', width: 100
            },
            {
                title: '班组', dataIndex: 'groupName', key: 'groupName', width: 100
            },
            {
                title: '工种名称', dataIndex: 'jobName', key: 'jobName', width: 100
            },
            {
                title: '基本工资（元）', dataIndex: 'basicSalary', key: 'basicSalary', width: 120
            },
            {
                title: '加班工资（元）', dataIndex: 'overtimeSalary', key: 'overtimeSalary', width: 120
            },
            {
                title: '计量工资（元）', dataIndex: 'calculateSalary', key: 'calculateSalary', width: 120
            },
            {
                title: '高温补贴（元）', dataIndex: 'hotAllowance', key: 'hotAllowance', width: 120
            },
            {
                title: '社保补贴（元）', dataIndex: 'socialSecurityAllowance', key: 'socialSecurityAllowance', width: 120
            },
            {
                title: '公积金补贴（元）', dataIndex: 'houseFundAllowance', key: 'houseFundAllowance', width: 140
            },
            {
                title: '其他收入（元）', dataIndex: 'otherIncome', key: 'otherIncome', width: 120
            },
            {
                title: '应付工资（元）', dataIndex: 'payableSalary', key: 'payableSalary', width: 120
            },

            {
                title: '个人社保（元）', dataIndex: 'personalSocialSecurity', key: 'personalSocialSecurity', width: 120
            },
            {
                title: '个人公积金（元）', dataIndex: 'personalHouseFund', key: 'personalHouseFund', width: 140
            },
            {
                title: '其他扣除（元）', dataIndex: 'otherDeduction', key: 'otherDeduction', width: 120
            },
            {
                title: '应付个税（元）', dataIndex: 'payTaxes', key: 'payTaxes', width: 120
            },
            {
                title: '实发工资（元）', dataIndex: 'realSalary', key: 'realSalary', width: 120
            },

            {
                title: '单位社保（元）', dataIndex: 'companySocialSecurity', key: 'companySocialSecurity', width: 120
            },
            {
                title: '单位公积金（元）', dataIndex: 'companyHouseFund', key: 'companyHouseFund', width: 140
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
            case 'generateSalary':
            {
                this.generateSalaryInfo();
            }
            case "export":
            {
                this.doExport();
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

    /**
     * 生成工资数据
     */
    generateSalaryInfo = () => {
        $.ajax({
            url: this.configuration.generateUrl,
            type: 'POST',
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

    doExport = () => {
        const newUrl = CommonHelper.getNewUrlWithParam(`${_ctx_}/salary/exportSalaries`, this.state.dataParam);
        this.refs.ifile.src = newUrl;
    };

    /**
     * helper method
     */
    clearSelection = () => {
        this.state.selectedRowKeys = [];
        this.state.selectedRows = [];
    };

    render = ()=> {
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
                    title={()=>`工资列表`}
                    rowKey={this.configuration.keyId}
                    loading={this.state.isLoading}
                    dataSource={this.state.dataSource}
                    columns={this.columns}
                    pagination={pagination}
                    rowSelection={rowSelection}
                    scroll={{x: 2222}}//列的总宽度+62(有选择框)
                    onRow={(record) => ({
                        onClick: () => {
                            this.selectRow(record);
                        },
                    })}
                />
                <iframe ref="ifile" style={{display:'none'}}></iframe>
            </div>
        );
    }
}


exports.SalaryManage = SalaryManage;
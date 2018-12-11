import React from 'react';
import {Table, message, Modal, Input, InputNumber, Spin, Button, Popconfirm, Form} from 'antd';
import {ConditionContainer} from 'component/ConditionContainer.jsx';
import {EditableTable} from 'component/EditableTable.jsx';
import {CommonHelper} from 'core/Common.jsx';
import {Constants} from 'core/Const.jsx';

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
            editingKey: '',
            downloadVisible: false,
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
            updateUrl: `${_ctx_}/salary/update`,
            generateUrl: `${_ctx_}/salary/generate`,
            exportSalaryDetailUrl: `${_ctx_}/salary/exportSalaryDetail`,
            exportSalarySummaryUrl: `${_ctx_}/salary/exportSalarySummary`,
            exportSalaryDispatchUrl: `${_ctx_}/salary/exportSalaryDispatch`,

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
                title: '出勤天数', dataIndex: 'attendanceDays', key: 'attendanceDays', width: 100
            },
            {
                title: '日工资（元/日）', dataIndex: 'dailySalary', key: 'dailySalary', width: 150
            },
            {
                title: '社保补贴（元/日）', dataIndex: 'socialSecurityAllowance', key: 'socialSecurityAllowance', width: 150
            },
            {
                title: '住房补贴（元/日）', dataIndex: 'houseFundAllowance', key: 'houseFundAllowance', width: 150
            },
            {
                title: '高温津贴（元/日）', dataIndex: 'hotAllowance', key: 'hotAllowance', width: 150
            },
            {
                title: '其他收入（元）', dataIndex: 'otherIncome', key: 'otherIncome', width: 120,
                editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                    return (getFieldDecorator(itemKey, {
                        initialValue: text,
                        // rules: rules
                    })(
                        <InputNumber min={0} step={0.1}/>
                    ));
                }
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
                title: '应付个税（元）', dataIndex: 'payTaxes', key: 'payTaxes', width: 120
            },
            {
                title: '其他扣除（元）', dataIndex: 'otherDeduction', key: 'otherDeduction', width: 120
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
                break;
            }
            case "exportSalaryDetail":
            {
                this.doExportSalaryDetail();
                break;
            }
            case "exportSalarySummary":
            {
                this.doExportSalarySummary();
                break;
            }
            case "exportSalaryDispatch":
            {
                this.doExportSalaryDispatch();
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
                    message.success(result.msg, 3);
                    this.doSearch();
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
     * 导出
     */
    doExportSalaryDetail = () => {
        this.doExport(this.configuration.exportSalaryDetailUrl);
    };
    doExportSalarySummary = () => {
        this.doExport(this.configuration.exportSalarySummaryUrl);
    };
    doExportSalaryDispatch = () => {
        this.doExport(this.configuration.exportSalaryDispatchUrl);
    };
    doExport = (targetUrl) => {
        // const newUrl = CommonHelper.getNewUrlWithParam(`${_ctx_}/salary/exportSalaryDetail`, this.state.dataParam);
        // this.refs.ifile.src = newUrl;

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
     * 编辑
     */
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
                } else {
                    message.error(result.msg || this.configuration.OPERATION_FAILED_MSG, 5);
                }
                this.doSearch();
            },
            error: (result) => {
                this.setState({isLoading: false});
                message.error(this.configuration.OPERATION_FAILED_MSG, 3);
            }
        });
    }

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
            showTotal: total => `共 ${this.state.total} 条记录`,
            showSizeChanger: true,
            pageSizeOptions: ['5','10'],
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
                <EditableTable
                    bordered
                    title={()=>`工资列表`}
                    rowKey={this.configuration.keyId}
                    loading={this.state.isLoading}
                    dataSource={this.state.dataSource}
                    columns={this.columns}
                    pagination={pagination}
                    scroll={{x: 2120}}//列的总宽度+62(有选择框)
                    empty
                    rowEdit
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


exports.SalaryManage = SalaryManage;
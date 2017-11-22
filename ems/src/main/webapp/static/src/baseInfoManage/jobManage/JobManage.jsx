import React from 'react';
import { Table, Input, Button, message, Modal, Form, Select } from 'antd';
import { ConditionContainer } from 'component/ConditionContainer.jsx';
import { JobEditForm } from 'baseInfoManage/jobManage/JobEditForm.jsx';
import { ModalForm } from 'component/ModalForm.jsx'

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
            {title: '工种名称', dataIndex: 'jobName', key: 'jobName', width: 140
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
            case "insert":
            {
                const { modalForm } = this.state;
                Object.assign(modalForm, {
                    formData: {},
                    modalVisible: true
                });
                this.setState({
                    modalForm: modalForm
                });
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
    doSearch = () => {
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
     * ModalForm相关
     */
    //引用Form
    saveFormRef = (form) => {
        this.state.modalForm.form = form;
    };
    //表单值改变
    handleFormFieldsChange = (props, values) => {
        console.log("JobManage 表单值改变:props[%o], values[%o]", props, values);
        //保存正在编辑的数据
        Object.assign(this.state.modalForm.formData, values);
    }
    //Modal提交
    handleSubmit = () => {
        const form = this.state.modalForm.form.props.form;
        form.validateFields((err, values) => {
            if (!err) {
                // TODO
                const { modalForm } = this.state;
                Object.assign(modalForm, {
                    isSubmitting: true
                });
                this.setState({
                    modalForm: modalForm
                });
                setTimeout(()=>{
                    this.handleCancel();
                }, 1000)
            }
        });
    };
    //Modal取消
    handleCancel = () => {
        const { modalForm } = this.state;
        Object.assign(modalForm, {
            isSubmitting: false,
            modalVisible: false,
            formData: {}
        });
        this.setState({
            modalForm: modalForm
        });
    };
    //表单字段
    getFormFields = () => {
        return [
            {
                label: "工种名称", key: "jobName", labelSpan:6, fieldSpan: 16,
                rules: [
                    {required: true, message: '请输入工种名称'}
                ],
                item:(
                    <Input disabled={this.state.modalForm.isSubmitting} />
                )
            }
        ];
    };

    /**
     *  操作按钮
     */

    /**
     * helper method
     */
    

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
                    title={()=>`工种列表`}
                    rowKey={this.configuration.keyId}
                    loading={this.state.isLoading}
                    dataSource={this.state.dataSource}
                    columns={this.columns}
                    pagination={pagination}
                />
                <ModalForm
                    title={this.state.modalForm.modalTitle}
                    visible={this.state.modalForm.modalVisible}
                    width={this.state.modalForm.modalWidth}
                    formFields={this.getFormFields()}
                    formData={this.state.modalForm.formData}
                    formDataIdKey={this.state.modalForm.formDataIdKey}
                    isSubmitting={this.state.modalForm.isSubmitting}
                    saveFormRef={this.saveFormRef}
                    handleFormFieldsChange={this.handleFormFieldsChange}
                    handleSubmit={this.handleSubmit}
                    handleCancel={this.handleCancel}
                >
                </ModalForm>
            </div>
        );
    }
}


exports.JobManage = JobManage;
import React from 'react';
import moment from 'moment';
import { Table, message, Modal, Input, InputNumber, DatePicker } from 'antd';
import { ConditionContainer } from 'component/ConditionContainer.jsx';
import { ConditionSelect } from 'component/ConditionSelect.jsx';
import { ModalForm } from 'component/ModalForm.jsx';

const confirm = Modal.confirm;

class EmployeeManage extends React.Component {
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
            NOT_SELECT_MSG: "请先选择员工",
            OPERATION_SUCCESS_MSG: "操作成功",
            OPERATION_FAILED_MSG: "操作失败，请重试，或与管理员联系",

            //Table相关配置
            conditionConfigCode: "EMPLOYEE_MANAGE",
            keyId: "id",
            selectionType: "radio",
            getUrl: `${_ctx_}/base/employee/getEmployees`,
            saveUrl: `${_ctx_}/base/employee/save`,
            disableUrl: `${_ctx_}/base/employee/disable`,

            dateFormat: 'YYYY-MM-DD'

        };
        /**
         * Table相关定义
         */
        this.columns = [
            {title: '员工姓名', dataIndex: 'employeeName', key: 'employeeName', width: 140
            },
            {title: '身份证', dataIndex: 'idCard', key: 'idCard', width: 140
            },
            {title: '性别', dataIndex: 'genderValue', key: 'genderValue', width: 140
            },
            {title: '地区', dataIndex: 'locationValue', key: 'locationValue', width: 140
            },
            {title: '班组', dataIndex: 'groupName', key: 'groupName', width: 140
            },
            {title: '工种', dataIndex: 'jobName', key: 'jobName', width: 140
            },
            {title: '入职时间', dataIndex: 'entryDate', key: 'entryDate', width: 140
            },
            {title: '合同号', dataIndex: 'contractNo', key: 'contractNo', width: 140
            }
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
            case "insert":
            {
                this.setModalFormState({
                    modalTitle: "新增员工",
                    formData: {},
                    modalVisible: true
                });
                break;
            }
            case "update":
            {
                this.handleUpdate();
                break;
            }
            case "disable":
            {
                this.handleDisable();
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
        this.setState({ selectedRowKeys });
    };

    /**
     * ModalForm相关
     */
    setModalFormState = (newState, callback) => {
        const { modalForm } = this.state;
        const newModalForm = Object.assign({}, modalForm, newState);
        this.setState({
            modalForm: newModalForm
        }, callback || (()=>{}));
    };
    //引用Form
    saveFormRef = (form) => {
        this.state.modalForm.form = form;
    };
    //表单值改变
    handleFormFieldsChange = (props, values) => {
        console.log("EmployeeManage 表单值改变:props[%o], values[%o]", props, values);

        //保存正在编辑的数据
        Object.assign(this.state.modalForm.formData, values);

        //处理Moment类转为String
        Object.keys(this.state.modalForm.formData).map(key => {
            const value = this.state.modalForm.formData[key];
            if(value instanceof moment) {
                this.state.modalForm.formData[key] = value.format(this.configuration.dateFormat);
            }
        });

    };
    //Modal提交
    handleSubmit = () => {
        const form = this.state.modalForm.form.props.form;
        form.validateFields((err, values) => {
            if (!err) {
                this.setModalFormState({
                    isSubmitting: true
                }, () => {
                    this.doSave();
                });
            }
        });
    };
    //Modal取消
    handleCancel = (doSearch) => {
        this.setModalFormState({
            modalVisible: false
        }, () => {
            this.state.modalForm.modalTitle = "";
            this.state.modalForm.isSubmitting = false;
            this.state.modalForm.formData = {};

            if(doSearch){
                this.state.dataParam.current = 1;
                this.doSearch();
            }
        });
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

    handleUpdate = () => {
        if(this.state.selectedRows.length <= 0){
            message.info(this.configuration.NOT_SELECT_MSG);
            return;
        }
        let formData = {};
        Object.assign(formData, this.state.selectedRows[0]);

        this.setModalFormState({
            modalTitle: "编辑员工",
            formData: formData,
            modalVisible: true
        });
    };
    doSave = () => {
        $.ajax({
            url: this.configuration.saveUrl,
            type: 'POST',
            data: this.state.modalForm.formData,
            async: true,
            dataType: "json",
            success: (result) => {
                if (result.success) {
                    this.saveSuccess(result);
                } else {
                    this.saveFail(result);
                }
            },
            error: (result) => {
                this.saveFail(result);
            }
        });
    };
    saveSuccess = (result) => {
        message.success(result.msg||this.configuration.OPERATION_SUCCESS_MSG);
        this.handleCancel(true);
    };
    saveFail = (result) => {
        message.error(result.msg||this.configuration.OPERATION_FAILED_MSG, 3);
        this.setModalFormState({
            isSubmitting: false
        });
    };

    handleDisable = () => {
        if(this.state.selectedRows.length <= 0){
            message.info(this.configuration.NOT_SELECT_MSG);
            return;
        }
        confirm({
            title: '确定作废该员工?',
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
        message.success(result.msg||this.configuration.OPERATION_SUCCESS_MSG);
        this.state.dataParam.current = 1;
        this.doSearch();
    };
    disableFail = (result) => {
        message.error(result.msg||this.configuration.OPERATION_FAILED_MSG, 3);
    };

    //表单字段
    getFormFields = () => {
        return [
            {
                label: "员工姓名", key: "employeeName", labelSpan:6, fieldSpan: 16,
                rules: [
                    {required: true, message: '请输入员工姓名'}
                ],
                item:(
                    <Input disabled={this.state.modalForm.isSubmitting} />
                )
            },
            {
                label: "性别", key: "gender", labelSpan:6, fieldSpan: 16,
                rules: [
                    {required: true, message: '请选择性别'}
                ],
                item:(
                    <ConditionSelect conditionCode="GENDER" disabled={this.state.modalForm.isSubmitting}></ConditionSelect>
                )
            },
            {
                label: "身份证", key: "idCard", labelSpan:6, fieldSpan: 16,
                rules: [
                    {required: true, message: '请输入员工身份证号'}
                ],
                item:(
                    <Input disabled={this.state.modalForm.isSubmitting} />
                )
            },
            {
                label: "地区", key: "location", labelSpan:6, fieldSpan: 16,
                rules: [
                    {required: true, message: '请选择地区'}
                ],
                item:(
                    <ConditionSelect conditionCode="LOCATION" disabled={this.state.modalForm.isSubmitting}></ConditionSelect>
                )
            },
            {
                label: "住址", key: "address", labelSpan:6, fieldSpan: 16,
                rules: [

                ],
                item:(
                    <Input disabled={this.state.modalForm.isSubmitting} />
                )
            },
            {
                label: "电话", key: "phone", labelSpan:6, fieldSpan: 16,
                rules: [

                ],
                item:(
                    <Input disabled={this.state.modalForm.isSubmitting} />
                )
            },

            {
                label: "班组", key: "groupId", labelSpan:6, fieldSpan: 16,
                rules: [

                ],
                item:(
                    <ConditionSelect conditionCode="GROUP_SELECT" disabled={this.state.modalForm.isSubmitting}></ConditionSelect>
                )
            },
            {
                label: "工种", key: "jobId", labelSpan:6, fieldSpan: 16,
                rules: [

                ],
                item:(
                    <ConditionSelect conditionCode="JOB_SELECT" disabled={this.state.modalForm.isSubmitting}></ConditionSelect>
                )
            },

            {
                label: "工资银行", key: "salaryBank", labelSpan:6, fieldSpan: 16,
                rules: [
                    {required: true, message: '请选择工资银行'}
                ],
                item:(
                    <ConditionSelect conditionCode="SALARY_BANK" disabled={this.state.modalForm.isSubmitting}></ConditionSelect>
                )
            },
            {
                label: "工资账户", key: "salaryAccount", labelSpan:6, fieldSpan: 16,
                rules: [
                    {required: true, message: '请输入工资账户'}
                ],
                item:(
                    <Input disabled={this.state.modalForm.isSubmitting} />
                )
            },
            {
                label: "社保号", key: "socialSecurityNo", labelSpan:6, fieldSpan: 16,
                rules: [
                    {required: true, message: '请输入社保号'}
                ],
                item:(
                    <Input disabled={this.state.modalForm.isSubmitting} />
                )
            },
            {
                label: "个人社保金额", key: "personalSsAmount", labelSpan:6, fieldSpan: 16,
                rules: [

                ],
                item:(
                    <InputNumber disabled={this.state.modalForm.isSubmitting} min={0} step={0.1}/>
                )
            },
            {
                label: "单位社保金额", key: "companySsAmount", labelSpan:6, fieldSpan: 16,
                rules: [

                ],
                item:(
                    <InputNumber disabled={this.state.modalForm.isSubmitting} min={0} step={0.1}/>
                )
            },
            {
                label: "住房公积金", key: "houseFundAmount", labelSpan:6, fieldSpan: 16,
                rules: [

                ],
                item:(
                    <InputNumber disabled={this.state.modalForm.isSubmitting} min={0} step={0.1}/>
                )
            },


            {
                label: "入职日期", key: "entryDate", labelSpan:6, fieldSpan: 16,
                rules: [

                ],
                item:(
                    <DatePicker allowClear={true} disabled={this.state.modalForm.isSubmitting} format={this.configuration.dateFormat}/>
                )
            },
            {
                label: "合同号", key: "contractNo", labelSpan:6, fieldSpan: 16,
                rules: [
                    {required: true, message: '请输入合同号'}
                ],
                item:(
                    <Input disabled={this.state.modalForm.isSubmitting} />
                )
            },
        ];
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
                    title={()=>`员工列表`}
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
                <ModalForm
                    title={this.state.modalForm.modalTitle}
                    visible={this.state.modalForm.modalVisible}
                    width={this.state.modalForm.modalWidth}
                    isSubmitting={this.state.modalForm.isSubmitting}
                    formData={this.state.modalForm.formData}
                    formDataIdKey={this.state.modalForm.formDataIdKey}
                    formFields={this.getFormFields()}
                    saveFormRef={this.saveFormRef}
                    handleFormFieldsChange={this.handleFormFieldsChange}
                    handleSubmit={this.handleSubmit}
                    handleCancel={()=>{this.handleCancel(false)}}
                >
                </ModalForm>
            </div>
        );
    }
}



exports.EmployeeManage = EmployeeManage;
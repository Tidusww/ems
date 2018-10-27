import React from 'react';
import moment from 'moment';
import {Table, message, Modal, Input, DatePicker, InputNumber} from 'antd';
import {ConditionContainer} from 'component/ConditionContainer.jsx';
import {ConditionSelect} from 'component/ConditionSelect.jsx'
import {ModalForm} from 'component/ModalForm.jsx'
import {EditableTable} from 'component/EditableTable.jsx';
import {CommonHelper} from 'core/Common.jsx';

const confirm = Modal.confirm;
const {MonthPicker} = DatePicker;

class SystemConfig extends React.Component {
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
                formType: '',
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
            NOT_SELECT_MSG: "请先选择一条记录",
            OPERATION_SUCCESS_MSG: "操作成功",
            OPERATION_FAILED_MSG: "操作失败，请重试，或与管理员联系",

            //Table相关配置
            conditionConfigCode: "SYSTEM_CONFIG",
            keyId: "id",
            selectionType: "radio",
            getUrl: `${_ctx_}/system/config/getConfigs`,
            saveUrl: `${_ctx_}/system/config/saveConfig`,
            disableUrl: `${_ctx_}/`,

            //
            dateFormat: "YYYY-MM-DD",
            monthFormat: "YYYY-MM",
            dateTypes: ['HOLIDAY', 'OVERTIME_DAY'],
            monthTypes: ['HOT_ALLOWANCE_BEGIN_MONTH', 'HOT_ALLOWANCE_END_MONTH'],
            numberTypes: ['HOT_ALLOWANCE', 'SOCIAL_SECURITY_ALLOWANCE', 'HOUSE_FUND_ALLOWANCE']
        };
        /**
         * Table相关定义
         */
        this.columns = [
            {
                title: '参数类型', dataIndex: 'configTypeValue', key: 'configTypeValue', width: 200
            },
            {
                title: '参数值', dataIndex: 'configValue', key: 'configValue', width: 200,
                editable: true, formItem: (getFieldDecorator, itemKey, text, record, index, dataIndex) => {
                    const configType = record.configType;
                    if (this.configuration.dateTypes.indexOf(configType) != -1) {
                        return (getFieldDecorator(itemKey, {
                            initialValue: moment(text, this.configuration.dateFormat),
                            // rules: rules
                        })(
                            <DatePicker format={this.configuration.dateFormat}/>
                        ));
                    }
                    if (this.configuration.monthTypes.indexOf(configType) != -1) {
                        return (getFieldDecorator(itemKey, {
                            initialValue: moment(text, this.configuration.monthFormat),
                            // rules: rules
                        })(
                            <MonthPicker  format={this.configuration.monthFormat}/>
                        ));
                    }
                    if (this.configuration.numberTypes.indexOf(configType) != -1) {
                        return (getFieldDecorator(itemKey, {
                            initialValue: text,
                            // rules: rules
                        })(
                            <InputNumber min={0} step={0.1}/>
                        ));
                    }
                }
            },
            {
                title: '参数描述', dataIndex: 'configDesc', key: 'configDesc', width: 200
            },
            {
                title: '修改时间', dataIndex: 'updateDate', key: 'updateDate', width: 200
            },
            {
                title: '创建时间', dataIndex: 'createDate', key: 'createDate', width: 200
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
            case "configHoliday":
            {
                CommonHelper.setModalFormState(this, {
                    modalTitle: "新增节假日",
                    formType: 'date',
                    formData: {configType: 'HOLIDAY'},
                    modalVisible: true
                });
                break;
            }
            case "configOvertime":
            {
                CommonHelper.setModalFormState(this, {
                    modalTitle: "新增节假日",
                    formType: 'date',
                    formData: {configType: 'OVERTIME_DAY'},
                    modalVisible: true
                });
                break;
            }
            case "configHotBegin":
            {
                CommonHelper.setModalFormState(this, {
                    modalTitle: "新增高温费开始月份",
                    formType: 'month',
                    formData: {configType: 'HOT_ALLOWANCE_BEGIN_MONTH'},
                    modalVisible: true
                });

                break;
            }
            case "configHotEnd":
            {
                CommonHelper.setModalFormState(this, {
                    modalTitle: "新增高温费结束月份",
                    formType: 'month',
                    formData: {configType: 'HOT_ALLOWANCE_END_MONTH'},
                    modalVisible: true
                });
                break;
            }
            case 'configHotAllowance':
            {
                CommonHelper.setModalFormState(this, {
                    modalTitle: "设置高温补贴",
                    formType: 'number',
                    formData: {configType: 'HOT_ALLOWANCE_END_MONTH'},
                    modalVisible: true
                });
                break;
            }
            case 'configSocialSecurity':
            {
                CommonHelper.setModalFormState(this, {
                    modalTitle: "设置社保补贴",
                    formType: 'number',
                    formData: {configType: 'SOCIAL_SECURITY_ALLOWANCE'},
                    modalVisible: true
                });
                break;
            }
            case 'configHouseFund':
            {
                CommonHelper.setModalFormState(this, {
                    modalTitle: "设置住房补贴",
                    formType: 'number',
                    formData: {configType: 'HOUSE_FUND_ALLOWANCE'},
                    modalVisible: true
                });
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
     * ModalForm相关
     */
    //引用Form
    saveFormRef = (form) => {
        this.state.modalForm.form = form;
    };
    //表单值改变
    handleFormFieldsChange = (props, values) => {
        console.log("ProjectManage 表单值改变:props[%o], values[%o]", props, values);

        //保存正在编辑的数据
        Object.assign(this.state.modalForm.formData, values);

        //处理Moment类转为String
        CommonHelper.parseMomentValueToStringInDataObject(this.state.modalForm.formData, this.configuration.dateFormat);
    };
    //Modal提交
    handleSubmit = () => {
        const form = this.state.modalForm.form.props.form;
        form.validateFields((err, values) => {
            if (!err) {
                CommonHelper.setModalFormState(this, {
                    isSubmitting: true
                }, () => {
                    this.doSave();
                });
            }
        });
    };
    //Modal取消
    handleCancel = (doSearch) => {
        CommonHelper.setModalFormState(this, {
            modalVisible: false
        }, () => {
            this.state.modalForm.modalTitle = "";
            this.state.modalForm.isSubmitting = false;
            this.state.modalForm.formType = '';
            this.state.modalForm.formData = {};
            if (doSearch) {
                this.state.dataParam.current = 1;
                this.doSearch();
            }
        });
    };
    //表单字段
    getFormFields = () => {
        const formFields = [];
        formFields.push({
            label: "参数类型", key: "configType", labelSpan: 6, fieldSpan: 16,
            rules: [],
            item: (
                <ConditionSelect conditionCode="SYSTEM_CONFIG_TYPE" disabled={true}/>
            )
        });
        switch (this.state.modalForm.formType) {
            case 'date':
            {
                formFields.push({
                    label: "参数值", key: "configValue", labelSpan: 6, fieldSpan: 16,
                    rules: [
                        {required: true, message: '请选择日期'}
                    ],
                    item: (
                        <DatePicker allowClear={true} disabled={this.state.modalForm.isSubmitting}
                                    format={this.configuration.dateFormat}/>
                    )
                });
                break;
            }
            case 'month':
            {
                formFields.push({
                    label: "参数值", key: "configValue", labelSpan: 6, fieldSpan: 16,
                    rules: [
                        {required: true, message: '请选择月份'}
                    ],
                    item: (
                        <MonthPicker allowClear={true} disabled={this.state.modalForm.isSubmitting}
                                     format={this.configuration.monthFormat}/>
                    )
                });
                break;
            }
            case 'number':
            {
                formFields.push({
                    label: "参数值", key: "configValue", labelSpan: 6, fieldSpan: 16,
                    rules: [
                        {required: true, message: '请输入有效数字'}
                    ],
                    item: (
                        <InputNumber disabled={this.state.modalForm.isSubmitting} min={0} step={0.1}/>
                    )
                });
                break;
            }
        }
        formFields.push({
            label: "参数描述", key: "configDesc", labelSpan: 6, fieldSpan: 16,
            rules: [
                {required: true, message: '请输入参数描述'}
            ],
            item: (
                <Input disabled={this.state.modalForm.isSubmitting}/>
            )
        });
        return formFields;
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
        if (this.state.selectedRows.length <= 0) {
            message.info(this.configuration.NOT_SELECT_MSG);
            return;
        }
        let formData = {};
        Object.assign(formData, this.state.selectedRows[0]);

        CommonHelper.setModalFormState(this, {
            modalTitle: "编辑项目",
            formData: formData,
            modalVisible: true
        });
    };
    doSave = () => {
        this.doUpdate(this.state.modalForm.formData)
    };
    doUpdate = (changeData, index) => {
        $.ajax({
            url: this.configuration.saveUrl,
            type: 'POST',
            data: changeData,
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
        message.success(result.msg || this.configuration.OPERATION_SUCCESS_MSG);
        this.handleCancel(true);
    };
    saveFail = (result) => {
        message.error(result.msg || this.configuration.OPERATION_FAILED_MSG, 3);
        CommonHelper.setModalFormState(this, {
            isSubmitting: false
        });
    };

    handleDisable = () => {
        if (this.state.selectedRows.length <= 0) {
            message.info(this.configuration.NOT_SELECT_MSG);
            return;
        }
        confirm({
            title: '确定作废该项目?',
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
                    title={()=>`项目列表`}
                    rowKey={this.configuration.keyId}
                    loading={this.state.isLoading}
                    dataSource={this.state.dataSource}
                    columns={this.columns}
                    pagination={pagination}
                    scroll={{x: 1000}}
                    empty
                    rowEdit
                    onSaveRow={(changedData, index) => {
                        console.log('EditableTable 单行编辑，第', index, '行 内容：', changedData);
                        // 这里需要特殊处理，因为不同类型的系统参数，configValue值类型不一样，如果是moment类型需要转换为String
                        if (this.configuration.dateTypes.indexOf(changedData.configType) != -1) {
                            CommonHelper.parseMomentValueToStringInDataObject(changedData, this.configuration.dateFormat);
                        }
                        if (this.configuration.monthTypes.indexOf(changedData.configType) != -1) {
                            CommonHelper.parseMomentValueToStringInDataObject(changedData, this.configuration.monthFormat);
                        }

                        this.doUpdate(changedData, index);
                    }}
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


exports.SystemConfig = SystemConfig;
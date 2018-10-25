import React from 'react';
import moment from 'moment';
import { Table, message, Modal, Input, DatePicker } from 'antd';
import { ConditionContainer } from 'component/ConditionContainer.jsx';
import { ModalForm } from 'component/ModalForm.jsx'
import { ConditionSelect } from 'component/ConditionSelect.jsx'

const confirm = Modal.confirm;

class ProjectManage extends React.Component {
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
            NOT_SELECT_MSG: "请先选择项目",
            OPERATION_SUCCESS_MSG: "操作成功",
            OPERATION_FAILED_MSG: "操作失败，请重试，或与管理员联系",

            //Table相关配置
            conditionConfigCode: "PROJECT_MANAGE",
            keyId: "id",
            selectionType: "radio",
            getUrl: `${_ctx_}/base/project/getProjects`,
            saveUrl: `${_ctx_}/base/project/save`,
            disableUrl: `${_ctx_}/base/project/disable`,

            //
            dateFormat: "YYYY-MM-DD"
        };
        /**
         * Table相关定义
         */
        this.columns = [
            {title: '所属单位', dataIndex: 'companyName', key: 'companyName', width: 140
            },
            {title: '项目名称', dataIndex: 'projectName', key: 'projectName', width: 140
            },
            {title: '项目描述', dataIndex: 'projectDesc', key: 'projectDesc', width: 140
            },
            {title: '开工日期', dataIndex: 'startDate', key: 'startDate', width: 140
            },
            {title: '完工日期', dataIndex: 'endDate', key: 'endDate', width: 140
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
            case "insert":
            {
                this.setModalFormState({
                    modalTitle: "新增项目",
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
        console.log("ProjectManage 表单值改变:props[%o], values[%o]", props, values);
        
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
    //表单字段
    getFormFields = () => {
        return [
            {
                label: "所属单位", key: "companyId", labelSpan:6, fieldSpan: 16,
                rules: [
                    {required: true, message: '请选择所属单位'}
                ],
                item:(
                    <ConditionSelect conditionCode="COMPANY_SELECT" disabled={this.state.modalForm.isSubmitting}></ConditionSelect>
                )
            },
            {
                label: "项目名称", key: "projectName", labelSpan:6, fieldSpan: 16,
                rules: [
                    {required: true, message: '请输入项目名称'}
                ],
                item:(
                    <Input disabled={this.state.modalForm.isSubmitting} />
                )
            },
            {
                label: "项目描述", key: "projectDesc", labelSpan:6, fieldSpan: 16,
                rules: [
                    {required: true, message: '请输入项目描述'}
                ],
                item:(
                    <Input disabled={this.state.modalForm.isSubmitting} />
                )
            },
            {
                label: "开工日期", key: "startDate", labelSpan:6, fieldSpan: 16,
                rules: [
                    {required: true, message: '请选择开工日期'}
                ],
                item:(
                    <DatePicker allowClear={true} disabled={this.state.modalForm.isSubmitting} format={this.configuration.dateFormat} />
                )
            },
            {
                label: "完工日期", key: "endDate", labelSpan:6, fieldSpan: 16,
                rules: [
                    {required: true, message: '请选择完工日期'}
                ],
                item:(
                    <DatePicker allowClear={false} disabled={this.state.modalForm.isSubmitting} format={this.configuration.dateFormat} />
                )
            },
        ];
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
            modalTitle: "编辑项目",
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
        message.success(result.msg||this.configuration.OPERATION_SUCCESS_MSG);
        this.state.dataParam.current = 1;
        this.doSearch();
    };
    disableFail = (result) => {
        message.error(result.msg||this.configuration.OPERATION_FAILED_MSG, 3);
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
                    title={()=>`项目列表`}
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



exports.ProjectManage = ProjectManage;
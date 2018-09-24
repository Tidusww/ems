import React from 'react';
import {Table, message, Modal, Input, InputNumber} from 'antd';
import {CommonHelper} from 'core/Common.jsx';
import {ConditionContainer} from 'component/ConditionContainer.jsx';
import {ModalForm} from 'component/ModalForm.jsx'
import {ModalTable} from 'component/ModalTable.jsx'
const Search = Input.Search;
const confirm = Modal.confirm;

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
            },
            modalForm: {
                form: undefined,
                modalTitle: "",
                modalVisible: false,
                modalWidth: "40%",
                formData: {},
                formDataIdKey: "id",
                isSubmitting: false
            },
            employeeTable: {
                title: '选择员工',
                visible: false,
                conditionCode: `EMPLOYEE_MANAGE`,
                searchUrl: `${_ctx_}/base/employee/getEmployees`,
                keyId: "id",
                multiSelect: false
            },
            projectTable: {
                title: '选择项目',
                visible: false,
                conditionCode: `PROJECT_MANAGE`,
                searchUrl: `${_ctx_}/base/project/getProjects`,
                keyId: "id",
                multiSelect: false
            }
        };
        this.configuration = {
            //提示
            NOT_SELECT_MSG: "请先选择班组",
            OPERATION_SUCCESS_MSG: "操作成功",
            OPERATION_FAILED_MSG: "操作失败，请重试，或与管理员联系",

            //Table相关配置
            conditionConfigCode: "GROUP_MANAGE",
            keyId: "id",
            selectionType: "radio",
            getUrl: `${_ctx_}/base/group/getGroups`,
            saveUrl: `${_ctx_}/base/group/save`,
            disableUrl: `${_ctx_}/base/group/disable`

        };
        /**
         * Table相关定义
         */
        this.columns = [
            {
                title: '班组名称', dataIndex: 'groupName', key: 'groupName', width: 140
            },
            {
                title: '班组组长', dataIndex: 'employeeName', key: 'employeeName', width: 100
            },
            {
                title: '联系电话', dataIndex: 'phone', key: 'phone', width: 75
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
                this.setModalFormState({
                    modalTitle: "新增班组",
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
            case "employeeSelect":
            {
                this.employeeSelect();
                break;
            }
            case "dispatchProject":
            {
                this.dispatchProject();
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
    setModalFormState = (newState, callback) => {
        const {modalForm} = this.state;
        const newModalForm = Object.assign({}, modalForm, newState);
        this.setState({
            modalForm: newModalForm
        }, callback || (()=> {
            }));
    };
    //引用Form
    saveFormRef = (form) => {
        this.state.modalForm.form = form;
    };
    //表单值改变
    handleFormFieldsChange = (props, values) => {
        console.log("GroupManage 表单值改变:props[%o], values[%o]", props, values);
        //保存正在编辑的数据
        Object.assign(this.state.modalForm.formData, values);
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
        CommonHelper.setModalFormState(this, {
            modalVisible: false
        }, () => {
            this.state.modalForm.modalTitle = "";
            this.state.modalForm.isSubmitting = false;
            this.state.modalForm.formData = {};

            if (doSearch) {
                this.state.dataParam.current = 1;
                this.doSearch();
            }
        });
    };
    //表单字段
    getFormFields = () => {
        return [
            {
                label: "班组名称", key: "groupName", labelSpan: 6, fieldSpan: 16,
                rules: [
                    {required: true, message: '请输入班组名称'}
                ],
                item: (
                    <Input disabled={this.state.modalForm.isSubmitting}/>
                )
            }
        ];
    };

    /**
     * EmployeeTable
     */
    getEmployeeTableFields = () => {
        return [
            {
                title: '员工姓名', dataIndex: 'employeeName', key: 'employeeName', width: 140
            },
            {
                title: '身份证', dataIndex: 'idCard', key: 'idCard', width: 140
            },
            {
                title: '性别', dataIndex: 'gender', key: 'gender', width: 140
            },
            {
                title: '班组', dataIndex: 'groupName', key: 'groupName', width: 140
            },
            {
                title: '工种', dataIndex: 'jobName', key: 'jobName', width: 140
            },
            {
                title: '入职时间', dataIndex: 'entryDate', key: 'entryDate', width: 140
            }
        ];
    };
    /**
     * 设置班组长
     */
    employeeSelect = () => {
        if (this.state.selectedRows.length <= 0) {
            message.info(this.configuration.NOT_SELECT_MSG);
            return;
        }
        CommonHelper.setModalTableState(this, 'employeeTable', {
            visible: true
        });
    };
    handleEmployeeTableConfirm = (selectedRowKeys, selectedRows) => {
        const selectedGroup = this.state.selectedRows[0];
        selectedGroup.employeeId = selectedRowKeys[0];

        $.ajax({
            url: this.configuration.saveUrl,
            type: 'POST',
            data: selectedGroup,
            async: true,
            dataType: "json",
            success: (result) => {
                if (result.success) {
                    this.saveEmployeeSuccess(result);
                } else {
                    this.saveEmployeeFail(result);
                }
            },
            error: (result) => {
                this.saveEmployeeFail(result);
            }
        });

    };
    saveEmployeeSuccess = (result) => {
        message.success(result.msg || this.configuration.OPERATION_SUCCESS_MSG);
        this.handleEmployeeTableCancel(true);
    };
    saveEmployeeFail = (result) => {
        message.error(result.msg || this.configuration.OPERATION_FAILED_MSG, 3);
    };
    handleEmployeeTableCancel = (doSearch) => {
        CommonHelper.setModalTableState(this, 'employeeTable', {visible: false}, ()=> {
            if (doSearch) {
                this.state.dataParam.current = 1;
                this.doSearch();
            }
        })
    };


    /**
     * ProjectTable
     */
    getProjectTableFields = () => {
        return [
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
    };
    /**
     * 派遣项目
     */
    dispatchProject = () => {
        if (this.state.selectedRows.length <= 0) {
            message.info(this.configuration.NOT_SELECT_MSG);
            return;
        }
        CommonHelper.setModalTableState(this, 'projectTable', {
            visible: true
        });
    };
    handleProjectTableConfirm = (selectedRowKeys, selectedRows) => {
        const selectedGroup = this.state.selectedRows[0];
        selectedGroup.projectId = selectedRowKeys[0];

        $.ajax({
            url: this.configuration.saveUrl,
            type: 'POST',
            data: selectedGroup,
            async: true,
            dataType: "json",
            success: (result) => {
                if (result.success) {
                    this.saveProjectSuccess(result);
                } else {
                    this.saveProjectFail(result);
                }
            },
            error: (result) => {
                this.saveProjectFail(result);
            }
        });

    };
    saveProjectSuccess = (result) => {
        message.success(result.msg || this.configuration.OPERATION_SUCCESS_MSG);
        this.handleProjectTableCancel(true);
    };
    saveProjectFail = (result) => {
        message.error(result.msg || this.configuration.OPERATION_FAILED_MSG, 3);
    };
    handleProjectTableCancel = (doSearch) => {
        CommonHelper.setModalTableState(this, 'projectTable', {visible: false}, ()=> {
            if (doSearch) {
                this.state.dataParam.current = 1;
                this.doSearch();
            }
        })
    };

    /**
     *  增删查改
     */
    doSearch = () => {
        this.clearSelection();
        this.setState({isLoading: true});

        const _this = this;
        $.ajax({
            url: this.configuration.getUrl,
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

    handleUpdate = () => {
        if (this.state.selectedRows.length <= 0) {
            message.info(this.configuration.NOT_SELECT_MSG);
            return;
        }
        let formData = {};
        Object.assign(formData, this.state.selectedRows[0]);

        this.setModalFormState({
            modalTitle: "编辑班组",
            formData: formData,
            modalVisible: true
        });
    };
    doSave = () => {
        const that = this;
        $.ajax({
            url: this.configuration.saveUrl,
            type: 'POST',
            data: this.state.modalForm.formData,
            async: true,
            dataType: "json",
            success: function (result) {
                if (result.success) {
                    that.saveSuccess(result);
                } else {
                    that.saveFail(result);
                }
            },
            error: function (result) {
                that.saveFail(result);
            }
        });
    };
    saveSuccess = (result) => {
        message.success(result.msg || this.configuration.OPERATION_SUCCESS_MSG);
        this.handleCancel(true);
    };
    saveFail = (result) => {
        message.error(result.msg || this.configuration.OPERATION_FAILED_MSG, 3);
        this.setModalFormState({
            isSubmitting: false
        });
    };

    handleDisable = () => {
        if (this.state.selectedRows.length <= 0) {
            message.info(this.configuration.NOT_SELECT_MSG);
            return;
        }
        confirm({
            title: '确定作废该班组?',
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
        const that = this;
        $.ajax({
            url: this.configuration.disableUrl,
            type: 'POST',
            data: {id: this.state.selectedRowKeys[0]},
            async: true,
            dataType: "json",
            success: function (result) {
                if (result.success) {
                    that.disableSuccess(result);
                } else {
                    that.disableFail(result);
                }
            },
            error: function (result) {
                that.disableFail(result);
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
                    title={()=>`班组列表`}
                    rowKey={this.configuration.keyId}
                    loading={this.state.isLoading}
                    dataSource={this.state.dataSource}
                    columns={this.columns}
                    pagination={pagination}
                    rowSelection={rowSelection}
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
                <ModalTable
                    title={this.state.employeeTable.title}
                    visible={this.state.employeeTable.visible}
                    width="80%"
                    conditionConfigCode={this.state.employeeTable.conditionCode}
                    searchUrl={this.state.employeeTable.searchUrl}
                    keyId={this.state.employeeTable.keyId}
                    column={this.getEmployeeTableFields()}
                    multiSelect={this.state.employeeTable.multiSelect}
                    handleConfirm={this.handleEmployeeTableConfirm}
                    handleCancel={this.handleEmployeeTableCancel}
                >
                </ModalTable>
                <ModalTable
                    title={this.state.projectTable.title}
                    visible={this.state.projectTable.visible}
                    width="80%"
                    conditionConfigCode={this.state.projectTable.conditionCode}
                    searchUrl={this.state.projectTable.searchUrl}
                    keyId={this.state.projectTable.keyId}
                    column={this.getProjectTableFields()}
                    multiSelect={this.state.projectTable.multiSelect}
                    handleConfirm={this.handleProjectTableConfirm}
                    handleCancel={this.handleProjectTableCancel}
                >
                </ModalTable>
            </div>
        );
    }
}


exports.GroupManage = GroupManage;
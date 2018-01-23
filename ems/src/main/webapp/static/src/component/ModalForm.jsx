/**
 * 外面是Modal,里面包裹着Form
 * 简单的新增, 修改操作可以使用这个组件, 不用额外自己定义表单
 *
 * 使用方法:
 *      上层组件需要提供:
 *          title, visible, width, isSubmitting,
 *          formFields, formData, formDataIdKey
 *      回调:
 *          saveFormRef                 保存表单索引
 *          handleFormFieldsChange      表单字段改变
 *          handleSubmit                点击了提交
 *          handleCancel                点击了取消
 *
 *  简单例子:

        //1. state中定义:
            modalForm: {
                form: undefined,
                modalTitle: "",
                modalVisible: false,
                modalWidth: "40%",
                formData: {},
                formDataIdKey: "id",
                isSubmitting: false
            }
 
        //2. 上层组件中定义:
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
                console.log("JobManage 表单值改变:props[%o], values[%o]", props, values);
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
                        label: "名称", key: "name", labelSpan:6, fieldSpan: 16,
                        rules: [
                            {required: true, message: '请输入名称'}
                        ],
                        item:(
                            <Input disabled={this.state.modalForm.isSubmitting} />
                        )
                    }
                ];
            };
 
        //3. render中:
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

        //4. 调用
            //编辑
            showModalForm  = (record) => {
                let formData = {};
                Object.assign(formData, record);
                this.setModalFormState({
                    modalTitle: "",
                    formData: formData,
                    modalVisible: true
                });
            };
 */

import React from 'react';
import { Modal, Button, Input, Form, Row, Col } from 'antd';
import moment from 'moment';

const FormItem = Form.Item;

class ModalForm extends React.Component {
    static propTypes = {
        title: React.PropTypes.string,
        visible: React.PropTypes.bool,
        width: React.PropTypes.string,
        formFields: React.PropTypes.array,
        formData: React.PropTypes.object,
        formDataIdKey: React.PropTypes.string,
        isSubmitting: React.PropTypes.bool,
        saveFormRef: React.PropTypes.func,
        handleFormFieldsChange: React.PropTypes.func,
        handleSubmit: React.PropTypes.func,
        handleCancel: React.PropTypes.func
    };

    constructor(props) {
        super(props);
        this.state = {

        }
    }

    /**
     * 生命周期
     */
    componentDidMount = () => {
        // console.log("ModalForm DidMount:%o", this.props);
    };
    componentWillReceiveProps = (nextProps) => {
        // console.log("modalFormWillReceiveProps");
    };
    shouldComponentUpdate = (nextProps, nextState) => {
        if(!this.props.visible && !nextProps.visible){
            //从隐藏状态变为隐藏状态,不需要更新
            return false;
        }
        return true;
    };
    componentWillUpdate = (nextProps, nextState) => {

    };
    componentDidUpdate = (prevProps, prevState) => {
        // console.log("ModalForm DidUpdate");
    };
    componentWillUnmount = () => {
        // console.log("modalForm will unmount");
    };

    /**
     * events
     */

    render = () => {
        const modalFooter = [
            <Button size="large" loading={this.props.isSubmitting} onClick={this.props.handleCancel} key="cancel">取 消</Button>,
            <Button size="large" loading={this.props.isSubmitting} onClick={this.props.handleSubmit} key="confirm" type="primary" >提 交</Button>
        ];

        const WrappedInnerForm = Form.create({
            onValuesChange: this.props.handleFormFieldsChange
        })(InnerForm);

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
                    <WrappedInnerForm
                        wrappedComponentRef={this.props.saveFormRef}
                        formFields={this.props.formFields}
                        formData={this.props.formData}
                        formDataIdKey={this.props.formDataIdKey}
                    />
                )}
            </Modal>
        );
    }
}


class InnerForm extends React.Component {
    static propTypes = {
        formFields: React.PropTypes.array,
        formData: React.PropTypes.object,
        formDataIdKey: React.PropTypes.string,
        isSubmitting: React.PropTypes.bool
    };

    constructor(props) {
        super(props);
    }

    /**
     * 生命周期
     */
    componentDidMount = () => {
        console.log("InnerForm DidMount:%o", this.props);
    };
    componentWillReceiveProps = (nextProps) => {

    };
    shouldComponentUpdate = (nextProps, nextState) => {
        return true;
    };
    componentWillUpdate = (nextProps, nextState) => {

    };
    componentDidUpdate = (prevProps, prevState) => {
        console.log("InnerForm DidUpdate");
    };
    componentWillUnmount = () => {
        console.log("InnerForm will unmount");
    };


    /**
     * helper method
     */
    renderFormItems = () => {
        const formItemsMetadata = this.props.formFields;

        const formItems = [];
        for(let i = 0; i < formItemsMetadata.length; i++) {
            const meta = formItemsMetadata[i];
            formItems.push(this.renderFormItem(i, meta["label"], meta["key"], meta["labelSpan"], meta["fieldSpan"], meta["rules"], meta["item"], meta["colon"]));
        }
        return formItems;
    };

    renderFormItem = (i, itemLabel, itemKey, labelSpan, fieldSpan, rules, item, colon) => {
        const { getFieldDecorator } = this.props.form;
        const formItemLayout = {
            labelCol: { span: labelSpan },
            wrapperCol: { span: fieldSpan },
        };
        let initialValue = "";
        if(itemKey in this.props.formData){

            initialValue = this.props.formData[itemKey];
            if (initialValue instanceof Object
                || initialValue instanceof Array) {
                //对象和数组的暂不做处理


            }else{
                if(initialValue != undefined) {
                    //非空且非对象\数组的都统一转为字符串
                    initialValue = initialValue.toString();
                }
            }
        }

        return (
            <Col key={i} span={24}>
                <FormItem
                    {...formItemLayout}
                    label={itemLabel}
                    colon={colon}
                >
                    {getFieldDecorator(itemKey, {
                        initialValue : initialValue,
                        rules: rules
                    })(
                        item
                    )}
                </FormItem>
            </Col>
        );
    };

    render = () => {
        const { getFieldDecorator } = this.props.form;
        const { data } = this.props;

        return (
            <Form layout="horizontal">
                {getFieldDecorator(this.props.formDataIdKey,
                    {initialValue: this.props.formData[this.props.formDataIdKey]})
                (<Input type="hidden"/>)}
                <Row gutter={24}>
                    {this.renderFormItems()}
                </Row>
            </Form>
        );
    }
}




exports.ModalForm = ModalForm;
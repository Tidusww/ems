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
                console.log(" 表单值改变:props[%o], values[%o]", props, values);
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
            handleCancel = (proxy, doSearch) => {
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
import PropTypes from 'prop-types';
import { Modal, Button, Input, Form, Row, Col } from 'antd';
import moment from 'moment';

const FormItem = Form.Item;



class ModalForm extends React.Component {
    static propTypes = {
        title: PropTypes.string,
        visible: PropTypes.bool,
        width: PropTypes.string,
        formFields: PropTypes.array,
        formData: PropTypes.object,
        formDataIdKey: PropTypes.string,
        isSubmitting: PropTypes.bool,
        saveFormRef: PropTypes.func,
        handleFormFieldsChange: PropTypes.func,
        handleSubmit: PropTypes.func,
        handleCancel: PropTypes.func
    };

    constructor(props) {
        super(props);
    }

    /**
     * 生命周期
     */
    componentDidMount = () => {
        console.log("ModalForm DidMount:%o", this.props);
    };
    componentWillReceiveProps = (nextProps) => {
        console.log("modalFormWillReceiveProps");
    };
    shouldComponentUpdate = (nextProps, nextState) => {
        if((this.props.visible && !nextProps.visible) || (!this.props.visible && nextProps.visible)) {
            //可见状态发生变化才需要更新
            return true;
        }

        if((this.props.isSubmitting && !nextProps.isSubmitting) || (!this.props.isSubmitting && nextProps.isSubmitting)) {
            //可见状态发生变化才需要更新
            return true;
        }

        return false;
    };
    componentWillUpdate = (nextProps, nextState) => {

    };
    componentDidUpdate = (prevProps, prevState) => {
        console.log("ModalForm DidUpdate");
    };
    componentWillUnmount = () => {
        console.log("modalForm will unmount");
    };

    /**
     * events
     */

    render = () => {
        const modalFooter = [
            <Button size="large" loading={this.props.isSubmitting} onClick={this.props.handleCancel} key="cancel">取 消</Button>,
            <Button size="large" loading={this.props.isSubmitting} onClick={this.props.handleSubmit} key="confirm" type="primary" >提 交</Button>
        ];

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
                        onValuesChange={this.props.handleFormFieldsChange}
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
        formFields: PropTypes.array,
        formData: PropTypes.object,
        formDataIdKey: PropTypes.string,
        isSubmitting: PropTypes.bool
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
            formItems.push(this.renderFormItem(i, meta));
        }
        return formItems;
    };

    renderFormItem = (i, meta) => {
        const { label, key, labelSpan, fieldSpan, rules, item, colon } = meta;
        const { getFieldDecorator } = this.props.form;
        const formItemLayout = {
            labelCol: { span: labelSpan },
            wrapperCol: { span: fieldSpan }
        };

        //1. 表单字段值默认值
        let initialValue = "";
        if(key in this.props.formData){
            initialValue = this.props.formData[key];
        }

        //2. 处理表单字段类型
        if(initialValue instanceof moment || item.props.format != undefined) {
            //initialValue为moment 或者 控件存在format属性, 则当做日期控件, 单独处理
            if (typeof(initialValue) == 'string' && initialValue != "") {
                //如果外部传入的是非空的日期字符串, 要先转为moment类型
                initialValue = moment(initialValue, item.props.format);
            } else if (!(initialValue instanceof moment)) {
                //既不是字符串也不是moment, 就不管了
                initialValue = undefined;
            }
        }else if (initialValue instanceof Object
            || initialValue instanceof Array) {
            //对象和数组的暂不做处理

        }else {
            if(initialValue != undefined) {
                //非空且非对象\数组的都统一转为字符串
                initialValue = initialValue.toString();
            }
        }

        return (
            <Col key={i} span={24}>
                <FormItem
                    {...formItemLayout}
                    label={label}
                    colon={colon}
                >
                    {getFieldDecorator(key, {
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
//必须放在InnerForm后
const WrappedInnerForm = Form.create({
    onValuesChange: (props, values) => {
        props.onValuesChange(props, values);
    }
})(InnerForm);




exports.ModalForm = ModalForm;
import React from 'react';
import { Modal, Button, Input, Form, InputNumber, Row, Col } from 'antd';

const FormItem = Form.Item;

class ModalForm extends React.Component {
    propTypes:{
        title: React.PropTypes.string,
        visible: React.PropTypes.bool,
        width: React.PropTypes.object,
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
        console.log(this.props);
    };
    componentWillReceiveProps = (nextProps) => {

    };
    shouldComponentUpdate = (nextProps, nextState) => {
        return true;
    };
    componentWillUpdate = (nextProps, nextState) => {

    };
    componentDidUpdate = (prevProps, prevState) => {
        console.log("ModalFormDidUpdate");
    };
    componentWillUnmount = () => {
        //todo 取消请求
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
                <WrappedInnerForm
                    wrappedComponentRef={this.props.saveFormRef}
                    formFields={this.props.formFields}
                    formData={this.props.formData}
                    formDataIdKey={this.props.formDataIdKey}
                />
            </Modal>
        );
    }
}


class InnerForm extends React.Component {
    propTypes:{
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
        console.log(this.props.data);
    };
    componentWillReceiveProps = (nextProps) => {

    };
    shouldComponentUpdate = (nextProps, nextState) => {
        return true;
    };
    componentWillUpdate = (nextProps, nextState) => {

    };
    componentDidUpdate = (prevProps, prevState) => {
        console.log("InnerFormDidUpdate");
    };
    componentWillUnmount = () => {
        //todo 取消请求
    };


    /**
     * helper method
     */
    renderFormItems = () => {
        const formItemsMetadata = this.props.formFields;

        const formItems = [];
        for(let i = 0; i < formItemsMetadata.length; i++) {
            const meta = formItemsMetadata[i];
            formItems.push(this.renderFormItem(i, meta["label"], meta["key"], meta["labelSpan"], meta["fieldSpan"], meta["rules"], meta["item"]));
        }
        return formItems;
    };

    renderFormItem = (i, itemLabel, itemKey, labelSpan, fieldSpan, rules, item) => {
        const { getFieldDecorator } = this.props.form;
        const formItemLayout = {
            labelCol: { span: labelSpan },
            wrapperCol: { span: fieldSpan },
        };
        return (
            <Col key={i} span={24}>
                <FormItem
                    {...formItemLayout}
                    label={itemLabel}
                >
                    {getFieldDecorator(itemKey, {
                        initialValue : this.props.formData[itemKey],
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
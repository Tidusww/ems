import React from 'react';
import { Input, Form, InputNumber, Row, Col } from 'antd';

const FormItem = Form.Item;

class JobEditForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data: props.data
        }
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

    };
    componentWillUnmount = () => {
        //todo 取消请求
    };


    /**
     * helper method
     */
    renderFormItems = () => {
        const { data } = this.state;
        const { getFieldDecorator } = this.props.form;

        const formItemsMetadata = [
            {label: "工种名称", key: "jobName", labelSpan:6, fieldSpan: 16, decorator: getFieldDecorator('jobName', {
                initialValue : data["jobName"],
                rules: [{required: true, message: '请输入工种名称'}]
            })(
                <Input disabled={!this.props.editable} />
            )}
        ];

        const formItems = [];
        for(let i = 0; i < formItemsMetadata.length; i++) {
            const meta = formItemsMetadata[i];
            formItems.push(this.renderFormItem(i, meta["label"], meta["key"], meta["labelSpan"], meta["fieldSpan"], meta["decorator"]));
        }
        return formItems;
    };

    renderFormItem = (i, itemLabel, itemKey, labelSpan, fieldSpan, decorator) => {
        const formItemLayout = {
            labelCol: { span: labelSpan },
            wrapperCol: { span: fieldSpan }
        };
        return (
            <Col key={i} span={24}>
                <FormItem
                    {...formItemLayout}
                    label={itemLabel}
                >
                    {decorator}
                </FormItem>
            </Col>
        );
    };

    render = () => {
        const { getFieldDecorator } = this.props.form;
        const { data } = this.state;
        
        return (
            <Form layout="horizontal">
                {getFieldDecorator('id',
                    {initialValue: data["id"]})
                (<Input type="hidden"/>)}
                <Row gutter={24}>
                    {this.renderFormItems()}
                </Row>
            </Form>
        );
    }
}
exports.JobEditForm = JobEditForm;
/**
 * 自动根据conditionCode加载下拉项的下拉框
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
 *  <ConditionSelect conditionCode="" disabled={this.state.modalForm.isSubmitting}></ConditionSelect>
 **/
import React from 'react';
import { Select, Spin, message } from 'antd';
import { CommonHelper } from 'core/Common.jsx';

const { Option } = Select;


class ConditionSelect extends React.Component {
    propTypes:{
        conditionCode: React.PropTypes.string
        };

    constructor(props) {
        super(props);

        this.state = {
            conditionItem: undefined,
            isLoading: false,
            failed: false,
            value: this.props.value || ''
        }
    }

    /**
     * 生命周期
     */
    componentDidMount = () => {
        console.log("ConditionSelect DidMount:%o", this.props);
        if(!this.state.conditionItem){
            console.log("ConditionSelect LoadData");
            this.getConditionItem();
        }
    };
    componentWillReceiveProps = (nextProps) => {
        console.log("conditionSelect WillReceiveProps");
        // Should be a controlled component.
        if ('value' in nextProps) {
            const value = nextProps.value;
            this.setState({value});
        }
    };
    shouldComponentUpdate = (nextProps, nextState) => {
        if(this.state.conditionItem != nextState.conditionItem){
            return true;
        }
        if(this.state.value != nextProps.value){
            return true;
        }
        return false;
    };
    componentWillUpdate = (nextProps, nextState) => {

    };
    componentDidUpdate = (prevProps, prevState) => {
        console.log("ConditionSelect DidUpdate");
    };
    componentWillUnmount = () => {
        console.log("ConditionSelect willUnmount");
    };

    /**
     * handler
     * @param value
     */
    handleValueChange = (value) => {
        if (!('value' in this.props)) {
            this.setState({ value });
        }
        this.triggerChange(value);
    };

    /**
     * 针对被Form包装的回调
     * @param changedValue
     */
    triggerChange = (changedValue) => {
        // Should provide an event to pass value to Form.
        const onChange = this.props.onChange;
        if (onChange) {
            onChange(changedValue);
        }
    };


    /**
     * helper
     */
    getConditionItem = () => {
        this.setState({isLoading: true});

        const that = this;
        $.ajax({
            url: `${_ctx_}/conditionConfig/getSelectItem`,
            type: 'GET',
            data: {conditionCode: this.props.conditionCode},
            async: true,
            dataType: "json",
            success: function (result) {
                if (result.success) {
                    that.handleConditionItemResult(result.data)
                } else {
                    message.error(`加载ConditionSelect失败:${result.msg}`, 3);
                    that.handleGetConditionItemFailed(result);
                }

            },
            error: function (result) {
                message.error(`加载ConditionSelect失败:${result.msg}`, 3);
                that.handleGetConditionItemFailed(result);
            }
        });
    };
    handleConditionItemResult = (conditionItem) => {
        this.setState({conditionItem, failed: false, isLoading: false});
    };
    handleGetConditionItemFailed = (result) => {
        this.setState({conditionItem: {}, failed: true, isLoading: false});
    };

    /**
     *
     */
    parseSelectOptions = (item) => {
        if (!item.keyValueMaps) {
            return null;
        }

        return item.keyValueMaps.map(keyValueMap => {
            return (
                <Option key={keyValueMap.key}>{keyValueMap.value}</Option>
            );
        });
    };


    render = () => {
        const item = this.state.conditionItem;
        let extMap;
        if(item){
            extMap = CommonHelper.getExtMap(item.conditionExt);
        }
        return (

            <Spin
                delay="500"
                spinning={this.state.isLoading}
            >
                {
                    item ? (
                        <Select key={item.conditionCode} allowClear
                                disabled={this.props.disabled}
                                placeholder={item.conditionPlaceholder}
                                onChange={this.handleValueChange}
                                value={this.state.value||undefined}
                        >
                            {this.parseSelectOptions(item)}
                        </Select>
                    ) : (<Select></Select>)
                }

            </Spin>
        )
    };
}


exports.ConditionSelect = ConditionSelect;
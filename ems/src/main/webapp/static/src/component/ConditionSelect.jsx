/**
 * 自动根据conditionCode加载下拉项的下拉框
 *
 * 使用方法:
 *      上层组件需要提供:
 *          conditionCode
 *      回调:
 *          onChange
 *
 *  简单例子:
 *  <ConditionSelect conditionCode="" disabled={this.state.modalForm.isSubmitting}></ConditionSelect>
 **/
import React from 'react';
import PropTypes from 'prop-types';
import { Select, Spin, message } from 'antd';
import { CommonHelper } from 'core/Common.jsx';
import { Cache } from 'core/Cache.jsx';

const { Option } = Select;


class ConditionSelect extends React.Component {
    static propTypes = {
        conditionCode: PropTypes.string,
        onChange: PropTypes.func
    };

    constructor(props) {
        super(props);

        this.state = {
            conditionItem: undefined,
            isLoading: false,
            failed: false,
            value: props.value || ''
        }
    }

    /**
     * 生命周期
     */
    componentDidMount = () => {
        // console.log("ConditionSelect DidMount:%o", this.props);
        if(!this.state.conditionItem){
            // console.log("ConditionSelect LoadData");
            this.getConditionItem();
        }
    };
    componentWillReceiveProps = (nextProps) => {
        if ('value' in nextProps) {
            const value = nextProps.value;
            this.setState({value});
        }
    };
    shouldComponentUpdate = (nextProps, nextState) => {
        if(this.state.conditionItem != nextState.conditionItem){
            return true;
        }
        if(this.state.value != nextState.value){
            return true;
        }
        if((this.props.disabled && !nextProps.disabled) || (!this.props.disabled && nextProps.disabled)) {
            return true;
        }


        return false;
    };
    componentWillUpdate = (nextProps, nextState) => {

    };
    componentDidUpdate = (prevProps, prevState) => {
        // console.log("ConditionSelect DidUpdate");
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
            //如果props中没有value, 说明不受form控制, 所以才需要setState来自己维护state(受form控制时不应setState)
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

        if(Cache[this.props.conditionCode]) {
            this.setState({isLoading: false});
            this.handleConditionItemResult(Cache[this.props.conditionCode]);
            return;
        }

        $.ajax({
            url: `${_ctx_}/conditionConfig/getSelectItem`,
            type: 'GET',
            data: {conditionCode: this.props.conditionCode},
            async: true,
            dataType: "json",
            success: (result) => {
                if (result.success) {
                    Cache[this.props.conditionCode] = result.data;
                    this.handleConditionItemResult(result.data)
                } else {
                    message.error(`加载ConditionSelect失败:${result.msg}`, 3);
                    this.handleGetConditionItemFailed(result);
                }

            },
            error: (result) => {
                message.error(`加载ConditionSelect失败:${result.msg}`, 3);
                this.handleGetConditionItemFailed(result);
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
                        <Select key={item.conditionCode}
                                allowClear={this.props.allowClear}
                                disabled={this.props.disabled}
                                style={this.props.style}
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
import 'css/core.css';
import React from 'react';
import {Spin, Button, Input, Select, DatePicker, message, Collapse} from 'antd';
import moment from 'moment';
import {Constants} from 'core/Const.jsx';
import { CommonHelper } from 'core/Common.jsx'


const Panel = Collapse.Panel;
const { RangePicker } = DatePicker;
const { Option } = Select;

class ConditionContainer extends React.Component {
    propTypes:{
        configCode: React.PropTypes.string.isRequired,
        conditionValues: React.PropTypes.array,
        onItemChange: React.PropTypes.func,
        onItemPressEnter: React.PropTypes.func,
        onButtonClick: React.PropTypes.func,
        conditionDidLoad: React.PropTypes.func
    };

    constructor(props) {

        super(props);
        //console.log(this.props.cusConditionLeft);
        this.state = {
            isLoading: true,
            failed: false,
            shouldUpdate: false,
            conditionItems: [],                             //所有条件字段
            parentConditionKeys: [],                        //被作为父下拉框的字段
            conditionValues: this.props.conditionValues||{} //保存所有条件的值
        };

        /**
         * 事件
         */
        this.onItemChange = (conditionKey, value) => {
            const conditionValues = this.state.conditionValues;
            const conditionItems = this.state.conditionItems;

            // 判断是否要清空子控件的值
            conditionItems.map(item => {
                //item为子控件
                if (item.conditionType == Constants.CONST_CONDITION_TYPE_SELECT
                    && item.conditionParentKey
                    && item.conditionParentKey == conditionKey
                    && conditionValues[conditionKey] != value/*父控件值发生变化*/) {

                    delete conditionValues[item.conditionKey];
                    this.props.onItemChange(item.conditionKey, "");
                }
            });

            if(!conditionValues[conditionKey] || conditionValues[conditionKey] != value){
                //值发生变化才通知
                conditionValues[conditionKey] = value;
                this.props.onItemChange(conditionKey, value);
                this.state.shouldUpdate = true;
                this.setState({conditionValues: conditionValues}, ()=>{
                    this.state.shouldUpdate = false;
                });
            }
        };
        this.handleDatePickerChange = (date, dateString) => {
            this.setState({value: dateString});
        };
        this.onItemPressEnter = (conditionKey) => {
            this.props.onItemPressEnter(conditionKey);
        };
        this.onButtonClick = (conditionKey) => {
            this.props.onButtonClick(conditionKey);
        };
        this.onReloadButtonClick = () => {
            this.getCondition();
        };
    }

    /**
     * 生命周期
     */
    componentDidMount = () => {
        this.getCondition();
    };
    componentWillReceiveProps = (nextProps) => {
        if(nextProps.conditionValues != this.props.conditionValues){
            this.setState({conditionValues: nextProps.conditionValues})
        }
    };
    shouldComponentUpdate = (nextProps, nextState) => {
        if(this.state.shouldUpdate || nextState.conditionItems !== this.state.conditionItems){
            // console.log("condition should update");
            return true;
        }
        // console.log("conditionon shouldn't update");
        return false;
    };
    componentWillUpdate = (nextProps, nextState) => {

    };
    componentDidUpdate = (prevProps, prevState) => {
        if( prevState.conditionItems != this.state.conditionItems ) {
            if (this.props.conditionDidLoad){
                this.props.conditionDidLoad();
            }
        }
    };
    componentWillUnmount = () => {
        //todo 取消请求
    };

    /**
     * helper
     */
    getCondition = () => {
        this.setState({isLoading: true});

        const that = this;
        $.ajax({
            url: `${_ctx_}/conditionConfig/getConditions`,
            type: 'GET',
            data: {configCode: this.props.configCode},
            async: true,
            dataType: "json",
            success: function (result) {
                //console.log("请求成功:" + result);
                if (result.success) {
                    that.handleConditionsResult(result.data)
                } else {
                    message.error(`条件组件初始化失败:${result.msg}`, 3);
                    that.handleGetConditionFailed(result);
                }

            },
            error: function (result) {
                message.error(`条件组件初始化失败:${result.msg}`, 3);
                that.handleGetConditionFailed(result);
            }
        });
    }
    handleConditionsResult = (conditionItems) => {
        //保存父下拉框key值, 监听变化清空子控件的值
        const parentConditionKeys = [];
        conditionItems.map(item => {
            if (item.conditionParentKey) {
                parentConditionKeys.push(item.conditionParentKey);
            }
        });
        this.setState({conditionItems, parentConditionKeys, failed: false, isLoading: false});
    };
    handleGetConditionFailed = (result) => {
        this.setState({failed: true, isLoading: false});
    };


    /**
     * parse component
     */
    parseInput = (item, key) => {
        const {conditionValues} = this.state;
        const extMap = CommonHelper.getExtMap(item.conditionExt);
        return (
            <Input key={item.conditionCode}
                   addonBefore={item.conditionName}
                   placeholder={item.conditionPlaceholder}
                   style={{ width:parseInt(extMap["width"])||180 }}
                   onChange={(e)=>this.onItemChange(item.conditionKey, e.target.value)}
                   onPressEnter={()=>this.onItemPressEnter(item.conditionKey)}
                   value={conditionValues[item.conditionKey]||undefined}
            />
        );
    };

    parseButton = (item, key, parsePrimary) => {
        const extMap = CommonHelper.getExtMap(item.conditionExt);
        if(parsePrimary){
            return extMap["type"] == "primary" ? (
                <Button key={item.conditionCode}
                        type={extMap["type"]}
                        icon={extMap["icon"]}
                        style={{ width:parseInt(extMap["width"])||180 }}
                        onClick={()=>this.onButtonClick(item.conditionKey)}>{item.conditionName}</Button>
            ) : (null);
        } else {
            return extMap["type"] != "primary" ? (
                <Button key={item.conditionCode}
                        type={extMap["type"]}
                        icon={extMap["icon"]}
                        style={{ width:parseInt(extMap["width"])||180 }}
                        onClick={()=>this.onButtonClick(item.conditionKey)}>{item.conditionName}</Button>
            ) : (null);
        }

    };

    /**
     * TODO Select 暂未实现默认值
     */
    parseSelect = (item, key) => {
        const {conditionValues} = this.state;
        const extMap = CommonHelper.getExtMap(item.conditionExt);
        return (
            <Select key={item.conditionCode} allowClear placeholder={item.conditionPlaceholder}
                    style={{ width:parseInt(extMap["width"])||180 }}
                    onChange={(value)=>this.onItemChange(item.conditionKey, value)}
                    value={conditionValues[item.conditionKey]||undefined}
            >
                {this.parseSelectOptions(item)}
            </Select>
        )
    };
    parseSelectOptions = (item) => {
        if(!item.keyValueMaps){
            return null;
        }

        // 非联动下拉框,直接输出
        if (!item.conditionParentKey) {
            return item.keyValueMaps.map(keyValueMap => {
                return (
                    <Option key={keyValueMap.key}>{keyValueMap.value}</Option>
                );
            });
        }

        const conditionParentKeyValue = this.state.conditionValues[item.conditionParentKey];
        if (!conditionParentKeyValue) {
            // 如果父下拉框的值为空则直接输出
            return item.keyValueMaps.map(keyValueMap => {
                return (
                    <Option key={keyValueMap.key}>{keyValueMap.value}</Option>
                );
            });
        } else {
            // 联动下拉框,根据父下拉框的值过滤选项
            const options = [];
            item.keyValueMaps.map(keyValueMap => {
                if (keyValueMap.parentKey == conditionParentKeyValue) {
                    options.push(<Option key={keyValueMap.key}>{keyValueMap.value}</Option>);
                }
            });
            return options;
        }
    };

    parseDatePicker = (item, key) => {
        const extMap = CommonHelper.getExtMap(item.conditionExt);
        const defaultMoment = moment().hour(0).minute(0).second(0);
        return (
            <DatePicker
                key={item.conditionCode}
                allowClear
                placeholder={item.conditionPlaceholder}
                format={extMap["format"]}
                showTime={extMap["showTimeFormat"] ? {
                    format: extMap["showTimeFormat"],
                    defaultValue: moment('00:00:00', extMap["showTimeFormat"])
                } : false}
                onChange={(value, dateString)=>this.onItemChange(item.conditionKey, dateString)}
                defaultValue={defaultMoment}
            />
        );
    };

    /**
     * TODO RangePicker 暂未实现默认值
     */
    parseRangePicker = (item, key) => {
        const keys = item.conditionKey.split(',');

        const extMap = CommonHelper.getExtMap(item.conditionExt);
        const format = extMap["format"];
        const showTimeFormat = extMap["showTimeFormat"];
        const showTimeDefault = extMap["showTimeDefault"].split(',');

        //设置默认值
        const defaultStartMoment = moment().hour(0).minute(0).second(0);
        const defaultEndMoment = moment().hour(0).minute(0).second(0).add(1, 'days').subtract(1, 'seconds');
        // if(!this.state.conditionValues[keys[0]]){
        //     // this.onItemChange(keys[0], defaultStartMoment.format(format));
        //     this.state.conditionValues[keys[0]] = defaultStartMoment;
        // }
        // if(!this.state.conditionValues[keys[1]]) {
        //     // this.onItemChange(keys[1], defaultEndMoment.format(format));
        //     this.state.conditionValues[keys[1]] = defaultEndMoment;
        // }

        return (
            <RangePicker
                key={item.conditionCode}
                allowClear
                placeholder={item.conditionPlaceholder.split(',')}
                format={format}
                showTime={showTimeFormat ? {
                    format: showTimeFormat,
                    defaultValue: [moment(showTimeDefault[0], showTimeFormat), moment(showTimeDefault[1], showTimeFormat)]
                } : false}
                onChange={(value, dateString)=>{
                    this.onItemChange(keys[0], dateString[0]);
                    this.onItemChange(keys[1], dateString[1]);
                }}
                value={[this.state.conditionValues[keys[0]]?moment(this.state.conditionValues[keys[0]], format):undefined,
                        this.state.conditionValues[keys[1]]?moment(this.state.conditionValues[keys[1]], format):undefined]}
                defaultValue={[defaultStartMoment, defaultEndMoment]}
            />
        );
    };


    render = () => {
        const {conditionItems} = this.state;
        return (
            <Spin
                delay="500"
                spinning={this.state.isLoading}
            >
                <div className={`condition-container ${this.state.failed?"condition-container-failed":""}`}>
                    {!this.state.failed ? (
                        <Collapse defaultActiveKey={['1']}>
                            <Panel header="查询条件" key="1">
                                <div className="condition-left">
                                    { conditionItems.length > 0 ?
                                        (conditionItems.map((item, key) => {
                                            switch (item.conditionType) {
                                                case Constants.CONST_CONDITION_TYPE_INPUT:
                                                {
                                                    return this.parseInput(item, key);
                                                }
                                                case Constants.CONST_CONDITION_TYPE_SELECT:
                                                {
                                                    return this.parseSelect(item, key);
                                                }
                                                case Constants.CONST_CONDITION_TYPE_DATE:
                                                {
                                                    return this.parseDatePicker(item, key);
                                                }
                                                case Constants.CONST_CONDITION_TYPE_RANGE:
                                                {
                                                    return this.parseRangePicker(item, key);
                                                }
                                            }
                                        })) : (<div></div>)
                                    }
                                </div>
                                <div className="condition-left">
                                    {conditionItems.length > 0 ?
                                        (conditionItems.map((item, key) => {
                                            switch (item.conditionType) {
                                                case Constants.CONST_CONDITION_TYPE_BUTTON:
                                                {
                                                    return this.parseButton(item, key, false);
                                                }
                                            }
                                        })) : (<div></div>)
                                    }
                                    {this.props.cusConditionLeft}
                                </div>
                                <div className="condition-right">
                                    { conditionItems.length > 0 ?
                                        (conditionItems.map((item, key) => {
                                            switch (item.conditionType) {
                                                case Constants.CONST_CONDITION_TYPE_BUTTON:
                                                {
                                                    return this.parseButton(item, key, true);
                                                }
                                            }
                                        })) : (<div></div>)
                                    }
                                </div>
                            </Panel>
                        </Collapse>) : (
                        <div>
                            <Button type="normal" icon=""
                                    style={{width: 180, textAlign: "center"}}
                                    onClick={this.onReloadButtonClick}>重新加载</Button>
                        </div>
                    )
                    }
                </div>
            </Spin>
        );
    }
}

exports.ConditionContainer = ConditionContainer;
import 'css/core.css';
import React from 'react';
import PropTypes from 'prop-types';
import { Input, Select, DatePicker } from 'antd';
import moment from 'moment';
import { Constants } from 'core/Const.jsx';



class EditableCell extends React.Component {
    static propTypes = {
        value: PropTypes.string.isRequired,       //单元格值
        isEditing: PropTypes.string.isRequired,   //是否正在编辑
        operation: PropTypes.string.isRequired,   //接受的操作:保存 / 取消
        cellType: PropTypes.string.isRequired,    //单元格类型
        defaultValue: PropTypes.string,           //编辑时value为空的默认值
        valueMap: PropTypes.object,               //Select类型用到的下拉数据
        dateFormat: PropTypes.string,             //DatePicker类型的日期格式
        timeFormat: PropTypes.string,             //DatePicker类型的时间格式
        save: PropTypes.func,                     //保存的回调
        cancel: PropTypes.func                    //取消的回调
    };

    constructor (props) {
        super(props);
        this.state = {
            value: props.value,
            isEditing: props.isEditing || false
        };
        this.handleInputChange = (e) => {
            const value = e.target.value;
            this.setState({value:value});
        };
        this.handleSelectChange = (value) => {
            this.setState({value:value});
        };
        this.handleDatePickerChange = (date, dateString) => {
            this.setState({value:dateString});
        };

    }

    /**
     * 生命周期
     */
    componentWillReceiveProps = (nextProps)  =>  {
        // console.log(`componentWillReceiveProps:`);

        if (nextProps.value !== this.state.value) {
            //外部更新了值
            this.setState({value: nextProps.value});
        }

        if (nextProps.isEditing !== this.state.isEditing) {
            //编辑状态发生改变
            this.setState({isEditing: nextProps.isEditing});

            if (nextProps.isEditing) {
                //准备进入编辑状态,保存初始值
                this.cacheValue = this.state.value;
            }
        }
        
        if (nextProps.operation && nextProps.operation !== this.props.operation) {
            //需要操作
            if (nextProps.operation === Constants.CONST_OPERATION_SAVE) {
                //保存最终值
                if(this.props.save){
                    switch(this.props.cellType) {
                        case Constants.CONST_EDITABLE_CELL_TYPE_INPUT:
                        case Constants.CONST_EDITABLE_CELL_TYPE_SELECT: 
                        case Constants.CONST_EDITABLE_CELL_TYPE_DATE_PICKER: {
                            if(!this.state.value){
                                //如果没有值,则取默认值
                                this.state.value = this.props.defaultValue;
                            }
                            this.props.save(this.state.value);
                            break;
                        }
                    }
                }
            } else if (nextProps.operation === Constants.CONST_OPERATION_CANCEL) {
                //恢复原始值
                this.setState({value: this.cacheValue});
                if(this.props.cancel){
                    this.props.cancel();
                }
            }
        }
    };

    shouldComponentUpdate = (nextProps, nextState) =>  {
        //接受新属性且与现有编辑状态不同,或者值发生改变
        return  nextProps.isEditing !== this.state.isEditing || nextState.value !== this.state.value;
    };
    componentDidUpdate = (prevProps, prevState) => {

    };

    /**
     * helper method
     */
    renderEditableCell = () => {
        const { value } = this.state;
        const { valueMap, cellType, dateFormat, timeFormat, defaultValue } = this.props;

        switch(cellType) {
            case Constants.CONST_EDITABLE_CELL_TYPE_INPUT: {
                return (
                    <Input
                        value={value||defaultValue}
                        onChange={e => this.handleInputChange(e)}
                    />
                );
            }
            case Constants.CONST_EDITABLE_CELL_TYPE_SELECT: {
                return (
                    <Select style={{ width: '100%' }}
                            onChange={this.handleSelectChange}
                            value={value||defaultValue}
                    >
                        {Object.keys(valueMap).map(key=>(
                            <Option value={key}>{valueMap[key]}</Option>
                        ))}
                    </Select>
                );
            }
            case Constants.CONST_EDITABLE_CELL_TYPE_DATE_PICKER: {
                const dateValue = moment(value||defaultValue, dateFormat);
                console.log(dateValue);
                return (
                    <DatePicker
                        showTime={timeFormat ? {format: timeFormat} : false}
                        value={dateValue}
                        format={dateFormat}
                        allowClear={false}
                        onChange={this.handleDatePickerChange}
                    />
                );
            }
        }
    };
    renderUnEditableCell = () => {
        const { value } = this.state;
        const { valueMap, cellType } = this.props;

        switch(cellType) {
            case Constants.CONST_EDITABLE_CELL_TYPE_INPUT: {
                return (
                    (<div className="editable-row-text">
                        {value ? value.toString() : Constants.CONST_RENDER_NULL_VALUE}
                    </div>)
                );
            }
            case Constants.CONST_EDITABLE_CELL_TYPE_SELECT: {
                return (
                    (<div className="editable-row-text">
                        {(value&&valueMap) ? valueMap[value] : Constants.CONST_RENDER_NULL_VALUE}
                    </div>)
                );
            }
            case Constants.CONST_EDITABLE_CELL_TYPE_DATE_PICKER: {
                return (
                    (<div className="editable-row-text">
                        {value ? value.toString() : Constants.CONST_RENDER_NULL_VALUE}
                    </div>)
                );
            }
        }
    };
    
    render = () => {
        const { isEditing } = this.state;
        return (
            <div>
                { isEditing ? this.renderEditableCell() : this.renderUnEditableCell() }
            </div>
        );
    }
}

exports.EditableCell = EditableCell;
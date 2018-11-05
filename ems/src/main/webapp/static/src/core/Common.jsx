import React from 'react';
import moment from 'moment';
import {EditableCell} from 'component/EditableCell.jsx'
import {Constants} from 'core/Const.jsx';


const CommonHelper = {
    renderNullValue: (value, valueMap) => {
        if (!value) {
            return Constants.CONST_RENDER_NULL_VALUE;
        } else if (!valueMap) {
            return value;
        } else {
            return CommonHelper.renderNullValue(valueMap[value]);
        }
    },
    renderEditableCell: (index, record, key, value, ext, saveFunc, cancelFunc) => {
        const {_isEditing_, _operation_} = record;

        return (<EditableCell
            isEditing={_isEditing_}
            operation={_operation_}
            value={value}
            defaultValue={ext.defaultValue}
            cellType={ext.cellType}
            valueMap={ext.valueMap}//value:text
            dateFormat={ext.dateFormat || ''}//
            timeFormat={ext.timeFormat || ''}//
            save={(newValue) => saveFunc(index, record, key, newValue)}
            cancel={cancelFunc}
        />);
    },
    /**
     * 获取condition_ext中的属性
     * @param conditionExt { String }
     * @returns { Object }
     */
    getExtMap: (conditionExt) => {
        const extMap = {};
        if (!conditionExt) {
            return extMap;
        }
        const extArray = conditionExt.split(';');
        if (!extArray || extArray.length == 0) {
            return extMap;
        }

        for (let i = 0; i < extArray.length; i++) {
            const ext = extArray[i];
            const extKeyValue = ext.split('=');
            if (!extKeyValue || extKeyValue.length != 2) {
                continue;
            }
            const extKey = extKeyValue[0], extValue = extKeyValue[1];
            extMap[extKey] = extValue;
        }

        return extMap;
    },
    setModalFormState: (context, newState, callback) => {
        const {modalForm} = context.state;
        const newModalForm = Object.assign({}, modalForm, newState);
        context.setState({
            modalForm: newModalForm
        }, callback || (()=> {
            }));
    },
    // setModalTableState : (context, newState, callback) => {
    //     const { modalTable } = context.state;
    //     const newModalTable = Object.assign({}, modalTable, newState);
    //     context.setState({
    //         modalTable: newModalTable
    //     }, callback || (()=>{}));
    // },
    setModalTableState: (context, oldTargetStateName, newTargetState, callback) => {
        const oldTargetState = context.state[oldTargetStateName];
        const finalNewTargetState = Object.assign({}, oldTargetState, newTargetState);
        const newState = {};
        newState[oldTargetStateName] = finalNewTargetState;
        context.setState(newState, callback || (()=> {
            }));
    },
    /**
     * 参数Map拼装成url请求字符串
     * @param paramArray
     * @returns {string}
     */
    getNewUrlWithParam: (originUrl, param) => {
        let newUrl = originUrl;
        if (!param || param.length == 0) {
            return newUrl;
        }

        let paramStr = "";
        for (const attr in param) {
            paramStr += "&" + attr + "=" + param[attr];
        }

        const index = originUrl.indexOf("?");
        if (index != -1) {
            newUrl = newUrl + paramStr;
        } else {
            newUrl = newUrl + "?" + paramStr.substring(1, paramStr.length);
        }

        return newUrl;
    },
    parseMomentValueToStringInDataObject: (dataObject, format) => {
        Object.keys(dataObject).map(key => {
            const value = dataObject[key];
            if (value instanceof moment) {
                dataObject[key] = value.format(format);
            }
        });
    },
    // 获取某月的最大天数
    getDaysInOneMonth: (year, month) => {
        var d= new Date(year, month, 0);
        return d.getDate();
    },
    // 配合Table onRow属性，实现点击即选中/取消该行
    selectRow: (context, record) => {
        const selectedRowKeys = [...context.state.selectedRowKeys];
        if (context.configuration.selectionType === 'radio') {
            // 单选
            if (selectedRowKeys.indexOf(record.id) >= 0) {
                // 清空
                selectedRowKeys.splice(0, selectedRowKeys.length);
            } else {
                selectedRowKeys.splice(0, selectedRowKeys.length);
                selectedRowKeys.push(record.id);
            }
        } else if (context.configuration.selectionType === 'checkbox') {
            // 多选
            if (selectedRowKeys.indexOf(record.id) >= 0) {
                selectedRowKeys.splice(selectedRowKeys.indexOf(record.id), 1);
            } else {
                selectedRowKeys.push(record.id);
            }
        }
        context.setState({selectedRowKeys});
    }
};

exports.CommonHelper = CommonHelper;
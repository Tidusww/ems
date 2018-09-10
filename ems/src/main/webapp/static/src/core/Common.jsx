import React from 'react';
import { EditableCell } from 'component/EditableCell.jsx'
import {Constants} from 'core/Const.jsx';


const CommonHelper = {
    renderNullValue : (value, valueMap) => {
        if (!value) {
            return Constants.CONST_RENDER_NULL_VALUE;
        } else if (!valueMap) {
            return value;
        } else {
            return CommonHelper.renderNullValue(valueMap[value]);
        }
    },
    renderEditableCell : (index, record, key, value, ext, saveFunc, cancelFunc) => {
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
    getExtMap : (conditionExt) => {
        const extMap = {};
        if(!conditionExt){
            return extMap;
        }
        const extArray = conditionExt.split(';');
        if(!extArray || extArray.length == 0){
            return extMap;
        }
    
        for(let i=0; i<extArray.length; i++){
            const ext = extArray[i];
            const extKeyValue = ext.split('=');
            if(!extKeyValue || extKeyValue.length != 2) {
                continue;
            }
            const extKey=extKeyValue[0], extValue=extKeyValue[1];
            extMap[extKey] = extValue;
        }
    
        return extMap;
    },
    setModalFormState : (context, newState, callback) => {
        const { modalForm } = context.state;
        const newModalForm = Object.assign({}, modalForm, newState);
        context.setState({
            modalForm: newModalForm
        }, callback || (()=>{}));
    },
    setModalTableState : (context, newState, callback) => {
        const { modalTable } = context.state;
        const newModalTable = Object.assign({}, modalTable, newState);
        context.setState({
            modalTable: newModalTable
        }, callback || (()=>{}));
    }
};

exports.CommonHelper = CommonHelper;
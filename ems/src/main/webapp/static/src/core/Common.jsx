// product begin
import React from 'react';
import { EditableCell } from '../core/EditableCell.jsx'
import { Constants } from '../core/Const.jsx';
// product end


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
    }
}

exports.CommonHelper = CommonHelper;
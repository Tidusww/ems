import React from 'react';
import PropTypes from 'prop-types';
import {Form, Table, Input, Button} from 'antd';
import clonedeep from 'lodash.clonedeep';
import isEqual from 'lodash/fp/isEqual';
import concat from 'lodash/fp/concat';
require('css/editable.css');

const FormItem = Form.Item;
const noop = () => {
};

class EditableTable extends React.Component {
    static propTypes = {
        className: PropTypes.string,
        empty: PropTypes.bool,
        rowEdit: PropTypes.oneOfType([
            PropTypes.bool,
            PropTypes.object
        ]),
        onSaveTable: PropTypes.func,
        onSaveRow: PropTypes.func
    }

    static defaultProps = {
        className: '',
        empty: false,
        rowEdit: false,
        onSaveTable: noop,
        onSaveRow: noop
    }

    constructor(props) {
        super(props);
        this.sourceKeyList = [];
        this.originRowClassName = this.props.rowClassName;
        this.state = {
            tableEditing: false,
            rowEditing: false,
            savingTable: false,
            savingRow: false,
            tables: this.handleTableProps(this.props),
            editRowIndex: -1
        };

        this.editTable = this.editTable.bind(this);
        this.saveTable = this.saveTable.bind(this);
        this.cancelTable = this.cancelTable.bind(this);
        this.editRow = this.editRow.bind(this);
        this.saveRow = this.saveRow.bind(this);
        this.cancelRow = this.cancelRow.bind(this);
    }

    componentDidMount = () => {
        console.log("EditableTable DidMount:%o", this.props);
    };
    componentWillReceiveProps = (nextProps) => {
        console.log("EditableTable WillReceiveProps, %o", nextProps);
    };
    shouldComponentUpdate(nextProps, nextState) {
        console.log("EditableTable shouldComponentUpdate, %o, %o", nextProps, nextState);
        const nextTableProps = this.handleTableProps(nextProps);

        // 1、编辑、保存状态改变
        if (nextState.tableEditing != this.state.tableEditing
            || nextState.rowEditing != this.state.rowEditing
            || nextState.savingTable != this.state.savingTable
            || nextState.savingRow != this.state.savingRow) {
            return true;
        }

        // 2、table的loading状态改变
        if (nextTableProps.loading != this.state.tables.loading) {
            this.state.tables = nextTableProps;
            return true;
        }

        // 3、dataSource改变
        if (!isEqual(nextTableProps.dataSource, this.state.tables.dataSource)) {
            this.state.tables = nextTableProps;
            return true;
        }

        return true;
    }
    componentDidUpdate = (prevProps, prevState) => {
        console.log("EditableTable DidUpdate");
    };

    /**
     * 根据props还原出antd table适用的props
     * @param props
     */
    handleTableProps(props) {
        const {getFieldProps, getFieldDecorator} = props.form;
        const {rowEdit} = props;

        // 1、Copy一份，去除EditableTable的属性
        const tableProps = clonedeep(props);
        if (Object.hasOwnProperty.call(tableProps, 'className')) {
            delete tableProps.className;
        }
        if (Object.hasOwnProperty.call(tableProps, 'empty')) {
            delete tableProps.empty;
        }
        if (Object.hasOwnProperty.call(tableProps, 'rowEdit')) {
            delete tableProps.rowEdit;
        }
        if (Object.hasOwnProperty.call(tableProps, 'onSaveTable')) {
            delete tableProps.onSaveTable;
        }
        if (Object.hasOwnProperty.call(tableProps, 'onSaveRow')) {
            delete tableProps.onSaveRow;
        }

        // 2、处理表字段
        const {columns} = tableProps;
        columns.map((col) => {
            // 2.1、使用 getFieldDecorator 包装字段
            if (col.editable) {
                const originRender = col.render;
                col.render = (text, record, index) => {
                    return (
                        <div className="hermes-editable-td">
                            <div className="hermes-editable-origin">
                                { originRender ? originRender(text, record, index) : text }
                            </div>
                            <FormItem className="hermes-editable-form-item">
                                {
                                    col.formItem ?
                                        col.formItem(getFieldDecorator, col.dataIndex + (index + 1), text, record, index, col.dataIndex) :
                                        (
                                            getFieldDecorator(id, {
                                                initialValue: text,
                                                // rules: rules
                                            })(
                                                <Input/>
                                            )
                                        )


                                }
                            </FormItem>
                        </div>
                    );
                };
            }

            // 2.2、保存需要编辑的字段dataIndex
            if (Object.prototype.hasOwnProperty.call(col, 'editable')) {
                this.sourceKeyList.push(col.dataIndex);
                delete col.editable;
            }

            // 2.3、保存需要进行校验的字段dataIndex
            if (Object.prototype.hasOwnProperty.call(col, 'verify')) {
                // TODO 控制字段是否需要校验
                delete col.verify;
            }

            return col;
        });

        // 3、添加操作列
        if (rowEdit) {
            let operation = {
                title: rowEdit.title || '操作',
                key: rowEdit.key || 'operation',
                dataIndex: rowEdit.dataIndex || 'operation',
                fixed: 'left',
                width: 120,
                render: (text, record, index) => {
                    const originRender = rowEdit.render;
                    const {rowEditing, tableEditing, savingRow, editRowIndex} = this.state;
                    const edit = (
                        <div className="edit-row-wrapper">
                            <span className={`edit-row-btn${tableEditing ? ' disabled' : ''}`}
                                  onClick={() => { this.editRow(index); }}
                                  style={{ display: ((rowEditing && index === editRowIndex) ? 'none' : 'inline') }}>编辑</span>
                            <div className="handel-row-edit"
                                 style={{ display: ((rowEditing && index === editRowIndex) ? 'inline' : 'none') }}>
                                <span className="edit-row-btn" onClick={(e) => { this.saveRow(index, e); }}>保存</span>
                                <span className="ft-bar">|</span>
                                <span className={`edit-row-btn${savingRow ? ' disabled' : ''}`}
                                      onClick={this.cancelRow}>取消</span>
                            </div>
                        </div>
                    );
                    return (
                        <div className="hermes-edit-row">
                            { originRender ? originRender(text, record, index, edit) : edit }
                        </div>
                    );
                }
            };
            if (rowEdit.width) operation.width = rowEdit.width;
            if (rowEdit.className) operation.className = rowEdit.className;
            if (rowEdit.fixed) operation.className = rowEdit.fixed;
            // 放到第一列
            tableProps.columns = concat([operation], columns);
            // columns.push(operation);


            // 3.1、调整宽度
            const {scroll} = tableProps;
            if (scroll) {
                scroll.x += operation.width
            }
        }


        return tableProps;
    }

    editTable() {
        if (this.state.tableEditing) return;
        this.setState({
            tableEditing: true
        });
    }

    saveTable(e) {
        e.preventDefault();
        if (this.state.savingTable) return;
        this.props.form.validateFields((errors, values) => {
            if (!errors) {
                this.setState({
                    savingTable: true
                });

                const dataSource = this.state.tables.dataSource;
                dataSource.map((data, idx) => {
                    this.sourceKeyList.map((item) => {
                        if (values[item + (idx + 1)]) {
                            data[item] = values[item + (idx + 1)];
                        }
                        return data[item];
                    });
                    return data;
                });
                const savePro = async() => {
                    await this.props.onSaveTable(clonedeep(dataSource));
                };

                savePro().then(() => {
                    dataSource.map((item) => {
                        Object.keys(item).map((i) => {
                            if (item[i] instanceof Array) {
                                item[i] = item[i].join(',');
                            }
                            return item[i];
                        });
                        return item;
                    });

                    this.setState({
                        savingTable: false,
                        tableEditing: false
                    });
                });
            }
        });
    }

    cancelTable() {
        if (!this.state.tableEditing) return;
        this.setState({
            tableEditing: false
        });
        if (this.props.empty) {
            this.props.form.resetFields();
        }
    }

    editRow(idx) {
        if (this.state.tableEditing) return;

        let rowClassName = (record, index) => {
            let temp = this.originRowClassName ? `${this.originRowClassName(record, index)} ` : '';
            return (idx === index) ? `${temp}row-editing` : `${temp}`;
        };
        this.state.tables.rowClassName = rowClassName;
        this.setState({
            rowEditing: true,
            editRowIndex: idx
        });
    }

    saveRow(idx, e) {
        e.preventDefault();
        if (this.state.savingRow) return;
        this.props.form.validateFields((errors, values) => {
            if (!errors) {
                this.setState({
                    savingRow: true
                });

                // 将内部form数据覆盖到指定行的数据源上
                // 注意这里不能修改到state中的数据源，避免如果form中的数据可能不为String导致的Table错误
                const dataSource = clonedeep(this.state.tables.dataSource[idx]);
                Object.keys(dataSource).map((dataIndex) => {
                    if (values[dataIndex + (idx + 1)]) {
                        dataSource[dataIndex] = values[dataIndex + (idx + 1)];
                    }
                    return dataSource[dataIndex];
                });
                const savePro = async() => {
                    await this.props.onSaveRow(dataSource, idx);
                };

                savePro().then(() => {
                    let rowClassName = (record, index) => {
                        let temp = this.originRowClassName ? `${this.originRowClassName(record, index)} ` : '';
                        return temp;
                    };
                    this.state.tables.rowClassName = rowClassName;
                    Object.keys(dataSource).map((item) => {
                        if (dataSource[item] instanceof Array) {
                            dataSource[item] = dataSource[item].join(',');
                        }
                        return dataSource[item];
                    });
                    this.setState({
                        savingRow: false,
                        rowEditing: false,
                        editRowIndex: -1
                    });
                });
            }
        });
    }

    cancelRow() {
        if (!this.state.rowEditing) return;
        let rowClassName = (record, index) => {
            let temp = this.originRowClassName ? `${this.originRowClassName(record, index)} ` : '';
            return temp;
        };
        this.state.tables.rowClassName = rowClassName;
        this.setState({
            rowEditing: false,
            editRowIndex: -1
        });
        if (this.props.empty) {
            this.props.form.resetFields();
        }
    }

    findParent(ele, parentTag) {
        let parentNode = ele.parentNode;
        while (parentNode) {
            if (parentNode.tagName.toLowerCase() === parentTag) break;
            parentNode = parentNode.parentNode;
        }
        return parentNode;
    }

    render() {
        const {className} = this.props;
        const {tableEditing, rowEditing, saving, tables} = this.state;

        return (
            <div
                className={`hermes-editable-table-block${tableEditing ? ' table-editing' : ''}${className.length ? ` ${className}` : ''}`}>
                <Form>
                    <Table {...tables} />
                </Form>
            </div>
        );
    }
}

EditableTable = Form.create({
    // onValuesChange: (props, values) => {
    //     props.onValuesChange(props, values);
    // }
})(EditableTable);

exports.EditableTable = EditableTable;

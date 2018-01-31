/**
 * 为了解决 Transfer 不支持在 Form 中使用的问题, 对 Transfer 进行二次封装
 * 1. 将 Form 中传入的 value 转换为 targetKeys/targetValues
 * 2. 接收 Transfer 的回调, 并将 targetKeys/targetValues 返回给 Form
 *
 * 使用方法:
 *      该组件接收的属性与 Transfer 一致, 并原封不动传入到 Transfer 中('targetKeys'和'onChange'除外)
 *      dataSourceCode: 如果dataSource是一个定义在数据库中的conditionItem, 那么传这个code进来加载远程数据
 *      回调:
 *          onChange([])        选中项改变后的回调
 *
 *  简单例子:
 *
            <WrappedTransfer
                dataSource={this.getSeasonDataSource()}
                listStyle={{ width: '40%' }}
                titles={['可选季节', '已选季节']}
                operations={}
                rowKey={item => item.seasonId}
                render={item => item.seasonName}
            />
 */


import React from 'react';
import { Transfer, Spin, message } from 'antd';

class WrappedTransfer extends React.Component {
    static propTypes = {
        dataSourceCode: React.PropTypes.string,
        value: React.PropTypes.array
    };

    constructor (props) {
        super(props);
        this.state = {
            targetKeys: props.value || [],
            dataSource: props.dataSource || [],
            isLoading: false,
            remoteDataSourceLoaded: false,
        }
    }


    /**
     * 生命周期
     */
    componentDidMount = () => {
        console.log("WrappedTransfer DidMount:%o", this.props);
        if(!this.props.dataSource && this.props.dataSourceCode){
            //优先使用外部静态dataSource
            console.log("WrappedTransfer LoadData");
            this.getConditionItem();
        }else{
            //用本地数据时,先生成一次form的value
            this.handleChange(this.parseTargetKeys(this.props.value));
        }
    };
    componentWillReceiveProps = (nextProps) => {
        // 响应Form表单传入的value
        // Form传入的是Item(包含key和value), 组件只需要key就可以了, 所以需要转换一下
        const targetItems = nextProps.value;
        const targetKeys = this.parseTargetKeys(targetItems);
        this.setState({targetKeys});
    };
    shouldComponentUpdate = (nextProps, nextState) => {
        //加载完数据
        if(nextState.remoteDataSourceLoaded != this.state.remoteDataSourceLoaded){
            return true;
        }

        //对比 nextState.targetKeys 和 this.state.targetKeys
        //这里知道keys是只会保存字符串的,不考虑对象的情况
        if(JSON.stringify(nextState.targetKeys.sort()) !== JSON.stringify(this.state.targetKeys.sort())) {
            return true;
        }

        return false;
    };
    componentWillUpdate = (nextProps, nextState) => {

    };
    componentDidUpdate = (prevProps, prevState) => {
        console.log("WrappedTransfer DidUpdate");
        if(prevState.remoteDataSourceLoaded != this.state.remoteDataSourceLoaded){
            //加载完数据更新完后生成一次form的value
            this.handleChange(this.parseTargetKeys(this.props.value));
        }
    };
    componentWillUnmount = () => {
        console.log("WrappedTransfer willUnmount");
    };

    /**
     * handler
     * @param value
     */
    handleChange = (targetKeys, direction, moveKeys) => {
        const { dataSource } = this.state;
        const newDataSource = targetKeys.map(targetKey => {

            //先找出选中的item
            for(let i=0; i<dataSource.length; i++){
                const item = dataSource[i];
                if(this.props.dataSourceCode){
                    //加载了远程数据
                    if(item.key == targetKey) {
                        return Object.assign({}, item);
                    }
                }else{
                    //使用外部数据
                    if(this.props.rowKey){
                        if(this.props.rowKey(item) == targetKey) {
                            return Object.assign({}, item);
                        }
                    }else {
                        if(item.key == targetKey) {
                            return Object.assign({}, item);
                        }
                    }
                }
            }

        });

        //被Form包装的回调
        const onChange = this.props.onChange;
        if (onChange) {
            onChange(newDataSource);
        }
    };

    /**
     * helper
     */
    parseTargetKeys = (targetItems) => {
        const targetKeys = targetItems.map(item => {
            if(this.props.dataSourceCode) {
                //加载了远程数据
                return item.key;
            }else{
                //使用外部数据
                if(this.props.rowKey){
                    return this.props.rowKey(item);
                }else {
                    return item.key;
                }
            }
        });
        return targetKeys;
    }


    getConditionItem = () => {
        this.setState({isLoading: true});

        $.ajax({
            url: `${_ctx_}/conditionConfig/getSelectItem`,
            type: 'GET',
            data: {conditionCode: this.props.dataSourceCode},
            async: true,
            dataType: "json",
            success: result => {
                if (result.success) {
                    this.handleConditionItemResult(result.data)
                } else {
                    message.error(`加载dataSource失败:${result.msg}`, 3);
                    this.handleGetConditionItemFailed(result);
                }

            },
            error: result => {
                message.error(`加载dataSource失败:${result.msg}`, 3);
                this.handleGetConditionItemFailed(result);
            }
        });
    };
    handleConditionItemResult = (conditionItem) => {
        this.setState({dataSource: conditionItem.keyValueMaps, remoteDataSourceLoaded: true, isLoading: false});
    };
    handleGetConditionItemFailed = (result) => {
        this.setState({dataSource: [], remoteDataSourceLoaded: false, isLoading: false});
    };

    render = () => {
        const { rowKey, render } = this.props;
        const { targetKeys, remoteDataSourceLoaded, isLoading } = this.state;
        return (
            <Spin
                delay="500"
                spinning={ isLoading }
            >
                <Transfer
                    {...this.props}
                    targetKeys={ targetKeys }
                    onChange={this.handleChange }
                    dataSource={ remoteDataSourceLoaded ? this.state.dataSource : this.props.dataSource }
                    rowKey={ remoteDataSourceLoaded ? item => item.key : rowKey }
                    render={ remoteDataSourceLoaded ? item => item.value : render }
                />
            </Spin>
        );
    }
}

exports.WrappedTransfer = WrappedTransfer;

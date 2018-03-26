
import React from 'react';
import { Button, message, Input } from 'antd';
const InputGroup = Input.Group;

class CacheManage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //button状态
            isRefreshingConditionCache: false

        };
        this.configuration = {
            //提示
            OPERATION_SUCCESS_MSG: "操作成功",
            OPERATION_FAILED_MSG: "操作失败，请重试，或与管理员联系",
            //url
            refreshConditionUrl: `${_ctx_}/system/refreshConditionCache`
        };
    }

    /**
     * 生命周期
     */
    componentDidMount = () => {
        
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


    //同步班级
    refreshConditionCache = () =>{
        this.setState({
            isRefreshingConditionCache: true
        });
        this.doAction(this.configuration.refreshConditionUrl, {

        },() => {
            this.setState({
                isRefreshingConditionCache: false
            });
        }, () => {
            this.setState({
                isRefreshingConditionCache: false
            });
        })
    };

    /**
     *  操作按钮
     */
    doAction = (url, data, handleSuccess, handleFail) => {

        $.ajax({
            url: url,
            type: 'POST',
            data: data,
            async: true,
            dataType: "json",
            success: function (result) {
                if (result.success) {
                    message.success("操作成功", 3);
                    handleSuccess();
                } else {
                    message.error("操作失败，请重试，或与管理员联系", 3);
                    handleFail();
                }
            },
            error: function (result) {
                message.error("操作失败，请重试，或与管理员联系", 3);
                handleFail();
            }
        });
    };

    render = () => {

        return (
            <div>
                <Button style={{margin:10}} type="primary" icon="sync" loading={this.state.isRefreshingConditionCache} onClick={this.refreshConditionCache}> 刷新静态条件缓存 </Button>
                <br />
            </div>
        );
    }
}


exports.CacheManage = CacheManage;
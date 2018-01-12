require(`./css/login.css`);
//控件
import React from 'react';
import ReactDOM from 'react-dom';
import { Button, Input, Icon, message } from 'antd';

//页面

//Common

class Login extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            loginButtonText: '登 录',
            doingLoginText: '登录中',
            doingLogin: false,
            username: '',
            password: ''
        };
        this.usernameInput = null;
        this.passwordInput = null;
    }

    /**
     * 生命周期
     */
    componentDidMount = () => {

    };

    /**
     * 事件
     */
    onChangeUsername = (e) => {
        this.setState({ username: e.target.value });
    };
    onChangePassword = (e) => {
        this.setState({ password: e.target.value });
    };

    onLoginClick = () => {
        const { username, password } = this.state;
        if(!username){
            message.warning('请输入通行证帐号', 5);
            this.usernameInput.focus();
            return;
        }
        if(!password){
            message.warning('请输入密码', 5);
            this.passwordInput.focus();
            return;
        }

        this.doLogin();
        this.setState({ doingLogin: true});
    };

    doLogin = () => {
        $.ajax({
            url: `${_ctx_}/doLogin`,
            type: 'POST',
            data: {
                username: this.state.username,
                password: this.state.password
            },
            async: true,
            dataType: "json",
            success: (result) => {
                if(result.success){
                    location.replace(`${_ctx_}/loginRedirect`);
                }else {
                    this.loginFail(result.msg);
                }
            },
            error: (result) => {
                this.loginFail("登陆失败，请检查是否连接网络");
            }
        });
    };

    loginFail = (msg) => {
        message.error(msg||'登录失败，请与管理员联系', 5);
        this.setState({ doingLogin: false});
    };

    clearUsername = () => {
        if(this.state.doingLogin){
            return;
        }
        this.setState({ username: '', password: ''});
        this.usernameInput.focus();
    };
    clearPassword = () => {
        if(this.state.doingLogin){
            return;
        }
        this.setState({ password: ''});
        this.passwordInput.focus();
    };

    render = () => {
        const { username, password, doingLogin, loginButtonText, doingLoginText } = this.state;
        const usernameSuffix = (username && !doingLogin) ? <Icon type="close-circle" onClick={this.clearUsername} /> : null;
        const passwordSuffix = (password && !doingLogin) ? <Icon type="close-circle" onClick={this.clearPassword} /> : null;
        return (
            <div>
                <div className="login-bg"></div>
                <div className="login-container">
                    <div className="login-title">
                        <img className="logo" src={require('./images/logo.png')}></img>
                        <p className="title">{`${_companyName_}`}人力资源管理系统</p>
                    </div>
                    <div className="login-box">
                        <Input
                            className="input-wrapper"
                            disabled={doingLogin}
                            placeholder="请输入通行证账号"
                            prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.3)' }} />}
                            suffix={usernameSuffix}
                            value={username}
                            onChange={this.onChangeUsername}
                            onPressEnter={this.onLoginClick}
                            ref={node => this.usernameInput = node}
                        />
                        <Input
                            className="input-wrapper"
                            type="password"
                            disabled={doingLogin}
                            placeholder="请输入密码"
                            prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.3)' }} />}
                            suffix={passwordSuffix}
                            value={password}
                            onChange={this.onChangePassword}
                            onPressEnter={this.onLoginClick}
                            ref={node => this.passwordInput = node}
                        />
                        <Button className="login-button" type="primary" icon="login" loading={this.state.doingLogin} onClick={this.onLoginClick}>
                            {doingLogin ? doingLoginText : loginButtonText}
                        </Button>
                    </div>
                </div>
            </div>
        );

    }
}

ReactDOM.render((
    <Login></Login>
), document.getElementById('loginContainer'));

require("./css/index.css");
require("./css/core.css");
//控件
import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter as Router, Route, Link, Switch} from 'react-router-dom';
import {Menu, Layout, Icon, Avatar, Dropdown, Button, Spin} from 'antd';

//国际化 needs antd-3.1.4
import { LocaleProvider } from 'antd';
import zh_CN from 'antd/lib/locale-provider/zh_CN';
import 'moment/locale/zh-cn';
import moment from 'moment';
moment.locale('zh-cn');

//Common
const {SubMenu} = Menu;
const {Header, Content, Footer, Sider} = Layout;

//页面
import {EmployeeManage} from 'baseInfoManage/employeeManage/EmployeeManage.jsx'
import {GroupManage} from 'baseInfoManage/groupManage/GroupManage.jsx'
import {JobManage} from 'baseInfoManage/jobManage/JobManage.jsx'
import {CompanyManage} from 'baseInfoManage/companyManage/CompanyManage.jsx'
import {ProjectManage} from 'baseInfoManage/projectManage/ProjectManage.jsx'

import {AttendanceManage} from 'attendance/AttendanceManage.jsx'

import {CacheManage} from 'system/CacheManage.jsx'

//菜单组件
const EmployeeManageComponent = ({match}) => (
    <EmployeeManage />
);
const GroupManageComponent = ({match}) => (
    <GroupManage />
);
const AreaManageComponent = ({match}) => (
    <AreaManage />
);
const JobManageComponent = ({match}) => (
    <JobManage />
);
const CompanyManageComponent = ({match}) => (
    <CompanyManage />
);
const ProjectManageComponent = ({match}) => (
    <ProjectManage />
);


const AttendanceManageComponent = ({match}) => (
    <AttendanceManage />
);

const CacheManageComponent = ({match}) => (
    <CacheManage />
);



class App extends React.Component {
    state = {
        collapsed: false,
        currentUrl: "",
        loading: false,
        failed: false,
        menus: [],
        routeAndComponent: {}
    };
    handleRequestMenuList = (result) => {
        if (result.success) {
            //成功获取内容
            // console.log(result);
            this.setState({menus: result.data.menus, routeAndComponent: result.data.routeAndComponent, loading: false});
        }else {
            this.setState({loading: false, failed: true});
        }
    };
    //生命周期
    componentDidMount = () => {
        this.doGetMenu();
    };
    doGetMenu = () => {
        // console.log("props.menuUrl is " + this.props.menuUrl);
        if (!this.props.menuUrl) {
            console.error("props.menuUrl is empty !");
            return;
        }
        this.setState({loading: true});
        $.get(this.props.menuUrl, this.handleRequestMenuList);
    };
    toggle = () => {
        this.setState({collapsed: !this.state.collapsed});
    };
    onMenuClick = (item) => {
        this.setState({currentUrl: item.key});
    };
    handleLogout = () => {
        window.location.href = `${_ctx_}/logout`;
    };


    render = () => {

        //用户菜单
        const userMenu = (
            <Menu selectable={false} onClick={this.handleLogout}>
                <Menu.Item>
                    <span><a>登出</a></span>
                    <Icon type="logout" style={{float: 'right', position: 'relative', top: 3}}/>
                </Menu.Item>
            </Menu>
        );

        return (
            <Layout className="app-layout">
                <Sider
                    trigger={null}
                    collapsible
                    collapsed={this.state.collapsed}
                >
                    <div className="app-logo"/>
                    <Spin
                        delay="500"
                        size="large"
                        tip="菜单加载中..."
                        spinning={this.state.loading}
                        style={{top:200}}
                    >
                        <Menu
                            theme="dark"
                            mode="inline"
                            //defaultSelectedKeys={['1']}
                            inlineCollapsed={this.state.collapsed}
                            onClick={this.onMenuClick}
                        >
                            {this.state.menus.length > 0 ?
                                (this.state.menus.map(item => {
                                    return (
                                        <SubMenu key={item.order}
                                                 title={<span>
                                                         <Icon type="info-circle-o"/>
                                                         <span>{item.menuName}</span>
                                                        </span>}>
                                            {item.subMenus.length > 0 ? (
                                                item.subMenus.map(subItem => {
                                                    return (
                                                        <Menu.Item key={subItem.id}>
                                                            <Link to={`${_ctx_}` + subItem.routeUrl}
                                                                  style={{display: 'inline'}}>{subItem.menuName}</Link>
                                                        </Menu.Item>
                                                    )
                                                })
                                            ) : (null)}
                                        </SubMenu>
                                    )
                                }))
                                : (this.state.failed ?
                                    (<Button ghost
                                             style={{width:"100%", textAlign: "center"}}
                                             onClick={this.doGetMenu}>没有可用的功能模块, 请点击重试</Button>)
                                    : (null)
                                )

                            }
                        </Menu>
                    </Spin>
                </Sider>
                <Layout>
                    <Header style={{background: '#fff', padding: 0}}>
                        <div className="app-tools-left">
                            <Icon
                                className="sider-trigger"
                                type={this.state.collapsed ? 'menu-unfold' : 'menu-fold'}
                                onClick={this.toggle}
                            />
                        </div>
                        <div className="app-brand">
                            {`${_companyName_}人力资源管理系统`}
                        </div>
                        <div className="app-tools-right">
                            <Dropdown overlay={userMenu} placement="bottomRight">
                                <a className="app-drop-link" href="javascript:void(0);">
                                    <Avatar type="user" className="app-avatar">{displayName}</Avatar>
                                    <Icon type="caret-down" className="app-avatar-arrow"/>
                                </a>
                            </Dropdown>
                        </div>

                    </Header>
                    <Content style={{margin: 8, padding: 16, background: '#fff', minHeight: 400}}>

                        {this.state.routeAndComponent ?
                            (Object.keys(this.state.routeAndComponent).map(path => {
                                    return <Route key={path} exact path={`${_ctx_}` + path}
                                                  component={eval(this.state.routeAndComponent[path])}></Route>
                                })
                            ) : (null)}
                    </Content>
                    <Footer className="app-footer">
                        {`Copyright © ${_companyName_}版权所有 2017-2018. All rights reserved.`}
                    </Footer>
                </Layout>
            </Layout>
        );
    }
}


// <LocaleProvider locale={zh_CN}>
//     <Router>
//         <App menuUrl={`${_ctx_}/menu/getUserMenus`}/>
//     </Router>
// </LocaleProvider>

ReactDOM.render((
    <LocaleProvider locale={zh_CN}>
        <Router>
            <App menuUrl={`${_ctx_}/menu/getUserMenus`}/>
        </Router>
    </LocaleProvider>
), document.getElementById('root'));

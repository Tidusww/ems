require("./css/index.css");
require("./css/core.css");
//控件
import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter as Router, Route, Link, Switch} from 'react-router-dom';
import {Menu, Layout, Icon, Avatar, Dropdown, Button, Spin} from 'antd';
import moment from 'moment';
import 'moment/locale/zh-cn';
moment.locale('zh-cn');

//页面
import {ClassManage} from './courseManage/ClassManage.jsx'
import {CityManage} from './baseInfoManage/CityManage.jsx'
import {DepartmentManage} from './baseInfoManage/DepartmentManage.jsx'
import {SemesterManage} from './baseInfoManage/SemesterManage.jsx'
import {AdManage} from './advertiseManage/AdManage.jsx'
import {CourseManage} from './courseManage/CourseManage.jsx'
import {AdminUserManage} from './adminManage/AdminUserManage.jsx'
import {RoleManage} from './adminManage/RoleManage.jsx'
import {KeywordManage} from './other/KeywordManage.jsx'
import {DataSync} from './other/DataSync.jsx'
import {GradationManage} from './gradeManage/GradationManage.jsx'
import {OrderManage} from './orderManage/OrderManage.jsx'
import {OrderAnalysis} from './orderManage/OrderAnalysis.jsx'

//Common
const {SubMenu} = Menu;
const {Header, Content, Footer, Sider} = Layout;

//菜单组件
const GradationManageComponent = ({match}) => (
    <GradationManage/>
);
const ClassManageComponent = ({match}) => (
    <ClassManage/>
);
const CityManageComponent = ({match}) => (
    <CityManage/>
);
const DepartmentManageComponent = ({match}) => (
    <DepartmentManage/>
);
const SemesterManageComponent = ({match}) => (
    <SemesterManage/>
);
const AdManageComponent = ({match}) => (
    <AdManage/>
);
const CourseManageComponent = ({match}) => (
    <CourseManage/>
);
const AdminUserManageComponent = ({match}) => (
    <AdminUserManage/>
);
const RoleManageComponent = ({match}) => (
    <RoleManage/>
);
const KeywordComponent = ({match}) => (
    <KeywordManage/>
);
const DataSyncComponent = ({match}) => (
    <DataSync/>
);
const OrderComponent = ({match}) => (
    <OrderManage/>
);
const OrderAnalysisComponent = ({match}) => (
    <OrderAnalysis/>
);



class App extends React.Component {
    state = {
        collapsed: false,
        currentUrl: "",
        loading: false,
        failed: false,
        menus: [],
        routeAndComponent: {},
        componentMap: {
            CityManageComponent: CityManageComponent,
            DepartmentManageComponent: DepartmentManageComponent,
            ClassManageComponent: ClassManageComponent,
            SemesterManageComponent: SemesterManageComponent,
            AdManageComponent: AdManageComponent,
            CourseManageComponent: CourseManageComponent,
            AdminUserManageComponent: AdminUserManageComponent,
            RoleManageComponent: RoleManageComponent,
            KeywordComponent: KeywordComponent,
            DataSyncComponent: DataSyncComponent,
            GradationManageComponent:GradationManageComponent,
            OrderComponent:OrderComponent,
            OrderAnalysisComponent:OrderAnalysisComponent
        }
    };
    handleRequestMenuList = (result) => {
        if (result.success) {
            //成功获取内容
            console.log(result);
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
        console.log("props.menuUrl is " + this.props.menuUrl);
        if (!this.props.menuUrl) {
            console.log("props.menuUrl is empty !");
            return;
        }
        this.setState({loading: true});
        $.get(this.props.menuUrl, this.handleRequestMenuList);
    }
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
                                    return (<SubMenu key={item.order} title={<span><Icon
                                        type="info-circle-o"/><span>{item.title}</span></span>}>
                                            {item.subMenus.length > 0 ? (
                                                item.subMenus.map(subItem => {
                                                    return (<Menu.Item key={subItem.routeName}><Link
                                                        to={`${_ctx_}` + subItem.routeName}
                                                        style={{display: 'inline'}}>{subItem.title}</Link></Menu.Item>)
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
                            {/*<SubMenu key="1" title={<span><Icon type="info-circle-o" /><span>课程信息管理</span></span>}>*/}
                            {/*<Menu.Item key="classManage"><Link to={`${_ctx_}/classManage`} style={{display:'inline'}}>班级管理</Link></Menu.Item>*/}
                            {/*<Menu.Item key="courseManage"><Link to={`${_ctx_}/courseManage`} style={{display:'inline'}}>课程管理</Link></Menu.Item>*/}
                            {/*</SubMenu>*/}
                            {/*<SubMenu key="2" title={<span><Icon type="info-circle-o" /><span>基础信息管理</span></span>}>*/}
                            {/*<Menu.Item key="cityManage"><Link to={`${_ctx_}/cityManage`} style={{display:'inline'}}>城市管理</Link></Menu.Item>*/}
                            {/*<Menu.Item key="departmentManage"><Link to={`${_ctx_}/departmentManage`} style={{display:'inline'}}>校区管理</Link></Menu.Item>*/}
                            {/*<Menu.Item key="semesterManage"><Link to={`${_ctx_}/semesterManage`} style={{display:'inline'}}>学期管理</Link></Menu.Item>*/}
                            {/*</SubMenu>*/}
                            {/*<SubMenu key="3" title={<span><Icon type="info-circle-o" /><span>广告管理</span></span>}>*/}
                            {/*<Menu.Item key="adManage"><Link to={`${_ctx_}/adManage`} style={{display:'inline'}}>广告管理</Link></Menu.Item>*/}
                            {/*</SubMenu>*/}
                            {/*<SubMenu key="4" title={<span><Icon type="info-setting-o" /><span>测试</span></span>}>*/}
                            {/*<Menu.Item key="demo"><Link to={`${_ctx_}/demo`} style={{display:'inline'}}>Demo</Link></Menu.Item>*/}
                            {/*</SubMenu>*/}
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
                            卓越网报后台管理系统
                        </div>
                        <div className="app-tools-right">
                            <Dropdown overlay={userMenu} placement="bottomRight">
                                <a className="app-drop-link" href="#">
                                    <Avatar type="user" className="app-avatar"/>
                                    <Icon type="caret-down" className="app-avatar-arrow"/>
                                </a>
                            </Dropdown>
                        </div>

                    </Header>
                    <Content style={{margin: 8, padding: 16, background: '#fff', minHeight: 400}}>
                        <Switch>
                            {this.state.routeAndComponent ?
                                (Object.keys(this.state.routeAndComponent).map(path => {
                                        return <Route key={path} exact path={`${_ctx_}` + path}
                                                      component={this.state.componentMap[this.state.routeAndComponent[path]]}></Route>
                                    })
                                ) : (null)}
                            {/*<Route exact path={`${_ctx_}/classManage`} component={ClassManageComponent}></Route>*/}
                            {/*<Route exact path={`${_ctx_}/cityManage`} component={CityManageComponent}></Route>*/}
                            {/*<Route exact path={`${_ctx_}/departmentManage`} component={DepartmentManageComponent}></Route>*/}
                            {/*<Route exact path={`${_ctx_}/semesterManage`} component={SemesterManageComponent}></Route>*/}
                            {/*<Route exact path={`${_ctx_}/adManage`} component={AdManageComponent}></Route>*/}
                            {/*<Route exact path={`${_ctx_}/courseManage`} component={CourseManageComponent}></Route>*/}
                        </Switch>
                    </Content>
                    <Footer className="app-footer">
                        Copyright © 卓越教育版权所有 1997-2017. All rights reserved.
                    </Footer>
                </Layout>
            </Layout>
        );
    }
}

ReactDOM.render((
    <Router>
        <App menuUrl={`${_ctx_}/menu/getUserMenus`}/>
    </Router>
), document.getElementById('root'));

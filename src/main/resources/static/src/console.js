import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Route, Switch, withRouter } from 'react-router-dom';
import { Layout, Menu, Icon } from 'antd';
import menus from './config/menus'

// 员工
import NormalItems from './apps/employee/normalItems/normalItems'
import DailyPlan from './apps/employee/dailyPlan/dailyPlan'
import PlanHistory from './apps/employee/planHistory/planHistory'
import NewPlan from './apps/employee/newPlan/newPlan'
import MyAssess from './apps/employee/myAssess/myAssess'


// 领导
import DailyPlanLeader from './apps/leader/dailyPlan/dailyPlan'
import ItemsReview from './apps/leader/itemsReview/itemsReview'
import NewItemsLeader from './apps/common/newItems/newItems'
import NormalItemsLeader from './apps/leader/normalItems/normalItems'
import PlanReview from './apps/leader/planReview/planReview'

// 人事
import NormalItemsPersonnel from './apps/personnel/normalItems/normalItems'
import NewItemsPersonnel from './apps/common/newItems/newItems'

const { Header, Sider, Content } = Layout;
const { SubMenu } = Menu;

//每次都传入一级菜单
const renderMenu = menu => {
    if (!menu.menus) {
        return <Menu.Item key={menu.key}><Icon type={menu.icon} />{menu.name}</Menu.Item>
    } else {
        return (
            <SubMenu key={menu.key} title={<span><Icon type={menu.icon} /><span>{menu.name}</span></span>}>
                {menu.menus.map(m => {
                    return <Menu.Item key={m.key}>{m.name}</Menu.Item>
                })}
            </SubMenu>
        )
    }
}
//返回当前一级菜单名
const getOpenKeys = () => {
    let path = window.location.pathname
    return path.split('/')[2]
}
//返回当前的菜单树
const getSelectedKeys= () => {
    let path = window.location.pathname
    return path.split('/').slice(2).join('/')
}

class SideMenu extends Component {
    // 读取到点击的key,push到history的location,改变url的值
    navigate = (item) => {
        window.scrollTo(0, 0)
        this.props.history.push(`/${item.key}`)
    }

    render() {
        let mu = []
        menus.all.map( m => {
            mu.push(renderMenu(m))
        })

        //onSelect	被选中时调用
        //defaultOpenKeys	初始展开的 SubMenu 菜单项 key 数组
        //defaultSelectedKeys	初始选中的菜单项 key 数组
        return (
            <Menu
                // mode='inline'
                theme="dark"
                onSelect = {this.navigate}
                defaultOpenKeys={[getOpenKeys()]}
                defaultSelectedKeys={[getSelectedKeys()]}
            >
                {mu}
            </Menu>
        )
    }
}

const SideMenuWrapper = withRouter(SideMenu)

class Work extends Component {

    state = {
        collapsed: false
    };

    onCollapse = (collapsed) => {
        this.setState({ collapsed });
    }

    render() {
        return <Layout style={{height:"100vh"}}>
                {/*<Header style={{ backgroundColor: '#0d5ca7' }}>*/}
                    {/*<div className="logo" style={{display: 'table-cell'}}>*/}
                        {/*<Link to="/" style={{ textDecoration: 'none' }}>*/}
                            {/*<span style={{color: '#fff', fontSize: 20}}><strong>工作笔记</strong></span>*/}
                            {/*<Divider type='vertical' style={{ height: 20 }}/>*/}
                            {/*<span style={{ color: 'white' }}>每天进步一点点</span>*/}
                        {/*</Link>*/}
                    {/*</div>*/}
                {/*</Header>*/}
                <Layout>
                    <Sider defaultselectedseys={['1']} collapsible collapsed={this.state.collapsed} onCollapse={this.onCollapse} >
                        {/*<Link to="/"><div className="logo"><h2 style={{color: '#fff'}}>工作笔记</h2></div></Link>*/}
                        <SideMenuWrapper/>
                    </Sider>
                    <Layout>
                        <Content style={{ background: '#fff' }}>
                            <div style={{ minHeight: '100%' }}>
                                <Switch>
                                    <Route path="/" exact></Route>
                                    {/* 员工 */}
                                    <Route path="/work/employee/normalItems" component={NormalItems}/>
                                    <Route path="/work/employee/dailyPlan" component={DailyPlan}/>
                                    <Route path="/work/employee/planHistory" component={PlanHistory}/>
                                    <Route path="/work/employee/newPlan" component={NewPlan}/>
                                    <Route path="/work/employee/myAssess" component={MyAssess}/>

                                    {/* 领导 */}
                                    <Route path="/work/leader/workItems/normalItems" component={NormalItemsLeader}/>
                                    <Route path="/work/leader/workItems/newItems" component={NewItemsLeader}/>
                                    <Route path="/work/leader/workItems/itemsReview" component={ItemsReview}/>
                                    <Route path="/work/leader/dailyPlan" component={DailyPlanLeader}/>
                                    <Route path="/work/leader/access" component={PlanReview}/>

                                    {/* 人事 */}
                                    <Route path="/work/personnel/normalItems" component={NormalItemsPersonnel}/>
                                    <Route path="/work/personnel/newItems" component={NewItemsPersonnel}/>
                                </Switch>
                            </div>
                        </Content>
                    </Layout>
                </Layout>
            </Layout>
        
    }
}

ReactDOM.render(<Router><Work /></Router>, document.getElementById("root"));
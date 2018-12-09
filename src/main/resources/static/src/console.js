import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Link, Route, Switch, withRouter } from 'react-router-dom';
import { Layout, Menu, Icon} from 'antd';

// 员工
import NormalItems from './apps/staff/normalItems/normalItems'
import NewItems from './apps/staff/newItems/newItems'
import DailyPlan from './apps/staff/dailyPlan/dailyPlan'
import PlanHistory from './apps/staff/planHistory/planHistory'
import NewPlan from './apps/staff/newPlan/newPlan'

// 领导
import DailyPlanLeader from './apps/leader/dailyPlan/dailyPlan'
import DailyUnfinished from './apps/leader/dailyUnfinished/dailyUnfinished'
import ItemsReview from './apps/leader/itemsReview/itemsReview'
import NewItemsLeader from './apps/leader/newItems/newItems'
import NormalItemsLeader from './apps/leader/normalItems/normalItems'
import menus from './config/menus'

const { Sider, Content } = Layout;
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
        menus.staff.map( m => {
            mu.push(renderMenu(m))
        })

        //onSelect	被选中时调用
        //defaultOpenKeys	初始展开的 SubMenu 菜单项 key 数组
        //defaultSelectedKeys	初始选中的菜单项 key 数组
        return (
            <Menu
                mode='inline'
                theme='dark'
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

    render() {
        return <Layout style={{height:"100vh"}}>
                <Sider defaultselectedseys={['1']} collapsible collapsed={this.state.collapsed} onCollapse={this.onCollapse} >
                    <Link to="/"><div className="logo"><h2 style={{color: '#fff'}}>工作笔记</h2></div></Link>
                    <SideMenuWrapper/>
                </Sider>
                <Layout>
                    <Content style={{ margin: '16px 16px 0' }}>
                        <div style={{ padding: 30, background: '#fff', minHeight: 700 }}>
                            <Switch>
                                <Route path="/" exact></Route>
                                {/* 员工 */}
                                <Route path="/work/employee/workItems/normalItems"><NormalItems /></Route>
                                <Route path="/work/employee/workItems/newItems"><NewItems /></Route>
                                <Route path="/work/employee/dailyPlan"><DailyPlan /></Route>
                                <Route path="/work/employee/planHistory"><PlanHistory /></Route>
                                <Route path="/work/employee/newPlan"><NewPlan /></Route>

                                {/* 领导 */}
                                <Route path="/work/leader/workItems/normalItems"><NormalItemsLeader /></Route>
                                <Route path="/work/leader/workItems/newItems"><NewItemsLeader /></Route>
                                <Route path="/work/leader/workItems/dailyUnfinished"><DailyUnfinished /></Route>
                                <Route path="/work/leader/workItems/itemsReview"><ItemsReview /></Route>
                                <Route path="/work/leader/plan/dailyPlan"><DailyPlanLeader /></Route>

                            </Switch>
                        </div>
                    </Content>
                </Layout>
            </Layout>
        
    }
}

ReactDOM.render(<Router><Work /></Router>, document.getElementById("root"));
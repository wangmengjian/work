export default {
    // 总的
    all: [
        {
            key: 'work/employee/normalItems',
            icon: 'user',
            name: '我的工作项',
        },{
            key: 'work/employee/dailyPlan',
            icon: 'ordered-list',
            name: '我的工作计划',
        },{
            key:'work/employee/myAssess',
            icon: 'user',
            name:'我的考核'
        },{
            key: 'work/leader/workItems',
            icon: 'user',
            name: '工作项管理',
            menus:[{
                key:'work/leader/workItems/normalItems',
                name:'查询常规工作项'
            },{
                key:'work/leader/workItems/newItems',
                name:'新增工作项'
            },{
                key:'work/leader/workItems/itemsReview',
                name:'工作项审批'
            }]
        },{
            key:'work/leader/dailyPlan',
            icon: 'ordered-list',
            name:'部门工作计划'
        },{
            key:'work/leader/access',
            icon: 'user',
            name:'部门考核'
        },{
            key: 'work/personnel/normalItems',
            icon: 'user',
            name: '人事工作项查询',
        },{
            key: 'work/personnel/newItems',
            icon: 'user',
            name: '人事新增工作项',
        }
    ],

    // 员工
    employee: [
        {
            key: 'work/employee/workItems',
            icon: 'user',
            name: '工作项管理',
            menus:[{
                key:'work/employee/workItems/normalItems',
                name:'查询工作项'
            },{
                key:'work/employee/workItems/newItems',
                name:'新增工作项'
            }]
        },{
            key: 'work/employee/dailyPlan',
            icon: 'team',
            name: '今日日计划',
        },{
            key: 'work/employee/planHistory',
            icon: 'team',
            name: '历史日计划',
        },{
            key: 'work/employee/newPlan',
            icon: 'pie-chart',
            name: '生成日计划',
        }
    ],

    // 领导
    leader: [
        {
            key: 'work/leader/workItems',
            icon: 'user',
            name: '工作项管理',
            menus:[{
                key:'work/leader/workItems/normalItems',
                name:'查询常规工作项'
            },{
                key:'work/leader/workItems/newItems',
                name:'新增工作项'
            },{
                key:'work/leader/workItems/dailyUnfinished',
                name:'查看未完成工作项'
            },{
                key:'work/leader/workItems/itemsReview',
                name:'工作项审批'
            }]
        },{
            key: 'work/leader/plan',
            icon: 'user',
            name: '日计划管理',
            menus:[{
                key:'work/leader/plan/dailyPlan',
                name:'查看员工日计划'
            },{
                key:'work/leader/plan/access',
                name:'日计划考核'
            }]
        }
    ],

    // 人事管理员
    hr: [

    ],

    // 系统管理员
    systemAdministrator: [

    ]

}

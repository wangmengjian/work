export default {
    // 员工
    staff: [
        {
            key: 'workItems',
            icon: 'user',
            name: '工作项管理',
            menus:[{
                key:'workItems/normalItems',
                name:'查询工作项'
            },{
                key:'workItems/newItems',
                name:'新增工作项'
            }]
        },{
            key: 'dailyPlan',
            icon: 'team',
            name: '今日日计划',
        },{
            key: 'planHistory',
            icon: 'team',
            name: '历史日计划',
        }
    ],

    // 领导
    leader: [
        {
            key: 'workItems',
            icon: 'user',
            name: '工作项管理',
            menus:[{
                key:'workItems/normalItems',
                name:'查询常规工作项'
            },{
                key:'workItems/newItems',
                name:'新增工作项'
            },{
                key:'workItems/dailyUnfinished',
                name:'查看部门当日未完成工作项'
            },{
                key:'workItems/itemsReview',
                name:'工作项审批'
            }]
        },{
            key: 'dailyPlan',
            icon: 'user',
            name: '日计划管理',
            menus:[{
                key:'workItems/dailyPlan',
                name:'查看员工日计划'
            },{
                key:'workItems/access',
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

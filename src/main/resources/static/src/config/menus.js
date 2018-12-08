export default {
    // 员工
    staff: [
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
            },{
                key:'work/employee/workItems/newItems',
                name:'生成工作计划'
            }]
        },{
            key: 'work/employee/dailyPlan',
            icon: 'team',
            name: '今日日计划',
        },{
            key: 'work/employee/planHistory',
            icon: 'team',
            name: '历史日计划',
        }
    ],

    // 领导
    leader: [
        {
            key: 'work/leadership/workItems',
            icon: 'user',
            name: '工作项管理',
            menus:[{
                key:'work/leadership/workItems/normalItems',
                name:'查询常规工作项'
            },{
                key:'work/leadership/workItems/newItems',
                name:'新增工作项'
            },{
                key:'work/leadership/workItems/dailyUnfinished',
                name:'查看部门当日未完成工作项'
            },{
                key:'work/leadership/workItems/itemsReview',
                name:'工作项审批'
            }]
        },{
            key: 'work/leadership/dailyPlan',
            icon: 'user',
            name: '日计划管理',
            menus:[{
                key:'work/leadership/dailyPlan/dailyPlan',
                name:'查看员工日计划'
            },{
                key:'work/leadership/dailyPlan/access',
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

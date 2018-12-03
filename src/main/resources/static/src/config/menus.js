export default {
    // 员工
    staff: [
        {
            key: 'workItems',
            icon: 'user',
            name: '工作项管理',
            menus:[{
                key:'workItems/query',
                name:'查询工作项'
            },{
                key:'workItems/add',
                name:'新增工作项'
            }]
        },{
            key: 'dailyPlan',
            icon: 'team',
            name: '日计划管理',
        }{
            key: 'planHistory',
            icon: 'team',
            name: '历史日计划',
        }
    ],

    // 领导
    leaderShip: [

    ],

    // 人事管理员
    hr: [

    ],

    // 系统管理员
    systemAdministrator: [

    ]

}

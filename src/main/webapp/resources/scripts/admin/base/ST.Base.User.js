Ext.BLANK_IMAGE_URL = "./../resources/scripts/ext/resources/images/default/s.gif";

Ext.namespace("ST.base");

Ext.reg('deleFlagField', ST.ux.ExtField.ComboBox);

Ext.apply(Ext.form.VTypes, {
    password: function(val, field){
        if (field.initialPassField) {
            var pwd = Ext.getCmp(field.initialPassField);
            return (val == pwd.getValue());
        }
        return true;
    },
    passwordText: '密码不匹配'
});
ST.base.userView = Ext.extend(ST.ux.ViewGrid, {
	dlgWidth: 360,
	dlgHeight: 152,
	//资源列表查询URL
	urlGridQuery: './../user/pageQueryUsers.json',
	urlAdd: './../user/insertUser.json',
	urlEdit: './../user/updateUser.json',
	urlLoadData: './../user/loadUser.json',
	urlRemove: './../user/deleteUser.json',
	addTitle: "增加用户",
    editTitle: "更新用户",
    gridTitle: "用户数据",
	girdColumns: [
				{header: 'ID', width: 150, dataIndex: 'id', hideGrid: true, hideForm: 'add', hidden:true,readOnly: true},
	            {header: '用户名称', width: 150, dataIndex: 'username', allowBlank:false},
	            {header: '密码', id: 'pass', width: 100, dataIndex: 'password', allowBlank:false, inputType: 'password'},
	            {header: '重复密码',id:'pass-cfrm', width: 100, dataIndex: 'pass-cfrm', hideGrid: true, inputType: 'password', allowBlank:false, vtype: 'password', initialPassField: 'pass'},
	            {header: '创建时间', width: 150, dataIndex: 'createTime', hideForm: 'all', allowBlank:false}
	        ],
	
	queryFormItms: [{ 
				layout: 'tableform',
	            layoutConfig: {
	           		columns: 3,
	            	columnWidths: [0.33, 0.33, 0.33]
	            },           
		        items:[{xtype:'spacer'},{xtype:'textfield', fieldLabel: '用户名称', name: 'userName', id: 'userName', anchor:'100%'},{xtype:'spacer'}]
		    }],
    
	constructor: function() {
		ST.base.userView.superclass.constructor.call(this, {});
	}
});
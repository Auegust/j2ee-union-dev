Ext.BLANK_IMAGE_URL = "./../resources/scripts/ext/resources/images/default/s.gif";

Ext.namespace("ST.base");
Ext.form.Field.prototype.msgTarget = 'under';
ST.base.distributorView = Ext.extend(ST.ux.ViewGrid, {
	dlgWidth: 360,
	dlgHeight: 300,
	urlGridQuery: './../distributor/pageQueryTDistributors.json',
	urlAdd: './../distributor/insertTDistributor.json',
	urlEdit: './../distributor/updateTDistributor.json',
	urlLoadData: './../distributor/loadDistributor.json',
	urlRemove: './../distributor/deleteTDistributor.json',
	addTitle: "新增经销商",
    editTitle: "更新经销商信息",
    gridTitle: "经销商列表",
    formbarTitle:"经销商查询",
	girdColumns: [  {header: 'ID', dataIndex: 'id', hideGrid: true, hideForm: 'add', hidden:true ,readOnly: true},
		            {header: '经销商编号', dataIndex: 'distributorCode', hideForm:'add' ,readOnly:true},
		            {header: '经销商名称', dataIndex: 'distributorName', allowBlank:false},
		            {header: '职级',  dataIndex: 'rankId', hidden:true ,hideGrid:true},
		            {header: '职级',  dataIndex: 'rankId_Name', hideForm:'all'},
		            {header: '上级ID', dataIndex: 'sponsorId',hideGrid:true ,hidden:true},
		            {header: '上级编号', dataIndex: 'sponsorCode'},  //输入上级编号的时候需要去数据库验证，数据库无记录的情况新增可以为空
		            {header: '上级名称', dataIndex: 'sponsor_Name', hideForm:'all'},
		            {header: '所属专卖店', dataIndex: 'shopId', allowBlank:false ,fieldtype:'shopCombo',hideGrid:true},
		            {header: '所属专卖店', dataIndex: 'shop_Name', hideForm:'all'},
		            {header: '联系地址', dataIndex: 'address',width:172},
		            {header: '联系电话', dataIndex: 'telephone'},
		            {header: '加入时间',dataIndex: 'createTime',hideForm:'all',width:172}
		         ],
	
	queryFormItms: [{ 
				layout: 'tableform',
	            layoutConfig: {
	           		columns: 3,
	            	columnWidths: [0.33, 0.33, 0.33]
	            },           
		        items:[{xtype:'rankCombo', fieldLabel: '职级', name: 'rankId', anchor:'100%'},
		               {xtype:'textfield', fieldLabel: '经销商编号', name: 'distributorCode', anchor:'100%'},
		               {xtype:'textfield', fieldLabel: '经销商名称', name: 'distributorName', anchor:'100%'}
		              ]
		    }],
	/*****
	 * 表单输入值后台Ajax验证	   
	 */ 
    AjaxValidFormFuc: function(form , action){
    	if(action.result.message == 'ok'){
    		return true;
    	}else{
        	form.findField('sponsorCode').markInvalid(action.result.message);
        	return false;	
    	}
    },
    
	constructor: function() {
		ST.base.distributorView.superclass.constructor.call(this, {});
	}
});
Ext.BLANK_IMAGE_URL = "./../resources/scripts/ext/resources/images/default/s.gif";

Ext.namespace("ST.base");
Ext.form.Field.prototype.msgTarget = 'under';
ST.base.shopView = Ext.extend(ST.ux.ViewGrid, {
	dlgWidth: 360,
	dlgHeight: 300,
	urlGridQuery: './../shop/pageQueryTShopInfos.json',
	urlAdd: './../shop/insertTShopInfo.json',
	urlEdit: './../shop/updateTShopInfo.json',
	urlLoadData: './../shop/loadTShopInfo.json',
	urlRemove: './../shop/deleteTShopInfo.json',
	addTitle: "新增专卖店",
    editTitle: "更新专卖店信息",
    gridTitle: "专卖店列表",
    formbarTitle:"专卖店查询",
	girdColumns: [  {header: 'ID', dataIndex: 'id', hideGrid: true, hideForm: 'add', hidden:true ,readOnly: true},
		            {header: '店铺编号', dataIndex: 'shopCode', allowBlank:false},
		            {header: '店铺名称', dataIndex: 'shopName',allowBlank:false},
		            {header: '店主编码',  dataIndex: 'shopOwner'},
		            {header: '国家', dataIndex: 'shopCountry'},
		            {header: '城市', dataIndex: 'shopCity'},  //输入上级编号的时候需要去数据库验证，数据库无记录的情况新增可以为空
		            {header: '店铺地址', dataIndex: 'shopAddr',width:172},
		            {header: '创建人', dataIndex: 'creator'},
		            {header: '加入时间',dataIndex: 'createTime',hidden:true ,width:172}
		         ],
	
	queryFormItms: [{ 
				layout: 'tableform',
	            layoutConfig: {
	           		columns: 3,
	            	columnWidths: [0.33, 0.33, 0.33]
	            },           
		        items:[{xtype:'textfield', fieldLabel: '编号', name: 'shopCode', anchor:'100%'},
		               {xtype:'textfield', fieldLabel: '名称', name: 'shopName', anchor:'100%'},
		               {xtype:'textfield', fieldLabel: '城市', name: 'shopCountry', anchor:'100%'}
		              ]
		    }],
	/*****
	 * 表单输入值后台Ajax验证	   
	 */ 
    AjaxValidFormFuc: function(form , action){
    	if(action.result.message == 'ok'){
    		return true;
    	}else{
        	form.findField('shopOwner').markInvalid(action.result.message);
        	return false;	
    	}
    },
	constructor: function() {
		ST.base.shopView.superclass.constructor.call(this, {});
		Ext.getCmp('delEntity').setVisible(false);
	}
});
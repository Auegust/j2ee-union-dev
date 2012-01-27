Ext.BLANK_IMAGE_URL = "./../resources/scripts/ext/resources/images/default/s.gif";

Ext.namespace("ST.base");
Ext.form.Field.prototype.msgTarget = 'under';
ST.base.shopView = Ext.extend(ST.ux.ViewGrid, {
	dlgWidth: 360,
	dlgHeight: 300,
	queryFormHeight : 120,
	urlGridQuery: './../shop/pageQueryTShopInfos.json',
	urlAdd: './../shop/insertTShopInfo.json',
	urlEdit: './../shop/updateTShopInfo.json',
	urlLoadData: './../shop/loadTShopInfo.json',
	urlRemove: './../shop/deleteTShopInfo.json',
	addTitle: "新增专卖店",
    editTitle: "更新专卖店信息",
    gridTitle: "专卖店列表",
    formbarTitle:"专卖店查询",
    isforceFit : true,
	girdColumns: [  {header: 'ID', dataIndex: 'id', hideGrid: true, hideForm: 'add', hidden:true ,readOnly: true},
		            {header: '店铺编号', dataIndex: 'shopCode', hideForm:'add' ,readOnly:true},
		            {header: '店铺名称', dataIndex: 'shopName',allowBlank:false},
		            {header: '店主编码',  dataIndex: 'shopOwner'},
		            {header: '国家', dataIndex: 'shopCountry'},
		            {header: '城市', dataIndex: 'shopCity'}, 
		            {header: '店铺地址', dataIndex: 'shopAddr',width:172},
		            {header: '创建人', dataIndex: 'creator'},
		            {header: '加入时间',dataIndex: 'createTime',hideForm:'all',width:172}
		         ],
	
	 queryFormItms: [{ 
			layout: 'tableform',
	        layoutConfig: {
	        	columns: 2,
	        	columnWidths: [0.5,0.5], 
	        	bodyStyle:'padding:90px' 
	        },         
	        defaults: {      
	        	 anchor:'90%'
	        },        
	        items:[{xtype:'textfield', fieldLabel: '编号', xtype:'shopCombo',hiddenName:'shopCode',allowBlank:true,valueField:'shopCode'},
	               {xtype:'textfield', fieldLabel: '名称', name: 'shopName'},
	               {xtype:'textfield', fieldLabel: '国家', name: 'shopCountry'},
	               {xtype:'textfield', fieldLabel: '城市', name: 'shopCity'}]
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
    
    /****
     * 用于拦截删除有违规行为的操作
     */
    delegateWhenDelete:function(response, opts){
		this.grid.body.unmask();
    	var resultJson = Ext.decode(response.responseText);
    	if(resultJson.message == 'ok'){
    		return true;
    	}else{
    		Ext.MessageBox.alert('警告',resultJson.message);
    		return false;
    	}
    },
    
    /****
     * 增加提示信息
     */
    addButtonOnBottombar:function(toolbar, index){
    	toolbar.insertButton(index++,{xtype: 'tbspacer', width: 50});
    	toolbar.insertButton(index++,'<b><font color=red>提示：专卖店有经销商信息时，请谨慎删除！</font><b>');
    },
    
	constructor: function() {
		ST.base.shopView.superclass.constructor.call(this, {});
	}
});
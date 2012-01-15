Ext.BLANK_IMAGE_URL = "./../resources/scripts/ext/resources/images/default/s.gif";

Ext.namespace("ST.base");
Ext.form.Field.prototype.msgTarget = 'side';
ST.base.orderView = Ext.extend(ST.ux.ViewGrid, {
	dlgWidth: 360,
	dlgHeight: 300,
	urlGridQuery: './../order/pageQueryTProductOrder.json',
	urlAdd: './../order/insertTProductOrder.json',
	urlEdit: './../order/updateTProductOrder.json',
	urlLoadData: './../order/loadTProductOrder.json',
	urlRemove: './../order/deleteTProductOrder.json',
	addTitle: "销售录入",
    editTitle: "更新销售信息",
    gridTitle: "销售信息列表",
    formbarTitle:"销售信息查询",
	girdColumns: [  {header: 'ID', dataIndex: 'id', hideGrid: true, hideForm: 'add', hidden:true ,readOnly: true},
	                {header: '日期',dataIndex: 'createTime',hideForm:'all',width:172},
		            {header: '经销商编号', dataIndex: 'distributorCode', allowBlank:false},
		            {header: '经销商名称', dataIndex: 'distributorName',readOnly:true},
		            {header: '产品编号',  dataIndex: 'productCode',allowBlank:false}, 
		            {header: '产品名称', dataIndex: 'productName',readOnly:true},
		            {header: 'PV值',dataIndex: 'PV',renderer: usMoneyFunc,readOnly:true},
		            {header: 'BV值',dataIndex: 'BV',renderer: usMoneyFunc,readOnly:true},
		            {header: '专卖店编号', dataIndex: 'shopCode',allowBlank:false},
		            {header: '专卖店名称', dataIndex: 'shopName',width:172,readOnly:true},
		            {header: '销售数量', dataIndex: 'saleNumber'},
		            {header: '产品价格',dataIndex: 'salePrice',renderer: usMoneyFunc}
		         ],
	
	queryFormItms: [{ 
				layout: 'tableform',
	            layoutConfig: {
	           		columns: 2,
	            	columnWidths: [0.5, 0.5]
	            },           
		        items:[{xtype:'textfield', fieldLabel: '经销商编号', name: 'distributorCode', anchor:'100%'},
		               {xtype:'textfield', fieldLabel: '经销商名称', name: 'distributorName', anchor:'100%'},
		               {xtype:'textfield', fieldLabel: '产品编号', name: 'productCode', anchor:'100%'},
		               {xtype:'textfield', fieldLabel: '产品名称', name: 'productName', anchor:'100%'},
		               {xtype:'textfield', fieldLabel: '专卖店编号', name: 'shopCode', anchor:'100%'},
		               {xtype:'textfield', fieldLabel: '专卖店名称', name: 'shopName', anchor:'100%'},
		               {xtype:'datetimefield', format: 'Y-m-d', editable: true, fieldLabel: '开始日期', name: 'startTime', anchor:'90%' },
		               {xtype:'datetimefield', format: 'Y-m-d', editable: true, fieldLabel: '结束日期', name: 'endTime', anchor:'90%' }]
		    }],
    //####布局元素
	addlayoutConfig: {
   		columns: 2,
    	columnWidths: [0.5,0.5], 
    	bodyStyle:'padding:10px'
    },
    editlayoutConfig: {
   		columns: 2,
    	columnWidths: [0.5,0.5], 
    	bodyStyle:'padding:10px'
    },
    formlayout : 'tableform',
	/*****
	 * 表单输入值后台Ajax验证	   
	 */ 
//    AjaxValidFormFuc: function(form , action){
//    	if(action.result.message == 'ok'){
//    		return true;
//    	}else{
//        	form.findField('shopOwner').markInvalid(action.result.message);
//        	return false;	
//    	}
//    },
	constructor: function() {
		ST.base.orderView.superclass.constructor.call(this, {});
	}
});
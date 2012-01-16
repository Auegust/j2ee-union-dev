Ext.BLANK_IMAGE_URL = "./../resources/scripts/ext/resources/images/default/s.gif";

Ext.namespace("ST.base");
Ext.form.Field.prototype.msgTarget = 'side';
ST.base.orderView = Ext.extend(ST.ux.ViewGrid, {
	dlgWidth: 700,
	dlgHeight: 225,
	//isFormAutoHeight:true,
	queryFormHeight : 180,
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
		            {header: '经销商编号', dataIndex: 'distributorCode', allowBlank:false , fieldtype:'distCombo',valueField:'distributorCode',
	                 hiddenName:'distributorCode',listeners:{
	                    'beforequery' : function(e) {
	            			e.combo.store.baseParams.distributorCode = e.query; //查询distributor编号
	                	},
	                	 'select':function(combo,record,index){
			            		this.ownerCt.form.findField('distributorName').setValue(record.data.distributorName);
			            		this.ownerCt.form.findField('shopCode').setValue(record.data.shop_Name);
			            		this.ownerCt.form.findField('shopName').setValue(record.data.shop_Code);
			             }
		            }},
		            {header: '经销商名称', dataIndex: 'distributorName',disabled:true,emptyText:'与编号联动'},
		            {header: '专卖店编号', dataIndex: 'shopCode',disabled:true,emptyText:'与编号联动'},
		            {header: '专卖店名称', dataIndex: 'shopName',disabled:true,emptyText:'与编号联动'},
		            {header: '产品编号',  dataIndex: 'productCode',allowBlank:false,fieldtype:'productCombo',valueField:'productCode',
			             hiddenName:'productCode',listeners:{
			            	 'beforequery' : function(e) {
			            			e.combo.store.baseParams.productCode = e.query;// 查询product编号
			                  },
		                	 'select':function(combo,record,index){
				            		this.ownerCt.form.findField('productName').setValue(record.data.productName);
				            		this.ownerCt.form.findField('pV').setValue(record.data.productPv);
				            		this.ownerCt.form.findField('bV').setValue(record.data.productBv);
				             }
		             }}, 
		            {header: '产品名称', dataIndex: 'productName',disabled:true,emptyText:'与编号联动'},
		            {header: '产品价格',dataIndex: 'salePrice', hideGrid:true,renderer: usMoneyFunc,//或许表示销售的实际价格
		            	regex : /^\d{0,8}\.{0,1}(\d{1,2})?$/,regexText:"请输入有效价格，保留两位精度!"},
		            {header: 'PV值',dataIndex: 'pV',disabled:true,renderer: usMoneyFunc,emptyText:'与编号联动'},
		            {header: 'BV值',dataIndex: 'bV',disabled:true,renderer: usMoneyFunc,emptyText:'与编号联动'},
		            {header: '销售数量', dataIndex: 'saleNumber',allowBlank:false ,regex : /^\d+$/,regexText:"只能输入数字!"}
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
		        items:[ {fieldLabel: '经销商编号',xtype:'distCombo',valueField:'distributorCode',allowBlank:false ,
		        		 hiddenName:'distributorCode',listeners:{
		                    'beforequery' : function(e) {
		            			e.combo.store.baseParams.distributorCode = e.query; //查询distributor编号
		                	},
		                	 'select':function(combo,record,index){
				            		this.ownerCt.ownerCt.form.findField('distributorName').setValue(record.data.distributorName);
				             }
			            }},
			            {xtype:'textfield',fieldLabel: '经销商名称', name: 'distributorName',width:172,disabled:true,emptyText:'与编号联动'},
		                {fieldLabel: '专卖店编号', allowBlank:false , xtype:'shopCombo',valueField:'shopCode',
				             hiddenName:'shopCode',listeners:{
				            	'select':function(combo,record,index){
				            		this.ownerCt.ownerCt.form.findField('shopName').setValue(record.data.shopName);
				            	}
				         }},
				        {xtype:'textfield',fieldLabel: '专卖店名称', name: 'shopName',width:172,disabled:true,emptyText:'与编号联动'},
				        {fieldLabel: '产品编号', xtype:'productCombo',valueField:'productCode',
			             hiddenName:'productCode',listeners:{
			            	 'beforequery' : function(e) {
			            			e.combo.store.baseParams.productCode = e.query;// 查询product编号
			                	},
		                	 'select':function(combo,record,index){
				            		this.ownerCt.ownerCt.form.findField('productName').setValue(record.data.productName);
				            }
			            }}, 
			            {xtype:'textfield',fieldLabel: '产品名称', name: 'productName',disabled:true,emptyText:'与编号联动'},
		                {xtype:'datetimefield', format: 'Y-m-d', editable: true, fieldLabel: '开始日期', name: 'startTime'},
		                {xtype:'datetimefield', format: 'Y-m-d', editable: true, fieldLabel: '结束日期', name: 'endTime' }]
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
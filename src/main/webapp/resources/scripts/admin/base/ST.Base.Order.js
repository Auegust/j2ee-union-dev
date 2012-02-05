Ext.BLANK_IMAGE_URL = "./../resources/scripts/ext/resources/images/default/s.gif";

Ext.namespace("ST.base");
Ext.form.Field.prototype.msgTarget = 'side';
ST.base.orderView = Ext.extend(ST.ux.ViewGrid, {
	dlgWidth: 700,
	dlgHeight: 225,
	//isFormAutoHeight:true,
	queryFormHeight : 155,
	urlGridQuery: './../order/pageQueryTProductOrder.json',
	urlAdd: './../order/insertTProductOrder.json',
	urlEdit: './../order/updateTProductOrder.json',
	urlLoadData: './../order/loadTProductOrder.json',
	urlRemove: './../order/deleteTProductOrder.json',
	addTitle: "订单录入",
    editTitle: "更新销售信息",
    gridTitle: "销售信息列表",
    formbarTitle:"销售信息查询",
	girdColumns: [  {header: 'ID', dataIndex: 'id', hideGrid: true, hideForm: 'add', hidden:true ,readOnly: true},
	                {header: '日期',dataIndex: 'createTime',hideForm:'all',width:172,remoteSort:true,sortable:true},
		            {header: '经销商编号', dataIndex: 'distributorCode', allowBlank:false , fieldtype:'distCombo',valueField:'distributorCode',
	                 hiddenName:'distributorCode',listeners:{
	                    'beforequery' : function(e) {
	            			e.combo.store.baseParams.distributorCode = e.query; //查询distributor编号
	                	},
	                	 'select':function(combo,record,index){
			            		this.ownerCt.form.findField('distributorName').setValue(record.data.distributorName);
			            		this.ownerCt.form.findField('shopCode').setValue(record.data.shop_Code);
			            		this.ownerCt.form.findField('shopName').setValue(record.data.shop_Name);
			             }
		            }},
		            {header: '经销商名称', dataIndex: 'distributorName',readOnly:true, emptyText:'与编号联动'},
		            {header: '专卖店编号', dataIndex: 'shopCode',readOnly:true,emptyText:'与编号联动',remoteSort:true,sortable:true},
		            {header: '专卖店名称', dataIndex: 'shopName',readOnly:true,emptyText:'与编号联动'},
		            {header: '产品编号',  dataIndex: 'productCode',allowBlank:false,fieldtype:'productCombo',valueField:'productCode',
			             hiddenName:'productCode',listeners:{
			            	 'beforequery' : function(e) {
			            			e.combo.store.baseParams.productCode = e.query;// 查询product编号
			                  },
		                	 'select':function(combo,record,index){
				            		this.ownerCt.form.findField('productName').setValue(record.data.productName);
				            		this.ownerCt.form.findField('pV').setValue(record.data.productPv);
				            		this.ownerCt.form.findField('bV').setValue(record.data.productBv);
				            		this.ownerCt.form.findField('productPrice').setValue(record.data.productBv);
				             }
		             },remoteSort:true,sortable:true}, 
		            {header: '产品名称', dataIndex: 'productName',readOnly:true,emptyText:'与编号联动'},
		            {header: '产品价格',dataIndex: 'productPrice', renderer: usMoneyFunc,emptyText:'与编号联动',allowBlank:false,readOnly:true},
		            {header: 'PV值',dataIndex: 'pV',readOnly:true,renderer: usMoneyFunc,emptyText:'与编号联动'},
		            {header: 'BV值',dataIndex: 'bV',readOnly:true,renderer: usMoneyFunc,emptyText:'与编号联动'},
		            {header: '销售数量', dataIndex: 'saleNumber',allowBlank:false ,regex : /^\d+$/,regexText:"只能输入数字!",listeners:{
	                	 'keyup':function(field , e){
	                		 var priceObj = this.ownerCt.form.findField('productPrice');
	                		 if(priceObj.getValue()!='' && field.getValue()!=''){
	                			 Ext.getCmp('totalprice').setValue(field.getValue()*priceObj.getValue());
	                		 }else{
	                			 Ext.getCmp('totalprice').setValue(0);
	                		 }
			             }
	             },remoteSort:true,sortable:true ,enableKeyEvents:true},
		            {header: '销售总额', dataIndex: 'sumPrice',hideForm:'all', renderer: usMoneyFunc,remoteSort:true,sortable:true},
		            {header: 'ＢＯＯＫ', dataIndex: 'book' ,allowBlank:false ,regex:/^\d*$/,regexText:"只能输入数字!",remoteSort:true,sortable:true},
		            {header: '统计',id:'totalprice',emptyText:'总计',readOnly:true,hideGrid:true,renderer: usMoneyFunc}
		         ],
	
	queryFormItms: [{ 
				layout: 'tableform',
	            layoutConfig: {
	            	columns: 3,
	            	columnWidths: [0.3,0.3,0.3], 
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
			            {xtype:'textfield',fieldLabel: '经销商名称', name: 'distributorName',width:172,readOnly:true,emptyText:'与编号联动'},
		                {fieldLabel: '专卖店编号', allowBlank:false , xtype:'shopCombo',valueField:'shopCode',
				             hiddenName:'shopCode',listeners:{
				            	'select':function(combo,record,index){
				            		this.ownerCt.ownerCt.form.findField('shopName').setValue(record.data.shopName);
				            	}
				         }},
				        {xtype:'textfield',fieldLabel: '专卖店名称', name: 'shopName',width:172,readOnly:true,emptyText:'与编号联动'},
				        {fieldLabel: '产品编号', xtype:'productCombo',valueField:'productCode',
			             hiddenName:'productCode',listeners:{
			            	 'beforequery' : function(e) {
			            			e.combo.store.baseParams.productCode = e.query;// 查询product编号
			                	},
		                	 'select':function(combo,record,index){
				            		this.ownerCt.ownerCt.form.findField('productName').setValue(record.data.productName);
				            }
			            }}, 
			            {xtype:'textfield',fieldLabel: '产品名称', name: 'productName',readOnly:true,emptyText:'与编号联动'},
		                {xtype:'datetimefield', format: 'Y-m-d', editable: true, fieldLabel: '开始日期', name: 'startTime'},
		                {xtype:'datetimefield', format: 'Y-m-d', editable: true, fieldLabel: '结束日期', name: 'endTime' },
		                {xtype:'textfield',fieldLabel: 'book', name: 'book'}
		               ]
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
    
    /**批量删除**/
    delData: function() {
        if (this.checkMany()) {
            Ext.Msg.confirm("提示", "是否确定？", function(btn, text) {
                if (btn == 'yes') {
                	var items = this.grid.getSelectionModel().selections.items;
                	var ids = [];
                	for(var index=0;index<items.length;index++){
                		ids.push(items[index].id);
                	}
                    this.grid.body.mask('正在处理，请稍等...', 'x-mask-loading');
                    Ext.Ajax.request({
                        url     : this.urlRemove,
                        params  : {ids : ids},
                        success : function(response, opts) {
                        	if(this.delegateWhenDelete(response, opts)){
                                  Ext.MessageBox.alert('提示', '操作成功！');
                                  this.grid.store.reload();
                        	}
                        },
                        failure : function(){
                        	this.grid.body.unmask();
                        },
                        scope   : this
                    });
                }
            }.createDelegate(this));
        }
    },
    
    /****
     * 增加下方提示信息
     */
    addButtonOnBottombar:function(toolbar, index){
    	toolbar.insertButton(index++,{xtype: 'tbspacer', width: 50});
    	toolbar.insertButton(index++,'<b><font color=red>提示：当经销商被删除时，订单信息也将被删除！</font><b>');
    },
    
    /****
     * 增加上方提示信息
     */
    addButtonOnTopbar:function(toolbar, index){
    	toolbar.insertButton(index++,{xtype: 'tbspacer', width: 30});
    	toolbar.insertButton(index++,'<b><font color=red>信息：订单的录入提供个计算经销商奖金及业绩的依据，请保证数据的准确性！</font><b>');
    },
    
    
	constructor: function() {
		ST.base.orderView.superclass.constructor.call(this, {});
	}
});
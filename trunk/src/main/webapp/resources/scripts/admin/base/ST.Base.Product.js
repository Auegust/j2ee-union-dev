Ext.BLANK_IMAGE_URL = "./../resources/scripts/ext/resources/images/default/s.gif";

Ext.namespace("ST.base");
//Ext.form.Field.prototype.msgTarget = 'under';
ST.base.productView = Ext.extend(ST.ux.ViewGrid, {
	dlgWidth: 360,
	dlgHeight: 300,
	urlGridQuery: './../product/pageQueryTProductInfos.json',
	urlAdd: './../product/insertTProductInfo.json',
	urlEdit: './../product/updateTProductInfo.json',
	urlLoadData: './../product/loadTProductInfo.json',
	urlRemove: './../product/deleteTProductInfo.json',
	addTitle: "新增产品",
    editTitle: "更新产品信息",
    gridTitle: "产品列表",
    formbarTitle:"产品查询",
	girdColumns: [  {header: 'ID', dataIndex: 'id', hideGrid: true, hideForm: 'add', hidden:true ,readOnly: true},
		            {header: '产品编号', dataIndex: 'productCode', hideForm:'add',readOnly: true},
		            {header: '产品名称', dataIndex: 'productName', allowBlank:false},
		            {header: '产品价格', dataIndex: 'productPrice',allowBlank:false,renderer: 'usMoney',
		            	regex : /^\d{0,8}\.{0,1}(\d{1,2})?$/,regexText:"请输入有效价格，保留两位有效数字!"},
		            {header: '产品价格', dataIndex: 'productPrice_Name', hideForm:'all'},	
		            {header: 'PV', dataIndex: 'productPv',allowBlank:false,renderer: 'usMoney',
		            	regex : /^\d{0,8}\.{0,1}(\d{1,2})?$/,regexText:"请输入有效价格，保留两位有效数字!"},
	            	{header: 'PV', dataIndex: 'productPv_Name', hideForm:'all'},	
		            {header: 'BV', dataIndex: 'productBv',allowBlank:false,renderer: 'usMoney',
		            		regex : /^\d{0,8}\.{0,1}(\d{1,2})?$/,regexText:"请输入有效价格，保留两位有效数字!"}, 
		            {header: 'PV', dataIndex: 'productBv_Name', hideForm:'all'},
		            {header: '是否有效', dataIndex: 'status',allowBlank:false ,hideGrid:true, fieldtype:'cb_isValid'},
		            {header: '是否有效', dataIndex: 'status_Name',hideForm:'all', renderer:colorfunc },
		            {header: '备注', dataIndex: 'remark'},
		         ],
	
	queryFormItms: [{ 
				layout: 'tableform',
	            layoutConfig: {
	           		columns: 2,
	            	columnWidths: [0.5, 0.5]
	            },           
		        items:[{xtype:'textfield', fieldLabel: '产品编号', name: 'productCode', anchor:'100%'},
		               {xtype:'textfield', fieldLabel: '产品名称', name: 'productName', anchor:'100%'},
		              ]
		    }],
	constructor: function() {
		ST.base.productView.superclass.constructor.call(this, {});
	}
});
Ext.BLANK_IMAGE_URL = "./../resources/scripts/ext/resources/images/default/s.gif";
Ext.namespace("ST.base");
Ext.form.Field.prototype.msgTarget = 'side';
ST.base.bonusView = Ext.extend(ST.ux.ViewGrid, {
	dlgWidth: 400,
	dlgHeight: 300,
	urlGridQuery: './../bonus/pageQueryTBounsConf.json',
	urlAdd: './../bonus/insertTBounsConf.json',
	urlEdit: './../bonus/updateTBounsConf.json',
	urlLoadData: './../bonus/loadTBounsConf.json',
	urlRemove: './../bonus/deleteTBounsConf.json',
	addTitle: "新增奖金配置信息",
    editTitle: "更新奖金配置信息",
    gridTitle: "奖金配置信息列表",
    formbarTitle:"奖金配信息置查询",
	girdColumns: [  {header: 'ID', dataIndex: 'id', hideGrid: true, hideForm: 'add', hidden:true ,readOnly: true},
		            {header: '职级', dataIndex: 'rankId',fieldtype:'rankCombo',hideForm:'edit',hiddenName:'rankId' ,hideGrid:true , allowBlank:false},
		            {header: '职级', dataIndex: 'rankId_Name',hideForm:'all'},
		            {header: '直接奖比例',     dataIndex: 'directP',  allowBlank:false, regex : /^\d{0,8}\.{0,1}(\d{1,2})?$/,regexText:"请输入有效比例，保留两位精度!"},
		            {header: '间接奖比例',     dataIndex: 'indirectP',allowBlank:false, regex : /^\d{0,8}\.{0,1}(\d{1,2})?-?\d{0,8}\.{0,1}(\d{1,2})?$/,regexText:"请输入有效比例段，保留两位精度!"},
		            {header: '荣衔奖比例', 	   dataIndex: 'honorP',   allowBlank:false, regex : /^\d{0,8}\.{0,1}(\d{1,2})?$/,regexText:"请输入有效比例，保留两位精度!"},	
		            {header: '特别奖比例', 	   dataIndex: 'specialP', allowBlank:false, regex : /^\d{0,8}\.{0,1}(\d{1,2})?$/,regexText:"请输入有效比例，保留两位精度!"},
	            	{header: '终身奖比例',  dataIndex: 'achieveP', allowBlank:false, regex : /^\d{0,8}\.{0,1}(\d{1,2})?$/,regexText:"请输入有效比例，保留两位精度!"},	
		            {header: '宽度1', dataIndex: 'w1', allowBlank:false}, 
		            {header: '宽度2', dataIndex: 'w2', allowBlank:false},
		            {header: '宽度3', dataIndex: 'w3', allowBlank:false},
		            {header: '宽度4', dataIndex: 'w4', allowBlank:false},
		            {header: '宽度5', dataIndex: 'w5', allowBlank:false},
		            {header: '宽度6', dataIndex: 'w6', allowBlank:false},
		            {header: '宽度7', dataIndex: 'w7', allowBlank:false},
		            {header: '宽度8', dataIndex: 'w8', allowBlank:false}
		         ],
	//####表单元素         
			queryFormItms: [{ 
				layout: 'tableform',
	            layoutConfig: {
	           		columns: 3,
	            	columnWidths: [0.4, 0.4, 0.4]
	            },           
		        items:[{xtype:'spacer',anchor:'80%'},
		               {xtype:'rankCombo', fieldLabel: '职级', name: 'productCode', anchor:'50%',hiddenName:'rankId'},
		               {xtype:'spacer',anchor:'100%'}]
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
    AjaxValidFormFuc: function(form , action){
    	if(action.result.message == 'ok'){
    		return true;
    	}else{
        	form.findField('rankId').markInvalid(action.result.message);
        	return false;	
    	}
    },
    /****
     * 增加提示信息
     */
    addButtonOnTopbar:function(toolbar, index){
    	toolbar.insertButton(index++,{xtype: 'tbspacer', width: 20});
    	toolbar.insertButton(index++,'<b><font color=red>*提示：职级不能轻易删除！</font><b>');
    },
	constructor: function() {
		ST.base.bonusView.superclass.constructor.call(this, {});
		Ext.getCmp(this.btn_reset_id).setVisible(false);
	}
});
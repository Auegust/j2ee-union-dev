Ext.namespace("ST.ux.ExtField");
Ext.BLANK_AVATER_URL = "./../resources/images/icons/default_icon.png";
ST.ux.ExtField.ClearableComboBox = Ext.extend(Ext.form.ComboBox, {
    initComponent: function() {
        this.triggerConfig = {
            tag:'span', cls:'x-form-twin-triggers', cn:[
            {tag: "img", src: Ext.BLANK_IMAGE_URL, cls: "x-form-trigger x-form-clear-trigger"},
            {tag: "img", src: Ext.BLANK_IMAGE_URL, cls: "x-form-trigger"}        
        ]};
        ST.ux.ExtField.ClearableComboBox.superclass.initComponent.call(this);
    },
    onTrigger1Click : function()
    {
        this.collapse();
        this.reset();                       // clear contents of combobox
        this.fireEvent('cleared');          // send notification that contents have been cleared
    },

    getTrigger: Ext.form.TwinTriggerField.prototype.getTrigger,
    initTrigger: Ext.form.TwinTriggerField.prototype.initTrigger,
    onTrigger2Click: Ext.form.ComboBox.prototype.onTriggerClick,
    trigger1Class: Ext.form.ComboBox.prototype.triggerClass,
    trigger2Class: Ext.form.ComboBox.prototype.triggerClass
});
Ext.reg('clearcombo', ST.ux.ExtField.ClearableComboBox);

/********
 * 基类
 */
ST.ux.ExtField.ComboBox = Ext.extend(ST.ux.ExtField.ClearableComboBox, {
    store : new Ext.data.JsonStore({  //填充的数据
    	url : "./../dict/queryDictionarys.json",
    	fields : new Ext.data.Record.create( ['code', 'name'])
 	}),
    allowBlank: false,
    editable : false,
    valueField: 'code',
    displayField:'name',
    typeAhead: true,
    forceSelection: true,
    mode:'local', 
    triggerAction: 'all',
    emptyText:'请选择字典类型',
    selectOnFocus:true,
    autoLoad:true,
    listeners: {
		'beforequery' : function(e) {
			e.combo.store.baseParams.dictTypeId = e.combo.dictTypeId;
    	},	
    	'afterrender' : function(combo) {
			if(combo.autoLoad) {
				combo.store.baseParams.dictTypeId = combo.dictTypeId;
				combo.store.load();
			} else {
				combo.mode="remote";
			}
    	}
    }
});


/********
 * 查询星级
 */
var rankCombo = new Ext.extend(ST.ux.ExtField.ComboBox, {
	valueField  :'id',
    displayField:'rankName',
    mode  :'remote', 
 	emptyText:'职级列表',
 	store:new Ext.data.Store({
		proxy  : new Ext.data.HttpProxy({url: './../web/findAllRank.json'}),
	    reader : new Ext.data.JsonReader({
	        fields: [
	        	{name: 'id'},
		        {name: 'rankName'},
		    ]
	    })
	}),
  	listeners: {
  		afterRender: function(combo) {
	           combo.setValue(102001);               
	           combo.setRawValue('一星');
	         }  
  	}
});
Ext.reg('rankCombo', rankCombo);

/********
 * 查询所有专卖店
 */
var shopCombo = new Ext.extend(ST.ux.ExtField.ComboBox, {
	valueField  :'id',
    displayField:'shopCode',
    tpl: new Ext.XTemplate(
    	    '<tpl for="."><div class="x-combo-list-item">',
    	    '<font color=red>Code:{shopCode}</font> *Name:{shopName}',
    	    '</div></tpl>'
    	),
    mode  :'remote', 
 	emptyText:'请输入您要查询的专卖店编号',
 	store:new Ext.data.Store({
		proxy  : new Ext.data.HttpProxy({url: './../web/findAllShop.json'}),
	    reader : new Ext.data.JsonReader({
	        fields: [
	        	{name: 'id'},
		        {name: 'shopName'},
		        {name:'shopCode'}
		    ]
	    })
	}),
  	listeners: {}
});
Ext.reg('shopCombo', shopCombo);

/*****
 * 查询所有经销商
 */
var distCombo = new Ext.extend(ST.ux.ExtField.ComboBox, {
	pageSize:8,
	editable : true,
	valueField  :'id',
    displayField:'distributorCode',
    enableKeyEvents:true,
    tpl: new Ext.XTemplate(
    	    '<tpl for="."><div class="x-combo-list-item">',
    	    '<font color=red>Code:{distributorCode}</font> *Name:{distributorName}',
    	    '</div></tpl>'
    	),
    mode  :'remote', 
 	emptyText:'请输入您要查询的经销商编号',
 	store:new Ext.data.Store({
		proxy  : new Ext.data.HttpProxy({url: './../distributor/pageQueryTDistributors.json'}),
	    reader : new Ext.data.JsonReader({
	    	root          : "result",
	        totalProperty : "totalCount",
	        idProperty    : "id",
	        fields: [
	        	{name: 'id'},
		        {name: 'distributorName'},
		        {name: 'distributorCode'},
		        {name: 'shop_Name'},
		        {name: 'shop_Code'}
		    ]
	    }),
	    baseParams:{start:0, limit:8}
	}),
	listeners:{
//    	specialKey :function(field,e){
//            if (e.getKey() == Ext.EventObject.ENTER){
//            	var queryJson = {};
//            	var queryText = e.combo.store.baseParams.distributorCode;
//            	queryJson.distributorCode = queryText;
//            	Ext.apply(this.store.lastOptions.params, queryJson);
//            	this.store.reload();
//            }
//          },
        'beforequery' : function(e) {
			e.combo.store.baseParams.distributorCode = e.query;
    	}
   }
});
Ext.reg('distCombo', distCombo);

/********
 * 查询所有产品
 */
var productCombo = new Ext.extend(ST.ux.ExtField.ComboBox, {
	pageSize:8,
	valueField  :'id',
    displayField:'productCode',
    tpl: new Ext.XTemplate(
    	    '<tpl for="."><div class="x-combo-list-item">',
    	    '<font color=red>Code:{productCode}</font> *Name:{productName}',
    	    '</div></tpl>'
    	),
    mode  :'remote', 
 	emptyText:'请输入您要查询的产品编号',
 	store:new Ext.data.Store({
		proxy  : new Ext.data.HttpProxy({url: './../product/pageQueryTProductInfos.json'}),
	    reader : new Ext.data.JsonReader({
	    	root          : "result",
	        totalProperty : "totalCount",
	        idProperty    : "id",
	        fields: [
	        	{name: 'id'},
		        {name: 'productName'},
		        {name: 'productCode'},
		        {name: 'productBv'},
		        {name: 'productPv'}
		    ],
		    baseParams:{start:0, limit:8}
	    })
	}),
  	listeners: {}
});
Ext.reg('productCombo', productCombo);

/*****
 * 渲染色调
 */
function colorfunc(value, p, record){
	return  "<b><font color=red>"+value+"</font></b>";
}

/******
 * 币值转换
 */
function usMoneyFunc(value, p, record){
	return  "<b><font color=red>"+value+"</font></b>";
}

/******
 * 比例转换
 */
function percentageFunc(value, p, record){
	if(value == 0){
		return  value+" %";
	}else{
		return  "<b><font color=red>"+value+" %"+"</font></b>";
	}
}

/*****
 * 日期转换
 */
function formatDate(value, p, record) {   
    if (Ext.isEmpty(value)) {//判断是否是日期类型的数据   
        return '';   
    } else {   
        if (Ext.isDate(value))   
        	// 用于时间控件返回值  
            return Ext.util.Format.date(value, 'Y-m-d'); 
        else   
        	// 转换为Date类型
            return Ext.util.Format.date(new Date(value), 'Y-m-d');   
    }   
}

var cb_isValid = Ext.extend(Ext.form.ComboBox ,{
    typeAhead: true,
    triggerAction: 'all',
    lazyRender:true,
    mode: 'local',
    forceSelection:true ,
    store: new Ext.data.ArrayStore({
        id: 0,
        fields: [
            'code',
            'displayText'
        ],
        data: [[1, '是'], [0, '否']]
    }),
    valueField: 'code',
    displayField: 'displayText',
    hiddenName:'status'
});
Ext.reg('cb_isValid', cb_isValid);


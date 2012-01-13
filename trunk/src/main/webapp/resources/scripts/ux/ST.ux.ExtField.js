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
 * 查询所属专卖店
 */
var shopCombo = new Ext.extend(ST.ux.ExtField.ComboBox, {
	hiddenName:'shopId',
	valueField  :'id',
    displayField:'shopName',
    mode  :'remote', 
 	emptyText:'专卖店列表',
 	store:new Ext.data.Store({
		proxy  : new Ext.data.HttpProxy({url: './../shop/findShopInfo.json'}),
	    reader : new Ext.data.JsonReader({
	        fields: [
	        	{name: 'id'},
		        {name: 'shopName'},
		    ]
	    })
	}),
  	listeners: {}
});
Ext.reg('shopCombo', shopCombo);

/*****
 * 渲染色调
 */
function colorfunc(value, p, record){
	console.info(value);
	return  "<b><font color=red>"+value+"</font></b>";
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
        data: [[1, '是'], [2, '否']]
    }),
    valueField: 'code',
    displayField: 'displayText',
    hiddenName:'status'
});
Ext.reg('cb_isValid', cb_isValid);


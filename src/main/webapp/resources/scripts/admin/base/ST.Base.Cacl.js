/*!
 * 业绩与奖金的计算
 * 
 */
Ext.onReady(function(){
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    var fs = new Ext.FormPanel({
        frame: true,
        title:'业绩奖金计算',
        labelAlign: 'right',
        labelWidth: 85,
        width:340,
        waitMsgTarget: true,
        items: [
            new Ext.form.FieldSet({
                title: '#############<font color=red>请谨慎操作</font>###',
                autoHeight: true,
                items: {xtype:'datetimefield', 
                		format: 'Y-m-d', 
                		editable: true, 
                		fieldLabel: '计算时间',
                		name: 'endDate' ,
                		width:190,
                		allowBlank:false}
            })
        ]
    });
    
    fs.addButton('重新选择', function(){
        fs.getForm().reset();
    });
    
    var submit = fs.addButton({
        text: '开始计算',
        handler: function(){
        	this.disable();
            fs.getForm().submit({
            		url: './../grade/calc.json',
                	waitMsg : '正在处理，请稍等...',
                    success: function(form, action) {
                    	console.info(action);
                    	Ext.MessageBox.alert("提示", action.result.message);
                    },
                    failure: function(form, action) {
						Ext.MessageBox.alert("警告", action.result.message);
						this.enable();
                    },
                    scope: this
                });
        }
    });
    fs.render('angel-calc');
    fs.on({
        actioncomplete: function(form, action){
            if(action.type == 'submit'){
                submit.enable();
            }
        }
    });
});
Ext.BLANK_IMAGE_URL = "./resources/scripts/ext/resources/images/default/s.gif";
Ext.namespace('Home');

Home.Logout = function() {
	Ext.MessageBox.confirm('退出系统', "您确认退出系统吗？", function(a,b,c){
		if(a == 'yes')
			window.location.href = "./user/logout";
	});
};

Home.ClickTopTab = function(node) {
	var b = Ext.getCmp("centerTabPanel");
	var panelId = "HomePanel_" + node.id;
	var d = b.getItem(panelId);
	var src = node.attributes.action;
	
	if (d == null) {
		var MIF = new Ext.ux.ManagedIFrame.Panel({id:panelId, defaultSrc: src,
			loadMask:true, title: node.attributes.text});
		d = b.add(MIF);
		b.activate(d);
	} else {
		b.activate(d);
	}
};

var HomePage = Ext.extend(Ext.Viewport, {
	north: new Ext.Panel({
        region: 'north',
        height: 32,
        border: false,
        frame: true,
        contentEl: "app-header",
        id: "north-Panel"
    }),
	center: null,
	west: new Ext.ux.AccordionPanel({
		region: 'west',
        id : 'west-panel', 
        title: '直销核算管理系统',
        split: true,
        animate: true,
        width: 200,
        minSize: 175,
        maxSize: 400,
        collapsible: true,
		plugins: [Ext.ux.PanelCollapsedTitle],
        margins: '0 0 0 2',
        items: []
	}),

	south: new Ext.Panel({
		region: "south",
		id: 'south-panel',
		border: false,
		margins: '0 0 0 2',
		bbar: [{
			text: "退出",
			iconCls: "btn-logout",
			handler: function() {
				Home.Logout();
			}
		},{
			text: "收展",
			iconCls: "btn-expand",
			handler: function() {
				var a = Ext.getCmp("north-Panel");
				var b = Ext.getCmp("west-panel");
				if (a.collapsed) {
					b.expand(true);
					a.expand(true);
				} else {
					b.collapse(true);
					a.collapse(true);
				}
			}
		}]
	}),
	
	constructor: function() {
		this.center = new Ext.TabPanel({
			id: "centerTabPanel",
			region: "center",
			deferredRender: true,
			enableTabScroll: true,
			activeTab: 0,
			defaults: {
				autoScroll: true,
				closable: true
			},
			items: [{
	                title: '系统主页',
	                closable: false,
	                iconCls: 'home',
	                autoScroll: true,	                
                  	frame:true,  
                  	html:''  
	            }],
			plugins: new Ext.ux.TabCloseMenu()
		});
		HomePage.superclass.constructor.call(this, {
			layout: "border",
			layoutConfig:{animate:true},
			items: [this.north, this.west, this.center, this.south]
		});
		this.loadWestMenu();
	},
	loadWestMenu: function() {
		var westPanel = Ext.getCmp("west-panel");
		var panel1 = new Ext.tree.TreePanel({
			title: '配置管理',
			layout: "fit",
			animate: true,
			border: false,
			expandable:true,
			autoScroll: true,
			root: new Ext.tree.AsyncTreeNode({
				children : [{
	                text : "专卖店管理",  
	                action: "./application/index",
	                leaf : true
	            },{  
	                text : "经销商管理",  
	                action: "./notice/index",
	                leaf : true 
	            },{  
	                text : "产品管理",  
	                action: "./recommend/index",
	                leaf : true 
	            },{  
	                text : "核算参数管理",  
	                action: "./recommend/zhuanti",
	                leaf : true 
	            },{  
	                text : "其它参数配置",  
	                action: "./comment/index",
	                leaf : true 
	            }]
			}),
			listeners: {
				"click": Home.ClickTopTab
			},
			rootVisible: false
		});
		var panel2 = new Ext.tree.TreePanel({
			title: '订单录入',
			layout: "fit",
			animate: true,
			border: false,
			expandable:true,
			autoScroll: true,
			root: new Ext.tree.AsyncTreeNode({
				children : [{
	                text : "订单录入",  
	                action: "./application/index",
	                leaf : true
	            }]
			}),
			listeners: {
				"click": Home.ClickTopTab
			},
			rootVisible: false
		});
		var panel3 =  new Ext.tree.TreePanel({
			title: '查询统计',
			layout: "fit",
			animate: true,
			border: false,
			expandable:true,
			autoScroll: true,
			root: new Ext.tree.AsyncTreeNode({
				children : [{
	                text : "经销商查询",  
	                action: "./application/index",
	                leaf : true
	            },{  
	                text : "产品销售查询",  
	                action: "./notice/index",
	                leaf : true 
	            },{  
	                text : "奖金查询",  
	                action: "./recommend/index",
	                leaf : true 
	            }]
			}),
			listeners: {
				"click": Home.ClickTopTab
			},
			rootVisible: false
		});
		westPanel.add(panel1);
		westPanel.add(panel2);
		westPanel.add(panel3);
		westPanel.doLayout();
	}
});


Ext.onReady(function(){
    new HomePage();
    Ext.select("#south-panel .x-panel-body").hide();
});
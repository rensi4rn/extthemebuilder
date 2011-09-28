Ext.namespace('Ext.ux.MyApp');

Ext.ux.MyApp.CesiumBaseWindow = Ext.extend(Ext.Window, {
    // Prototype Defaults, can be overridden by user's config object
    layout:'fit',
    width:500,
    height:500,
    maximizable:true,
    minimizable:true,
    collapsible:true,
    shim:false,
    animCollapse:false,
    border:false,
    closeAction:'close',
    closable: true,
    resizable: true,
    iconCls:'window-icon',
    footer:false,
    constrainHeader:true,
    
    initComponent: function(){
        // Called during component initialization

        // Config object has already been applied to 'this' so properties can
        // be overriden here or new properties (e.g. items, tools, buttons)
        // can be added, eg:
/*
        Ext.apply(this, {
            propA: 3
        });
*/

        // Before parent code

        // Call parent (required)
        Ext.ux.MyApp.CesiumBaseWindow.superclass.initComponent.call(this);

        // After parent code
        // e.g. install event handlers on rendered component
        this.addListener('minimize',function(th){
                th.hide()});

        this.addListener('maximize',function(th){
                myView.winBodyResize(th,Ext.lib.Dom.getViewHeight());
                 });

        this.addListener('show',function(th){
            if (th.maximized){th.maximize()}
         });

        this.addListener('activate', function( th ){
            if (th.maximized){th.maximize()}
            var myBtn = th.mybtn;
            if (myBtn){
                myBtn.removeClass(myView.inactivButtonClass);
                myBtn.currCls=myView.activButtonClass;
                myBtn.addClass(myView.activButtonClass);
            }
             });

        this.addListener('deactivate', function( th ){
            var myBtn = th.mybtn;
            if (myBtn){
                myBtn.removeClass(myView.activButtonClass);
                myBtn.currCls=myView.inactivButtonClass;
                myBtn.addClass(myView.inactivButtonClass);
            }
             });

        this.addListener('close',function(th){
            var myBtn = th.mybtn;
            if (myBtn)
                myView.statusBar.removeMyButton(myBtn);
        });

    }/*,

    // Override other inherited methods
    onRender: function(){

        // Before parent code

        // Call parent (required)
        MyScope.superclass.onRender.apply(this, arguments);

        // After parent code

    }*/
});

// register xtype to allow for lazy initialization
Ext.reg('cesiumbasewindow', Ext.ux.MyApp.CesiumBaseWindow);
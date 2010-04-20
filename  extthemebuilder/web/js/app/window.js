Ext.namespace('Ext.ux.MyApp');
var win1;
Ext.ux.MyApp.showMyWindow=function(){
    if ((!win1)||(win1=="undefined")){
        win1 = new Ext.Window({
            layout:'fit',
            title:'new window',
            width:310,
            height:160,
            //manager:windows,
            //autoHeight:true,
            maximizible:true,
            //minimizable:true,
            collapsible:true,
            shim:false,
            animCollapse:false,
            border:false,
            closeAction:'hide',
            closable: true,
            resizable: true,
            //plain: false,
            footer:false,
            constrain:true/*,
            border: true*//*,
    items: [login]*/
        });

    }
    win1.show();
    return win1;
}
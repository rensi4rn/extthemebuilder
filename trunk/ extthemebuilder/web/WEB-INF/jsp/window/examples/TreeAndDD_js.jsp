<%@ page contentType="text/javascript;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/jsp/include/include_js.jsp" %>

Ext.namespace('Ext.ux.MyApp');

Ext.ux.MyApp.TreeAndDDWindow=Ext.extend(Ext.ux.MyApp.Module, {
init : function(){
        var idWindow = 'list-ct-tree';

        var oldWin = myView.getTaskButtonByIdWin(idWindow).win;
        if ((!oldWin)||oldWin==undefined){
            oldWin = this.createWindow(idWindow);
            myView.statusBar.addTaskButton(oldWin);
        }
        oldWin.show();

    },


createWindow : function(idWin){
    var listWin;

    if ((!listWin)||(listWin==undefined)){

    var tree = new Ext.tree.TreePanel({
         margin:'10 10 10 10'
        , region:'center'
        , layout:'fit'
        , buttonAlign:'center',
        height: 300,
        width: 400,
        useArrows:true,
        autoScroll:true,
        animate:true,
        enableDD:true,
        containerScroll: true,
        rootVisible: false,
        frame: true,
        root: {
            nodeType: 'async'
        },

        // auto create TreeLoader
        dataUrl: '<c:url value="/examples/tree/check-nodes.js"/>',

        listeners: {
            'checkchange': function(node, checked){
                if(checked){
                    node.getUI().addClass('complete');
                }else{
                    node.getUI().removeClass('complete');
                }
            }
        },

        buttons: [{
            text: 'Get Completed Tasks',
            handler: function(){
                var msg = '', selNodes = tree.getChecked();
                Ext.each(selNodes, function(node){
                    if(msg.length > 0){
                        msg += ', ';
                    }
                    msg += node.text;
                });
                Ext.Msg.show({
                    title: 'Completed Tasks',
                    msg: msg.length > 0 ? msg : 'None',
                    icon: Ext.Msg.INFO,
                    minWidth: 200,
                    buttons: Ext.Msg.OK
                });
            }
        }]
    });

    tree.getRootNode().expand(true);
/*


        var panel = new Ext.Panel({
            frame:true
            ,xtype:'panel'
            , margin:'10 10 10 10'
            , region:'center'
            , layout:'fit'
            , buttonAlign:'center'
            , items:[tabs]});*/

        listWin = new Ext.ux.MyApp.CesiumBaseWindow({
            id:idWin,
            width:630,
            height:400,
            title:'Tree and DD example',
            manager:windows,
            items: [tree]
        });


    }
    return listWin;
}
})
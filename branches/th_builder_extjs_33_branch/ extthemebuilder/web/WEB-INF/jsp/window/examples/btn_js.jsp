<%@ page contentType="text/javascript;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/jsp/include/include_js.jsp" %>
Ext.namespace('Ext.ux.MyApp');

Ext.ux.MyApp.btnWindow=Ext.extend(Ext.ux.MyApp.Module, {
init : function(){
        var idWindow = 'simple-btn';

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

    //SamplePanel type definition
    var SamplePanel = Ext.extend(Ext.form.FormPanel, {
        layout:'fit',
        region:'center',
        //style: 'margin-top:15px',
        bodyStyle: 'padding:10px',
        autoScroll: true
    });

    var htmlEditor = new Ext.form.HtmlEditor({
        value: Ext.dummy.shortBogusMarkup,
        name:'paramsHtmlEditor'
        ,xtype: 'htmleditor'
        , grow:true
        , growMax:200
        ,fieldLabel:'HtmlEditor'
        , margin:'10 10 10 10'
        ,labelStyle: 'font-weight:bold;'

    });
    //Button panel
        var buttonPanel = new SamplePanel({
        title: 'Mix and match icon sizes to create a huge unusable toolbar',
        items:htmlEditor,
        tbar: [{
            xtype: 'buttongroup',
            columns: 3,
            title: 'Clipboard',
            items: [{
                text: 'Paste',
                scale: 'large',
                rowspan: 3, iconCls: 'add',
                iconAlign: 'top',
                cls: 'x-btn-as-arrow'
            },{
                xtype:'splitbutton',
                text: 'Menu Button',
                scale: 'large',
                rowspan: 3,
                iconCls: 'add',
                iconAlign: 'top',
                arrowAlign:'bottom',
                menu: [{text: 'Menu Item 1'}]
            },{
                xtype:'splitbutton', text: 'Cut', iconCls: 'add16', menu: [{text: 'Cut Menu Item'}]
            },{
                text: 'Copy', iconCls: 'add16'
            },{
                text: 'Format', iconCls: 'add16'
            }]
        },{
            xtype: 'buttongroup',
            columns: 3,
            title: 'Other Actions',
            items: [{
                text: 'Paste',
                scale: 'large',
                rowspan: 3, iconCls: 'add',
                iconAlign: 'top',
                cls: 'x-btn-as-arrow'
            },{
                xtype:'splitbutton',
                text: 'Menu Button',
                scale: 'large',
                rowspan: 3,
                iconCls: 'add',
                iconAlign: 'top',
                arrowAlign:'bottom',
                menu: [{text: 'Menu Button 1'}]
            },{
                xtype:'splitbutton', text: 'Cut', iconCls: 'add16', menu: [{text: 'Cut Menu Item'}]
            },{
                text: 'Copy', iconCls: 'add16'
            },{
                text: 'Format', iconCls: 'add16'
            }]
        },{text:'Date',xtype:'datefield', style: 'margin-left:35px',value:'03/01/2010'}
        ]

    });

        listWin = new Ext.ux.MyApp.CesiumBaseWindow({
            id:idWin,
            width:630,
            height:400,
            title:'Buttons example',
            manager:windows,
            layout:'fit',
            region:'center',
            items: buttonPanel
        });

    }
    return listWin;
}
})
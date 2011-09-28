<%@ page contentType="text/javascript;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/jsp/include/include_js.jsp" %>

Ext.namespace('Ext.ux.MyApp');

Ext.ux.MyApp.pivotWindow=Ext.extend(Ext.ux.MyApp.Module, {
init : function(){
        var idWindow = 'simple-pivot';

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

    var SaleRecord = Ext.data.Record.create([
        {name: 'person',   type: 'string'},
        {name: 'product',  type: 'string'},
        {name: 'city',     type: 'string'},
        {name: 'state',    type: 'string'},
        {name: 'month',    type: 'int'},
        {name: 'quarter',  type: 'int'},
        {name: 'year',     type: 'int'},
        {name: 'quantity', type: 'int'},
        {name: 'value',    type: 'int'}
    ]);

    var myStore = new Ext.data.Store({
        url: 'getPivotData.htm',
        autoLoad: true,
        reader: new Ext.data.JsonReader({
            root: 'rows',
            idProperty: 'id'
        }, SaleRecord)
    });

    var pivotGrid = new Ext.grid.PivotGrid({
        width     : 800,
        height    : 259,
        store     : myStore,
        aggregator: 'sum',
        measure   : 'value',

        viewConfig: {
            title: 'Sales Performance'
        },

        leftAxis: [
            {
                width: 80,
                dataIndex: 'person'
            },
            {
                width: 90,
                dataIndex: 'product'
            }
        ],

        topAxis: [
            {
                dataIndex: 'year'
            },
            {
                dataIndex: 'city'
            }
        ]
    });


        listWin = new Ext.ux.MyApp.CesiumBaseWindow({
            id:idWin,
            width:800,
            height:290,
            maximizable:false,
            resizable:false,
            title:'Pivot Grid example',
            manager:windows,
            layout:'fit',
            region:'center',
            items: pivotGrid
        });

    }
    return listWin;
}
})
<%@ page contentType="text/javascript;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/jsp/include/include_js.jsp" %>

Ext.namespace('Ext.ux.MyApp');

Ext.ux.MyApp.winWindow=Ext.extend(Ext.ux.MyApp.Module, {
init : function(){
        var idWindow = 'simple-window';

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

        listWin = new Ext.ux.MyApp.CesiumBaseWindow({
            id:idWin,
            width:630,
            height:400,
            title:'Window example',
            manager:windows,
            items: []
        });

    }
    return listWin;
}
})
<%@ page contentType="text/javascript;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/include_js.jsp" %>

<%--
  ~ Theme Builder for ExtJS framework Project.
  ~
  ~ Copyright (c) 2009 - 2011 Sergey Chentsov. All rights reserved.
  ~
  ~ License: LGPL_v3
  ~ Author: Sergey Chentsov (extjs id: iv_ekker)
  ~ mailto: sergchentsov@gmail.com
  --%>

<%--<%@ page session="false"%>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description: This is a Theme Builder application main workarea
 * @license: GPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 11.08.2009
 * @Time: 14:31:28
 */
Ext.namespace('Ext.ux.MyApp');

var invStr =function (exStr){
        var res;
        try{
            res = eval(exStr);
        }catch(e){
            if (console) console.error(e);
            Ext.Msg.alert('<spring:message code="show.window.error"/>','<spring:message code="show.window.error.message"/> '+e.name+': '+e.message);
        }
        return res;
    };

var windows = new Ext.WindowGroup();

var disableWindow = function(w){w.disable();}
var enableWindow = function(w){w.enable();}

var logoutAvailable=false;

var askConfirm=function(){
    if (!logoutAvailable){
        logoutAvailable=false;
        return 'Do you really wish to leave the page?';
    }
};
window.onbeforeunload=askConfirm;
////
String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g, ""); };

Ext.Toolbar.prototype.enableOverflow = true;

var _mb_alert = Ext.Msg.alert;
Ext.Msg.alert = function(){
    var zseed = Ext.WindowMgr.zseed;
    Ext.WindowMgr.zseed = 50000;
    var res = _mb_alert.apply(this,arguments);
    Ext.WindowMgr.zseed = zseed;
    return res;
};

var _mb_confirm = Ext.Msg.confirm;
Ext.Msg.confirm = function(){
    var zseed = Ext.WindowMgr.zseed;
    Ext.WindowMgr.zseed = 50000;
    var res = _mb_confirm.apply(this,arguments);
    Ext.WindowMgr.zseed = zseed;
    return res;
};

var _mb_wait = Ext.Msg.wait;
Ext.Msg.wait = function(){
    var zseed = Ext.WindowMgr.zseed;
    Ext.WindowMgr.zseed = 50000;
    var res = _mb_wait.apply(this,arguments);
    Ext.WindowMgr.zseed = zseed;
    return res;
};

var _mb_show = Ext.Msg.show;
Ext.Msg.show = function(){
    var zseed = Ext.WindowMgr.zseed;
    Ext.WindowMgr.zseed = 50000;
    var res = _mb_show.apply(this,arguments);
    Ext.WindowMgr.zseed = zseed;
    return res;
};

Ext.ux.MyApp.springApp = function(config){
    Ext.apply(this, config);
    this.addEvents({
        'ready' : true,
        'beforeunload' : true
    });

    Ext.onReady(this.initApp, this);
};

var onClick = Ext.form.Checkbox.prototype.onClick;
Ext.override(Ext.form.Checkbox, {
    onClick : function(){
            var p=this.el.up('form');
            if(!p)p=Ext.fly(document.body);
            var els = p.dom['params['+this.el.dom.name+']'];
            if (els) els.value=this.inputValue;

        onClick.apply(this, arguments);
        }
 });

Ext.extend(Ext.ux.MyApp.springApp, Ext.util.Observable, {
    isReady: false,
    startMenu: null,
    modules: null,
    btnIdPrefix:'btn-',
    inactivButtonClass:'x-btn-over',
    activButtonClass:'x-btn-click',
    loadResInvoke:null,
    postRequest:null,
    handleRes:null,
    processRes:null,
    onClickLeaf:null,
    onClickButton:null,
    onThemeSelectClick:null,
    onLogoutButtonClick:null,
    logoutUrl:null,
    mainMenuToolBar:null,
    statusBar:null,
    taskbar:null,
    onUnload : null,
    getStartConfig : function(){},
    viewport:null,
    infoPortal:null,
    appTitle:null,
    scsFn:null,
    
    initApp : function(){
    	this.startConfig = this.startConfig || this.getStartConfig();

        this.onThemeSelectClick=function(n) {
                var themeUrl = n.url;
                Ext.util.CSS.swapStyleSheet('theme', themeUrl);
            };

        this.onLogoutButtonClick=function (btn){
            document.location.href=myView.logoutUrl;
        };

        this.onUnload = function(e){
/*            if ( (undefined ==logoutAvailable)||(!logoutAvailable)){

                e.stopEvent();
            }
            logoutAvailable=false;*/
            if (!logoutAvailable){
                if(this.fireEvent('beforeunload', this) === false){
                    e.stopEvent();
                }
            }else {
                logoutAvailable=false;
            }
        };
        this.statusBar = new Ext.Toolbar(
        {
            height:25,
            items:[ {xtype: 'tbseparator'}],
            addTaskButton : function(win){
                return this.addMyButton(win);
            },
            addMyButton : function(win){
                var btn = new Ext.Toolbar.Button({
                        xtype:'button'
                        ,cls:myView.inactivButtonClass
                        ,currCls:myView.inactivButtonClass
                        ,text: Ext.util.Format.ellipsis(win.title, 20)
                        , width:75
                        , minWidth:75
                        ,listeners:{mouseout: function( th, e ){
                                th.addClass( th.currCls);
                                    },
                            mouseover:function ( th, e){
                                th.addClass( th.currCls);
                            }
                        }
                        ,handler : function(){
                            if(win.minimized || win.hidden){
                                win.show();
                                if(win.maximized){
                                    myView.winBodyResize(win,Ext.lib.Dom.getViewHeight());
                                };
                            }else if(win == win.manager.getActive()){
                                win.minimize();
                            }else{
                                win.toFront();
                            }
                        }
                        , clickEvent:'mousedown'
                });
                btn.win=win;
                win.mybtn=btn;
                btn.id=(myView.btnIdPrefix + win.id) ;
                myView.statusBar.add(btn);
                myView.statusBar.doLayout();

                if(!this.buttonWidthSet){
                    this.lastButtonWidth = btn.width;
                }

                this.setActiveButton(btn);
                
                return btn;
            },
            setActiveButton : function(btn){
                myView.statusBar.activeButton = btn;
            },
            removeMyButton : function(btn){
                myView.statusBar.items.remove(btn).destroy();
                myView.statusBar.doLayout();
            }
        }
                );

        this.loadResInvoke=function (name, type,invokeStr, params, mthd, iconCls){
            var createdObj = document.getElementById(name);
            if (createdObj)
                document.getElementsByTagName('head')[0].removeChild(createdObj);
            /*if (!createdScriptOrLinkObject){*/
                var ref;
                if (type=='js'){
                    var resObj;
                    if (name){
                        ref=document.createElement('script');
                        ref.setAttribute('TYPE','text/javascript');
                        ref.setAttribute('CHARSET','UTF-8');
                        ref.setAttribute('id', name);
                Ext.Ajax.request({
                            url:name,
                    params:params,
                            method: mthd?mthd:'POST',
                    timeout:600000,
                            disableCaching : false,
                    success:function(resp, option){
                                    //if (isValidResponse(resp)){
                                        ref.text=resp.responseText;
                                        document.getElementsByTagName('head')[0].appendChild(ref);
                                        if (invokeStr){
                                            resObj = invStr(invokeStr);
                                            if (resObj&&iconCls&&resObj.getWindow){
                                                resObj.getWindow().setIconClass(iconCls);
                    }
                                        }
                                    //}
                            }
                });
                    }else{
                        if (invokeStr) {
                            resObj = invStr(invokeStr);
                            if (resObj&&iconCls&&resObj.getWindow){
                                resObj.getWindow().setIconClass(iconCls);
                            }
                        }
                    }
                }
                else if (type=='css'){ //if filename is an external CSS file
                    ref=document.createElement('link');
                    ref.setAttribute('rel', 'stylesheet');
                    ref.setAttribute('type', 'text/css');
                    ref.setAttribute('href', name);
                    ref.setAttribute('id', name);
                    if (typeof ref!='undefined')
                      document.getElementsByTagName('head')[0].appendChild(ref)
                }
        };

        this.postRequest = function(url, params){
            var temp=document.createElement("form");
            temp.action=url;
            temp.method="POST";
            temp.style.display="none";
            for(var x in params) {
                var opt=document.createElement("textarea");
                opt.name=x;
                opt.value=params[x];
                temp.appendChild(opt);
            }
            document.body.appendChild(temp);
            temp.submit();
            document.body.removeChild(temp);
        };

        this.handleRes = function(res){
            var type = res.restype;
            var resUrl = res.url;
            var iconCls = res.iconCls;
            var params = res.params;

            if ('js'==type){
                var сlassName = res.entityClassName;
                var capt = res.text;
                var clsOrCaptn = (сlassName||capt);
                var winType = res.winType;
                var winUrl = resUrl
                            ?(resUrl +
                                (clsOrCaptn
                                ?('?entityClassName='+сlassName +
                                    '&<%=ApplicationConstants.P_CAPTION%>='+capt)
                                :''))
                            :null;

                myView.loadResInvoke(winUrl
                        ,'js'
                    ,(winType+сlassName)?'new Ext.ux.MyApp.'
                    +winType
                    +сlassName.replace(/\./g,'_')
                        +'Window();'
                    :false
                    ,params||{},'GET', iconCls);
            }else if ('css'==type){
                myView.loadResInvoke(resUrl
                    ,type
                    ,null
                    ,params||{}, 'GET');
                    }
            };

        this.processRes = function(resourcesJSON, res){
            //var resources = Ext.getCmp(ownerTreeId).nodeHash[id].attributes.resources;
            var resources = Ext.util.JSON.decode(resourcesJSON);
            var resource = resources[res];

            myView.handleRes(resource);

            res++;
            if (res<resources.length)
                setTimeout('myView.processRes(\''+resourcesJSON+'\','+ res+')',270);
                //setTimeout('myView.processRes("'+ownerTreeId+'","'+ id+'",'+ res+')',270);
        };
        this.onClickLeaf = function(n) {
            var resources = n.attributes.resources;
            //var ownerTreeId = n.ownerTree.getId();
            var res=0;
            myView.processRes(Ext.util.JSON.encode(resources), res);
        };
        this.onClickButton = function(n) {
            var resources = n['initialConfig'].resources;
            //var ownerTreeId = n.ownerTree.getId();
            var res=0;
            myView.processRes(Ext.util.JSON.encode(resources), res);
        };
        var clickListener = {
            click: this.onClickLeaf
        };
        var tools = [{
            id:'close',
            handler: function(e, target, panel){
                panel.ownerCt.remove(panel, true);
            }
        }];

        var colItems = new Array();

        var colItem_0 = new Array();

       colItem_0.push({
            title: 'Theme creation',
            tools: tools,
            html: '1.Click <b>Ext Theme Builder</b>.<br>2.Select <b>Template</b> (blue or gray) in the top toolbar (i recommend - <b>gray</b>)<br>3.Select <b>Base Color</b>, <b>Header Color</b>, <b>Background color</b>, <b>Border color</b>, <b>Header Font,</b> <b>Font, </b><b>Window transparency,&nbsp;</b><b>Toolset</b> (Default, Vista, TargetProcess, Graphite) <b>and ExtJS version</b>. Unselected Border color will be assigned automatically according to base color<br>4.Click <b>Apply</b><br>5.Enter <b>Theme name</b><br>6.Click <b>Download Theme</b> (Button is disabled in demo mode). It will be xtheme-<b>Theme name</b>.zip file<b><br></b>'
        });

       colItem_0.push({
            title: 'Ads',
            tools: tools,
            html: 'With <b>Ext Theme Builder</b>&nbsp;you can create themes of <b>any</b> color.<br>You can make your ExtJS application colourful.<br><br>It can be a palette of themes.<br>It can be color of your firm or corporate style.<br>It can be your favourite color.<br><br>You, your customers and users will be happy.<br><b>Welcome !</b><br><br>'
        });


       colItem_0.push({
            title: 'News',
            tools: tools,
            html: 'Theme builder for ExtJS now supports next ExtJS versions:<br><b><font color="Black"><font color="#008000">3.3</font>, 3.2, 3.1, 3.0, <font color="#008000">2.3</font></font>.</b>'
        });

        colItems.push(colItem_0);

        var colItem_1 = new Array();

       colItem_1.push({
            title: 'How to create theme changer in your application',
            tools: tools,
            html: 'xtheme-<b>Theme name</b>.zip file contains two folders: <b>css</b> and <b>images</b>. You should unzip the file and copy them to the <b>resources</b> folder of your ExtJS application. <br>You should create <b>tag</b> <br>&lt;link rel="stylesheet" type="text/css" id="theme" href=""/&gt; <br>and <b>handler</b>: <br>function(n) {<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; var themeUrl = your xtheme-<b>Theme name</b>.css url ;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Ext.util.CSS.swapStyleSheet("theme", themeUrl);<br>};<br>'
        });



       colItem_1.push({
            title: 'Dictionary of colors',
            tools: tools,
            html: '​The dictionary of colors is a table of names of colors and hexadecimal values.It supports Drag and Drop record of the table on <b>Base color</b> field and <b>Theme name </b>field on the theme builder form.<br>'
        });

       colItem_1.push({
            title: 'About Apply theme Live Link',
            tools: tools,
            html: 'You can quickly apply created theme to your application to see the effect of new theme!<br>Panel <b><font color="#008000">Apply theme LiveLink </font></b>contains link on generated theme on the server. <br>You can just to <b>copy</b> <b><font color="#008000">Apply theme Live Link</font></b> text to the clipboard, <b>open</b> your application in the new browsers tab, <b>paste</b> <b><font color="#008000">Apply theme </font></b><b><font color="#008000">Live Link</font></b> text from clipboard to the browsers address field and<b> push Enter</b>. Generated theme will be applied to your application and you can see effect of new theme! <br>To clean the effect just reload page with your application.'
        });

        colItems.push(colItem_1);


        var colItem_2 = new Array();

       colItem_2.push({
            title: 'Contacts',
            tools: tools,
            html: '<b>​Ext Theme Builder</b>.<br>by Sergei Chentsov.<br><br>Feel free to send any questions to <a href="mailto:sergchentsov@gmail.com?subject=Ext%20Theme%20Builder">sergchentsov@gmail.com</a><br><br>'
        });

       colItem_2.push({
            title: 'Apply Theme Live Link',
            tools: tools,
            html: '<br>'
        });

       colItem_2.push({
            title: 'Theme Builder Window in your application! ',
            tools: tools,
            html: 'There is possibility to invoke Theme Builder Window in your ExtJS application quickly!<br>You can use Theme Builder Window in your ExtJS application <b>right now</b>.<br><pre class="bbcode_code" style="height: 96px;">javascript:(function(){<br>var fileref=document.createElement("script");<br>fileref.setAttribute("type","text/javascript");<br>fileref.setAttribute("src","http://extbuilder.dynalias.com/springapp/js/app/builder.js");<br>fileref.setAttribute("id","extthemebuilder_"+Math.random());<br>if (typeof fileref!="undefined")<br>document.getElementsByTagName("head")[0]<br>.appendChild(fileref);}())</pre><br><br>Just copy the code and paste to the browser address field on the page with your application.<br>You will see Theme Builder Window injected inside your app.<br>And you can use it and try different theme settings to look and feel of your app.'
        });

        colItems.push(colItem_2);

        this.infoPortal = new Ext.ux.Portal({
            xtype:'portal',
            region:'center',
            margins:'0 0 0 0',
            split:true,
            border:false,
            items: [{
                columnWidth:.33,
                style:'padding:10px 0 10px 10px',
                items:colItems[0]
            },{
                columnWidth:.33,
                style:'padding:10px 0 10px 10px',
                items:colItems[1]
            },{
                columnWidth:.33,
                style:'padding:10px 0 10px 10px',
                items:colItems[2]
            }]
        });
        this.viewport = new Ext.Viewport(
    {
        layout: 'border',
        monitorResize:true,
        items: [
            {
                region:'center',
                layout:'border',
                xtype:'panel',
                border: false,
                margins: '0 0 0 0',
                title:this.appTitle,
                bbar: this.statusBar,
                split:true,
                defaults:{
                    collapseMode:'mini',
                    split : true,
                    border:false
                },
                items:[{
                    region : "west",
                    title : this.navigationPanelTitle,
                    width : 210,
                    plain:true,
                    layout:'border',
                    collapsible : true,
                    items : [
                        {
                            region:'center',
                            border:false,
                            layout : "accordion",
                            layoutConfig : {
                                    activeOnTop : false,
                                    animate : true,
                                    autoWidth : true,
                                    collapseFirst : true,
                                    fill : true,
                                    hideCollapseTool : false,
                                    border:false,
                                    titleCollapse : true
                            },
                            defaults:{
                                    layout:'border',
                                    autoHeight : false,
                                    collapsed:false,
                                    height:200
                                    },
                            items : [
                            {
                                title : "<spring:message code="app.main.menu.theme.builder"/>",
                                items:{
                                    region:'center',
                                    layout:'fit',
                                    xtype:'treepanel',
                                    border:false,
                                    autoScroll:true,
                                    root: new Ext.tree.AsyncTreeNode({
                                        expanded: true,
                                        children: [{
                                            text: '<spring:message code="app.title"/>',
                                            resources:[
<%--
                                            {
                                                url:'<c:url value="/js/ext/colorpicker/colorpicker.js"/>',
                                                restype:'js'
                                            },{
                                                url:'<c:url value="/js/ext/colorpicker/colorpickerfield.js"/>',
                                                restype:'js'
                                            },
--%>
                                            {
                                                url:'<c:url value="/js/ext/colorpicker/colorpicker.css"/>',
                                                restype:'css'
                                            },
                                            {
                                                text: '<spring:message code="app.title"/>',
                                            winType:'simple',
                                            url:'<c:url value="/simplewindow_js.htm"/>',
                                            entityClassName:'',
                                                restype:'js'
                                            }],
                                            leaf: true
                                        }
                                        ]
                                    }),
                                    rootVisible: false,
                                    listeners: clickListener
                                }
                            },
                            {
                                title : "<spring:message code="app.main.menu.examples"/>",
                                items:{
                                    region:'center',
                                    layout:'fit',
                                    xtype:'treepanel',
                                    border:false,
                                    autoScroll:true,
                                    root: new Ext.tree.AsyncTreeNode({
                                        expanded: true,
                                        children: [{
                                            text: 'Grid',
                                            resources:[{
                                                text: 'Grid',
                                            winType:'grid',
                                            url:'<c:url value="/grid_js.htm"/>',
                                            entityClassName:'',
                                                restype:'js'
                                            }],
                                            leaf: true
                                        },{
                                            text: 'Tabs',
                                            resources:[{
                                                text: 'Tabs',
                                            winType:'tabs',
                                            url:'<c:url value="/tabs_js.htm"/>',
                                            entityClassName:'',
                                                restype:'js'
                                            }],
                                            leaf: true
                                        }
                                        ,{
                                            text: 'Tree and DD',
                                            resources:[{
                                                text: 'Tree and DD',
                                            winType:'TreeAndDD',
                                            url:'<c:url value="/dd_js.htm"/>',
                                            entityClassName:'',
                                                restype:'js'
                                            }],
                                            leaf: true
                                        },{
                                            text: 'Window',
                                            resources:[{
                                                text: 'Window',
                                            winType:'win',
                                            url:'<c:url value="/win_js.htm"/>',
                                            entityClassName:'',
                                                restype:'js'
                                            }],
                                            leaf: true
                                        },{
                                            text: 'Buttons',
                                            resources:[{
                                                text: 'Buttons',
                                            winType:'btn',
                                            url:'<c:url value="/btn_js.htm"/>',
                                            entityClassName:'',
                                                restype:'js'
                                            }],
                                            leaf: true
                                        },{
                                            text: 'Pivot Grid',
                                            resources:[{
                                                text: 'Pivot Grid',
                                                winType:'pivot',
                                                url:'<c:url value="/pivot_js.htm"/>',
                                                entityClassName:'',
                                                restype:'js'
                                            }],
                                            leaf: true
                                        }
                                        ]
                                    }),
                                    rootVisible: false,
                                    listeners: clickListener
                                }
                            },
                            {title:'empty', items:{region:'center'},hidden:true, collapsed:true }
                            ]
                        }]
                }, this.infoPortal
                ,{
                region:'east',
                xtype:'grid',
                title:'Dictionary of Colors',
                width : 210,
                collapsible : true,
                store:new Ext.data.JsonStore({
                        fields : [
                                   {name: 'name', mapping : 'name'},
                                   {name: 'color', mapping : 'color'}
                                ],
                        data   : {
                                    records : [
                                        { name : 'AliceBlue', color : '#F0F8FF' },
                                        { name : 'AntiqueWhite', color : '#FAEBD7' },
                                        { name : 'Aqua', color : '#00FFFF' },
                                        { name : 'Aquamarine', color : '#7FFFD4' },
                                        { name : 'Azure', color : '#F0FFFF' },
                                        { name : 'Beige', color : '#F5F5DC' },
                                        { name : 'Bisque', color : '#FFE4C4' },
                                        { name : 'Black', color : '#000000' },
                                        { name : 'BlanchedAlmond', color : '#FFEBCD' },
                                        { name : 'Blue', color : '#0000FF' },
                                        { name : 'BlueViolet', color : '#8A2BE2' },
                                        { name : 'Brown', color : '#A52A2A' },
                                        { name : 'BurlyWood', color : '#DEB887' },
                                        { name : 'CadetBlue', color : '#5F9EA0' },
                                        { name : 'Chartreuse', color : '#7FFF00' },
                                        { name : 'Chocolate', color : '#D2691E' },
                                        { name : 'Coral', color : '#FF7F50' },
                                        { name : 'CornflowerBlue', color : '#6495ED' },
                                        { name : 'Cornsilk', color : '#FFF8DC' },
                                        { name : 'Crimson', color : '#DC143C' },
                                        { name : 'Cyan', color : '#00FFFF' },
                                        { name : 'DarkBlue', color : '#00008B' },
                                        { name : 'DarkCyan', color : '#008B8B' },
                                        { name : 'DarkGoldenRod', color : '#B8860B' },
                                        { name : 'DarkGray', color : '#A9A9A9' },
                                        { name : 'DarkGreen', color : '#006400' },
                                        { name : 'DarkKhaki', color : '#BDB76B' },
                                        { name : 'DarkMagenta', color : '#8B008B' },
                                        { name : 'DarkOliveGreen', color : '#556B2F' },
                                        { name : 'Darkorange', color : '#FF8C00' },
                                        { name : 'DarkOrchid', color : '#9932CC' },
                                        { name : 'DarkRed', color : '#8B0000' },
                                        { name : 'DarkSalmon', color : '#E9967A' },
                                        { name : 'DarkSeaGreen', color : '#8FBC8F' },
                                        { name : 'DarkSlateBlue', color : '#483D8B' },
                                        { name : 'DarkSlateGray', color : '#2F4F4F' },
                                        { name : 'DarkTurquoise', color : '#00CED1' },
                                        { name : 'DarkViolet', color : '#9400D3' },
                                        { name : 'DeepPink', color : '#FF1493' },
                                        { name : 'DeepSkyBlue', color : '#00BFFF' },
                                        { name : 'DimGray', color : '#696969' },
                                        { name : 'DodgerBlue', color : '#1E90FF' },
                                        { name : 'FireBrick', color : '#B22222' },
                                        { name : 'FloralWhite', color : '#FFFAF0' },
                                        { name : 'ForestGreen', color : '#228B22' },
                                        { name : 'Fuchsia', color : '#FF00FF' },
                                        { name : 'Gainsboro', color : '#DCDCDC' },
                                        { name : 'GhostWhite', color : '#F8F8FF' },
                                        { name : 'Gold', color : '#FFD700' },
                                        { name : 'GoldenRod', color : '#DAA520' },
                                        { name : 'Gray', color : '#808080' },
                                        { name : 'Green', color : '#008000' },
                                        { name : 'GreenYellow', color : '#ADFF2F' },
                                        { name : 'HoneyDew', color : '#F0FFF0' },
                                        { name : 'HotPink', color : '#FF69B4' },
                                        { name : 'IndianRed', color : '#CD5C5C' },
                                        { name : 'Indigo', color : '#4B0082' },
                                        { name : 'Ivory', color : '#FFFFF0' },
                                        { name : 'Khaki', color : '#F0E68C' },
                                        { name : 'Lavender', color : '#E6E6FA' },
                                        { name : 'LavenderBlush', color : '#FFF0F5' },
                                        { name : 'LawnGreen', color : '#7CFC00' },
                                        { name : 'LemonChiffon', color : '#FFFACD' },
                                        { name : 'LightBlue', color : '#ADD8E6' },
                                        { name : 'LightCoral', color : '#F08080' },
                                        { name : 'LightCyan', color : '#E0FFFF' },
                                        { name : 'LightGoldenRodYellow', color : '#FAFAD2' },
                                        { name : 'LightGrey', color : '#D3D3D3' },
                                        { name : 'LightGreen', color : '#90EE90' },
                                        { name : 'LightPink', color : '#FFB6C1' },
                                        { name : 'LightSalmon', color : '#FFA07A' },
                                        { name : 'LightSeaGreen', color : '#20B2AA' },
                                        { name : 'LightSkyBlue', color : '#87CEFA' },
                                        { name : 'LightSlateGray', color : '#778899' },
                                        { name : 'LightSteelBlue', color : '#B0C4DE' },
                                        { name : 'LightYellow', color : '#FFFFE0' },
                                        { name : 'Lime', color : '#00FF00' },
                                        { name : 'LimeGreen', color : '#32CD32' },
                                        { name : 'Linen', color : '#FAF0E6' },
                                        { name : 'Magenta', color : '#FF00FF' },
                                        { name : 'Maroon', color : '#800000' },
                                        { name : 'MediumAquaMarine', color : '#66CDAA' },
                                        { name : 'MediumBlue', color : '#0000CD' },
                                        { name : 'MediumOrchid', color : '#BA55D3' },
                                        { name : 'MediumPurple', color : '#9370D8' },
                                        { name : 'MediumSeaGreen', color : '#3CB371' },
                                        { name : 'MediumSlateBlue', color : '#7B68EE' },
                                        { name : 'MediumSpringGreen', color : '#00FA9A' },
                                        { name : 'MediumTurquoise', color : '#48D1CC' },
                                        { name : 'MediumVioletRed', color : '#C71585' },
                                        { name : 'MidnightBlue', color : '#191970' },
                                        { name : 'MintCream', color : '#F5FFFA' },
                                        { name : 'MistyRose', color : '#FFE4E1' },
                                        { name : 'Moccasin', color : '#FFE4B5' },
                                        { name : 'NavajoWhite', color : '#FFDEAD' },
                                        { name : 'Navy', color : '#000080' },
                                        { name : 'OldLace', color : '#FDF5E6' },
                                        { name : 'Olive', color : '#808000' },
                                        { name : 'OliveDrab', color : '#6B8E23' },
                                        { name : 'Orange', color : '#FFA500' },
                                        { name : 'OrangeRed', color : '#FF4500' },
                                        { name : 'Orchid', color : '#DA70D6' },
                                        { name : 'PaleGoldenRod', color : '#EEE8AA' },
                                        { name : 'PaleGreen', color : '#98FB98' },
                                        { name : 'PaleTurquoise', color : '#AFEEEE' },
                                        { name : 'PaleVioletRed', color : '#D87093' },
                                        { name : 'PapayaWhip', color : '#FFEFD5' },
                                        { name : 'PeachPuff', color : '#FFDAB9' },
                                        { name : 'Peru', color : '#CD853F' },
                                        { name : 'Pink', color : '#FFC0CB' },
                                        { name : 'Plum', color : '#DDA0DD' },
                                        { name : 'PowderBlue', color : '#B0E0E6' },
                                        { name : 'Purple', color : '#800080' },
                                        { name : 'Red', color : '#FF0000' },
                                        { name : 'RosyBrown', color : '#BC8F8F' },
                                        { name : 'RoyalBlue', color : '#4169E1' },
                                        { name : 'SaddleBrown', color : '#8B4513' },
                                        { name : 'Salmon', color : '#FA8072' },
                                        { name : 'SandyBrown', color : '#F4A460' },
                                        { name : 'SeaGreen', color : '#2E8B57' },
                                        { name : 'SeaShell', color : '#FFF5EE' },
                                        { name : 'Sienna', color : '#A0522D' },
                                        { name : 'Silver', color : '#C0C0C0' },
                                        { name : 'SkyBlue', color : '#87CEEB' },
                                        { name : 'SlateBlue', color : '#6A5ACD' },
                                        { name : 'SlateGray', color : '#708090' },
                                        { name : 'Snow', color : '#FFFAFA' },
                                        { name : 'SpringGreen', color : '#00FF7F' },
                                        { name : 'SteelBlue', color : '#4682B4' },
                                        { name : 'Tan', color : '#D2B48C' },
                                        { name : 'Teal', color : '#008080' },
                                        { name : 'Thistle', color : '#D8BFD8' },
                                        { name : 'Tomato', color : '#FF6347' },
                                        { name : 'Turquoise', color : '#40E0D0' },
                                        { name : 'Violet', color : '#EE82EE' },
                                        { name : 'Wheat', color : '#F5DEB3' },
                                        { name : 'White', color : '#FFFFFF' },
                                        { name : 'WhiteSmoke', color : '#F5F5F5' },
                                        { name : 'Yellow', color : '#FFFF00' },
                                        { name : 'YellowGreen', color : '#9ACD32' }
                                    ]
                                },
                        root   : 'records'
                    }),
                    columns:[
                                { id : 'name', header: 'Color Name', width: 180, sortable: true, dataIndex: 'name'},
                                {header: "Color HEX", width: 80, sortable: true, dataIndex: 'color',
                                    renderer:function(value, metaData, record, rowIndex, colIndex, store){
                                                var t1 = '0123456789ABCDEF#';
                                                var t2 = 'FEDCBA9876543210#';
                                                var inverseValue =
                                                value.replace( /./gi,
                                                function (s) {
                                                    return t2.charAt(t1.indexOf(s));
                                                }
                                                )
                                                metaData.attr='style="color:'+inverseValue+';background-color:'+value+';"';
                                         return value;
                                    }
                                }
                            ],
                    ddGroup: 'gridDDGroup',
                    enableDragDrop   : true,
                    stripeRows       : true,
                    autoExpandColumn : 'name',
                    selModel         : new Ext.grid.RowSelectionModel({singleSelect : true})
                }
                ]
            }]
    }
            );

        this.getTaskButtonByIdWin=function(idW){
            var btnItem={};
            var items = myView.statusBar.items;
            for(var btnItemIndex = 0, len = items.length; btnItemIndex < len; btnItemIndex++ ){
                var currentBtnItem=items.itemAt(btnItemIndex);
                if (currentBtnItem.id==(myView.btnIdPrefix + idW)){
                    btnItem = currentBtnItem;
                    break;
                }
            }
            return btnItem;
        };

        this.winBodyResize =function ( p, height ){
            if (height>(Ext.lib.Dom.getViewHeight()-myView.statusBar.height)){
                p.setHeight(Ext.lib.Dom.getViewHeight()-myView.statusBar.height);
            }
        }

    this.cascade = function() {
        var x = 0,
        y = 0;
        windows.each(function(win) {
            if (win.isVisible() && !win.maximized) {
                win.setPosition(x, y);
                x += 20;
                y += 20;
            }
        },
        this);
    };
    this.xTickSize = this.yTickSize = 1;
    this.desktopEl = Ext.getBody();

    this.tile = function() {
        var availWidth = this.desktopEl.getWidth(true);
        var x = this.xTickSize;
        var y = this.yTickSize;
        var nextY = y;
        windows.each(function(win) {
            if (win.isVisible() && !win.maximized) {
                var w = win.el.getWidth();

                //              Wrap to next row if we are not at the line start and this Window will go off the end
                if ((x > this.xTickSize) && (x + w > availWidth)) {
                    x = this.xTickSize;
                    y = nextY;
                }

                win.setPosition(x, y);
                x += w + this.xTickSize;
                nextY = Math.max(nextY, y + win.el.getHeight() + this.yTickSize);
            }
        },
        this);
    };

    this.contextMenu = new Ext.menu.Menu({
        items: [{
            text: 'Tile',
            handler: this.tile,
            scope: this
        },
        {
            text: 'Cascade',
            handler: this.cascade,
            scope: this
        }]
    });
    this.desktopEl.on('contextmenu',
        function(e) {
            e.stopEvent();
            this.contextMenu.showAt(e.getXY());
        },
        this);


        Ext.EventManager.on(window, 'beforeunload', this.onUnload, this);
		this.fireEvent('ready', this);
        this.isReady = true;
    }
        }
        )
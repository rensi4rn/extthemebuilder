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

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description: This is a Theme Builder settings form window
 * @license: GPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 11.08.2009
 * @Time: 14:31:28
 */

Ext.namespace('Ext.ux.MyApp');

Ext.ux.MyApp.simpleWindow=Ext.extend(Ext.ux.MyApp.Module, {
init : function(){
        var idWindow = 'list-ct-simple';

        var oldWin = myView.getTaskButtonByIdWin(idWindow).win;
        if ((!oldWin)||oldWin==undefined){
            oldWin = this.createWindow(idWindow);
            myView.statusBar.addTaskButton(oldWin);
        }
        oldWin.show();

        //setup current schema fields values
        var currToken = hist.getToken();
        if (currToken&&(currToken!='')){
            var params = Ext.util.JSON.decode(currToken);
                oldWin.setForm(params);
        }
       /****
        * Setup Drop Targets
        ***/
        // This will make sure we only drop to the view container
        var formPanelDropTargetEl =  oldWin.body.dom;

        var formPanelDropTarget = new Ext.dd.DropTarget(formPanelDropTargetEl, {
            ddGroup     : 'gridDDGroup',
            notifyEnter : function(ddSource, e, data) {
                //Add some flare to invite drop.
                var bdy = oldWin.panel.body;
                bdy.stopFx();
                bdy.highlight();
            },
            notifyDrop  : function(ddSource, e, data){
                // Reference the record (single selection) for readability
                var selectedRecord = ddSource.dragData.selections[0];
                oldWin.panel.getForm().loadRecord(selectedRecord);
                oldWin.downloadButton.disable();
                return(true);
            }
        });

    },

createWindow : function(idWin){
    var listWin;

    if ((!listWin)||(listWin==undefined)){
        var setFocusToNameFld = function(){
            schemaNameFld.focus(true, true);
        };
        var saveHist=function(options, sh){
            if (sh){
                var paramsJsonToken = Ext.util.JSON.encode(options.params);
                hist.addToken(paramsJsonToken);
            }
            refreshHistBtns();
        };
        var refreshHistBtns=function(){
            if (hist.isLast()){
                nextSchemeBtn.disable();
                lastSchemeBtn.disable();
            }else{
                nextSchemeBtn.enable();
                lastSchemeBtn.enable();
            }
            if (hist.isFirst()){
                prevSchemeBtn.disable();
                firstSchemeBtn.disable();
            }else{
                prevSchemeBtn.enable();
                firstSchemeBtn.enable();
            }
        };

        var onChange = function(th, newVal){
            downloadBtn.disable();
            this.originalValue=String(newVal);
        };
        
        var onScsThProc = function(resp, option){
                myView.scsFn(resp, option);

                if (option.saveHist) refreshHistBtns();
                downloadBtn.enable();

                var thToken = Ext.util.JSON.encode(option.params);
                Ext.util.Cookies.set('theme',thToken);
        };

        var setForm= function(params){
            var transp = params.<%=ApplicationConstants.NEW_TRANSP%>;
            var toolsetName = params.<%=ApplicationConstants.TOOLSET_NAME%>;
            var ver = params.version;
            var thId = params.<%=ApplicationConstants.TEMPLATE_ID%>;
            var famHeaderFont = params.<%=ApplicationConstants.FAMILY_HEADER_FONT%>;
            var wghtHeaderFont = params.<%=ApplicationConstants.WEIGHT_HEADER_FONT%>;
            var szHeaderFont = params.<%=ApplicationConstants.SIZE_HEADER_FONT%>;
            var famFont = params.<%=ApplicationConstants.FAMILY_FONT%>;
            var wghtFont = params.<%=ApplicationConstants.WEIGHT_FONT%>;
            var szFont = params.<%=ApplicationConstants.SIZE_FONT%>;
            var headerColor = params.<%=ApplicationConstants.NEW_HEADER_COLOR%>;
            schemaNameFld.setValue(params.<%=ApplicationConstants.NEW_SCHEMA_NAME%>); schemaNameFld.originalValue=String(schemaNameFld.getValue());
            baseCpf.setValue(params.<%=ApplicationConstants.NEW_COLOR%>); baseCpf.originalValue=String(baseCpf.getValue());
            fontCpf.setValue(params.<%=ApplicationConstants.NEW_FONT_COLOR%>); fontCpf.originalValue=String(fontCpf.getValue());
            headerFontCpf.setValue(params.<%=ApplicationConstants.NEW_HEADER_FONT_COLOR%>); headerFontCpf.originalValue=String(headerFontCpf.getValue());
            borderCpf.setValue(params.<%=ApplicationConstants.NEW_BORDER_COLOR%>); borderCpf.originalValue=String(borderCpf.getValue());
            bgCpf.setValue(params.<%=ApplicationConstants.NEW_BG_COLOR%>); bgCpf.originalValue=String(bgCpf.getValue());
            transpSldr.setValue(transp||0==transp?transp:'255'); transpSldr.originalValue=String(transpSldr.getValue());
            toolsetChooser.setValue(toolsetName?toolsetName:'default'); toolsetChooser.originalValue=String(toolsetChooser.getValue());
            verCb.setValue(ver&&(ver.indexOf('.')==1)?ver:'<%=ApplicationConstants.DEFAULT_EXTJS_VERSION%>'); verCb.originalValue=String(verCb.getValue());
            thTemplateCb.setValue(thId?thId:0); thTemplateCb.originalValue=String(thTemplateCb.getValue());
            famHeaderFontSbs.setValue(famHeaderFont?famHeaderFont:'tahoma,arial,verdana,sans-serif'); famHeaderFontSbs.originalValue=String(famHeaderFontSbs.getValue());
            wghtHeaderFontCb.setValue(wghtHeaderFont?wghtHeaderFont:'bold'); wghtHeaderFontCb.originalValue=String(wghtHeaderFontCb.getValue());
            szHeaderFontSpn.setValue(szHeaderFont?szHeaderFont:'11'); szHeaderFontSpn.originalValue=String(szHeaderFontSpn.getValue());
            famFontSbs.setValue(famFont?famFont:'arial,tahoma,helvetica,sans-serif'); famFontSbs.originalValue=String(famFontSbs.getValue());
            wghtFontCb.setValue(wghtFont?wghtFont:'normal'); wghtFontCb.originalValue=String(wghtFontCb.getValue());
            szFontSpn.setValue(szFont?szFont:'11'); szFontSpn.originalValue=String(szFontSpn.getValue());
            headerCpf.setValue(headerColor?headerColor:(thId&&0!=thId?'#D7D7D7':'#CDDEF3')); headerCpf.originalValue=String(headerCpf.getValue());
        };
        var getParams=function(){
            return {
                    '<%=ApplicationConstants.TEMPLATE_ID%>':thTemplateCb.getValue(),
                    '<%=ApplicationConstants.NEW_SCHEMA_NAME%>':schemaNameFld.getValue(),
                    '<%=ApplicationConstants.NEW_COLOR%>':baseCpf.getValue(),
                    '<%=ApplicationConstants.NEW_HEADER_COLOR%>':headerCpf.getValue(),
                    '<%=ApplicationConstants.NEW_HEADER_FONT_COLOR%>':headerFontCpf.getValue(),
                    '<%=ApplicationConstants.FAMILY_HEADER_FONT%>':famHeaderFontSbs.getValue(),
                    '<%=ApplicationConstants.WEIGHT_HEADER_FONT%>':wghtHeaderFontCb.getValue(),
                    '<%=ApplicationConstants.SIZE_HEADER_FONT%>':szHeaderFontSpn.getValue(),
                    '<%=ApplicationConstants.FAMILY_FONT%>':famFontSbs.getValue(),
                    '<%=ApplicationConstants.WEIGHT_FONT%>':wghtFontCb.getValue(),
                    '<%=ApplicationConstants.SIZE_FONT%>':szFontSpn.getValue(),
                    '<%=ApplicationConstants.NEW_BORDER_COLOR%>':borderCpf.getValue(),
                    '<%=ApplicationConstants.NEW_TRANSP%>':transpSldr.getValue(),
                    '<%=ApplicationConstants.TOOLSET_NAME%>':toolsetChooser.getValue(),
                    'version':verCb.getValue(),
                    '<%=ApplicationConstants.NEW_FONT_COLOR%>':fontCpf.getValue(),
                    '<%=ApplicationConstants.NEW_BG_COLOR%>':bgCpf.getValue()
                };
        };
        var changeScheme = function(){
             if (formPanel.getForm().isValid()){
                var params = getParams();
                ccs(params,true,onScsThProc,true);
            }else{
                Ext.Msg.alert ('Warning','Form is not valid');
            }
        };

        var resetScheme = function(){
            formPanel.getForm().reset();
            var thId = thTemplateCb.getValue();
            if (0==thId){
                baseCpf.setValue('#DFE8F6');
                headerCpf.setValue('#CDDEF3');
                headerFontCpf.setValue('#15428B');
                borderCpf.setValue('#99BBE8');
                Ext.util.CSS.swapStyleSheet('theme', '<c:url value="/js/ext/resources/css/xtheme-blue.css?rnd="/>'+ Math.random());
            } else if (1==thId){
                baseCpf.setValue('#F1F1F1');
                headerCpf.setValue('#D7D7D7');
                headerFontCpf.setValue('#222222');
                borderCpf.setValue('#D0D0D0');
                Ext.util.CSS.swapStyleSheet('theme', '<c:url value="/js/ext/resources/css/xtheme-gray.css?rnd="/>'+ Math.random());
            }
            transpSldr.setValue(255);
            toolsetChooser.setValue('default');
            verCb.setValue('<%=ApplicationConstants.DEFAULT_EXTJS_VERSION%>');
            bgCpf.setValue('#FFFFFF');
            fontCpf.setValue('#000000');
            famHeaderFontSbs.setValue('tahoma,arial,verdana,sans-serif');
            wghtHeaderFontCb.setValue('bold');
            szHeaderFontSpn.setValue(11);
            famFontSbs.setValue('arial,tahoma,helvetica,sans-serif');
            wghtFontCb.setValue('normal');
            szFontSpn.setValue(11);

            Ext.util.Cookies.set('theme','');
            downloadBtn.disable();
            var params=getParams();
            saveHist({params:params},true);
        };

        var downloadScheme = function(th){
            if (formPanel.getForm().isValid()){
                logoutAvailable = true;
                var params = {'<%=ApplicationConstants.TEMPLATE_ID%>': thTemplateCb.getValue(),
                            '<%=ApplicationConstants.NEW_SCHEMA_NAME%>': schemaNameFld.getValue(),
                            'version': verCb.getValue()};
                myView.postRequest('<c:url value="/downloadSchema.htm?rnd="/>'+ Math.random()
                ,params);
            }else{
                Ext.Msg.show({
                    title:'<spring:message code="message.level.warning"/>',
                    icon: Ext.MessageBox.WARNING,
                    buttons: Ext.Msg.OK,
                    fn:setFocusToNameFld,
                    msg:'<spring:message code="schema.generator.form.message.enter.schema.name"/>'
                });
            }
        };
        var onBlurCP = function(th){
            if(this.isDirty()){
                downloadBtn.disable();
                this.originalValue=String(this.getValue());
            }
        };

        var Cb = Ext.extend(Ext.form.ComboBox,
        {constructor:function(cnfg){
                        cnfg = Ext.apply({
                            listeners:{change:onChange},
            editable:false,
            allowBlank:false,
                            typeAhead: true,
                            mode: 'local',
                            triggerAction: 'all',
                            width:70,
                            labelStyle:'font-weight:bold;'
                        },cnfg);
                        Cb.superclass.constructor.call(this, cnfg);
                    }
        });

        var thTemplateCb = new Cb({
            store: new Ext.data.ArrayStore({
                id: 0,
                fields: [
                    'thId',
                    'thName'
                ],
                data: [[0, 'Blue'], [1, 'Gray']]
            }),
            valueField: 'thId',
            displayField: 'thName',
            title:'Template',
            value:0
        });

        var Cpf = Ext.extend(Ext.ux.form.ColorPickerField,
        {
            constructor:function(cnf){
                cnf = Ext.apply({
            width:300,
            listeners:{blur:onBlurCP},
                    allowBlank:false
                },cnf);
                Cpf.superclass.constructor.call(this, cnf);
            }
        });
        var baseCpf = new Cpf({
            fieldLabel: 'Base color',
            name: 'color',
            value: '#DFE8F6'
        });
        var headerCpf = new Cpf({
            fieldLabel: 'Header color',
            name: 'colorHeader',
            value: '#CDDEF3'
        });
        var bgCpf = new Cpf({
            fieldLabel: 'Background color',
            name: 'colorBg',
            value: '#FFFFFF'
        });
        var fontCpf = new Cpf({
            fieldLabel: 'Color',
            name: 'colorFont',
            value: '#000000'
        });

        var headerFontCpf = new Cpf({
            fieldLabel: 'Color',
            name: 'colorHeaderFont',
            value: '#15428B'
        });
        
        var famFontData = [['tahoma','tahoma'],['arial','arial'],['verdana','verdana'],['sans-serif','sans-serif'],['helvetica','helvetica']];
        var famHeaderFontSbs = new Ext.ux.form.SuperBoxSelect({
            editable:false,
            mode:'local',
            store:famFontData,
            fieldLabel: 'Family',
            name: 'familyHeaderFont',
            value: 'tahoma,arial,verdana,sans-serif'
        });

        var famFontSbs = new Ext.ux.form.SuperBoxSelect({
            editable:false,
            mode:'local',
            store:famFontData,
            fieldLabel: 'Family',
            name: 'familyFont',
            value: 'arial,tahoma,helvetica,sans-serif'
        });

        var wghtFlds = [
            'weightId',
            'weightName'
        ];
        var wghtData = [['normal', 'normal'], ['bold', 'bold']];
        var wghtHeaderFontCb = new Cb({
            editable:false,
            allowBlank:false,
            store: new Ext.data.ArrayStore({
                id: 0,
                fields: wghtFlds,
                data: wghtData
            }),
            valueField: 'weightId',
            displayField: 'weightName',
            fieldLabel:'Weight',
            value:'bold'
        });

        var wghtFontCb = new Cb({
            store: new Ext.data.ArrayStore({
                id: 0,
                fields: wghtFlds,
                data: wghtData
            }),
            valueField: 'weightId',
            displayField: 'weightName',
            fieldLabel:'Weight',
            value:'normal'
        });

        var szHeaderFontSpn = new Ext.ux.form.SpinnerField( {
            inputType:'text',
            allowBlank:false,
            allowDecimals:false,
            allowNegative:false,
            fieldLabel:'Size',
            value:11,
            labelStyle:'font-weight:bold;',
            name:'sizeHeaderFont',
            onBlur:onBlurCP,
            listeners:{'spin':onBlurCP},
            width:70,
            maxValue:70,
            minValue:5
        });

        var szFontSpn = new Ext.ux.form.SpinnerField( {
            inputType:'text',
            allowBlank:false,
            allowDecimals:false,
            allowNegative:false,
            fieldLabel:'Size',
            value:11,
            labelStyle:'font-weight:bold;',
            name:'sizeFont',
            onBlur:onBlurCP,
            listeners:{'spin':onBlurCP},
            width:70,
            maxValue:70,
            minValue:5
        });

        var headerFontFS = new Ext.form.FieldSet({
            title:'Header Font',
            layout:'column',
            labelWidth:45,
            defaults:{
                    border:false
                    , height:'auto'
                    , xtype:'fieldset'
                },
            items:[
                {   columnWidth: 0.75
                    , defaults:{
                            width:330,
                            listeners:{blur:onBlurCP},
                            allowBlank:false,
                            labelStyle:'font-weight:bold;'
                },
                        items:[headerFontCpf,famHeaderFontSbs]
                },
                {
                    columnWidth: 0.25
                    ,items:[wghtHeaderFontCb, szHeaderFontSpn]
                }]
        });

        var fontFS = new Ext.form.FieldSet({
            title:'Font',
            layout:'column',
            labelWidth:45,
            defaults:{
                    border:false
                    , height:'auto'
                    , xtype:'fieldset'
                },
            items:[
                {   columnWidth: 0.75
                    , defaults:{
                            width:330,
                            listeners:{blur:onBlurCP},
                            allowBlank:false,
                            labelStyle:'font-weight:bold;'
                        }
                    , items:[fontCpf,famFontSbs]
                },
                {
                    columnWidth: 0.25
                    ,items:[wghtFontCb, szFontSpn]
                }]
        });


        var borderCpf = new Cpf({
            fieldLabel: 'Border color',
            name: 'colorBorder',
            value: '#99BBE8'
        });

        var schemaNameFld = new Ext.form.TextField({
            width:300,
            allowBlank:false,
            fieldLabel: 'Theme name',
            name: 'name',
            value: 'yourtheme'
        });
        var downloadBtn = new Ext.Button({
            text:'Download Theme',
            tooltip:'Download Theme',
            iconCls:'ext-ux-downloadbtn',
            disabled:true,
            handler:downloadScheme
            });
        var prevScheme = function(){
            hist.back();
            refreshHistBtns();
            var params = Ext.util.JSON.decode(hist.getToken());
            setForm(params);
            ccs(params,false,onScsThProc,true);
            };
        var nextScheme = function(){
            hist.forward();
            refreshHistBtns();
            var params = Ext.util.JSON.decode(hist.getToken());
            setForm(params);
            ccs(params,false,onScsThProc,true);
            };
        var firstScheme = function(){
            hist.first();
            refreshHistBtns();
            var params = Ext.util.JSON.decode(hist.getToken());
            setForm(params);
            ccs(params,false,onScsThProc,true);
        };
        var lastScheme = function(){
            hist.last();
            refreshHistBtns();
            var params = Ext.util.JSON.decode(hist.getToken());
            setForm(params);
            ccs(params,false,onScsThProc,true);
        };
        var resetBtn = new Ext.Button({
            text:'Reset',
            tooltip:'Reset form',
            disabled:false,
            iconCls:"ext-ux-button-reset",
            handler:resetScheme
        });
        var prevSchemeBtn = new Ext.Button({
            tooltip:'Prev theme',
            iconCls:'ext-ux-prevbtn',
            disabled:hist.isFirst(),
            handler:prevScheme
        });
        var nextSchemeBtn = new Ext.Button({
            tooltip:'Next theme',
            iconCls:'ext-ux-nextbtn',
            disabled:hist.isLast(),
            handler:nextScheme
        });
        var firstSchemeBtn = new Ext.Button({
            tooltip:'First theme',
            iconCls:'ext-ux-firstbtn',
            disabled:hist.isFirst(),
            handler:firstScheme
        });
        var lastSchemeBtn = new Ext.Button({
            tooltip:'Last theme',
            iconCls:'ext-ux-lastbtn',
            disabled:hist.isLast(),
            handler:lastScheme
        });
        var buttons = [
            {
                text:'Apply',
                tooltip:'Apply changes',
                iconCls:'ext-ux-applybtn',
                handler:changeScheme
            },
            {xtype:'tbseparator'},
            firstSchemeBtn,
            prevSchemeBtn,
            nextSchemeBtn,
            lastSchemeBtn,
            {xtype:'tbspacer', width:10},
            resetBtn,
            {xtype:'tbseparator'},
            thTemplateCb,
            {xtype:'tbseparator'},
            downloadBtn,
            {xtype:'tbfill'},
            {
                text:'<spring:message code="button.close"/>'
                ,tooltip:'<spring:message code="button.close"/>'
                ,iconCls:"ext-ux-uploaddialog-resetbtn"
                , handler:function(){
            listWin.close();
        }
        }];

        var topToolBar = new Ext.Toolbar({items:buttons});
        var transpSldr = new Ext.Slider({
                                name:'transpSlider',
                                fieldLabel: 'Window transparency',
                                width:300,
                                value: 255,
                                increment: 5,
                                minValue: 0,
                                maxValue: 255,
                                listeners:{changecomplete:onChange},
                                plugins: new Ext.slider.Tip()
                            });

        var toolsetChooser = new Ext.ux.form.ImagePickerField({
            defaultUrl:'images/toolsets/',
            defaultValue:'default',
            fieldLabel:'Toolset',
            url:'getToolsets.htm',
            labelStyle:'font-weight:bold;',
            value:'default',
            width:102,
            height:85,
            viewWidth:395,
            viewHeight:320,
            style:{marginBottom:'3px'},
            listeners:{change:onChange}
            });
            
        var verCb = new Cb({
            store: new Ext.data.ArrayStore({
                id: 0,
                fields: [
                    'verId',
                    'verName'
                ],
                data: [['3.3', '3.3'], ['3.2', '3.2'], ['3.1', '3.1'], ['3.0', '3.0'], ['2.3', '2.3']]
            }),
            valueField: 'verId',
            displayField: 'verName',
            fieldLabel: 'ExtJS version',
            value:'<%=ApplicationConstants.DEFAULT_EXTJS_VERSION%>',
            labelWidth:50
        });

        var toolsetAndVersionFS = new Ext.form.FieldSet({
            layout:'column',
            border:false,
            labelWidth:170,
            defaults:{
                xtype:'fieldset',
                style:{marginBottom:'0px',marginTop:'0px'},
                border:false
            },
            items:[
                {   items:[toolsetChooser],
                    columnWidth: 0.6
                },
                {
                    items:[verCb],
                    labelWidth:120,
                    columnWidth: 0.4
                }]
        });
        
        var formPanel = new Ext.form.FormPanel({
            frame:true
            ,xtype:'form'
            ,monitorValid : true
            , margin:'10px 10px 5px 10px'
            , region:'center'
            , buttonAlign:'center'
            , border:false
            , bodyBorder:false
            , defaults:{
                style:{marginBottom:'7px'},
                labelStyle:'font-weight:bold;margin-left:10px;'
                }
            , labelWidth:180
            , items:[
                schemaNameFld,
                baseCpf,
                headerCpf,
                bgCpf,
                borderCpf,
                headerFontFS,
                fontFS,
                transpSldr,
                toolsetAndVersionFS
             ]});

        listWin = new Ext.ux.MyApp.CesiumBaseWindow({
            id:idWin,
            height: 590,
            width: 590,
            resizable: false,
            maximizable: false,
            tbar: topToolBar,
            title:'<spring:message code="app.title"/>',
            manager:windows,
            panel:formPanel,
            setForm:setForm,
            downloadButton:downloadBtn,
            resetBtn:resetBtn,
            items: [formPanel]
        });

    }
    return listWin;
}
})
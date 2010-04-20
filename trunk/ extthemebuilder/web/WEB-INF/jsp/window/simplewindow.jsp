<%@ page contentType="text/javascript;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/jsp/include/include_js.jsp" %>

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description: This is a Theme Builder settings form window
 * @license: LGPL_v3
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

        //oldWin.familyHeaderFont.setValue('tahoma,arial,verdana,sans-serif');
        //setup current schema fields values
        var currToken = histStack.getToken();
        if (currToken!=null){
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
                oldWin.panel.body.stopFx();
                oldWin.panel.body.highlight();
            },
            notifyDrop  : function(ddSource, e, data){

                // Reference the record (single selection) for readability
                var selectedRecord = ddSource.dragData.selections[0];
                //alert(selectedRecord.data['name']);
                oldWin.panel.getForm().loadRecord(selectedRecord);
                oldWin.downloadButton.disable();

                return(true);
            }
        });

    },

createWindow : function(idWin){
    var listWin;

    if ((!listWin)||(listWin==undefined)){
        var waitMsg;
        var setFocusToNameField = function(){
            schemaNameField.focus(true, true);
        };
        var saveHist=function(options, sh){
            if (sh){
                var paramsJsonToken = Ext.util.JSON.encode(options.params);
                histStack.addToken(paramsJsonToken);
            }
            refreshHistBtns();
        };
        var refreshHistBtns=function(){
            if (histStack.isLast()){
                nextSchemeBtn.disable();
            }else{
                nextSchemeBtn.enable();
            }
            if (histStack.isFirst()){
                prevSchemeBtn.disable();
            }else{
                prevSchemeBtn.enable();
            }
        };

        var onChange = function(th, newVal){
            downloadButton.disable();
            this.originalValue=String(newVal);
        };
        
        var onSuccessThProcessing = function(resp, option){
                var blue='blue';
                var gray='gray';
                var isGray = option.params.templateId&&(option.params.templateId==1);
                var versionPostFix = ((!option.params.version)||option.params.version.indexOf(".")<0)
                                    ?'32'
                                    :(option.params.version=='3.0'?'':option.params.version.replace('.',''));
                var resDirSuffix = (isGray?'_'+gray:'')+versionPostFix;
                var thName = isGray?gray:blue;
                Ext.util.CSS.swapStyleSheet('theme'
                        , (Ext.isIE&&!Ext.isIE7&&!Ext.isIE8
                        ?'<c:url value="/getResource.htm"/>?resourcePath='+'/WEB-INF/resources'+resDirSuffix+'/css/xtheme-'+thName+'_ie6.css'+'&rnd='
                        :'<c:url value="/getResource.htm"/>?resourcePath='+'/WEB-INF/resources'+resDirSuffix+'/css/xtheme-'+thName+'.css'+'&rnd=')
                        + Math.random());

                saveHist(option, option.saveHist);
                downloadButton.enable();
                var thToken = Ext.util.JSON.encode(option.params);
                Ext.util.Cookies.set('theme',thToken);
        };

        var setForm= function(params){
                schemaNameField.setValue(params.newSchemaName); schemaNameField.originalValue=String(schemaNameField.getValue());
                colorPickerField.setValue(params.newColor); colorPickerField.originalValue=String(colorPickerField.getValue());
                colorPickerFieldFont.setValue(params.newFontColor); colorPickerFieldFont.originalValue=String(colorPickerFieldFont.getValue());
                colorPickerFieldHeaderFont.setValue(params.newHeaderFontColor); colorPickerFieldHeaderFont.originalValue=String(colorPickerFieldHeaderFont.getValue());
                colorPickerFieldBorder.setValue(params.newBorderColor); colorPickerFieldBorder.originalValue=String(colorPickerFieldBorder.getValue());
                colorPickerFieldBg.setValue(params.newBgColor); colorPickerFieldBg.originalValue=String(colorPickerFieldBg.getValue());
                transpSlider.setValue(params.newTransp||0==params.newTransp?params.newTransp:'255'); transpSlider.originalValue=String(transpSlider.getValue());
                toolsetChooser.setValue(params.toolsetName?params.toolsetName:'default'); toolsetChooser.originalValue=String(toolsetChooser.getValue());
                versionRadioGroup.setValue(params.version&&(params.version.indexOf('.')==1)?params.version:'3.2'); versionRadioGroup.originalValue=String(versionRadioGroup.getValue().getGroupValue());
                comboThemeTemplate.setValue(params.templateId?params.templateId:0); comboThemeTemplate.originalValue=String(comboThemeTemplate.getValue());
                familyHeaderFont.setValue(params.familyHeaderFont?params.familyHeaderFont:'tahoma,arial,verdana,sans-serif'); familyHeaderFont.originalValue=String(familyHeaderFont.getValue());
                weightHeaderFont.setValue(params.weightHeaderFont?params.weightHeaderFont:'bold'); weightHeaderFont.originalValue=String(weightHeaderFont.getValue());
                sizeHeaderFont.setValue(params.sizeHeaderFont?params.sizeHeaderFont:'11'); sizeHeaderFont.originalValue=String(sizeHeaderFont.getValue());
                familyFont.setValue(params.familyFont?params.familyFont:'arial,tahoma,helvetica,sans-serif'); familyFont.originalValue=String(familyFont.getValue());
                weightFont.setValue(params.weightFont?params.weightFont:'normal'); weightFont.originalValue=String(weightFont.getValue());
                sizeFont.setValue(params.sizeFont?params.sizeFont:'11'); sizeFont.originalValue=String(sizeFont.getValue());
                colorPickerFieldHeader.setValue(params.newHeaderColor?params.newHeaderColor:(params.templateId&&0!=params.templateId?'#D7D7D7':'#CDDEF3')); colorPickerFieldHeader.originalValue=String(colorPickerFieldHeader.getValue());
        };
        var getParams=function(){
            var thId = comboThemeTemplate.getValue();
            return {
                    id:'CURRENT_SCHEMA_ATTRIBUTE',
                    templateId:comboThemeTemplate.getValue(),
                    newSchemaName:schemaNameField.getValue(),
                    //oldColor:'#C8DAF2', newColor:colorPickerField.getValue(),
                    oldColor:(0==thId)?'#DFE8F6':'#F1F1F1', newColor:colorPickerField.getValue(),
                    oldHeaderColor:(0==thId)?'#CDDEF3':'#D7D7D7', newHeaderColor:colorPickerFieldHeader.getValue(),
                    oldHeaderFontColor:(0==thId)?'#15428B':'#222222', newHeaderFontColor:colorPickerFieldHeaderFont.getValue(),
                    familyHeaderFont:familyHeaderFont.getValue(),
                    weightHeaderFont:weightHeaderFont.getValue(),
                    sizeHeaderFont:sizeHeaderFont.getValue(),
                    familyFont:familyFont.getValue(),
                    weightFont:weightFont.getValue(),
                    sizeFont:sizeFont.getValue(),
                    oldBorderColor:(0==thId)?'#99BBE8':'#D0D0D0', newBorderColor:colorPickerFieldBorder.getValue(),
                    oldTransp:'255', newTransp:transpSlider.getValue(),
                    toolsetName:toolsetChooser.getValue(),
                    version:versionRadioGroup.getValue().getGroupValue(),
                    oldFontColor:'#000000', newFontColor:colorPickerFieldFont.getValue(),
                    oldBgColor:'#FFFFFF', newBgColor:colorPickerFieldBg.getValue()
                };
        };
        var changeColorScheme = function(){
             if (formPanel.getForm().isValid()){
                var params = getParams();
                ccs(params,true,onSuccessThProcessing,true);
            }else{
                Ext.MessageBox.alert ('Warning','Form is not valid');
            }
        };

        var resetColorScheme = function(){
            formPanel.getForm().reset();
            var thId = comboThemeTemplate.getValue();
            if (0==thId){
                //colorPickerField.setValue('#C8DAF2');
                colorPickerField.setValue('#DFE8F6');
                colorPickerFieldHeader.setValue('#CDDEF3');
                colorPickerFieldHeaderFont.setValue('#15428B');
                colorPickerFieldBorder.setValue('#99BBE8');
                Ext.util.CSS.swapStyleSheet('theme', '<c:url value="/js/ext/resources/css/xtheme-blue.css?rnd="/>'+ Math.random());
            } else if (1==thId){
                colorPickerField.setValue('#F1F1F1');
                colorPickerFieldHeader.setValue('#D7D7D7');
                colorPickerFieldHeaderFont.setValue('#222222');
                colorPickerFieldBorder.setValue('#D0D0D0');
                Ext.util.CSS.swapStyleSheet('theme', '<c:url value="/js/ext/resources/css/xtheme-gray.css?rnd="/>'+ Math.random());
            }
            transpSlider.setValue(255);
            toolsetChooser.setValue('default');
            versionRadioGroup.setValue('3.2');
            colorPickerFieldBg.setValue('#FFFFFF');
            colorPickerFieldFont.setValue('#000000');
            familyHeaderFont.setValue('tahoma,arial,verdana,sans-serif');
            weightHeaderFont.setValue('bold');
            sizeHeaderFont.setValue(11);
            familyFont.setValue('arial,tahoma,helvetica,sans-serif');
            weightFont.setValue('normal');
            sizeFont.setValue(11);

            Ext.util.Cookies.set('theme','');
            downloadButton.disable();
            var params=getParams();
            saveHist({params:params},true);
        };

        var downloadColorScheme = function(th){
            if (formPanel.getForm().isValid()){
                logoutAvailable = true;
                var params = {id:'CURRENT_SCHEMA_ATTRIBUTE',
                            templateId: comboThemeTemplate.getValue(),
                            newSchemaName: schemaNameField.getValue(),
                            version: versionRadioGroup.getValue().getGroupValue()};
                myView.postRequest('<c:url value="/downloadSchema.htm?rnd="/>'+ Math.random()
                ,params);
            }else{
                Ext.Msg.show({
                    title:'<spring:message code="message.level.warning"/>',
                    icon: Ext.MessageBox.WARNING,
                    buttons: Ext.Msg.OK,
                    fn:setFocusToNameField,
                    msg:'<spring:message code="schema.generator.form.message.enter.schema.name"/>'
                });
            }
        };
        var onBlurCP = function(th){
            if(this.isDirty()){
                downloadButton.disable();
                this.originalValue=String(this.getValue());
            }
        };

        var comboThemeTemplate = new Ext.form.ComboBox({
            editable:false,
            allowBlank:false,
            store: new Ext.data.ArrayStore({
                id: 0,
                fields: [
                    'templateId',
                    'templateName'
                ],
                data: [[0, 'Blue'], [1, 'Gray']]
            }),
            valueField: 'templateId',
            displayField: 'templateName',
            title:'Template',
            value:0,
            typeAhead: true,
            mode: 'local',
            triggerAction: 'all',
            listeners:{change:onChange},
            width:70
        });

        var colorPickerField = new Ext.ux.form.ColorPickerField({
            xtype: 'colorpickerfield',
            fieldLabel: 'Base color',
            width:300,
            allowBlank:false,
            name: 'color',
            //value: '#C8DAF2',
            value: '#DFE8F6',
            ref: 'color',
            listeners:{blur:onBlurCP},
            id: 'fieldColor'
        });
        var colorPickerFieldHeader = new Ext.ux.form.ColorPickerField({
            xtype: 'colorpickerfield',
            fieldLabel: 'Header color',
            width:300,
            //labelStyle:'font-weight:bold;',
            allowBlank:false,
            name: 'colorHeader',
            value: '#CDDEF3',
            ref: 'colorHeader',
            listeners:{blur:onBlurCP},
            id: 'fieldColorHeader'
        });
        var colorPickerFieldBg = new Ext.ux.form.ColorPickerField({
            xtype: 'colorpickerfield',
            fieldLabel: 'Background color',
            width:300,
            allowBlank:false,
            name: 'colorBg',
            value: '#FFFFFF',
            ref: 'colorBg',
            listeners:{blur:onBlurCP},
            id: 'fieldColorBg'
        });
        var colorPickerFieldFont = new Ext.ux.form.ColorPickerField({
            xtype: 'colorpickerfield',
            fieldLabel: 'Color',
            width:320,
            labelStyle:'font-weight:bold;',
            allowBlank:false,
            name: 'colorFont',
            value: '#000000',
            ref: 'colorFont',
            listeners:{blur:onBlurCP},
            id: 'fieldColorFont'
        });

        var colorPickerFieldHeaderFont = new Ext.ux.form.ColorPickerField({
            xtype: 'colorpickerfield',
            fieldLabel: 'Color',
            width:320,
            labelStyle:'font-weight:bold;',
            allowBlank:false,
            name: 'colorHeaderFont',
            value: '#15428B',
            ref: 'colorHeaderFont',
            listeners:{blur:onBlurCP},
            id: 'fieldColorHeaderFont'
        });
        
        var familyHeaderFont = new Ext.ux.form.SuperBoxSelect({
            xtype:'superboxselect',
            fieldLabel: 'Family',
            width:320,
            labelStyle:'font-weight:bold;',
            allowBlank:false,
            editable:false,
            name: 'familyHeaderFont',
            value: 'tahoma,arial,verdana,sans-serif',
            mode:'local',
            store:[['tahoma','tahoma'],['arial','arial'],['verdana','verdana'],['sans-serif','sans-serif'],['helvetica','helvetica']],
            ref: 'familyHeaderFont',
            listeners:{blur:onBlurCP},
            id: 'familyHeaderFont'
        });

        var familyFont = new Ext.ux.form.SuperBoxSelect({
            xtype:'superboxselect',
            fieldLabel: 'Family',
            width:320,
            labelStyle:'font-weight:bold;',
            allowBlank:false,
            editable:false,
            name: 'familyFont',
            value: 'arial,tahoma,helvetica,sans-serif',
            mode:'local',
            store:[['tahoma','tahoma'],['arial','arial'],['verdana','verdana'],['sans-serif','sans-serif'],['helvetica','helvetica']],
            ref: 'familyFont',
            listeners:{blur:onBlurCP},
            id: 'familyFont'
        });

        var weightHeaderFont = new Ext.form.ComboBox({
            editable:false,
            allowBlank:false,
            store: new Ext.data.ArrayStore({
                id: 0,
                fields: [
                    'weightId',
                    'weightName'
                ],
                data: [['normal', 'normal'], ['bold', 'bold']]
            }),
            valueField: 'weightId',
            displayField: 'weightName',
            fieldLabel:'Weight',
            labelStyle:'font-weight:bold;',
            value:'bold',
            typeAhead: true,
            mode: 'local',
            triggerAction: 'all',
            listeners:{change:onChange},
            width:70
        });

        var weightFont = new Ext.form.ComboBox({
            editable:false,
            allowBlank:false,
            store: new Ext.data.ArrayStore({
                id: 0,
                fields: [
                    'weightId',
                    'weightName'
                ],
                data: [['normal', 'normal'], ['bold', 'bold']]
            }),
            valueField: 'weightId',
            displayField: 'weightName',
            fieldLabel:'Weight',
            labelStyle:'font-weight:bold;',
            value:'normal',
            typeAhead: true,
            mode: 'local',
            triggerAction: 'all',
            listeners:{change:onChange},
            width:70
        });

        var sizeHeaderFont = new Ext.ux.form.SpinnerField( {
            xtype:'spinnerfield',
            inputType:'text',
            allowBlank:false,
            allowDecimals:false,
            allowNegative:false,
            fieldLabel:'Size',
            value:11,
            labelStyle:'font-weight:bold;',
            name:'sizeHeaderFont',
            id:'sizeHeaderFont',
            ref:'sizeHeaderFont',
            onBlur:onBlurCP,
            listeners:{'spin':onBlurCP},
            width:70,
            maxValue:20,
            minValue:5
        });

        var sizeFont = new Ext.ux.form.SpinnerField( {
            xtype:'spinnerfield',
            inputType:'text',
            allowBlank:false,
            allowDecimals:false,
            allowNegative:false,
            fieldLabel:'Size',
            value:11,
            labelStyle:'font-weight:bold;',
            name:'sizeFont',
            id:'sizeFont',
            ref:'sizeFont',
            onBlur:onBlurCP,
            listeners:{'spin':onBlurCP},
            width:70,
            maxValue:20,
            minValue:5
        });

        var headerFontFS = new Ext.form.FieldSet({
            title:'Header Font',
            layout:'column',
            labelWidth:45,
            items:[
                {   xtype:'fieldset'
                    , border:false
                    , columnWidth: 0.75
                    , items:[colorPickerFieldHeaderFont,familyHeaderFont]
                },
                {xtype:'fieldset'
                    , border:false
                    , columnWidth: 0.25
                    ,items:[weightHeaderFont, sizeHeaderFont]
                }]
        });

        var fontFS = new Ext.form.FieldSet({
            title:'Font',
            layout:'column',
            labelWidth:45,
            items:[
                {   xtype:'fieldset'
                    , border:false
                    , columnWidth: 0.75
                    , items:[colorPickerFieldFont,familyFont]
                },
                {xtype:'fieldset'
                    , border:false
                    , columnWidth: 0.25
                    ,items:[weightFont, sizeFont]
                }]
        });


        var colorPickerFieldBorder = new Ext.ux.form.ColorPickerField({
            xtype: 'colorpickerfield',
            fieldLabel: 'Border color',
            width:300,
            //labelStyle:'font-weight:bold;',
            allowBlank:false,
            name: 'colorBorder',
            value: '#99BBE8',
            ref: 'colorBorder',
            listeners:{blur:onBlurCP},
            id: 'fieldColorBorder'
        });

        var schemaNameField = new Ext.form.TextField({
            xtype: 'textfield',
            width:300,
            allowBlank:false,
            fieldLabel: 'Theme name',
            name: 'name',
            value: 'default',
            ref: 'name',
            id: 'schemaNameId'
        });
        var downloadButton = new Ext.Button({
            text:'Download Theme',
                tooltip:'Download Theme',
                iconCls:'ext-ux-downloadbtn',
                disabled:true,
                handler:downloadColorScheme
            });
        var prevColorScheme = function(){
            histStack.back();
            refreshHistBtns();
            //alert(histStack.getToken());
            var params = Ext.util.JSON.decode(histStack.getToken());
            setForm(params);
            ccs(params,false,onSuccessThProcessing,true);
            };
        var nextColorScheme = function(){
            histStack.forward();
            refreshHistBtns();
            //alert(histStack.getToken());
            var params = Ext.util.JSON.decode(histStack.getToken());
            setForm(params);
            ccs(params,false,onSuccessThProcessing,true);
            };
        var resetBtn = new Ext.Button({
            text:'Reset',
            tooltip:'Reset form',
            disabled:false,
            iconCls:"ext-ux-button-reset",
            handler:resetColorScheme
        });
        var prevSchemeBtn = new Ext.Button({
            tooltip:'Prev theme',
            iconCls:'ext-ux-prevbtn',
            //disabledClass:'ext-ux-prevbtn-disabled',
            disabled:histStack.isFirst(),
            handler:prevColorScheme
        });
        var nextSchemeBtn = new Ext.Button({
            tooltip:'Next theme',
            iconCls:'ext-ux-nextbtn',
            //disabledClass:'ext-ux-nextbtn-disabled',
            disabled:histStack.isLast(),
            handler:nextColorScheme
        });
        var buttons = [
            {
                text:'Apply',
                tooltip:'Apply changes',
                iconCls:'ext-ux-applybtn',
                handler:changeColorScheme
            },
            {xtype:'tbseparator'},
            nextSchemeBtn,
            prevSchemeBtn,
            {xtype:'tbspacer'},
            resetBtn,
            {xtype:'tbseparator'},
            comboThemeTemplate,
            {xtype:'tbseparator'},
            downloadButton,
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
        //var formPanel = new Ext.form.BasicForm({
        var transpSlider = new Ext.Slider({
                                id:'transpSlider',
                                name:'transpSlider',
                                ref:'transpSlider',
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
            xtype:'imagepickerfield',
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
            
        var versionRadioGroup = new Ext.form.RadioGroup({
            // Use the default, automatic layout to distribute the controls evenly
            // across a single row
            xtype: 'radiogroup',
            columns:1,
            fieldLabel: 'ExtJS version',
            labelStyle:'font-weight:bold;',
            labelWidth:100,
            listeners:{change:onChange},
            items: [
                new Ext.form.Radio({xtype:'radio', boxLabel: '3.2', name: 'rb-version', inputValue: '3.2', checked: true}),
                new Ext.form.Radio({xtype:'radio', boxLabel: '3.1', name: 'rb-version', inputValue: '3.1'}),
                new Ext.form.Radio({xtype:'radio', boxLabel: '3.0', name: 'rb-version', inputValue: '3.0'})
            ]
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
                    items:[versionRadioGroup],
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
            //, buttons:buttons
            , labelWidth:180
            , items:[
                        schemaNameField,
                        colorPickerField,
                        colorPickerFieldHeader,
                        colorPickerFieldBg,
                        colorPickerFieldBorder,
                        headerFontFS,
                        fontFS,
                        transpSlider,
                        //toolsetChooser
                        toolsetAndVersionFS
                         ]});

        listWin = new Ext.ux.MyApp.CesiumBaseWindow({
            id:idWin,
            height: 590,
            width: 560,
            resizable: false,
            maximizable: false,
            tbar: topToolBar,
            title:'<spring:message code="app.title"/>',
            manager:windows,
            panel:formPanel,
            setForm:setForm,
            downloadButton:downloadButton,
            resetBtn:resetBtn,
            items: [formPanel]
        });

    }
    return listWin;
}
})
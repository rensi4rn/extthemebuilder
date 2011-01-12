<%@ page contentType="text/javascript;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/jsp/include/include_js.jsp" %>

Ext.namespace('Ext.ux.MyApp');

Ext.ux.MyApp.tabsWindow=Ext.extend(Ext.ux.MyApp.Module, {
init : function(){
        var idWindow = 'list-ct-tabs';

        var oldWin = myView.getTaskButtonByIdWin(idWindow).win;
        if ((!oldWin)||oldWin==undefined){
            oldWin = this.createWindow(idWindow);
            myView.statusBar.addTaskButton(oldWin);
        }
        oldWin.show();

    },


createWindow : function(idWin){
    var listWin;
    var listStore;

    if ((!listWin)||(listWin==undefined)){

    var individual = [{
        bodyStyle: 'padding-right:5px;',
        items: {
            xtype: 'fieldset',
            title: 'Individual Checkboxes',
            autoHeight: true,
            defaultType: 'checkbox', // each item will be a checkbox
            items: [{
                xtype: 'textfield',
                name: 'txt-test1',
                fieldLabel: 'Alignment Test'
            }, {
                fieldLabel: 'Favorite Animals',
                boxLabel: 'Dog',
                name: 'fav-animal-dog'
            }, {
                fieldLabel: '',
                labelSeparator: '',
                boxLabel: 'Cat',
                name: 'fav-animal-cat'
            }, {
                checked: true,
                fieldLabel: '',
                labelSeparator: '',
                boxLabel: 'Monkey',
                name: 'fav-animal-monkey'
            }]
        }
    }, {
        bodyStyle: 'padding-left:5px;',
        items: {
            xtype: 'fieldset',
            title: 'Individual Radios',
            autoHeight: true,
            defaultType: 'radio', // each item will be a radio button
            items: [{
                xtype: 'textfield',
                name: 'txt-test2',
                fieldLabel: 'Alignment Test'
            }, {
                checked: true,
                fieldLabel: 'Favorite Color',
                boxLabel: 'Red',
                name: 'fav-color',
                inputValue: 'red'
            }, {
                fieldLabel: '',
                labelSeparator: '',
                boxLabel: 'Blue',
                name: 'fav-color',
                inputValue: 'blue'
            }, {
                fieldLabel: '',
                labelSeparator: '',
                boxLabel: 'Green',
                name: 'fav-color',
                inputValue: 'green'
            }]
        }
    }];

    /*====================================================================
     * CheckGroup example
     *====================================================================*/
    var checkGroup = {
        xtype: 'fieldset',
        title: 'Checkbox Groups',
        autoHeight: true,
        layout: 'form',
        collapsed: false,  
        collapsible: true,
        items: [{
            xtype: 'textfield',
            name: 'txt-test3',
            fieldLabel: 'Alignment Test',
            anchor: '95%'
        },{
            // Use the default, automatic layout to distribute the controls evenly
            // across a single row
            xtype: 'checkboxgroup',
            fieldLabel: 'Auto Layout',
            items: [
                {boxLabel: 'Item 1', name: 'cb-auto-1'},
                {boxLabel: 'Item 2', name: 'cb-auto-2', checked: true},
                {boxLabel: 'Item 3', name: 'cb-auto-3'},
                {boxLabel: 'Item 4', name: 'cb-auto-4'},
                {boxLabel: 'Item 5', name: 'cb-auto-5'}
            ]
        },{
            xtype: 'checkboxgroup',
            fieldLabel: 'Single Column',
            itemCls: 'x-check-group-alt',
            // Put all controls in a single column with width 100%
            columns: 1,
            items: [
                {boxLabel: 'Item 1', name: 'cb-col-1'},
                {boxLabel: 'Item 2', name: 'cb-col-2', checked: true},
                {boxLabel: 'Item 3', name: 'cb-col-3'}
            ]
        },{
            xtype: 'checkboxgroup',
            fieldLabel: 'Multi-Column (horizontal)',
            // Distribute controls across 3 even columns, filling each row
            // from left to right before starting the next row
            columns: 3,
            items: [
                {boxLabel: 'Item 1', name: 'cb-horiz-1'},
                {boxLabel: 'Item 2', name: 'cb-horiz-2', checked: true},
                {boxLabel: 'Item 3', name: 'cb-horiz-3'},
                {boxLabel: 'Item 4', name: 'cb-horiz-4'},
                {boxLabel: 'Item 5', name: 'cb-horiz-5'}
            ]
        },{
            xtype: 'checkboxgroup',
            fieldLabel: 'Multi-Column (vertical)',
            itemCls: 'x-check-group-alt',
            // Distribute controls across 3 even columns, filling each column
            // from top to bottom before starting the next column
            columns: 3,
            vertical: true,
            items: [
                {boxLabel: 'Item 1', name: 'cb-vert-1'},
                {boxLabel: 'Item 2', name: 'cb-vert-2', checked: true},
                {boxLabel: 'Item 3', name: 'cb-vert-3'},
                {boxLabel: 'Item 4', name: 'cb-vert-4'},
                {boxLabel: 'Item 5', name: 'cb-vert-5'}
            ]
        },{
            xtype: 'checkboxgroup',
            fieldLabel: 'Multi-Column<br />(custom widths)',
            // Specify exact column widths (could also include float values for %)
            columns: [100, 100],
            vertical: true,
            items: [
                {boxLabel: 'Item 1', name: 'cb-custwidth', inputValue: 1},
                {boxLabel: 'Item 2', name: 'cb-custwidth', inputValue: 2, checked: true},
                {boxLabel: 'Item 3', name: 'cb-custwidth', inputValue: 3},
                {boxLabel: 'Item 4', name: 'cb-custwidth', inputValue: 4},
                {boxLabel: 'Item 5', name: 'cb-custwidth', inputValue: 5}
            ]
        },{
            xtype: 'checkboxgroup',
            itemCls: 'x-check-group-alt',
            fieldLabel: 'Custom Layout<br />(w/ validation)',
            allowBlank: false,
            anchor: '95%',
            items: [{
                // You can pass sub-item arrays along with width/columnWidth configs
                // ColumnLayout-style for complete layout control.  In this example we
                // only want one item in the middle column, which would not be possible
                // using the columns config.  We also want to make sure that our headings
                // end up at the top of each column as expected.
                columnWidth: '.25',
                items: [
                    {xtype: 'label', text: 'Heading 1', cls:'x-form-check-group-label', anchor:'-15'},
                    {boxLabel: 'Item 1', name: 'cb-cust-1'},
                    {boxLabel: 'Item 2', name: 'cb-cust-2'}
                ]
            },{
                columnWidth: '.5',
                items: [
                    {xtype: 'label', text: 'Heading 2', cls:'x-form-check-group-label', anchor:'-15'},
                    {boxLabel: 'A long item just for fun', name: 'cb-cust-3'}
                ]
            },{
                columnWidth: '.25',
                items: [
                    {xtype: 'label', text: 'Heading 3', cls:'x-form-check-group-label', anchor:'-15'},
                    {boxLabel: 'Item 4', name: 'cb-cust-4'},
                    {boxLabel: 'Item 5', name: 'cb-cust-5'}
                ]
            }]
        }]
    };

    /*====================================================================
     * RadioGroup examples
     *====================================================================*/
    // NOTE: These radio examples use the exact same options as the checkbox ones
    // above, so the comments will not be repeated.  Please see comments above for
    // additional explanation on some config options.

    var radioGroup = {

        xtype: 'fieldset',
        title: 'Radio Groups',
        autoHeight: true,
        items: [{
            xtype: 'textfield',
            name: 'txt-test4',
            fieldLabel: 'Alignment Test',
            anchor: '95%'
        },{
            xtype: 'radiogroup',
            fieldLabel: 'Auto Layout',
            items: [
                {boxLabel: 'Item 1', name: 'rb-auto', inputValue: 1},
                {boxLabel: 'Item 2', name: 'rb-auto', inputValue: 2, checked: true},
                {boxLabel: 'Item 3', name: 'rb-auto', inputValue: 3},
                {boxLabel: 'Item 4', name: 'rb-auto', inputValue: 4},
                {boxLabel: 'Item 5', name: 'rb-auto', inputValue: 5}
            ]
        },{
            xtype: 'radiogroup',
            fieldLabel: 'Single Column',
            itemCls: 'x-check-group-alt',
            columns: 1,
            items: [
                {boxLabel: 'Item 1', name: 'rb-col', inputValue: 1},
                {boxLabel: 'Item 2', name: 'rb-col', inputValue: 2, checked: true},
                {boxLabel: 'Item 3', name: 'rb-col', inputValue: 3}
            ]
        },{
            xtype: 'radiogroup',
            fieldLabel: 'Multi-Column<br />(horiz. auto-width)',
            columns: 3,
            items: [
                {boxLabel: 'Item 1', name: 'rb-horiz', inputValue: 1},
                {boxLabel: 'Item 2', name: 'rb-horiz', inputValue: 2, checked: true},
                {boxLabel: 'Item 3', name: 'rb-horiz', inputValue: 3},
                {boxLabel: 'Item 4', name: 'rb-horiz', inputValue: 4},
                {boxLabel: 'Item 5', name: 'rb-horiz', inputValue: 5}
            ]
        },{
            xtype: 'radiogroup',
            fieldLabel: 'Multi-Column<br />(vert. auto-width)',
            itemCls: 'x-check-group-alt',
            columns: 3,
            vertical: true,
            items: [
                {boxLabel: 'Item 1', name: 'rb-vert', inputValue: 1},
                {boxLabel: 'Item 2', name: 'rb-vert', inputValue: 2, checked: true},
                {boxLabel: 'Item 3', name: 'rb-vert', inputValue: 3},
                {boxLabel: 'Item 4', name: 'rb-vert', inputValue: 4},
                {boxLabel: 'Item 5', name: 'rb-vert', inputValue: 5}
            ]
        },{
            xtype: 'radiogroup',
            fieldLabel: 'Multi-Column<br />(custom widths)',
            columns: [100, 100],
            vertical: true,
            items: [
                {boxLabel: 'Item 1', name: 'rb-custwidth', inputValue: 1},
                {boxLabel: 'Item 2', name: 'rb-custwidth', inputValue: 2, checked: true},
                {boxLabel: 'Item 3', name: 'rb-custwidth', inputValue: 3},
                {boxLabel: 'Item 4', name: 'rb-custwidth', inputValue: 4},
                {boxLabel: 'Item 5', name: 'rb-custwidth', inputValue: 5}
            ]
        },{
            xtype: 'radiogroup',
            itemCls: 'x-check-group-alt',
            fieldLabel: 'Custom Layout<br />(w/ validation)',
            allowBlank: false,
            anchor: '95%',
            items: [{
                columnWidth: '.25',
                items: [
                    {xtype: 'label', text: 'Heading 1', cls:'x-form-check-group-label', anchor:'-15'},
                    {boxLabel: 'Item 1', name: 'rb-cust', inputValue: 1},
                    {boxLabel: 'Item 2', name: 'rb-cust', inputValue: 2}
                ]
            },{
                columnWidth: '.5',
                items: [
                    {xtype: 'label', text: 'Heading 2', cls:'x-form-check-group-label', anchor:'-15'},
                    {boxLabel: 'A long item just for fun', name: 'rb-cust', inputValue: 3}
                ]
            },{
                columnWidth: '.25',
                items: [
                    {xtype: 'label', text: 'Heading 3', cls:'x-form-check-group-label', anchor:'-15'},
                    {boxLabel: 'Item 4', name: 'rb-cust', inputValue: 4},
                    {boxLabel: 'Item 5', name: 'rb-cust', inputValue: 5}
                ]
            }]
        }]
    };

    // combine all that into one huge form
    var fpCheckBox = new Ext.FormPanel({
        frame: true,
        header:false,
        layout:'fit',
        region:'center',
        labelWidth: 110,
        border:false,
        bodyStyle: 'padding:0 10px 0;',
        items: [
            checkGroup
        ],
        buttons: [{
            text: 'Save',
            handler: function(){
               if(fpCheckBox.getForm().isValid()){
                    Ext.Msg.alert('Submitted Values', 'The following will be sent to the server: <br />'+
                        fpCheckBox.getForm().getValues(true).replace(/&/g,', '));
                }
            }
        },{
            text: 'Reset',
            handler: function(){
                fpCheckBox.getForm().reset();
            }
        }]
    });

    var fpRadio = new Ext.FormPanel({
        frame: true,
        header:false,
        layout:'fit',
        region:'center',
        labelWidth: 110,
        border:false,
        bodyStyle: 'padding:0 10px 0;',
        items: [
            radioGroup
        ],
        buttons: [{
            text: 'Save',
            handler: function(){
               if(fpRadio.getForm().isValid()){
                    Ext.Msg.alert('Submitted Values', 'The following will be sent to the server: <br />'+
                        fpRadio.getForm().getValues(true).replace(/&/g,', '));
                }
            }
        },{
            text: 'Reset',
            handler: function(){
                fpRadio.getForm().reset();
            }
        }]
    });

    var fpInd = new Ext.FormPanel({
        frame: true,
        header:false,
        layout:'fit',
        region:'center',
        labelWidth: 110,
        border:false,
        bodyStyle: 'padding:0 10px 0;',
        items: [
            {
                layout: 'column',
                border: false,
                // defaults are applied to all child items unless otherwise specified by child item
                defaults: {
                    columnWidth: '.5',
                    border: false
                },
                items: individual
            }
        ],
        buttons: [{
            text: 'Save',
            handler: function(){
               if(fpInd.getForm().isValid()){
                    Ext.Msg.alert('Submitted Values', 'The following will be sent to the server: <br />'+
                        fpInd.getForm().getValues(true).replace(/&/g,', '));
                }
            }
        },{
            text: 'Reset',
            handler: function(){
                fpInd.getForm().reset();
            }
        }]
    });

        var tpBottomTab = new Ext.TabPanel({
            resizeTabs:true,
            minTabWidth: 115,
            tabWidth:170,
            tabPosition:'bottom',
            enableTabScroll:true,
            layoutOnTabChange: true,
            deferredRender: false,
            items:[ {
                title: 'Bottom Tab 0',
                iconCls: 'tabs',
                html: 'Tab Body 0 <br/><br/>'
                        + Ext.dummy.shortBogusMarkup,
                closable:true,
                layout:'fit',
                region:'center'
            }, {
                title: 'Bottom Tab 1',
                iconCls: 'tabs',
                html: 'Tab Body 1 <br/><br/>'
                        + Ext.dummy.shortBogusMarkup,
                closable:true,
                layout:'fit',
                region:'center'
            }],
            border:false,
            defaults: {autoScroll:true,layout:'border'}
        });

        var checkBoxTab = {
            title: 'Checkboxes',
            iconCls: 'tabs',
            closable:true,
            layout:'fit',
            region:'center',
            items:fpCheckBox
        };
        var radioTab = {
            title: 'Radio',
            iconCls: 'tabs',
            closable:true,
            layout:'fit',
            region:'center',
            items:fpRadio
        };
        var indTab = {
            title: 'Individual',
            iconCls: 'tabs',
            closable:true,
            layout:'fit',
            region:'center',
            items:fpInd
        };
        var bottomTab = {
            title: 'Bottom tabs',
            iconCls: 'tabs',
            closable:true,
            layout:'fit',
            region:'center',
            items:tpBottomTab
        };
        var tabs = new Ext.TabPanel({
            /*renderTo:'tabs',*/
            resizeTabs:true, // turn on tab resizing
            minTabWidth: 115,
            tabWidth:170,
            enableTabScroll:true,
            layoutOnTabChange: true,
            deferredRender: false,
            items:[bottomTab, checkBoxTab,radioTab,indTab],
            border:false,

/*                width:'auto',
            height:250,*/
            defaults: {autoScroll:true,layout:'border'}
        });

        // tab generation code
        var index = 4;

        var addTab = function(){
            tabs.add({
                title: 'New Tab ' + (++index),
                iconCls: 'tabs',
                html: 'Tab Body ' + (index) + '<br/><br/>'
                        + Ext.dummy.bogusMarkup,
                closable:true,
                layout:'fit',
                region:'center'
            }).show();
        };

        while(index < 7){
            addTab();
        }



        listWin = new Ext.ux.MyApp.CesiumBaseWindow({
            id:idWin,
            width:630,
            height:590,
            title:'Tabs example',
            manager:windows,
            items: [tabs]
        });

        tabs.activate(0);
        tpBottomTab.activate(0);
    }
    return listWin;
}
})
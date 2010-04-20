<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<html>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

<head><title><spring:message code="app.title"/></title>
<meta name="description"
      content="Simple online theme builder for ExtJS 3.x" />
<meta name="keywords"
      content="ExtJS, ExtJS themes, Theme, Download, ONLINE, ON-LINE,ON-LINE builder, online theme builder, ExtJS theme builder, on-line theme builder for ExtJS, ExtJS, ExtJS theme generator, Download ExtJS themes, extjs schemes, schema for extjs" />

<!--it is section for custom css-->
<link rel="stylesheet" type="text/css" href="<c:url value="/css/cesium.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/js/ext/multiselect/superboxselect.css"/>" />

<link rel="stylesheet" type="text/css" href="<c:url value="/js/ext/colorpicker/colorpicker.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/js/ext/portal/Portal.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/js/ext/image_chooser/ImageChooserField.css"/>" />

<!--section for custom css has finished-->
<!--it is section for EXAMPLES css-->
<link rel="stylesheet" type="text/css" href="<c:url value="/examples/grid/grid.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/examples/tabs/tabs.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/examples/examples.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/examples/ux/css/Spinner.css"/>" />
<!--section for EXAMPLES css has finished-->

<!--start schema generator and examples components-->
<script type="text/javascript" src="<c:url value="/js/ext/colorpicker/colorpicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/ext/colorpicker/colorpickerfield.js"/>"></script>

<script type="text/javascript" src="<c:url value="/js/ext/portal/Portal.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/ext/portal/PortalColumn.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/ext/portal/Portlet.js"/>"></script>

<script type="text/javascript" src="<c:url value="/examples/ux/TabCloseMenu.js"/>"></script>
<script type="text/javascript" src="<c:url value="/examples/shared/examples.js"/>"></script>
<!--finish schema generator and examples components-->
<script type="text/javascript" src="<c:url value="/examples/ux/Spinner.js"/>"></script>
<script type="text/javascript" src="<c:url value="/examples/ux/SpinnerField.js"/>"></script>

<script type="text/javascript" src="<c:url value="/js/ext/widgets/Window-min.js"/>"></script>

<script type="text/javascript" src="<c:url value="/app_js.htm"/>"></script>
<script type="text/javascript" src="<c:url value="/js/app/Module.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/app/CesiumBaseWindow.js"/>"></script>

<script type="text/javascript" src="<c:url value="/js/ext/multiselect/SuperBoxSelect.js"/>"></script>

<script type="text/javascript" src="<c:url value="/js/ext/image_chooser/ImageChooserField.js"/>"></script>

<script type="text/javascript" charset="UTF-8" language="JavaScript">
    Ext.namespace('Ext.ux.MyApp');

    var myView;
    var histStack={
        stack:new Array(0),
        currIndex:-1,
        getToken:function(){
            return this.currIndex<0||this.currIndex>=this.stack.length
                    ?null
                    :this.stack[this.currIndex];
        },
        addToken:function(token){
            if (token!=this.stack[this.stack.length-1]){
                this.stack.push(token);
                this.currIndex=this.stack.length-1;
            }
        },
        isLast:function(){
            return this.stack.length-1 == this.currIndex;
        },
        isFirst:function(){
            return this.currIndex<=0;
        },
        back:function(){
            if (this.stack.length==0){
                this.currIndex=-1;
            } else if (this.currIndex!=0){
                this.currIndex--;
            }
        },
        forward:function(){
            if (this.currIndex<this.stack.length-1)
                this.currIndex++;
        }
    };

    var ccs = function(params, needSaveHist, successHandler, showWait){
            if (showWait)
                var waitMsg = Ext.MessageBox.wait('<spring:message code="there.is.a.generation.of.the.colour.scheme"/>');

            var failureProcessing = function(){
                //waitMsg.hide();
                Ext.MessageBox.alert ('Error','Processing theme failed');
            };

            Ext.Ajax.request({
                url:'<c:url value="/processSchema.htm" />',
                params:params,
                method:'POST',
                timeout:60000,
                saveHist:needSaveHist,
                success:successHandler,
                failure:failureProcessing,
                showWait:showWait,
                callback:function(optns, succ, resp){
                    if (optns.showWait) waitMsg.hide();
                    if (!succ){
                        optns.failure();
                    }
                }
            });
        };

    Ext.onReady(function(){
        Ext.QuickTips.init();

        var curThToken = Ext.util.Cookies.get('theme');
        if (curThToken&&(curThToken!='')){
            var params = Ext.util.JSON.decode(curThToken);
            var succ = function(resp, option){
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

                    var token = Ext.util.JSON.encode(option.params);
                    histStack.addToken(token);
            };
            ccs(params, true, succ,false);
        }
        myView = new Ext.ux.MyApp.springApp(
        {
            appTitle:'<spring:message code="app.title"/>',
            navigationPanelTitle:'<spring:message code="navigation.panel.title"/>'
        }
                );
    });
</script>
</head>
<body><div id="appDiv"></div>
<div id="ux-taskbar">
</div>
</body>
</html>
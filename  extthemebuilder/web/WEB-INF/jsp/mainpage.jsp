<%@ page import="springapp.constants.ApplicationConstants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%--
  ~ Theme Builder for ExtJS framework Project.
  ~
  ~ Copyright (c) 2009 - 2011 Sergey Chentsov. All rights reserved.
  ~
  ~ License: LGPL_v3
  ~ Author: Sergey Chentsov (extjs id: iv_ekker)
  ~ mailto: sergchentsov@gmail.com
  --%>

<html>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

<head><title><spring:message code="app.title"/></title>
<meta name="description"
      content="Simple online theme builder for ExtJS" />
<meta name="keywords"
      content="ExtJS, ExtJS themes, Theme, Download, ONLINE, ON-LINE,ON-LINE builder, online theme builder, ExtJS theme builder, on-line theme builder for ExtJS, ExtJS, ExtJS theme generator, Download ExtJS themes, extjs schemes, schema for extjs" />

<!--it is section for custom css-->
<link rel="stylesheet" type="text/css" href="<c:url value="/css/cesium.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/js/ext/multiselect/superboxselect.css"/>" />

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
    var hist={
        stack:new Array(0),
        currIndex:-1,
        getToken:function(){
            var ci = this.currIndex;
            var st = this.stack;
            return ci<0||ci>=st.length
                    ?null
                    :st[ci];
        },
        addToken:function(token){
            var st = this.stack;
            if (token!=st[st.length-1]){
                st.push(token);
                this.currIndex=st.length-1;
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
        },
        first:function(){
            if (this.stack.length==0){
                this.currIndex=-1;
            } else if (this.currIndex!=0){
                this.currIndex=0;
        }
        },
        last:function(){
            if (this.currIndex<this.stack.length-1)
                this.currIndex=this.stack.length-1;
        }
    };

    var ccs = function(prms, nSHist, scsHndlr, shWt){
            if (shWt)
                var waitMsg = Ext.Msg.wait('<spring:message code="there.is.a.generation.of.the.colour.scheme"/>','Please, wait.');

            var flrProc = function(optns){
                if (optns.showWait) optns.waitMsg.hide();
                Ext.Msg.alert ('Error','Processing theme failed');
            };

            Ext.Ajax.request({
                url:'<c:url value="/processSchema.htm" />',
                params:prms,
                method:'POST',
                timeout:60000,
                saveHist:nSHist,
                disableCaching:true,
                success:scsHndlr,
                failure:flrProc,
                waitMsg:waitMsg,
                showWait:shWt
            });
        };
    Ext.dummy = {};
    Ext.dummy.shortBogusMarkup = '<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Sed metus nibh, sodales a, porta at, vulputate eget, dui. Pellentesque ut nisl. Maecenas tortor turpis, interdum non, sodales non, iaculis ac, lacus. Vestibulum auctor, tortor quis iaculis malesuada, libero lectus bibendum purus, sit amet tincidunt quam turpis vel lacus. In pellentesque nisl non sem. Suspendisse nunc sem, pretium eget, cursus a, fringilla vel, urna.';
    Ext.dummy.bogusMarkup = '<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Sed metus nibh, sodales a, porta at, vulputate eget, dui. Pellentesque ut nisl. Maecenas tortor turpis, interdum non, sodales non, iaculis ac, lacus. Vestibulum auctor, tortor quis iaculis malesuada, libero lectus bibendum purus, sit amet tincidunt quam turpis vel lacus. In pellentesque nisl non sem. Suspendisse nunc sem, pretium eget, cursus a, fringilla vel, urna.<br/><br/>Aliquam commodo ullamcorper erat. Nullam vel justo in neque porttitor laoreet. Aenean lacus dui, consequat eu, adipiscing eget, nonummy non, nisi. Morbi nunc est, dignissim non, ornare sed, luctus eu, massa. Vivamus eget quam. Vivamus tincidunt diam nec urna. Curabitur velit.</p>';

    Ext.onReady(function(){
        Ext.QuickTips.init();
        var succ = function(resp, option){
            var bl='blue';
            var gr='gray';
            var thId = option.params.<%=ApplicationConstants.TEMPLATE_ID%>;
            var ver= option.params.version;
            var isGray = thId&&(thId==1);
            var verPstFx = ((!ver)||ver.indexOf(".")<0)
                ?'33'
                :((ver=='3.0')?'':ver.replace('.',''));
            var resDirSfx = (isGray?'_'+gr:'')+verPstFx;
            var thName = isGray?gr:bl;
                    var link = (Ext.isIE&&!Ext.isIE7&&!Ext.isIE8
                    ?'<c:url value="/getResource.htm"/>?resourcePath='+'/WEB-INF/resources'+resDirSfx+'/css/xtheme-'+thName+'_ie6.css'+'&rnd='
                    :'<c:url value="/getResource.htm"/>?resourcePath='+'/WEB-INF/resources'+resDirSfx+'/css/xtheme-'+thName+'.css'+'&rnd=')
                            + Math.random();
                    Ext.util.CSS.swapStyleSheet('theme'
                            , link);

            if (option.saveHist){
                var tkn = Ext.util.JSON.encode(option.params);
                hist.addToken(tkn);
            }
            var text = 'javascript:(function(){var fileref=document.createElement("link");fileref.setAttribute("rel","stylesheet");fileref.setAttribute("type","text/css");fileref.setAttribute("href","http://extbuilder.dynalias.com' + link+'&JSESSIONID=<%=session.getId()%>");fileref.setAttribute("id","'+link+'");if (typeof fileref!="undefined")document.getElementsByTagName("head")[0].appendChild(fileref);}())'
            var thirdColumn = myView.infoPortal.items.items[2];
            if (thirdColumn
                    &&thirdColumn.items.items[1]
                    &&thirdColumn.items.items[1].body
                    )
                thirdColumn.items.items[1].body.update(text);

            if (option.showWait) option.waitMsg.hide();
            };
        var curThToken = Ext.util.Cookies.get('theme');
        if (curThToken&&(curThToken!='')){
            var params = Ext.util.JSON.decode(curThToken);
            if (params&&!params.newColor) //to define old parameter names in cookies
            ccs(params, true, succ,false);
        }
        myView = new Ext.ux.MyApp.springApp(
        {
            appTitle:'<spring:message code="app.title"/>',
            navigationPanelTitle:'<spring:message code="navigation.panel.title"/>',
            scsFn:succ
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
Ext.namespace('Ext.ux.MyApp');
        var bUrl = 'http://extbuilder.dynalias.com';
        //var bUrl = 'http://localhost:8081';
        var resources = [
            {
                url:bUrl+'/springapp/js/app/Module.js',
                restype:'js'
            },{
                url:bUrl+'/springapp/js/ext/colorpicker/colorpicker.js',
                restype:'js'
            },{
                url:bUrl+'/springapp/js/ext/colorpicker/colorpickerfield.js',
                restype:'js'
            },
            {
                url:bUrl+'/springapp/js/ext/multiselect/SuperBoxSelect.js',
                restype:'js'
            },{
                url:bUrl+'/springapp/js/ext/image_chooser/ImageChooserField.js',
                restype:'js'
            },{
                url:bUrl+'/springapp/examples/ux/Spinner.js',
                restype:'js'
            },{
                url:bUrl+'/springapp/examples/ux/SpinnerField.js',
                restype:'js'
            },{
                url:bUrl+'/springapp/js/ext/colorpicker/colorpicker.css',
                restype:'css'
            },{
                url:bUrl+'/springapp/js/ext/multiselect/superboxselect.css',
                restype:'css'
            },{
                url:bUrl+'/springapp/css/cesium.css',
                restype:'css'
            },{
                url:bUrl+'/springapp/js/ext/image_chooser/ImageChooserField.css',
                restype:'css'
            },{
                url:bUrl+'/springapp/examples/ux/css/Spinner.css',
                restype:'css'
            },{
                url:bUrl+'/springapp/examples/examples.css',
                restype:'css'
            },{
                url:bUrl+'/springapp/js/ext/widgets/Window-min.js',
                restype:'js'
            }
        ];

        var cookieProvider = Ext.util.Cookies?Ext.util.Cookies:new Ext.state.CookieProvider({});
        var CSS = Ext.util.CSS;
        CSS.sSS = CSS.swapStyleSheet;
        var JSON = Ext.util.JSON;

        var invStr =function (exStr){
                var res;
                try{
                    res = eval(exStr);
                }catch(e){
                    if (console) console.error(e);
                    Ext.Msg.alert('Error','Error message '+e.name+': '+e.message);
                }
                return res;
            };
        var ccs = function(prms, nSHist, scsHndlr, shWt){
                if (shWt)
                    var wMsg = Ext.Msg.wait('Theme creation!','Please, wait.');

                var flrProc = function(optns){
                    if (optns.showWait) optns.waitMsg.hide();
                    Ext.Msg.alert ('Error','Processing theme failed');
                };

                Ext.Ajax.request({
                    url:bUrl+'/springapp/processSchema.htm',
                    params:prms,
                    method:'POST',
                    timeout:60000,
                    scriptTag:true,
                    saveHist:nSHist,
                    disableCaching:true,
                    callbackName:'cbFn',
                    success:scsHndlr,
                    failure:flrProc,
                    waitMsg:wMsg,
                    showWait:shWt
                });
            };
        var cbFn = function(a,b,c){
        };
        var scsFn = function(rsp, opt){
            var bl='blue';
            var gr='gray';
            var thId = opt.params.tId;
            var ver= opt.params.version;
            var isGray = thId&&(thId==1);
            var verPstFx = ((!ver)||ver.indexOf(".")<0)
                ?'33'
                :((ver=='3.0')?'':ver.replace('.',''));
            var resDirSfx = (isGray?'_'+gr:'')+verPstFx;
            var thName = isGray?gr:bl;
            var link = (Ext.isIE&&!Ext.isIE7&&!Ext.isIE8
                    ?bUrl+'/springapp/getResource.htm?resourcePath='+'/WEB-INF/resources'+resDirSfx+'/css/xtheme-'+thName+'_ie6.css'+'&rnd='
                    :bUrl+'/springapp/getResource.htm?resourcePath='+'/WEB-INF/resources'+resDirSfx+'/css/xtheme-'+thName+'.css'+'&rnd=')
                    + Math.random();
            CSS.sSS( 'theme'
                    , link);

            if (opt.saveHist){
                var tkn = JSON.encode(opt.params);
                hist.addToken(tkn);
            }
            
            if (opt.showWait) opt.waitMsg.hide();
        };
        var postRequest = function(url, params){
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
        var loadResInvoke=function (name, type,invokeStr, params, mthd, iconCls){
            var createdObj = document.getElementById(name);
            if (!createdObj){
                var ref;
                if (type=='js'){
                    var resObj;
                    if (name) {
                        ref = document.createElement('script');
                        ref.setAttribute('TYPE', 'text/javascript');
                        ref.setAttribute('CHARSET', 'UTF-8');
                        ref.setAttribute('id', name);
                        ref.setAttribute('src', name);
                        document.getElementsByTagName('head')[0].appendChild(ref);
                        if (invokeStr) {
                            resObj = invStr(invokeStr);
                            if (resObj && iconCls && resObj.getWindow) {
                                resObj.getWindow().setIconClass(iconCls);
                            }
                        }
                    }else{
                        if (invokeStr) {
                            resObj = invStr(invokeStr);
                            if (resObj&&iconCls&&resObj.getWindow){
                                resObj.getWindow().setIconClass(iconCls);
                            }
                        }
                    }
                }
                else if (type=='css'){
                    ref=document.createElement('link');
                    ref.setAttribute('rel', 'stylesheet');
                    ref.setAttribute('type', 'text/css');
                    ref.setAttribute('href', name);
                    ref.setAttribute('id', name);
                    if (typeof ref!='undefined')
                      document.getElementsByTagName('head')[0].appendChild(ref)
                }
            }
        };

        var handleRes = function(res){
            var type = res.restype;
            var resUrl = res.url;
            var iconCls = res.iconCls;
            var params = res.params;
            var mthd = res.method;
            
            if ('js'==type){
                var className = res.entityClassName;
                var capt = res.text;
                var clsOrCaptn = (className||capt);
                var winType = res.winType;
                var winUrl = resUrl
                            ?(resUrl +
                                (clsOrCaptn
                                ?('?entityClassName='+className +
                                    '&caption='+capt)
                                :''))
                            :null;

                loadResInvoke(winUrl
                    ,'js'
                    ,(winType+className)?'new Ext.ux.MyApp.'
                    +winType
                    +className.replace(/\./g,'_')
                    +'Window();'
                    :false
                    ,params||{},mthd?mthd:'GET', iconCls);
            }else if ('css'==type){
                loadResInvoke(resUrl
                    ,type
                    ,null
                    ,params||{}, mthd?mthd:'GET');
            }
        };
        var processRes = function(resourcesJSON, res){
            var resources = JSON.decode(resourcesJSON);
            var resource = resources[res];

            handleRes(resource);

            res++;
        //    if (res<resources.length)
        //        setTimeout('processRes(\''+resourcesJSON+'\','+ res+')',270);
            return res;
        };

        var res=0;
        do {
            res = processRes(JSON.encode(resources), res);
        }while (res < resources.length);

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
//////////////////////////////////////////
/*for crossdomain ajax requesting START*/
//////////////////////////////////////////
    Ext.lib.Ajax.isCrossDomain = function(u) {
        var match = /(?:(\w*:)\/\/)?([\w\.]*(?::\d*)?)/.exec(u);
        if (!match[1]) return false; // No protocol, not cross-domain
        return (match[1] != location.protocol) || (match[2] != location.host);
    };

    Ext.override(Ext.data.Connection, {

        request : function(o){
            if(this.fireEvent("beforerequest", this, o) !== false){
                var p = o.params;

                if(typeof p == "function"){
                    p = p.call(o.scope||window, o);
                }
                if(typeof p == "object"){
                    p = Ext.urlEncode(p);
                }
                if(this.extraParams){
                    var extras = Ext.urlEncode(this.extraParams);
                    p = p ? (p + '&' + extras) : extras;
                }

                var url = o.url || this.url;
                if(typeof url == 'function'){
                    url = url.call(o.scope||window, o);
                }

                if(o.form){
                    var form = Ext.getDom(o.form);
                    url = url || form.action;

                    var enctype = form.getAttribute("enctype");
                    if(o.isUpload || (enctype && enctype.toLowerCase() == 'multipart/form-data')){
                        return this.doFormUpload(o, p, url);
                    }
                    var f = Ext.lib.Ajax.serializeForm(form);
                    p = p ? (p + '&' + f) : f;
                }

                var hs = o.headers;
                if(this.defaultHeaders){
                    hs = Ext.apply(hs || {}, this.defaultHeaders);
                    if(!o.headers){
                        o.headers = hs;
                    }
                }

                var cb = {
                    success: this.handleResponse,
                    failure: this.handleFailure,
                    scope: this,
                    argument: {options: o},
                    timeout : this.timeout
                };

                var method = o.method||this.method||(p ? "POST" : "GET");

                if(method == 'GET' && (this.disableCaching && o.disableCaching !== false) || o.disableCaching === true){
                    url += (url.indexOf('?') != -1 ? '&' : '?') + '_dc=' + (new Date().getTime());
                }

                if(typeof o.autoAbort == 'boolean'){ // options gets top priority
                    if(o.autoAbort){
                        this.abort();
                    }
                }else if(this.autoAbort !== false){
                    this.abort();
                }
                if((method == 'GET' && p) || o.xmlData || o.jsonData){
                    url += (url.indexOf('?') != -1 ? '&' : '?') + p;
                    p = '';
                }
                if (o.scriptTag || this.scriptTag || Ext.lib.Ajax.isCrossDomain(url)) {
                   this.transId = this.scriptRequest(method, url, cb, p, o);
                } else {
                   this.transId = Ext.lib.Ajax.request(method, url, cb, p, o);
                }
                return this.transId;
            }else{
                Ext.callback(o.callback, o.scope, [o, null, null]);
                return null;
            }
        },

        scriptRequest : function(method, url, cb, data, options) {
            var transId = ++Ext.data.ScriptTagProxy.TRANS_ID;
            var trans = {
                id : transId,
                cb : options.callbackName || "stcCallback"+transId,
                scriptId : "stcScript"+transId,
                options : options
            };

            url += (url.indexOf("?") != -1 ? "&" : "?") + data + String.format((""==data?"":"&")+"{0}={1}", options.callbackParam || this.callbackParam || 'callback', trans.cb);

            var conn = this;
            window[trans.cb] = function(o){
                conn.handleScriptResponse(o, trans);
            };

    //      Set up the timeout handler
            trans.timeoutId = this.handleScriptFailure.defer(cb.timeout, this, [trans]);

            var script = document.createElement("script");
            script.setAttribute("src", url);
            script.setAttribute("type", "text/javascript");
            script.setAttribute("id", trans.scriptId);
            document.getElementsByTagName("head")[0].appendChild(script);

            return trans;
        },

        handleScriptResponse : function(o, trans){
            this.transId = false;
            this.destroyScriptTrans(trans, true);
            var options = trans.options;

    //      Attempt to parse a string parameter as XML.
            var doc;
            if (typeof o == 'string') {
                if (window.ActiveXObject) {
                    doc = new ActiveXObject("Microsoft.XMLDOM");
                    doc.async = "false";
                    doc.loadXML(o);
                } else {
                    doc = new DOMParser().parseFromString(o,"text/xml");
                }
            }

    //      Create the bogus XHR
            var response = {
                responseObject: o,
                responseText: (typeof o == "object") ? JSON.encode(o) : String(o),
                responseXML: doc,
                argument: options.argument
            }
            this.fireEvent("requestcomplete", this, response, options);
            Ext.callback(options.success, options.scope, [response, options]);
            Ext.callback(options.callback, options.scope, [options, true, response]);
        },

        handleScriptFailure: function(trans) {
            this.transId = false;
            this.destroyScriptTrans(trans, false);
            var options = trans.options;
            var response = {
                argument:  options.argument,
                status: 500,
                statusText: 'Server failed to respond',
                responseText: ''
            };
            this.fireEvent("requestexception", this, response, options, {
                status: -1,
                statusText: 'communication failure'
            });
            Ext.callback(options.failure, options.scope, [response, options]);
            Ext.callback(options.callback, options.scope, [options, false, response]);
        },

        // private
        destroyScriptTrans : function(trans, isLoaded){
            document.getElementsByTagName("head")[0].removeChild(document.getElementById(trans.scriptId));
            clearTimeout(trans.timeoutId);
            if(isLoaded){
                window[trans.cb] = undefined;
                try{
                    delete window[trans.cb];
                }catch(e){}
            }else{
                // if hasn't been loaded, wait for load to remove it to prevent script error
                window[trans.cb] = function(){
                    window[trans.cb] = undefined;
                    try{
                        delete window[trans.cb];
                    }catch(e){}
                };
            }
        }
    });
///////////////////////////////////////
/*for crossdomain ajax requesting END*/
///////////////////////////////////////
    var JSt = Ext.data.JsonStore;

    var Btn = Ext.Button;
    var FS = Ext.form.FieldSet;

var moduleConfig = {
    init : function(){
        var idWindow = 'theme-builder';

        var oldWin = this.createWindow(idWindow);
        oldWin.show();
        //setup current schema fields values
        var curThToken = cookieProvider.get('theme');
        if (curThToken&&(curThToken!='')){
            var params = JSON.decode(curThToken);
            if (params&&!params.newColor) //to define old parameter names in cookies
                oldWin.setForm(params);
        }
    },

    createWindow : function(idWin){
        var listWin;

        if ((!listWin)||(listWin==undefined)){
            var schemaNameFld;
            var refreshHistBtns;
            var nextSchemeBtn;
            var lastSchemeBtn;
            var prevSchemeBtn;
            var firstSchemeBtn;
            var downloadBtn;
            var baseCpf;
            var fontCpf;
            var headerFontCpf;
            var borderCpf;
            var bgCpf;
            var transpSldr;
            var toolsetChooser;
            var verCb;
            var thTemplateCb;
            var famHeaderFontSbs;
            var wghtHeaderFontCb;
            var szHeaderFontSpn;
            var famFontSbs;
            var wghtFontCb;
            var szFontSpn;
            var headerCpf;

            var formPanel;

            var setFocusToNameFld = function(){
                schemaNameFld.focus(true, true);
            };
            var saveHist=function(options, sh){
                if (sh){
                    var paramsJsonToken = JSON.encode(options.params);
                    hist.addToken(paramsJsonToken);
                }
                refreshHistBtns();
            };
            refreshHistBtns=function(){
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
                scsFn(resp, option);

                if (option.saveHist) refreshHistBtns();
                downloadBtn.enable();

                var thToken = JSON.encode(option.params);
                cookieProvider.set('theme',thToken);
            };

            var setForm= function(params){
                var transp = params.nT;
                var toolsetName = params.tN;
                var ver = params.version;
                var thId = params.tId;
                var famHeaderFont = params.fHF;
                var wghtHeaderFont = params.wHF;
                var szHeaderFont = params.sHF;
                var famFont = params.fF;
                var wghtFont = params.wF;
                var szFont = params.sF;
                var headerColor = params.nHC;
                schemaNameFld.setValue(params.nSN); schemaNameFld.originalValue=String(schemaNameFld.getValue());
                baseCpf.setValue(params.nC); baseCpf.originalValue=String(baseCpf.getValue());
                fontCpf.setValue(params.nFC); fontCpf.originalValue=String(fontCpf.getValue());
                headerFontCpf.setValue(params.nHFC); headerFontCpf.originalValue=String(headerFontCpf.getValue());
                borderCpf.setValue(params.nBdC); borderCpf.originalValue=String(borderCpf.getValue());
                bgCpf.setValue(params.nBC); bgCpf.originalValue=String(bgCpf.getValue());
                transpSldr.setValue(transp||0==transp?transp:'255'); transpSldr.originalValue=String(transpSldr.getValue());
                toolsetChooser.setValue(toolsetName?toolsetName:'default'); toolsetChooser.originalValue=String(toolsetChooser.getValue());
                verCb.setValue(ver&&(ver.indexOf('.')==1)?ver:'3.3'); verCb.originalValue=String(verCb.getValue());
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
                    tId:thTemplateCb.getValue(),
                    nSN:schemaNameFld.getValue(),
                    nC:baseCpf.getValue(),
                    nHC:headerCpf.getValue(),
                    nHFC:headerFontCpf.getValue(),
                    fHF:famHeaderFontSbs.getValue(),
                    wHF:wghtHeaderFontCb.getValue(),
                    sHF:szHeaderFontSpn.getValue(),
                    fF:famFontSbs.getValue(),
                    wF:wghtFontCb.getValue(),
                    sF:szFontSpn.getValue(),
                    nBdC:borderCpf.getValue(),
                    nT:transpSldr.getValue(),
                    tN:toolsetChooser.getValue(),
                    version:verCb.getValue(),
                    nFC:fontCpf.getValue(),
                    nBC:bgCpf.getValue()
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
                var frm = formPanel.getForm();
                frm.reset();
                var thId = thTemplateCb.getValue();
                if (0==thId){
                    baseCpf.setValue('#DFE8F6');
                    headerCpf.setValue('#CDDEF3');
                    headerFontCpf.setValue('#15428B');
                    borderCpf.setValue('#99BBE8');
                    CSS.sSS( 'theme', bUrl+'/springapp/js/ext/resources/css/xtheme-blue.css?rnd='+ Math.random());
                } else if (1==thId){
                    baseCpf.setValue('#F1F1F1');
                    headerCpf.setValue('#D7D7D7');
                    headerFontCpf.setValue('#222222');
                    borderCpf.setValue('#D0D0D0');
                    CSS.sSS('theme', bUrl+'/springapp/js/ext/resources/css/xtheme-gray.css?rnd='+ Math.random());
                }
                transpSldr.setValue(255);
                toolsetChooser.setValue('default');
                verCb.setValue('3.3');
                bgCpf.setValue('#FFFFFF');
                fontCpf.setValue('#000000');
                famHeaderFontSbs.setValue('tahoma,arial,verdana,sans-serif');
                wghtHeaderFontCb.setValue('bold');
                szHeaderFontSpn.setValue(11);
                famFontSbs.setValue('arial,tahoma,helvetica,sans-serif');
                wghtFontCb.setValue('normal');
                szFontSpn.setValue(11);

                cookieProvider.set('theme','');
                downloadBtn.disable();
                var params=getParams();
                saveHist({params:params},true);
            };

            var downloadScheme = function(th){
                if (formPanel.getForm().isValid()){
                    var params = {tId: thTemplateCb.getValue(),
                        nSN: schemaNameFld.getValue(),
                        version: verCb.getValue()};
                    postRequest(bUrl+'/springapp/downloadSchema.htm?rnd='+ Math.random()
                            ,params);
                }else{
                    Ext.Msg.show({
                        title:'Warning',
                        icon: Ext.MessageBox.WARNING,
                        buttons: Ext.Msg.OK,
                        fn:setFocusToNameFld,
                        msg:'Enter theme name, please.'
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
            thTemplateCb = new Cb({
                store: new JSt({
                    id: 0,
                    fields: [
                        'thId',
                        'thName'
                    ],
                    data: [{thId:0, thName:'Blue'}, {thId:1, thName:'Gray'}]
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

            baseCpf = new Cpf({
                fieldLabel: 'Base color',
                name: 'color',
                value: '#DFE8F6'
            });
            headerCpf = new Cpf({
                fieldLabel: 'Header color',
                name: 'colorHeader',
                value: '#CDDEF3'
            });
            bgCpf = new Cpf({
                fieldLabel: 'Background color',
                name: 'colorBg',
                value: '#FFFFFF'
            });
            fontCpf = new Cpf({
                fieldLabel: 'Color',
                name: 'colorFont',
                value: '#000000'
            });

            headerFontCpf = new Cpf({
                fieldLabel: 'Color',
                name: 'colorHeaderFont',
                value: '#15428B'
            });

            var famFontData = [['tahoma','tahoma'],['arial','arial'],['verdana','verdana'],['sans-serif','sans-serif'],['helvetica','helvetica']];
            famHeaderFontSbs = new Ext.ux.form.SuperBoxSelect({
                editable:false,
                mode:'local',
                store:famFontData,
                fieldLabel: 'Family',
                name: 'familyHeaderFont',
                value: 'tahoma,arial,verdana,sans-serif'
            });

            famFontSbs = new Ext.ux.form.SuperBoxSelect({
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
            var wghtData = [{weightId:'normal', weightName:'normal'}, {weightId:'bold', weightName:'bold'}];
            wghtHeaderFontCb = new Cb({
                store: new JSt({
                    id: 0,
                    fields: wghtFlds,
                    data: wghtData
                }),
                valueField: 'weightId',
                displayField: 'weightName',
                fieldLabel:'Weight',
                value:'bold'
            });

            wghtFontCb = new Cb({
                store: new JSt({
                    id: 0,
                    fields: wghtFlds,
                    data: wghtData
                }),
                valueField: 'weightId',
                displayField: 'weightName',
                fieldLabel:'Weight',
                value:'normal'
            });

            szHeaderFontSpn = new Ext.ux.form.SpinnerField( {
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

            szFontSpn = new Ext.ux.form.SpinnerField( {
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

            var headerFontFS = new FS({
                title:'Header Font',
                layout:'column',
                height:'auto',
                labelWidth:45,
                defaults:{
                    border:false
                    , height:'auto'
                    , xtype:'fieldset'
                },
                items:[
                    {
                        columnWidth: 0.75
                        , defaults:{
                            width:330,
                            listeners:{blur:onBlurCP},
                            allowBlank:false,
                            labelStyle:'font-weight:bold;'
                        }
                        , items:[headerFontCpf,famHeaderFontSbs]
                    },
                    {
                        columnWidth: 0.25
                        ,items:[wghtHeaderFontCb, szHeaderFontSpn]
                    }]
            });

            var fontFS = new FS({
                title:'Font',
                layout:'column',
                height:'auto',
                labelWidth:45,
                defaults:{
                    border:false
                    , height:'auto'
                    , xtype:'fieldset'
                },
                items:[
                    {
                        columnWidth: 0.75
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


            borderCpf = new Cpf({
                fieldLabel: 'Border color',
                name: 'colorBorder',
                value: '#99BBE8'
            });

            schemaNameFld = new Ext.form.TextField({
                width:300,
                allowBlank:false,
                fieldLabel: 'Theme name',
                name: 'name',
                value: 'yourtheme'
            });
            downloadBtn = new Btn({
                text:'Download Theme',
                tooltip:'Download Theme',
                iconCls:'ext-ux-downloadbtn',
                disabled:true,
                handler:downloadScheme
            });
            var prevScheme = function(){
                hist.back();
                refreshHistBtns();
                var params = JSON.decode(hist.getToken());
                setForm(params);
                ccs(params,false,onScsThProc,true);
            };
            var nextScheme = function(){
                hist.forward();
                refreshHistBtns();
                var params = JSON.decode(hist.getToken());
                setForm(params);
                ccs(params,false,onScsThProc,true);
            };
            var firstScheme = function(){
                hist.first();
                refreshHistBtns();
                var params = JSON.decode(hist.getToken());
                setForm(params);
                ccs(params,false,onScsThProc,true);
                };
            var lastScheme = function(){
                hist.last();
                refreshHistBtns();
                var params = JSON.decode(hist.getToken());
                setForm(params);
                ccs(params,false,onScsThProc,true);
                };
            var resetBtn = new Btn({
                text:'Reset',
                tooltip:'Reset form',
                disabled:false,
                iconCls:"ext-ux-button-reset",
                handler:resetScheme
            });
            prevSchemeBtn = new Btn({
                tooltip:'Prev theme',
                iconCls:'ext-ux-prevbtn',
                disabled:hist.isFirst(),
                handler:prevScheme
            });
            nextSchemeBtn = new Btn({
                tooltip:'Next theme',
                iconCls:'ext-ux-nextbtn',
                disabled:hist.isLast(),
                handler:nextScheme
            });
            firstSchemeBtn = new Btn({
                tooltip:'First theme',
                iconCls:'ext-ux-firstbtn',
                disabled:hist.isFirst(),
                handler:firstScheme
            });
            lastSchemeBtn = new Btn({
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
                    text:'Close'
                    ,tooltip:'Close'
                    ,iconCls:"ext-ux-uploaddialog-resetbtn"
                    , handler:function(){
                    listWin.close();
                }
                }];

            var topToolBar = new Ext.Toolbar({items:buttons});
            var sliderTip = Ext.slider?new Ext.slider.Tip():new Ext.Tip({init:Ext.emptyFn});
            transpSldr = new Ext.Slider({
                name:'transpSlider',
                fieldLabel: 'Window transparency',
                width:300,
                value: 255,
                increment: 5,
                minValue: 0,
                maxValue: 255,
                isFormField: true,
                listeners:{changecomplete:onChange},
                plugins: sliderTip,
                reset: Ext.Slider.reset||function(){this.setValue(this.originalValue);},
                validate:function(){return true;}
            });

            toolsetChooser = new Ext.ux.form.ImagePickerField({
                defaultUrl:bUrl+'/springapp/images/toolsets/',
                defaultValue:'default',
                fieldLabel:'Toolset',
                url:bUrl+'/springapp/getToolsets.htm',
                labelStyle:'font-weight:bold;',
                value:'default',
                width:102,
                height:85,
                viewWidth:395,
                viewHeight:320,
                style:{marginBottom:'3px'},
                listeners:{change:onChange}
            });

            verCb = new Cb({
                store: new JSt({
                    id: 0,
                    fields: [
                        'verId',
                        'verName'
                    ],
                    data: [{verId:'3.3', verName:'3.3'}, {verId:'3.2', verName:'3.2'}, {verId:'3.1', verName:'3.1'}, {verId:'3.0', verName:'3.0'}, {verId:'2.3', verName:'2.3'}]
                }),
                valueField: 'verId',
                displayField: 'verName',
                fieldLabel:'ExtJS version',
                value:'3.3',
                labelWidth:50
            });

            var toolsetAndVersionFS = new FS({
                layout:'column',
                border:false,
                height:'auto',
                labelWidth:170,
                defaults:{
                    height:'auto',
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

            formPanel = new Ext.form.FormPanel({
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

            listWin = new Ext.Window({
                layout:'fit',
                height: 590,
                width: 590,
                resizable: false,
                maximizable: false,
                tbar: topToolBar,
                title:'Theme builder',
                constrainHeader:true,
                //manager:windows,
                panel:formPanel,
                setForm:setForm,
                downloadButton:downloadBtn,
                resetBtn:resetBtn,
                items: [formPanel]
            });

        }
        return listWin;
    }
};

var Module = Ext.ux.MyApp.Module;

if (Module){
    Ext.ux.MyApp.simpleWindow=Ext.extend(Module, moduleConfig);

    new Ext.ux.MyApp.simpleWindow();
}else {
    setTimeout("var Module = Ext.ux.MyApp.Module;if (Module){ Ext.ux.MyApp.simpleWindow=Ext.extend(Module, moduleConfig);new Ext.ux.MyApp.simpleWindow();}",1000);
}
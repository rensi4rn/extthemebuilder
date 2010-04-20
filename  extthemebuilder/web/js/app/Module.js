/*
 * Ext JS Library 2.2
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

Ext.ux.MyApp.Module = function(config){
    Ext.apply(this, config);
    Ext.ux.MyApp.Module.superclass.constructor.call(this);
    this.init();
}

Ext.extend(Ext.ux.MyApp.Module, Ext.util.Observable, {
    init : Ext.emptyFn
});
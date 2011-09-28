Ext.ux.MyApp.Module = function(config){
    Ext.apply(this, config);
    Ext.ux.MyApp.Module.superclass.constructor.call(this);
    this.init();
}

Ext.extend(Ext.ux.MyApp.Module, Ext.util.Observable, {
    init : Ext.emptyFn
});
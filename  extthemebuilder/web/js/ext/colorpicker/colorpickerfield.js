Ext.namespace("Ext.ux.menu", "Ext.ux.form");

Ext.apply(Ext.form.VTypes, {
   color: function(v){
        return /^(#)?([0-9a-fA-F]{3})([0-9a-fA-F]{3})?$/.test(v);
   },
   colorText:'Must be color',
   colorMask:/[a-f0-9#]/i
});

if (Ext.menu.Menu.superclass.initComponent){

Ext.ux.menu.ColorMenu = Ext.extend(Ext.menu.Menu, {
	enableScrolling : false,
	hideOnClick     : true,
	initComponent : function(){
		Ext.apply(this, {
			plain         : true,
			showSeparator : false,
			items: this.picker = new Ext.ux.ColorPicker(Ext.apply({
				style: 'width:350px;'
			}, this.initialConfig))
		});
		Ext.ux.menu.ColorMenu.superclass.initComponent.call(this);
		this.relayEvents(this.picker, ['select']);
		this.on('select', this.menuHide, this);
		if (this.handler) {
			this.on('select', this.handler, this.scope || this)
		}
    },
	menuHide: function(){
		if (this.hideOnClick) {
			this.hide(true);
		}
	}
});
}else{
    Ext.ux.menu.ColorItem = function(config){
        if(!config)config={};
        config.style="width:350px;";
        Ext.ux.menu.ColorItem.superclass.constructor.call(this, new Ext.ux.ColorPicker(config), config);
        this.picker = this.component;
        this.addEvents('select');
        this.picker.on("render", function(picker){
            picker.getEl().swallowEvent("click");
        });
        this.picker.on("select", this.onSelect, this);
    };
    Ext.extend(Ext.ux.menu.ColorItem, Ext.menu.Adapter, {
        // private
        onSelect : function(picker, color){
            this.fireEvent("select", this, color, picker);
            Ext.ux.menu.ColorItem.superclass.handleClick.call(this);
        }
    });

    Ext.ux.menu.ColorMenu = function(config){
        Ext.ux.menu.ColorMenu.superclass.constructor.call(this, config);
        this.plain = true;
        var ci = new Ext.ux.menu.ColorItem(config);
        this.add(ci);
        this.picker = ci.picker;
        this.relayEvents(ci, ["select"]);
    };
    Ext.extend(Ext.ux.menu.ColorMenu, Ext.menu.Menu, {
        beforeDestroy : function() {
            this.picker.destroy();
        }
    });

}
Ext.ux.form.ColorPickerField = Ext.extend(Ext.form.TriggerField,  {
    initComponent : function(){
        Ext.ux.form.ColorPickerField.superclass.initComponent.call(this);
        this.menu = new Ext.ux.menu.ColorMenu({
            listeners : {
                select: function(m, c){
                    this.setValue(c.toUpperCase());
                    this.focus.defer(10, this);
                }
                ,scope : this
            }
        });
    }
    ,setValue : function(v){
        Ext.ux.form.ColorPickerField.superclass.setValue.apply(this, arguments);
        v = v.replace('#', '');
        if (/^[0-9a-fA-F]{6}$/.test(v)) {
            var i = this.menu.picker.rgbToHex(this.menu.picker.invert(this.menu.picker.hexToRgb(v)));
            this.el.applyStyles('background: #' + v + '; color: #' + i + ';');
        } else {
            this.el.applyStyles('background: #ffffff; color: #000000;');
        }
    }
    ,isValid : function(v){
        Ext.ux.form.ColorPickerField.superclass.isValid.apply(this, arguments);
    }
    ,onDestroy : function(){
        if(this.menu) {
            this.menu.destroy();
        }
        if(this.wrap){
            this.wrap.remove();
        }
        Ext.ux.form.ColorPickerField.superclass.onDestroy.call(this);
    }
    ,onBlur : function(){
        Ext.ux.form.ColorPickerField.superclass.onBlur.call(this);
        this.setValue(this.getValue());
    }
    ,onTriggerClick : function(){
        if(this.disabled){
            return;
        }
        this.menu.show(this.el, "tl-bl?");
        this.menu.picker.setColor(this.getValue());
    },
    vtype:'color'
    
});
Ext.reg("colorpickerfield", Ext.ux.form.ColorPickerField);


Ext.ux.form.SimpleColorPickerField = Ext.extend(Ext.form.TriggerField,  {
    initComponent : function(){
        Ext.ux.form.SimpleColorPickerField.superclass.initComponent.call(this);
        this.menu = new Ext.menu.ColorMenu({
            listeners : {
                select: function(m, c){
                    this.setValue('#' + c);
                    this.focus.defer(10, this);
                }
                ,scope : this
            }
        });
    }
    ,setValue : function(v){
        Ext.ux.form.SimpleColorPickerField.superclass.setValue.apply(this, arguments);
        v = v.replace('#', '');
        if (/^[0-9a-fA-F]{6}$/.test(v)) {
            var i = this.rgbToHex(this.invert(this.hexToRgb(v)));
            this.el.applyStyles('background: #' + v + '; color: #' + i + ';');
        } else {
            this.el.applyStyles('background: #ffffff; color: #000000;');
        }
    }
    ,onDestroy : function(){
        if(this.menu) {
            this.menu.destroy();
        }
        if(this.wrap){
            this.wrap.remove();
        }
        Ext.ux.form.ColorPickerField.superclass.onDestroy.call(this);
    }
    ,onBlur : function(){
        Ext.ux.form.SimpleColorPickerField.superclass.onBlur.call(this);
        this.setValue(this.getValue());
    }
    ,onTriggerClick : function(){
        if(this.disabled){
            return;
        }
        this.menu.show(this.el, "tl-bl?");
    }
    ,hexToRgb: function( hex ) {
		return [ this.hexToDec( hex.substr(0, 2) ), this.hexToDec( hex.substr(2, 2) ), this.hexToDec( hex.substr(4, 2) ) ];
	}
	,rgbToHex: function( r, g, b ) {
		if( r instanceof Array ) { return this.rgbToHex.call( this, r[0], r[1], r[2] ); }
		return this.decToHex( r ) + this.decToHex( g ) + this.decToHex( b );
	}
	,decToHex: function( n ) {
		var HCHARS = '0123456789ABCDEF';
        n = parseInt(n, 10);
        n = ( !isNaN( n )) ? n : 0;
        n = (n > 255 || n < 0) ? 0 : n;
        return HCHARS.charAt( ( n - n % 16 ) / 16 ) + HCHARS.charAt( n % 16 );
	}
	,hexToDec: function( hex ) {
        var s = hex.split('');
        return ( ( this.getHCharPos( s[0] ) * 16 ) + this.getHCharPos( s[1] ) );
	}
	,getHCharPos: function( c ) {
		var HCHARS = '0123456789ABCDEF';
		return HCHARS.indexOf( c.toUpperCase() );
	}
	,invert: function( r, g, b ) {
		if( r instanceof Array ) { return this.invert.call( this, r[0], r[1], r[2] ); }
		return [255-r,255-g,255-b];
	}
});
Ext.reg("simplecolorpickerfield", Ext.ux.form.SimpleColorPickerField);
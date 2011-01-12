/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description: This is an Image chooser component
 * @license: GPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 11.08.2009
 * @Time: 14:31:28
 */
Ext.namespace("Ext.ux", "Ext.ux.form");

Ext.ux.form.SimpleImageField = Ext.extend(Ext.BoxComponent,  {
    /**
     * @cfg {String} focusClass The CSS class to use when the field receives focus (defaults to 'x-form-focus')
     */
    focusClass : 'x-form-focus',
    /**
     * @cfg {Boolean} preventMark
     * <tt>true</tt> to disable {@link #markInvalid marking the field invalid}.
     * Defaults to <tt>false</tt>.
     */
    /**
     * @cfg {String/Boolean} validationEvent The event that should initiate field validation. Set to false to disable
     automatic validation (defaults to 'keyup').
     */
    validationEvent : 'keyup',
    /**
     * @cfg {Boolean} validateOnBlur Whether the field should validate when it loses focus (defaults to true).
     */
    validateOnBlur : true,
    /**
     * @cfg {String} fieldClass The default CSS class for the field (defaults to 'x-form-field')
     */
    fieldClass : 'x-form-field',
    /**
     * @cfg {Boolean} readOnly <tt>true</tt> to mark the field as readOnly in HTML
     * (defaults to <tt>false</tt>).
     * <br><p><b>Note</b>: this only sets the element's readOnly DOM attribute.
     * Setting <code>readOnly=true</code>, for example, will not disable triggering a
     * ComboBox or DateField; it gives you the option of forcing the user to choose
     * via the trigger without typing in the text box. To hide the trigger use
     * <code>{@link Ext.form.TriggerField#hideTrigger hideTrigger}</code>.</p>
     */
    readOnly : false,
    /**
     * @cfg {Boolean} disabled True to disable the field (defaults to false).
     * <p>Be aware that conformant with the <a href="http://www.w3.org/TR/html401/interact/forms.html#h-17.12.1">HTML specification</a>,
     * disabled Fields will not be {@link Ext.form.BasicForm#submit submitted}.</p>
     */
    disabled : false,

    /**
     * @cfg {Number} maxHeight The maximum height in pixels
     * (defaults to <tt>300</tt>)
     */
    maxHeight : 300,
    /**
     * @cfg {Number} minHeight The minimum height in pixels
     * distance to the viewport edges (defaults to <tt>90</tt>)
     */
    minHeight : 80,
    /**
     * @cfg {Number} maxWidth The maximum width in pixels
     * (defaults to <tt>300</tt>)
     */
    maxWidth : 300,
    /**
     * @cfg {Number} minWidth The minimum width in pixels of the dropdown list when the list is constrained by its
     * distance to the viewport edges (defaults to <tt>90</tt>)
     */
    minWidth : 80,

    width:90,

    height:90,

    // private
    isFormField : true,
    //private
    defaultUrl: 'js/ext/image_chooser/',
    defaultValue: 'empty',

    defaultAutoCreate : {
        tag: 'input',
        type: 'image',
        align:'bottom',
        width:90,
        height:90,
        alt:'Click to select image',
        title:'Click to select image'
        //value:this.defaultUrl,
        //src: this.defaultUrl
    },
    // private
    initComponent : function(){
        Ext.ux.form.SimpleImageField.superclass.initComponent.call(this);
        this.addEvents(
            /**
             * @event focus
             * Fires when this field receives input focus.
             * @param {Ext.form.Field} this
             */
                'focus',
            /**
             * @event blur
             * Fires when this field loses input focus.
             * @param {Ext.form.Field} this
             */
                'blur',
            /**
             * @event specialkey
             * Fires when any key related to navigation (arrows, tab, enter, esc, etc.) is pressed.
             * To handle other keys see {@link Ext.Panel#keys} or {@link Ext.KeyMap}.
             * You can check {@link Ext.EventObject#getKey} to determine which key was pressed.
             * For example: <pre><code>
             var form = new Ext.form.FormPanel({
             ...
             items: [{
             fieldLabel: 'Field 1',
             name: 'field1',
             allowBlank: false
             },{
             fieldLabel: 'Field 2',
             name: 'field2',
             listeners: {
             specialkey: function(field, e){
             // e.HOME, e.END, e.PAGE_UP, e.PAGE_DOWN,
             // e.TAB, e.ESC, arrow keys: e.LEFT, e.RIGHT, e.UP, e.DOWN
             if (e.{@link Ext.EventObject#getKey getKey()} == e.ENTER) {
             var form = field.ownerCt.getForm();
             form.submit();
             }
             }
             }
             }
             ],
             ...
             });
             * </code></pre>
             * @param {Ext.form.Field} this
             * @param {Ext.EventObject} e The event object
             */
                'specialkey',
            /**
             * @event change
             * Fires just before the field blurs if the field value has changed.
             * @param {Ext.form.Field} this
             * @param {Mixed} newValue The new value
             * @param {Mixed} oldValue The original value
             */
                'change',
            /**
             * @event invalid
             * Fires after the field has been marked as invalid.
             * @param {Ext.form.Field} this
             * @param {String} msg The validation message
             */
                'invalid',

                'mouseup',
            /**
             * @event valid
             * Fires after the field has been validated with no errors.
             * @param {Ext.form.Field} this
             */
                'valid'
                );
    },
    /**
     * Returns the {@link Ext.form.Field#name name} or {@link Ext.form.ComboBox#hiddenName hiddenName}
     * attribute of the field if available.
     * @return {String} name The field {@link Ext.form.Field#name name} or {@link Ext.form.ComboBox#hiddenName hiddenName}
     */
    getName : function(){
        return this.rendered && this.el.dom.name ? this.el.dom.name : this.name || this.id || '';
    },

    // private
    onRender : function(ct, position){
        if(!this.el){
            var cfg = this.getAutoCreate();

            if(!cfg.name){
                cfg.name = this.name || this.id;
            }
            if(this.align){
                cfg.align = this.align;
            }

            if (this.width){
                var ww = this.width;
                cfg.width=ww<this.minWidth?this.minWidth:(ww>this.maxWidth?this.maxWidth:ww);
            }else{
                cfg.width=this.minWidth;
            }
            if (this.height){
                cfg.height=this.height;
            }else{
                var hh = this.minHeight;
                cfg.height=hh<this.minHeight?this.minHeight:(hh>this.maxHeight?this.maxHeight:hh);
            }
            this.autoEl = cfg;
        }

        Ext.ux.form.SimpleImageField.superclass.onRender.call(this, ct, position);

        this.el.addClass('x-form-field');

        if(this.readOnly){
            this.el.dom.readOnly = true;
        }
        if(this.tabIndex !== undefined){
            this.el.dom.setAttribute('tabIndex', this.tabIndex);
        }

        this.el.addClass([this.fieldClass, this.cls]);
    },

    // private
    getItemCt : function(){
        return this.itemCt;
    },
    getWidth:function(){
        return this.el.getWidth();
    },
    setWidth:function(w){
        Ext.ux.form.SimpleImageField.superclass.setWidth.call(this, w);
        this.el.setWidth(w<this.minWidth?this.minWidth:(w>this.maxWidth?this.maxWidth:w));
    },
    getHeight:function(){
        return this.el.getHeight();
    },
    setHeight:function(h){
        Ext.ux.form.SimpleImageField.superclass.setHeight.call(this, h);
        this.el.setHeight(h<this.minHeight?this.minHeight:(h>this.maxHeight?this.maxHeight:h));
    },
    // private
    initValue : function(){
        if(this.value !== undefined){
            this.setValue(this.value);
        }else {
            this.setValue(this.defaultValue);
        }
        /**
         * The original value of the field as configured in the {@link #value} configuration, or
         * as loaded by the last form load operation if the form's {@link Ext.form.BasicForm#trackResetOnLoad trackResetOnLoad}
         * setting is <code>true</code>.
         * @type mixed
         * @property originalValue
         */
        this.originalValue = this.getValue();
    },
    /**
     * <p>Returns true if the value of this Field has been changed from its original value.
     * Will return false if the field is disabled or has not been rendered yet.</p>
     * <p>Note that if the owning {@link Ext.form.BasicForm form} was configured with
     * {@link Ext.form.BasicForm}.{@link Ext.form.BasicForm#trackResetOnLoad trackResetOnLoad}
     * then the <i>original value</i> is updated when the values are loaded by
     * {@link Ext.form.BasicForm}.{@link Ext.form.BasicForm#setValues setValues}.</p>
     * @return {Boolean} True if this field has been changed from its original value (and
     * is not disabled), false otherwise.
     */
    isDirty : function() {
        if(this.disabled || !this.rendered) {
            return false;
        }
        return String(this.getValue()) !== String(this.originalValue);
    },
    // private
    afterRender : function(){
        Ext.ux.form.SimpleImageField.superclass.afterRender.call(this);
        this.initEvents();
        this.initValue();
    },
    // private
    fireKey : function(e){
        if(e.isSpecialKey()){
            this.onSpecialKey(e);

            this.fireEvent('specialkey', this, e);
        }
    },
    /**
     * Resets the current field value to the originally loaded value and clears any validation messages.
     * See {@link Ext.form.BasicForm}.{@link Ext.form.BasicForm#trackResetOnLoad trackResetOnLoad}
     */
    reset : function(){
        this.setValue(this.originalValue);
        this.clearInvalid();
    },
    /**
     * Clear any invalid styles/messages for this field
     */
    clearInvalid : function(){
        /*
                if(!this.rendered || this.preventMark){ // not rendered
                    return;
                }
                this.el.removeClass(this.invalidClass);
                var mt = this.getMessageHandler();
                if(mt){
                    mt.clear(this);
                }else if(this.msgTarget){
                    this.el.removeClass(this.invalidClass);
                    var t = Ext.getDom(this.msgTarget);
                    if(t){
                        t.innerHTML = '';
                        t.style.display = 'none';
                    }
                }
                this.fireEvent('valid', this);
        */
    },
    // private
    initEvents : function(){
        this.mon(this.el, Ext.EventManager.useKeydown ? 'keydown' : 'keypress', this.fireKey,  this);
        this.mon(this.el, 'focus', this.onFocus, this);
        this.mon(this.el, 'mouseup', this.onClick, this);

        // standardise buffer across all browsers + OS-es for consistent event order.
        // (the 10ms buffer for Editors fixes a weird FF/Win editor issue when changing OS window focus)
        this.mon(this.el, 'blur', this.onBlur, this);
    },
    /**
     * Returns the raw data value which may or may not be a valid, defined value.  To return a normalized value see {@link #getValue}.
     * @return {Mixed} value The field value
     */
    getRawValue : function(){
        var v = this.rendered ? this.srcToValue(this.el.dom.src) : Ext.value(this.value, '');
        if(v === this.emptyText){
            v = '';
        }
        return v;
    },

    // private
    preFocus: Ext.emptyFn,

    // private
    onFocus : function(){
        this.preFocus();
        if(this.focusClass){
            this.el.addClass(this.focusClass);
        }
        if(!this.hasFocus){
            this.hasFocus = true;
            this.startValue = this.getValue();
            this.fireEvent('focus', this);
        }
    },
    //private
    onClick : Ext.emptyFn,
    // private
    beforeBlur : Ext.emptyFn,

    onSpecialKey:Ext.emptyFn,
    // private
    onBlur : function(){
        this.beforeBlur();
        if(this.focusClass){
            this.el.removeClass(this.focusClass);
        }
        this.hasFocus = false;
        if(this.validationEvent !== false && (this.validateOnBlur || this.validationEvent != 'blur')){
            this.validate();
        }
        var v = this.getValue();
        if(String(v) !== String(this.startValue)){
            this.fireEvent('change', this, v, this.startValue);
        }
        this.fireEvent('blur', this);
        this.postBlur();
    },

    // private
    postBlur : Ext.emptyFn,

    /**
     * Returns the normalized data value (undefined or emptyText will be returned as '').  To return the raw value see {@link #getRawValue}.
     * @return {Mixed} value The field value
     */
    getValue : function(){
        if(!this.rendered) {
            return this.value;
        }
        var v = this.srcToValue(this.el.dom.src);
        if(v === this.emptyText || v === undefined){
            v = '';
        }
        return v;
    },
    getSrc : function(){
        var v = this.el.dom.src;
        if(v === this.emptyText || v === undefined){
            v = '';
        }
        return v;
    },

    /**
     * Sets the underlying DOM field's value directly, bypassing validation.  To set the value with validation see {@link #setValue}.
     * @param {Mixed} value The value to set
     * @return {Mixed} value The field value that is set
     */
    setRawValue : function(v){
        return this.rendered ? (this.el.dom.src = this.valueToSrc(Ext.isEmpty(v) ? '' : v)) : '';
    },

    /**
     * Validates the field value
     * @return {Boolean} True if the value is valid, else false
     */
    validate : function(){
        if(this.disabled || this.validateValue(this.processValue(this.getRawValue()))){
            this.clearInvalid();
            return true;
        }
        return false;
    },
    /**
     * This method should only be overridden if necessary to prepare raw values
     * for validation (see {@link #validate} and {@link #isValid}).  This method
     * is expected to return the processed value for the field which will
     * be used for validation (see validateValue method).
     * @param {Mixed} value
     */
    processValue : function(value){
        return value;
    },

    /**
     * @private
     * Subclasses should provide the validation implementation by overriding this
     * @param {Mixed} value
     */
    validateValue : function(value){
        return true;
    },

    /**
     * Sets a data value into the field and validates it.  To set the value directly without validation see {@link #setRawValue}.
     * @param {Mixed} value The value to set
     * @return {Ext.form.Field} this
     */
    setValue : function(v){
        this.value = v;
        if(this.rendered){
            this.el.dom.src = this.valueToSrc(v);
            this.validate();
        }
        return this;
    },
    setSrc : function(vv){
        this.value = this.srcToValue(vv);
        if(this.rendered){
            this.el.dom.src = (Ext.isEmpty(vv) ? '' : vv);
            this.validate();
        }
        return this;
    },

    srcToValue:function(vv){
        return vv.substring(vv.lastIndexOf('/')+1,vv.lastIndexOf('.'));
    },
    valueToSrc:function(v){
        return this.defaultUrl+(Ext.isEmpty(v) ? '' : v)+'.jpg';
    }

});
Ext.reg('simpleimagefield', Ext.ux.form.SimpleImageField);


//ImageTriggerField
Ext.ux.form.ImageTriggerField = Ext.extend(Ext.ux.form.SimpleImageField,  {
    /**
     * @cfg {String} triggerClass
     * An additional CSS class used to style the trigger button.  The trigger will always get the
     * class <tt>'x-form-trigger'</tt> by default and <tt>triggerClass</tt> will be <b>appended</b> if specified.
     */
    /**
     * @cfg {Mixed} triggerConfig
     * <p>A {@link Ext.DomHelper DomHelper} config object specifying the structure of the
     * trigger element for this Field. (Optional).</p>
     * <p>Specify this when you need a customized element to act as the trigger button for a TriggerField.</p>
     * <p>Note that when using this option, it is the developer's responsibility to ensure correct sizing, positioning
     * and appearance of the trigger.  Defaults to:</p>
     * <pre><code>{tag: "img", src: Ext.BLANK_IMAGE_URL, cls: "x-form-trigger " + this.triggerClass}</code></pre>
     */
    /**
     * @cfg {String/Object} autoCreate <p>A {@link Ext.DomHelper DomHelper} element spec, or true for a default
     * element spec. Used to create the {@link Ext.Component#getEl Element} which will encapsulate this Component.
     * See <tt>{@link Ext.Component#autoEl autoEl}</tt> for details.  Defaults to:</p>
     * <pre><code>{tag: "input", type: "text", size: "16", autocomplete: "off"}</code></pre>
     */
    defaultAutoCreate : {
        tag: 'input',
        type: 'image',
        align:'bottom',
        width:90,
        height:90,
        alt:'Click to select image',
        title:'Click to select image'/*,
        value:this.defaultUrl,
        src: this.defaultUrl*/},
    /**
     * @cfg {Boolean} hideTrigger <tt>true</tt> to hide the trigger element and display only the base
     * text field (defaults to <tt>false</tt>)
     */
    hideTrigger:false,
    /**
     * @cfg {Boolean} editable <tt>false</tt> to prevent the user from typing text directly into the field,
     * the field will only respond to a click on the trigger to set the value. (defaults to <tt>true</tt>)
     */
    editable: true,
    /**
     * @cfg {String} wrapFocusClass The class added to the to the wrap of the trigger element. Defaults to
     * <tt>x-trigger-wrap-focus</tt>.
     */
    wrapFocusClass: 'x-trigger-wrap-focus',
    /**
     * @hide
     * @method autoSize
     */
    autoSize: Ext.emptyFn,
    // private
    monitorTab : true,
    // private
    deferHeight : true,
    // private
    mimicing : false,

    actionMode: 'wrap',

    defaultTriggerWidth: 17,

    // private
    onResize : function(w, h){
        Ext.ux.form.ImageTriggerField.superclass.onResize.call(this, w, h);
        var tw = this.getTriggerWidth();
        if(Ext.isNumber?Ext.isNumber(w):(w=Ext.num(w,0))){
            this.el.setWidth(w - tw);
        }
        this.wrap.setWidth(this.el.getWidth() + tw);
    },

    getTriggerWidth: function(){
        var tw = this.trigger.getWidth();
        if(!this.hideTrigger && tw === 0){
            tw = this.defaultTriggerWidth;
        }
        return tw;
    },

    // private
    alignErrorIcon : function(){
        if(this.wrap){
            this.errorIcon.alignTo(this.wrap, 'tl-tr', [2, 0]);
        }
    },

    // private
    onRender : function(ct, position){
        this.doc = Ext.isIE ? Ext.getBody() : Ext.getDoc();
        Ext.ux.form.ImageTriggerField.superclass.onRender.call(this, ct, position);

        this.wrap = this.el.wrap({cls: 'x-form-field-wrap x-form-field-trigger-wrap'});
        this.trigger = this.wrap.createChild(this.triggerConfig ||
                                             {tag: "img", src: Ext.BLANK_IMAGE_URL, cls: "x-form-trigger " + this.triggerClass});
        if(this.hideTrigger){
            this.trigger.setDisplayed(false);
        }
        this.initTrigger();
        if(!this.width){
            this.wrap.setWidth(this.el.getWidth()+this.trigger.getWidth());
        }
        if(!this.editable){
            this.editable = true;
            this.setEditable(false);
        }
        this.resizeEl = this.positionEl = this.wrap;
    },

    afterRender : function(){
        Ext.ux.form.ImageTriggerField.superclass.afterRender.call(this);
    },

    // private
    initTrigger : function(){
        this.mon(this.trigger, 'click', this.onTriggerClick, this, {preventDefault:true});
        this.trigger.addClassOnOver('x-form-trigger-over');
        this.trigger.addClassOnClick('x-form-trigger-click');
    },

    // private
    onDestroy : function(){
        Ext.destroy(this.trigger, this.wrap);
        if (this.mimicing){
            this.doc.un('mousedown', this.mimicBlur, this);
        }
        Ext.ux.form.ImageTriggerField.superclass.onDestroy.call(this);
    },

    // private
    onFocus : function(){
        Ext.ux.form.ImageTriggerField.superclass.onFocus.call(this);
        if(!this.mimicing){
            this.wrap.addClass(this.wrapFocusClass);
            this.mimicing = true;
            this.doc.on('mousedown', this.mimicBlur, this, {delay: 10});
            if(this.monitorTab){
                this.on('specialkey', this.checkTab, this);
            }
        }
    },

    // private
    checkTab : function(me, e){
        if(e.getKey() == e.TAB){
            this.triggerBlur();
        }
    },

    // private
    onBlur : Ext.emptyFn,

    // private
    mimicBlur : function(e){
        if(!this.isDestroyed && !this.wrap.contains(e.target) && this.validateBlur(e)){
            this.triggerBlur();
        }
    },

    // private
    triggerBlur : function(){
        this.mimicing = false;
        this.doc.un('mousedown', this.mimicBlur, this);
        if(this.monitorTab && this.el){
            this.un('specialkey', this.checkTab, this);
        }
        Ext.ux.form.ImageTriggerField.superclass.onBlur.call(this);
        if(this.wrap){
            this.wrap.removeClass(this.wrapFocusClass);
        }
    },

    beforeBlur : Ext.emptyFn,

    /**
     * Allow or prevent the user from directly editing the field text.  If false is passed,
     * the user will only be able to modify the field using the trigger.  This method
     * is the runtime equivalent of setting the 'editable' config option at config time.
     * @param {Boolean} value True to allow the user to directly edit the field text
     */
    setEditable : function(value){
        if(value == this.editable){
            return;
        }
        this.editable = value;
        if(!value){
            this.el.addClass('x-trigger-noedit').on('click', this.onTriggerClick, this).dom.setAttribute('readOnly', true);
        }else{
            this.el.removeClass('x-trigger-noedit').un('click', this.onTriggerClick,  this).dom.removeAttribute('readOnly');
        }
    },

    // private
    // This should be overriden by any subclass that needs to check whether or not the field can be blurred.
    validateBlur : function(e){
        return true;
    },

    /**
     * The function that should handle the trigger's click event.  This method does nothing by default
     * until overridden by an implementing function.  See Ext.form.ComboBox and Ext.form.DateField for
     * sample implementations.
     * @method
     * @param {EventObject} e
     */
    onTriggerClick : Ext.emptyFn

    /**
     * @cfg {Boolean} grow @hide
     */
    /**
     * @cfg {Number} growMin @hide
     */
    /**
     * @cfg {Number} growMax @hide
     **/
});
Ext.reg('imagetrigger', Ext.ux.form.ImageTriggerField);


////////////////////////////////////////////////////////////
Ext.ux.form.ImagePickerField = Ext.extend(Ext.ux.form.ImageTriggerField,  {

    initComponent : function(){
        Ext.ux.form.ImagePickerField.superclass.initComponent.call(this);
        this.menu = new Ext.ux.menu.ImageMenu({
            viewWidth:this.viewWidth,
            viewHeight:this.viewHeight,
            url:this.url,
            defaultUrl:this.defaultUrl,
            listeners : {
                select: function(m, c){
                    this.focus(this);
                    this.setSrc.defer(100, this, [c]);
                }
                ,scope : this
            }
        });
    }
    ,setValue : function(v){
        Ext.ux.form.ImagePickerField.superclass.setValue.apply(this, arguments);
    }

    ,onDestroy : function(){
    if(this.menu) {
        this.menu.destroy();
    }
    if(this.wrap){
        this.wrap.remove();
    }
    Ext.ux.form.ImagePickerField.superclass.onDestroy.call(this);
    }
    ,onBlur : function(){
    Ext.ux.form.ImagePickerField.superclass.onBlur.call(this);
    }
    ,onTriggerClick : function(e){
    if(this.disabled){
        return;
    }
    this.menu.show(this.el, "tl-bl?");
    this.menu.picker.setUrl(this.getSrc());//select active image
    },
    onClick:function(e){
        if(this.disabled){
            return;
        }

        this.menu.show(this.el, "tl-bl?");
        this.menu.picker.setUrl(this.getSrc());//select active image
    },
    onSpecialKey:function(e){
        if (e.getKey() == e.ENTER) {
            this.onClick();
        }
    },
    vtype:'image'

});
Ext.reg("imagepickerfield", Ext.ux.form.ImagePickerField);

//////////////////////////////////////////////////////
Ext.ux.ImagePicker = Ext.extend( Ext.Container, {
    defaultUrl:null,
    /**
     *
     */
    initComponent: function() {
        Ext.ux.ImagePicker.superclass.initComponent.apply( this, arguments );
        this.addEvents('select');
        this.addEvents('hideMenu');
        this.lookup = {};
        this.initTemplates();
        this.additionalInit();
    },
    /**
     *
     */
    onRender: function() {
        Ext.ux.ImagePicker.superclass.onRender.apply( this, arguments );
		// check if container, self-container or renderTo exists
        this.body = this.body || ( this.container || ( this.renderTo || Ext.DomHelper.append( Ext.getBody(), {}, true ) ) );
        if( !this.el ) {
            this.el = this.body;
            if( this.cls ) { Ext.get( this.el ).addClass( this.cls ); }
        }
		// render this component
        this.renderComponent();
    },

    additionalInit :function(){
        this.store = new Ext.data.JsonStore({
            url: this.url,
            callbackName:'cbFn',
            root: 'images',
            autoDestroy:true,
            proxy: new Ext.data.HttpProxy({
                callbackName:'cbFn',
                method: 'GET',
                prettyUrls: false,
                url: this.url
            }),
            fields: [
                'name', 'url', 'value',
                {name:'size', type: 'float'},
                {name:'lastmod', type:'date', dateFormat:'timestamp'}
            ],
            listeners: {
                'load': {fn:function(){ this.selectActiveImage();this.showDetails() }, scope:this, single:true}
            }
        });
        this.cbFn=function(a,b,c){
        };
        this.showDetails = function(){
            var selNode = this.view.getSelectedNodes();
            var detailEl = Ext.getCmp('img-detail-panel').body;
            if(selNode && selNode.length > 0){
                selNode = selNode[0];
                Ext.getCmp('ok-btn').enable();
                var data = this.lookup[selNode.id];
                detailEl.hide();
                this.detailsTemplate.overwrite(detailEl, data);
                detailEl.slideIn('l', {stopFx:true,duration:.2});
            }else{
                Ext.getCmp('ok-btn').disable();
                detailEl.update('');
            }
        };

        this.doCallback = function(){
            var selNode = this.view.getSelectedNodes()[0];
            var lookup = this.lookup;
            if(selNode){
                this.fireEvent('select', this, this.defaultUrl+'/'+lookup[selNode.id].url);
            }
        };

        this.closeMenu = function(){
            this.fireEvent('hideMenu', this);
        };

        this.onLoadException = function(v,o){
            this.view.getEl().update('<div style="padding:10px;">Error loading images.</div>');
        };

        this.formatSize = function(data){
            if(data.size < 1024) {
                return data.size + " bytes";
            } else {
                return (Math.round(((data.size*10) / 1024))/10) + " KB";
            }
        };

        this.formatData = function(data){
            data.shortName = data.name.ellipse(15);
            data.sizeString = this.formatSize(data);
            data.dateString = new Date(data.lastmod).format("m/d/Y g:i a");
            this.lookup[data.name] = data;
            return data;
        };

        this.view = new Ext.DataView({
            tpl: this.thumbTemplate,
            layout:'fit',
            singleSelect: true,
            overClass:'x-view-over',
            itemSelector: 'div.thumb-wrap',
            emptyText : '<div style="padding:10px;">No images match the specified filter</div>',
            store: this.store,
            listeners: {
                'selectionchange': {fn:this.showDetails, scope:this, buffer:100},
                'dblclick'       : {fn:this.doCallback, scope:this},
                'loadexception'  : {fn:this.onLoadException, scope:this},
                'beforeselect'   : {fn:function(view){
                    return view.store.getRange().length > 0;
                }}
            },
            prepareData: this.formatData.createDelegate(this)
        });

    },


    initTemplates : function(){
        this.thumbTemplate = new Ext.XTemplate(
                '<tpl for=".">',
                '<div class="thumb-wrap" id="{name}">',
                '<div class="thumb"><img src="' + this.defaultUrl + '{url}" title="{name}"></div>',
                '<span>{shortName}</span></div>',
                '</tpl>'
                );
        this.thumbTemplate.compile();

        this.detailsTemplate = new Ext.XTemplate(
                '<div class="details">',
                '<tpl for=".">',
                '<img src="' + this.defaultUrl + '{url}"><div class="details-info">',
                '<b>Toolset Name:</b>',
                '<span>{name}</span>',
                '</tpl>',
                '</div>'
                );
        this.detailsTemplate.compile();
    },

    /**
     *
     */
    renderComponent: function() {

        this.store.load();

        var chooserViewPanel = new Ext.Panel({
            id: 'img-chooser-view',
            xtype:'panel',
            layout:'fit',
            region: 'center',
            autoScroll: true,
            items: this.view
        });
        var detailPanel = new Ext.Panel({
            id: 'img-detail-panel',
            xtype:'panel',
            layout:'fit',
            region: 'east',
            split: true,
            width: 150,
            minWidth: 150,
            maxWidth: 250
        });
        var cfg = {
            renderTo: this.getEl(),
            xtype:'panel',
            title: 'Select a toolset',
            id: 'img-chooser-dlg',
            layout:'border',
            width:this.viewWidth?this.viewWidth:500,
            height:this.viewHeight?this.viewHeight:400,
            minWidth: 300,
            minHeight: 200,
            //modal: true,
            closeAction: 'hide',
            border: true,
            items:[chooserViewPanel,detailPanel],
            buttons: [{
                id: 'ok-btn',
                text: 'OK',
                handler: this.doCallback,
                scope: this
            },{
                text: 'Cancel',
                handler: this.closeMenu/*function(){ this.hide(); }*/,
                scope: this
            }],
            keys: {
                key: 27, // Esc key
                handler: this.closeMenu,
                scope: this
            }
        };
        Ext.apply(cfg, this.config);
        this.mainPanel = new Ext.Panel(cfg);
        this.add(this.mainPanel);
    },
    /**
     *
     */
    cpGetId: function( postfix ) {
        return this.getId() + '__' + ( postfix || 'cp' );
    },

    /**
     *
     */
    findNodeByUrl:function(url){
        if (this.lookup){
            var i,c=0;
            for (var o in this.lookup){
                var obj = this.lookup[o];
                if (url.lastIndexOf(obj.url)==url.length-obj.url.length){
                    i=c;
                    break;
                };
                c++;
            }
            return i;
        }
    },
    selectActiveImage:function(){
        if (this.value){
            var nodeToSelect = this.findNodeByUrl(this.value);
            this.view.select(nodeToSelect);
        }else{
            this.view.select(0);
        }
    },
    /**
     *
     */
    setUrl: function(c) {
        this.value=c;
        this.selectActiveImage();
        this.showDetails();
    }

});

////////////////////////////////////////////////////////////
Ext.namespace("Ext.ux.menu");
if (Ext.menu.Menu.superclass.initComponent){

    Ext.ux.menu.ImageMenu = Ext.extend(Ext.menu.Menu, {
        enableScrolling : false,
        hideOnClick     : true,
        initComponent : function(){
            Ext.apply(this, {
                plain         : true,
                showSeparator : false,
                items: this.picker = new Ext.ux.ImagePicker(Ext.apply({
                    url:this.url,
                    defaultUrl:this.defaultUrl,
                    style: 'width:'+(this.viewWidth?this.viewWidth:500)+'px;height:'
                            +(this.viewHeight?this.viewHeight:400)+'px'
                }, this.initialConfig))
            });
            Ext.ux.menu.ImageMenu.superclass.initComponent.call(this);
            this.relayEvents(this.picker, ['select','hideMenu']);
            this.on('select', this.menuHide, this);
            this.on('hideMenu', this.menuHide, this);
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
        Ext.ux.menu.ImageItem = function(config){
        if(!config)config={};
        config.style='width:'+(config.viewWidth?config.viewWidth:500)+'px;height:'
                            +(config.viewHeight?config.viewHeight:400)+'px';
        Ext.ux.menu.ImageItem.superclass.constructor.call(this, new Ext.ux.ImagePicker(config), config);
        this.picker = this.component;
        this.addEvents('select');
        this.picker.on("render", function(picker){
            picker.getEl().swallowEvent("click");
        });
        this.picker.on("select", this.onSelect, this);
    };
    Ext.extend(Ext.ux.menu.ImageItem, Ext.menu.Adapter, {
        // private
        onSelect : function(picker, color){
            this.fireEvent("select", this, color, picker);
            Ext.ux.menu.ImageItem.superclass.handleClick.call(this);
        }
    });

    Ext.ux.menu.ImageMenu = function(config){
        Ext.ux.menu.ImageMenu.superclass.constructor.call(this, config);
        this.plain = true;
        var ci = new Ext.ux.menu.ImageItem(config);
        this.add(ci);
        this.picker = ci.picker;
        this.relayEvents(ci, ['select','hideMenu']);
    };
    Ext.extend(Ext.ux.menu.ImageMenu, Ext.menu.Menu, {
        beforeDestroy : function() {
            this.picker.destroy();
        }
    });
}

String.prototype.ellipse = function(maxLength){
    if(this.length > maxLength){
        return this.substr(0, maxLength-3) + '...';
    }
    return this;
};

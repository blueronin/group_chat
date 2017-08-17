var MensajesView = Backbone.View.extend({
    
    initialize: function(options){
        if (!(options && options.model))
            throw new Error("model is not specified");

        this.bus = options.bus;
        this.bus.on("mensajeAdded", this.onMensajeAdded, this);

        this.model.on("add", this.onAddMensaje, this);  
    },

    events: {
        "click #logout": "onLogout"
    },

    onLogout: function(){
        logout();
    },

    onAddMensaje: function(mensaje){
        var mensajeView = new MensajeView({ model: mensaje });                
        self.$("#mensajes").append(mensajeView.render().$el);
        self.$('.chat-container').animate({ scrollTop: self.$('.chat-container').get(0).scrollHeight }, 2000);
    },

    onMensajeAdded: function(mensaje){
        this.model.add(mensaje);
        usuario.mensajes++;
    },

    render: function () {
        var self = this;

        var template = $("#MensajesTemplate").html();
        var html = Mustache.render(template, this.model.toJSON());
        this.$el.html(html);

        this.model.forEach(function (mensaje) {
            var mensajeView = new MensajeView({ model: mensaje });            
            self.$("#mensajes").append(mensajeView.render().$el);            
        });        

        return this;
    }

});
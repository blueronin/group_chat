var MensajeView = Backbone.View.extend({
    
    className: "col-10 chat-comment clear",

    initialize: function(options){
        if (!(options && options.model))
            throw new Error("model is not specified");        
    },

    render: function () {
                
        if (this.model.get("Descripcion").indexOf(".gif") >= 0) this.model.set("esImagen", true);

        var template = $("#MensajeTemplate").html();
        var html = Mustache.render(template, this.model.toJSON());
        this.$el.html(html);

        if (this.model.get("Nickname") == usuario.Nickname) this.$el.addClass("chat-comment-personal");
        else this.$el.addClass("chat-comment-another");        

        return this;        
    }

});
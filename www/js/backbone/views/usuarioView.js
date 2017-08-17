var UsuarioView = Backbone.View.extend({

    tagName: "option",

    initialize: function(options){
        if(!(options && options.model))
            throw new Error("model is not specified");
    },

    render: function () {

        this.$el.html(this.model.get("Nickname"));
        return this;

    }

});
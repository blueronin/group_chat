var NuevoMensajeView = Backbone.View.extend({

    initialize: function (options) {
        this.bus = options.bus;
    },

    events: {
        "click #add": "onMensajeAdd",
        "keypress #newMensaje": "onKeyPress",
        "click #search": "onSearch"
    },

    onSearch: function () {        
        $("#btnModalGiphy").click();
    },

    onKeyPress: function (e) {
        if (e.keyCode == 13)
            this.onMensajeAdd();
    },

    onMensajeAdd: function () {
        var $newMensaje = this.$("#newMensaje");
        if ($newMensaje.val()) {
            var mensaje = new Mensaje({ IdUsuario: usuario.IdUsuario, Descripcion: $newMensaje.val(), Nickname: usuario.Nickname });
            mensaje.save();            
            $newMensaje.val("");
            this.bus.trigger("mensajeAdded", mensaje);
        }
    },

    render: function () {
        var template = $("#NuevoMensajeTemplate").html();
        var html = Mustache.render(template, this);
        this.$el.html(html);

        return this;
    }

});
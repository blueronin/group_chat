var UsuariosView = Backbone.View.extend({
    
    initialize: function (options) {
        if (!(options && options.model))
            throw new Error("model is not specified");
    },

    events: {
        "click #btnIngresar": "onLogin",
        "keypress": "onKeyPress"
    },

    onKeyPress: function (e) {
        if (e.keyCode == 13)
            this.onLogin();
    },

    onLogin: function () {
        var usuario = new Usuario();
        usuario.fetch({
            data: { Nickname: this.$("#selectUsuarios").val() },
            success: function (results) {

                if (results.toJSON().IdUsuario > 0) {
                    usuario.set("IdUsuario", results.toJSON().IdUsuario);
                    usuario.set("Nickname", results.toJSON().Nickname);

                    this.$(".alert-login").addClass("alert-success");
                    this.$(".alert-login").html("¡Bienvenido! ...");

                    login(usuario.get("Nickname"), usuario.get("IdUsuario"));

                    setTimeout(function () {
                        var router = new ChatRouter();
                        router.navigate("chat", { trigger: true });
                    }, 1500);

                }
                else {
                    this.$(".alert-login").addClass("alert-danger");
                    this.$(".alert-login").html("El usuario ya tiene una sesión iniciada.");
                }
            }
        });        
    },

    render: function () {

        var template = $("#LoginTemplate").html();
        var html = Mustache.render(template, this);
        this.$el.html(html);

        var self = this;
        this.model.forEach(function (usuario_model) {
            var usuarioView = new UsuarioView({ model: usuario_model });
            self.$("#selectUsuarios").append(usuarioView.render().$el);
        });

        return this;

    }

});
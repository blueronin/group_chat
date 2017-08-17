var ChatRouter = Backbone.Router.extend({
    routes: {
        "login": "loginView",
        "chat": "mensajesView",
        "*other": "loginView"
    },

    loginView: function () {
        var usuarios = new Usuarios();
        usuarios.fetch({
            success: function () {                
                var usuariosView = new UsuariosView({ model: usuarios });
                $("#container").html(usuariosView.render().$el);
                $("#subcontainer").html("");
            }
        });
    },

    mensajesView: function () {        
        
        if(usuario.IdUsuario == null){
            logout();
            return;
        }

        var mensajes = new Mensajes();
        
        var bus = _.extend({}, Backbone.Events);        

        var giphys = new Giphys();
        var giphysView = new GiphysView({ model: giphys, bus: bus });
        $("#modalBody").html(giphysView.render().$el);

        mensajes.fetch({
            success: function () {
                usuario.mensajes = mensajes.length;                
                var mensajesView = new MensajesView({ model: mensajes, bus: bus });
                $("#container").html(mensajesView.render().$el);
                $('.chat-container').animate({ scrollTop: $('.chat-container').get(0).scrollHeight }, 2000);                
                var nuevoMensajeView = new NuevoMensajeView({ bus: bus });
                $("#subcontainer").html(nuevoMensajeView.render().$el);

                setTimeout(function () { actualizarChat(); }, 3000);
            }
        });  

        actualizarChat = function () {
            
            var mensajesDos = new Mensajes();
            mensajesDos.fetch({ 
                success: function () {                    
                    if (mensajesDos.length > usuario.mensajes) {                        
                        //hay nuevos mensajes
                        for (var i = usuario.mensajes; i < mensajesDos.length; i++) {  
                            var mensaje = mensajesDos.at(i);
                            var mensajeView = new MensajeView({ model: mensaje });                                                      
                            $("#mensajes").append(mensajeView.render().$el);
                            $('.chat-container').animate({ scrollTop: $('.chat-container').get(0).scrollHeight }, 0);
                            usuario.mensajes++;
                        }
                    }
                }
            });
            
            setTimeout(function () { actualizarChat(); }, 5000);
        }

    }

});


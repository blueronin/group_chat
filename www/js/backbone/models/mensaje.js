var Mensaje = Backbone.Model.extend({
    urlRoot: "http://safhl817.eastus.cloudapp.azure.com:81/webapi/Mensaje",

    defaults: {
        IdUsuario: null,
        esImagen: false
    }

});
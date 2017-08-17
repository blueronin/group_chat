var Mensajes = Backbone.Collection.extend({
    model: Mensaje,
    url: "http://safhl817.eastus.cloudapp.azure.com:81/webapi/Mensajes",
});
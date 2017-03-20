var Usuarios = Backbone.Collection.extend({
    model: Usuario,
    url: "http://safhl817.eastus.cloudapp.azure.com:81/webapi/Usuarios"
});
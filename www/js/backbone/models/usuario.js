var Usuario = Backbone.Model.extend({
    
    idAttibute: "IdUsuario",

    urlRoot: "http://safhl817.eastus.cloudapp.azure.com:81/webapi/Usuario",

    defaults: {
        IdUsuario: null,
        Nickname: null        
    }

});
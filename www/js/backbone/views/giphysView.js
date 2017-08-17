var GiphysView = Backbone.View.extend({

    model: Giphys,

    initialize: function (options) {
        if (!(options && options.model && options.bus))
            throw new Error("model is not specified in collection");

        this.bus = options.bus;
        this.bus.on("giphySelected", this.onGiphySelected, this);        
    },

    onGiphySelected: function (giphy) {
        var self = this;
        if (giphy.get("seleccionada")) {
            //Se ponen todas en false
            this.model.forEach(function (giphy2) {
                if (giphy2.get("id") != giphy.get("id")) giphy2.set("seleccionada", false);
            });
            this.$el.find(".giphy-seleccionada").removeClass("giphy-seleccionada");
            this.$el.find("#" + giphy.get("id")).addClass("giphy-seleccionada");
        }                
    },

    events: {
        "click #searchButton": "onSearch",
        "keypress #searchInput": "onKeyPress",
        "click #searchEnviar": "onSearchEnviar"
    },

    onKeyPress: function (e) {
        if (e.keyCode == 13)
            this.onSearch();
    },

    onSearch: function(){        
        var $search = this.$("#searchInput");
        if ($search.val()) {            
            var self = this;
            this.model.fetch({ data: { busqueda: $search.val() }, success: function () { $("#modalBody").append(self.render().$el); } });    
        } 
    },

    onSearchEnviar: function(){
        
        //Busco por la seleccionada        
        var giphySeleccionado = this.model.findWhere({ seleccionada: true });        
        if (giphySeleccionado) {            
            var mensaje = new Mensaje({
                IdUsuario: usuario.IdUsuario,
                Descripcion: giphySeleccionado.get("images").fixed_height.url,
                Nickname: usuario.Nickname
            });
            mensaje.save();            
            this.bus.trigger("mensajeAdded", mensaje);
            $("#modalGiphy .close").click();
        }       

    },

    render: function() {
        var self = this;

        var template = $("#GiphysTemplate").html();
        var html = Mustache.render(template, this);
        this.$el.html(html);

        this.model.forEach(function (giphy) {
            var giphyView = new GiphyView({ model: giphy, bus: self.bus });            
            self.$("#giphyImages").append(giphyView.render().$el);
        });

        return this;
    }

});
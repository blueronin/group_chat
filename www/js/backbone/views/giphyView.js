var GiphyView = Backbone.View.extend({    

    className: "col-4 giphy-image",

    initialize: function (options) {
        if (!(options && options.model && options.bus))
            throw new Error("model is not specified");

        this.bus = options.bus;        
    },

    events: {
        "click": "onClick"
    },

    onClick: function () {                
        if (this.model.get("seleccionada")) {
            this.$el.removeClass("giphy-seleccionada");
            this.model.set("seleccionada", false);
        }
        else {
            this.$el.addClass("giphy-seleccionada");
            this.model.set("seleccionada", true);
        }
        this.bus.trigger("giphySelected", this.model);
    },

    render: function () {

        var template = $("#GiphyTemplate").html();
        var html = Mustache.render(template, this.model.toJSON());
        this.$el.html(html);
        this.$el.attr("id", this.model.get("id"));

        return this;

    }

});
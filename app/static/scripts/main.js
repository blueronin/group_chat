/*global require*/
'use strict';

require.config({
  shim: {
    socketio: {
      exports: 'io'
    },
    bootstrap: {
      deps: ['jquery'],
      exports: 'jquery'
    },
      handlebars: {
      exports: 'Handlebars'
    },
    moment: {
      exports: 'moment'
    },
    backboneLocalstorage: {
      deps: ['backbone'],
      exports: 'Store'
    }
  },
  paths: {
    jquery: '../bower_components/jquery/dist/jquery',
    backbone: '../bower_components/backbone/backbone',
    underscore: '../bower_components/lodash/dist/lodash',
    bootstrap: '../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap',
    handlebars: '../bower_components/handlebars/handlebars',
    socketio: '../bower_components/socket.io-client/dist/socket.io',
    moment: '../bower_components/moment/min/moment.min',
    backboneLocalstorage: '../bower_components/backbone.localStorage/backbone.localStorage-min'
  }
});

require([
  'backbone',
  'routes/app'
], function (Backbone, AppRouter) {
  new AppRouter();
  Backbone.history.start();
});

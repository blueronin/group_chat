/*global define*/

define([
  'jquery',
  'backbone',
  'views/login',
  'views/chat'
], function ($, Backbone, LoginView, ChatView) {
  'use strict';

  var AppRouter = Backbone.Router.extend({

    routes: {
      '' : 'login',
      'chat': 'chat',
      '*default': 'login'
    },

    initialize: function(){

      var loginView = new LoginView;
      var chatView = new ChatView;

      this.on('route:login', function(){
        loginView.render();
      });

      this.on('route:chat', function(){
        chatView.initialize();
      });

    }

  });

  return AppRouter;
});

/*global define*/

define([
  'jquery',
  'underscore',
  'backbone',
  'templates'
], function ($, _, Backbone, JST) {
  'use strict';

  var LoginView = Backbone.View.extend({
    template: JST['app/scripts/templates/login.hbs'],

    el: '#app',

    tagName: 'div',

    id: 'login-form',

    className: '',

    events: {
      'submit #login-form': 'onLogin'
    },

    serialize : function() {
      return {
        nickname: this.$("#nickname").val()
      };
    },

    initialize: function () {
      var nickname = localStorage.getItem('nickname');
      if(nickname !== null || nickname !== undefined){
        Backbone.history.navigate('/chat', true);
      }
    },

    render: function () {
      this.$el.html(this.template());
    },

    onLogin: function(e){
      e.preventDefault();
      var nickname = this.serialize().nickname;
      localStorage.setItem("nickname", nickname);
      Backbone.history.navigate('/chat', true);
    }

  });

  return LoginView;
});

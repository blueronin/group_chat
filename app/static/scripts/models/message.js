/*global define*/

define([
  'underscore',
  'backbone'
], function (_, Backbone) {
  'use strict';

  var MessageModel = Backbone.Model.extend({
    defaults: {
      msg: '',
      date: function () {
        new Date();
      }
    }
  });

  return MessageModel;
});

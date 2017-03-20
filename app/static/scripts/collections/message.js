/*global define*/

define([
  'underscore',
  'backbone',
  'backboneLocalstorage',
  'models/Message'
], function (_, Backbone, Store, MessageModel) {
  'use strict';

  var MessageCollection = Backbone.Collection.extend({
    model: MessageModel,
    localStorage: new Store('messages')
  });

  return MessageCollection;
});

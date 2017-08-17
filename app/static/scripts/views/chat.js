/*global define*/

define([
  'jquery',
  'underscore',
  'backbone',
  'socketio',
  'templates',
  'moment',
  'models/message',
  'collections/message'
], function ($, _, Backbone, io, JST, moment, MessageModel,MessageCollection) {
  'use strict';

  var ChatView = Backbone.View.extend({

    template: JST['app/scripts/templates/chat.hbs'],

    el: '#app',

    tagName: 'div',

    id: 'chat-group',

    className: '',

    events: {
      'submit #chat-form': 'onSend'
    },

    collection: new MessageCollection(),
    socket:{},
    nickname: null,
    endpointYoutube: 'https://www.googleapis.com/youtube/v3/search',
    endpointGiphy: 'http://api.giphy.com/v1/gifs/random',
    regexYoutube: /(?:\/youtube \w+\/\w+)/g,
    regexGiphy: /(?:\/giphy)/g,
    paramsYoutube : {
      part: 'snippet',
      key: 'AIzaSyChV_SHv1cecL1K6BzQW3hYesO63NOvXbE',
      video: 'video',
      maxResults: 1
    },
    paramsGiphy: {
      api_key: 'dc6zaTOxFJmzC'
    },

    serialize: function(){
      return {
        message: this.$('#message').val()
      }
    },

    initialize: function () {
      var self   = this;
      this.nickname = localStorage.getItem('nickname');

      if(this.nickname === null || this.nickname === undefined){
        Backbone.history.navigate('login', {trigger: true, replace: true});
        return false;
      }

      this.socket = io.connect('http://localhost:5000/chat');
      this.socket.on('connect', function() {
        self.socket.emit('joined', { 'nickname': self.nickname});
      });
      this.socket.on('status', function(data) {
        self.collection.create({
          msg: data.msg,
          date: moment.unix(data.time).format("YYYY-MM-DD HH:mm")
        });
      });
      this.socket.on('message', function(data) {
        self.collection.create({
          msg: data.msg,
          date: moment.unix(data.time).format("YYYY-MM-DD HH:mm")
        });
      });
      this.listenTo(this.collection, 'change', this.render);
    },

    render: function () {
      this.$el.html(this.template({collection: this.collection.toJSON()}));
    },

    onSend: function(e) {
      e.preventDefault();
      var self    = this;
      var message = this.serialize().message;

      if(this.regexYoutube.test(message)){
        var matchs  = message.match(this.regexYoutube);
        var i = 0;
        for(i; i < matchs.length; i++){
          this.paramsYoutube.q = matchs[i].replace('/youtube ', '');
          $.ajax({
            url: this.endpointYoutube,
            dataType: 'json',
            async: false,
            data: this.paramsYoutube
          })
          .done(function(data) {
            var embed = '<iframe id="ytplayer" type="text/html" width="640" height="360" src="http://www.youtube.com/embed/'+data.items[0].id.videoId+'" frameborder="0"/>';
            message = message.replace(matchs[i], embed);
          });
        }

        setTimeout(function () {
          self.socket.emit('text', {'nickname': self.nickname, 'msg': message});
          self.$el.find('#message').empty();
        }, 1000*matchs.length);

      }else if(this.regexGiphy.test(message)) {
        var matchs = message.match(this.regexGiphy);
        $.ajax({
          url: this.endpointGiphy,
          dataType: 'json',
          data: this.paramsGiphy
        })
        .done(function(response) {
          var gif = '<img width="'+response.data.image_width+'" height="'+response.data.image_height+'" src="'+response.data.image_url+'" />';
          message = message.replace(matchs[0], gif);
          self.socket.emit('text', {'nickname': self.nickname, 'msg': message});
          self.$el.find('#message').empty();
        });
      }else{
        this.socket.emit('text', {'nickname': self.nickname, 'msg': message});
        this.$el.find('#message').empty();
      }
    }
  });

  return ChatView;
});

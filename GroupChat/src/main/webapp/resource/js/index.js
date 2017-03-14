/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global app, Backbone */

var wsocket;
var serviceLocation = "ws://0.0.0.0:8080/GroupChat/chat/";
var $nickName;
var $message;
var $messageReceived;
var $chatWindow;
var room = '';

var app = {}; // create namespace for our app
//--------------
// Models
//--------------

//--------------
// Collections
//--------------
app.MessageList = Backbone.Collection.extend({
    localStorage: new Store("Messages")
});

// instance of the Collection
app.MessageList = new app.MessageList();

//--------------
// Views
//--------------

// renders individual todo items list (li)
app.MessageView = Backbone.View.extend({
    tagName: 'tr',
    template: _.template($('#message-template').html()),
    render: function () {
        this.$el.html(this.template(this.model.toJSON()));
        return this;
    }

});


// renders the full list of todo items calling TodoView for each one.


//--------------
// Initializers
//--------------   


function onMessageReceived(evt) {
    var msg = JSON.parse(evt.data); // native API
    app.MessageList.add(msg);
}

function onClose(event) {
    $('.chat-signin').show();
    $('.chat-wrapper').hide();
    alert(event.reason);
    console.log(event.reason);
}
function sendMessage() {
    var msg = '{"message":"' + $message.val() + '", "sender":"'
            + $nickName.val() + '", "received":""}';
    wsocket.send(msg);
    $message.val('').focus();
}

function connectToChatserver() {
    room = 'Sala de Chat de Pruebas';
    wsocket = new WebSocket(serviceLocation + room + '/' + $nickName.val());
    wsocket.onmessage = onMessageReceived;
    wsocket.onclose = onClose;
}

function leaveRoom() {
    wsocket.close();
    $chatWindow.empty();
    $('.chat-wrapper').hide();
    $('.chat-signin').show();
    $nickName.focus();
}

$(document).ready(function () {
    $nickName = $('#nickname');
    $message = $('#message');
    $chatWindow = $('#response');
    $('.chat-wrapper').hide();
    $nickName.focus();
});








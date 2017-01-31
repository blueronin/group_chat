
window.onload = init;
var HOST = "ws://obscure-bayou-60747.herokuapp.com";
console.log(HOST);
console.log(HOST+"/actions");
var socket = new WebSocket(HOST+"/actions");
socket.onmessage = onMessage;

function onMessage(event) {
    var message = JSON.parse(event.data);
    if (message.action === "addMessage") {
        showForm();
        printMessage(message);
    }
    
}


function addUser(name) {
    var UserAction = {
        action: "addUser",
        userName: name
    };
    socket.send(JSON.stringify(UserAction));
}


function printMessage(message) {
    var content = document.getElementById("chatListId");
   
                                       
    var liMessage = document.createElement("li");
    liMessage.setAttribute("class", "left clearfix ");
    content.appendChild(liMessage);

    var spanImage = document.createElement("span");
    spanImage.setAttribute("class", "chat-img pull-left");
    liMessage.appendChild(spanImage);
    var image = document.createElement("img");
    image.setAttribute("class", "img-circle");
    image.setAttribute("src", "http://placehold.it/50/55C1E7/fff&text=U");
    spanImage.appendChild(image);

    var divMessage = document.createElement("div");
    divMessage.setAttribute("class", "chat-body clearfix");
    liMessage.appendChild(divMessage);
    
    var divHeader = document.createElement("div");
    divHeader.setAttribute("class", "header");
    divMessage.appendChild(divHeader);
    
    var username = document.createElement("strong");
    username.setAttribute("class", "primary-font");
    username.innerHTML = message.username;
    divHeader.appendChild(username);
    
    var message = document.createElement("p");
    message.innerHTML = message.message;
    divMessage.appendChild(message);
    
}

function showForm() {
    document.getElementById("chatContainerId").style.display = '';
}

function hideForm() {
    document.getElementById("chatContainerId").style.display = "none";
}


function login() {
    var name =$("#username").val();
    $("#username").val('');
    addUser(name);
    
}

function init() {
    hideForm();
    //events
    $("#signInButton").click(function (){
    alert( "Handler for .click() called." );
    login();
})
}





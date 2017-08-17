var usuario = {};

usuario.logged = false;
usuario.nickname = null;
usuario.idUsuario = null;
usuario.scrollHeight = null;
usuario.mensajes = null;

function login(nickname, idUsuario) {    

    usuario.logged = true;
    usuario.Nickname = nickname;
    usuario.IdUsuario = idUsuario; 

}

function logout() {      

    usuario.logged = false;
    usuario.nickname = null;
    usuario.idUsuario = null;

    var router = new ChatRouter();
    router.navigate("login", { trigger: true });

}
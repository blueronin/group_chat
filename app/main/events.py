import time
from flask import session
from flask_socketio import emit, join_room, leave_room
from .. import socketio


@socketio.on('joined', namespace='/chat')
def joined(data):
    """Sent by clients when they enter a room.
    A status message is broadcast to all people in the room."""
    if 'nickname' in data:
        session['username'] = data['nickname']
        room = session['room'] = 'default'
        join_room(room)
        emit('status', {'msg': data['nickname'] + ' has entered the room.', 'time': time.time()}, room=room)
    else:
        return


@socketio.on('text', namespace='/chat')
def text(data):
    """Sent by a client when the user entered a new message.
    The message is sent to all people in the room."""
    if 'nickname' in data:
        session['username'] = data['nickname']
        room = session['room'] = 'default'
        emit('message', {'msg': data['nickname'] + ': ' + data['msg'], 'time': time.time()}, room=room)
    else:
        return


@socketio.on('left', namespace='/chat')
def left(data):
    """Sent by clients when they leave a room.
    A status message is broadcast to all people in the room."""
    if 'nickname' in data:
        session['username'] = data['nickname']
        room = session['room'] = 'default'
        leave_room(room)
        emit('status', {'msg': data['nickname'] + ' has left the room.', 'time': time.time()}, room=room)
    else:
        return


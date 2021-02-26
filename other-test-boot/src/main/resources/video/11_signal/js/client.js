'use strict'

//
var userName = document.querySelector('input#username');
var inputRoom = document.querySelector('input#room');
var btnConnect = document.querySelector('button#connect');
var btnLeave = document.querySelector('button#leave');
var outputArea = document.querySelector('textarea#output');
var inputArea = document.querySelector('textarea#input');
var btnSend = document.querySelector('button#send');

var socket;
var room;

btnConnect.onclick = () => {
    //connect
    socket = io.connect();

    //recieve message
    socket.on('joined', (room, id) => {
        btnConnect.disabled = true;
        btnLeave.disabled = false;
        inputArea.disabled = false;
        btnSend.disabled = false;
    });

    socket.on('leaved', (room, id) => {
        btnConnect.disabled = false;
        btnLeave.disabled = true;
        inputArea.disabled = true;
        btnSend.disabled = true;

        socket.disconnect();
    });

    socket.on('full', (room, id) => {
        outputArea.scrollTop = outputArea.scrollHeight;//窗口总是显示最后的内容
        outputArea.value = outputArea.value + 'Room is full' + '\r';

        btnConnect.disabled = false;
        btnLeave.disabled = true;
        inputArea.disabled = true;
        btnSend.disabled = true;

        socket.disconnect();
    });

    socket.on('message', (room, data) => {
        outputArea.scrollTop = outputArea.scrollHeight;//窗口总是显示最后的内容
        outputArea.value = outputArea.value + data + '\r';
    });

    socket.on('disconnect', (socket) => {
        btnConnect.disabled = false;
        btnLeave.disabled = true;
        inputArea.disabled = true;
        btnSend.disabled = true;
    });

    //send message
    room = inputRoom.value;
    socket.emit('join', room, userName.value);
};

btnSend.onclick = () => {
    sendMessage();
};

btnLeave.onclick = () => {
    room = inputRoom.value;
    socket.emit('leave', room, userName.value);
};

inputArea.onkeypress = (event) => {
    //event = event || window.event;
    if (event.keyCode === 13) { //回车发送消息
        sendMessage();
        event.preventDefault();//阻止默认行为
    }
};

function sendMessage() {
    var data = inputArea.value;
    data = userName.value + ':' + data;
    socket.emit('message', room, data);
    inputArea.value = '';
}
'use strict'

var log4js = require('log4js');
var http = require('http');
var https = require('https');
var fs = require('fs');
var socketIo = require('socket.io');

var express = require('express');
var serveIndex = require('serve-index');

var USERCOUNT = 3;

log4js.configure({
    appenders: {
        file: {
            type: 'file',
            filename: 'app.log',
            layout: {
                type: 'pattern',
                pattern: '%r %p - %m',
            }
        }
    },
    categories: {
        default: {
            appenders: ['file'],
            level: 'debug'
        }
    }
});

var logger = log4js.getLogger();

var app = express();
app.use(serveIndex('../'));
app.use(express.static('../'));


//http server
var http_server = http.createServer(app);


var options = {
    key: fs.readFileSync('./cert/server.key', 'utf8'),
    cert: fs.readFileSync('./cert/server-cert.pem', 'utf8')
}
//https server
var https_server = https.createServer(options, app);


https_server.listen(443, '0.0.0.0');
http_server.listen(80, '0.0.0.0');


// var io = socketIo.listen(https_server);
var io = socketIo.listen(http_server);
io.sockets.on('connection', (socket) => {

    socket.on('message', (room, data) => {
        logger.debug('message, room: ' + room + ", data, type:" + data.type);
        socket.in(room).emit('message', room, data);
    });

    /*
    socket.on('message', (room)=>{
        logger.debug('message, room: ' + room );
        socket.to(room).emit('message',room);
    });
    */

    socket.on('join', (room, username) => {
        socket.join(room);
        var myRoom = io.sockets.adapter.rooms[room];
        var users = (myRoom) ? Object.keys(myRoom.sockets).length : 0;
        logger.debug('the user number of room (' + room + ') is: ' + users);

        if (users <= USERCOUNT) {
            socket.emit('joined', room, socket.id); //发给除自己之外的房间内的所有人
            socket.in(room).emit('message', room, "welcome, "+ username);
        } else {
            socket.leave(room);
            socket.emit('full', room, socket.id);
        }
        //socket.emit('joined', room, socket.id); //发给自己
        //socket.broadcast.emit('joined', room, socket.id); //发给除自己之外的这个节点上的所有人
        //io.in(room).emit('joined', room, socket.id); //发给房间内的所有人
    });

    socket.on('leave', (room, username) => {

        socket.leave(room);

        var myRoom = io.sockets.adapter.rooms[room];
        var users = (myRoom) ? Object.keys(myRoom.sockets).length : 0;
        logger.debug('the user number of room is: ' + users);

        //socket.emit('leaved', room, socket.id);
        //socket.broadcast.emit('leaved', room, socket.id);
        socket.in(room).emit('message', room, 'bye, ' + username);
        socket.emit('leaved', room, socket.id);
        //io.in(room).emit('leaved', room, socket.id);
    });

});




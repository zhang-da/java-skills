'use strict'

//filter
var filtersSelect = document.querySelector('select#filter')

//picture
var snapshot = document.querySelector('button#snapshot')
var picture = document.querySelector('canvas#picture')
picture.width = 640
picture.height = 480

//获取HTML页面中的video标签
var videoplay = document.querySelector('video#player');

//播放视频流
function gotMediaStream(stream) {
    var videoTrack = stream.getVideoTracks()[0];

    window.stream = stream;
    videoplay.srcObject = stream;
}

function handleError(err) {
    console.log('getUserMedia error:', err);
}

function start() {
    if (!navigator.mediaDevices || !navigator.mediaDevices.getUserMedia) {
        console.log('getUserMedia is not supported!');
        return;
    } else {
        //对采集的数据做一些限制
        var constraints = {
            video: {
                width: 640,
                height: 480,
                frameRate: 15,
                facingMode: 'environment'
            },
            audio: false
        };

        //采集音视频数据流
        navigator.mediaDevices.getUserMedia(constraints)
            .then(gotMediaStream)
            .catch(handleError);
    }
}

filtersSelect.onchange = function () {
    videoplay.className = filtersSelect.value;
}

snapshot.onclick = function () {
    // picture.className = filtersSelect.value;
    // picture.getContext('2d').drawImage(videoplay, 0, 0, picture.width, picture.height)


    var ctx = picture.getContext('2d')
    console.log(ctx.filter)
    console.log(filtersSelect.value)
    if(filtersSelect.value === "sepia"){
        ctx.filter = "sepia(1)";
    }else if(filtersSelect.value === "grayscale"){
        ctx.filter = "grayscale(1)";
    }else if(filtersSelect.value === "blur"){
        ctx.filter = "blur(3px)";
    }else if(filtersSelect.value === "invert"){
        ctx.filter = "invert(1)";
    }else{
        ctx.filter = "none";
    }
    ctx.drawImage(videoplay, 0, 0, picture.width, picture.height);
};

document.querySelector('button#save').onclick = function () {
    downLoad(picture.toDataURL("image/jpeg"))
}

function downLoad(url){
    let oA = document.createElement("a");
    oA.download = 'photo';// 设置下载的文件名，默认是'下载'
    oA.href = url;
    document.body.appendChild(oA);
    oA.click();
    oA.remove(); // 下载之后把创建的元素删除
}

start()

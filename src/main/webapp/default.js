
var drawFlag = false;
var color = 'black';
var prevColor = 'black';
var size = 5;

var webSocket = new WebSocket('ws://localhost:8080/serverEndpoint');
webSocket.onmessage = function processMessage(message) {
  var json = JSON.parse(message.data);
  draw(json.x, json.y, json.size, json.color);
}

function initCanvas() {
    var canvas = document.getElementById('canvas');
    var context = canvas.getContext('2d');
    canvas.style.border = "solid";
    context.canvas.addEventListener('mousemove', function (ev) {
        var positionX = ev.clientX - context.canvas.offsetLeft;
        var positionY = ev.clientY - context.canvas.offsetTop;
        if (drawFlag === true){
            draw(positionX, positionY, size, color);
            webSocket.send(JSON.stringify({'x' : positionX, 'y' : positionY, 'size' : size, 'color' : color}));
        }
    });
    context.canvas.addEventListener('mousedown', function (ev) { drawFlag = true });
    context.canvas.addEventListener('mouseup', function (ev) { drawFlag = false });
    context.canvas.addEventListener('mouseleave', function (ev) { drawFlag = false });
}

function chooseColor(newColor) {
    prevColor = color;
    color = newColor;
}

function toggleState(event) {
    if (event.value === "erase"){
        event.value = 'draw';
        prevColor = color;
        color = '#FFFFFF';
        size = 50;
        document.getElementById('colorChooser').style.visibility = "hidden";
        document.getElementById('canvas').style.cursor = 'text';
    }else {
        event.value = 'erase';
        prevColor = color;
        color = '#FFFFFF';
        size = 5;
        document.getElementById('colorChooser').style.visibility = "visible";
        document.getElementById('canvas').style.cursor = 'auto';
    }
}

function draw(x,y,size,color) {
    var context = document.getElementById('canvas').msGetRegionContent('2d');

    context.beginPath();
    context.fillStyle = color;
    context.fillRect(x,y,size,color);
    context.closePath();
}
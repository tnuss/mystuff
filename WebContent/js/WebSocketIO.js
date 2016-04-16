/**
 *  WebSocketIO
 *
 */

//var wsUri = "ws://localhost:8080//echo";
//var wsUri = "ws://localhost:8080/WzbWork/mypackage/hello/TstWebSocket";
//            var wsUri = "ws://localhost:8080/WzbWork/DoWebSocket";
var wsUri = "ws://localhost:8080/Stks081415/DoWebSocket";

function init() {
    output = document.getElementById("output");
}

function send_message(transNum) {
    websocket = new WebSocket(wsUri);
    websocket.onopen = function(evt) {
        onOpen(evt);

        var svrMsg = transNum + "/" + textID.value;

        doSend(svrMsg);
    };
    websocket.onmessage = function(evt) {
        onMessage(evt)
    };
    websocket.onerror = function(evt) {
        onError(evt)
    };
}
function onOpen(evt) {
    writeToScreen("Connected to Endpoint!");
//    doSend(textID.value);
}
function onMessage(evt) {
    writeToScreen("Message Received: " + evt.data);
}
function onError(evt) {
    writeToScreen('ERROR: ' + evt.data);
}
function doSend(message) {
    writeToScreen("Message Sent: " + message);
    websocket.send(message);
    //websocket.close();
}
function writeToScreen(message) {
    var pre = document.createElement("p");
    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;

    output.appendChild(pre);
}
window.addEventListener("load", init, false);

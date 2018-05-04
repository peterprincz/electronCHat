var stompClient = null;


function connect(serverUrl) {
    var socket = new SockJS(serverUrl + '/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe(serverUrl + '/topic/message', function (message) {
            console.log(message)
        });
    });
}


function sendMessage(messageJsonObject) {
    stompClient.send("/app/send-message", {}, messageJsonObject);
}


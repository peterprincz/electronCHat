var app = angular.module('myApp', []);
app.controller('messageController', function($scope, $http) {
    $scope.roomId = 1;
    $scope.userName = "Kocka";
    $scope.serverUrl = "http://localhost:8080";

    connectToSocket();

    $scope.sendChatMessage = function(){
        let chatMessageObject = JSON.stringify({"content":$scope.chatMessage,
                                                "sender":$scope.userName,
                                                "chatRoomId":$scope.roomId});
        console.log(chatMessageObject);
        sendMessage(chatMessageObject);
        $scope.chatMessage = "";
    };

    $scope.logmessage =function(){
        console.log($scope.chatMessage);
        $scope.chatMessage = "";
    }

    var stompClient = null;


    function connectToSocket() {
        console.log($scope.serverUrl + '/chatty-websocket');
        var socket = new SockJS($scope.serverUrl + '/chatty-websocket');
        $scope.stompClient = Stomp.over(socket);
        $scope.stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            getMessages();
            $scope.stompClient.subscribe('/frontend-listener/chatroom/1/new-message', function (message) {
                $scope.room.chatMessages.push(JSON.parse(message.body).body);
                $scope.$apply();
            });
        });
    }

    function getMessages() {
        $http({
            url: $scope.serverUrl + "/get-room-by-id/" + $scope.roomId,
            method: "GET"
        }).then(function successCallback(response) {
            $scope.room = response.data;
            console.log($scope.room);
        }, function errorCallback(response) {
            console.log(response)
        });
    }


    function sendMessage(messageJsonObject) {
        $scope.stompClient.send("/backend-listener/chatroom/1/new-message", {}, messageJsonObject);
    }




});

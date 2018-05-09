var app = angular.module('myApp', []);
app.controller('messageController', function($scope, $http) {
    $scope.connectedRoomId = -1;
    $scope.userName = "kocka2";
    $scope.serverUrl = "http://localhost:8080";
    $scope.isPeopleListShown = true;
    $scope.isRoomListShow = false;

    $http({
        url: $scope.serverUrl + "/get-all-room",
        method: "GET"
    }).then(function successCallback(response) {
        $scope.rooms = response.data;
    }, function errorCallback(response) {
        console.log(response)
    });


    $scope.sendChatMessage = function(){
        let chatMessageObject = JSON.stringify({"content":$scope.chatMessage,
                                                "sender":$scope.userName,
                                                "chatRoomId":$scope.connectedRoomId});
        sendMessage(chatMessageObject);
        $scope.chatMessage = "";
    };

    $scope.changeRoom = function (roomId) {
        $scope.connectedRoomId = roomId;
        $scope.connectToRoom();
    }

    $scope.connectToRoom = function() {
        if($scope.stompClient != undefined){
            $scope.stompClient.disconnect();
            removeSelfFromRoom();
        }
        addSelfToRoom();
        var socket = new SockJS($scope.serverUrl + '/chatty-websocket');
        $scope.stompClient = Stomp.over(socket);
        $scope.stompClient.connect({}, function (frame) {
            getMessages();

            $scope.stompClient.subscribe('/frontend-listener/chatroom/'+ $scope.connectedRoomId +'/new-message', function (message) {
                $scope.room.chatMessages.push(JSON.parse(message.body).body);
                $scope.$apply();
            });
        });
    };

    function getMessages() {
        $http({
            url: $scope.serverUrl + "/get-room-by-id/" + $scope.connectedRoomId,
            method: "GET"
        }).then(function successCallback(response) {
            $scope.room = response.data;
        }, function errorCallback(response) {
            console.log(response)
        });
    }


    function sendMessage(messageJsonObject) {
        $scope.stompClient.send("/backend-listener/chatroom/"+ $scope.connectedRoomId +"/new-message", {}, messageJsonObject);
    }


    $scope.showRooms = function () {
        $scope.isRoomListShown = true;
        $scope.isPeopleListShown = false;

    };

    $scope.showPeople = function () {
        $scope.isRoomListShown = false;
        $scope.isPeopleListShown = true;
    };

    function addSelfToRoom(){
        $http({
            url: $scope.serverUrl + "/join-room",
            method: "post",
            data: {userName:$scope.userName, roomId:$scope.connectedRoomId},
            headers: { "Content-Type": "application/json" },
            dataType : 'json'
        }).then(function successCallback(response) {
            console.log(response.data);
        }, function errorCallback(response) {
            console.log(response);
        });
    }

    function removeSelfFromRoom(){
        $http({
            url: $scope.serverUrl + "/leave-room",
            method: "post",
            data: {userName:$scope.userName, roomId:$scope.connectedRoomId},
            headers: { "Content-Type": "application/json" },
            dataType : 'json'
        }).then(function successCallback(response) {
            console.log(response.data);
        }, function errorCallback(response) {
            console.log(response);
        });
    }

});


var app = angular.module('myApp', []);
app.controller('messageController', function($scope, $http) {
    $scope.roomId = 1;
    $scope.userName = "Kocka";
    $scope.serverUrl = "http://192.168.178.32:8080";

    connect($scope.serverUrl);

    $http({
        url: $scope.serverUrl + "/get-room-by-id/" + $scope.roomId,
        method: "GET"
    }).then(function successCallback(response) {
        $scope.room = response.data;
        console.log($scope.room);
    }, function errorCallback(response) {
        console.log(response)
    });

    $scope.sendChatMessage = function(){
        let chatMessageObject = JSON.stringify({"content":$scope.chatMessage,
                                                "sender":$scope.userName,
                                                "chatRoomId":1});
        console.log(chatMessageObject);
        sendMessage(chatMessageObject);
        $scope.chatMessage = "";
    };

    $scope.logmessage =function(){
        console.log($scope.chatMessage);
        $scope.chatMessage = "";
    }


});

var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/crystals/stomp');
    stompClient = Stomp.over(socket);
    stompClient.connect({
        'login': 'admin@gmail.com',
        'password': 'password'
    }, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/crystals/incoming/complete', function (incoming) {
            showGreeting(incoming.body);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendPrivate() {
    stompClient.send("/crystals/chat/private/add", {}, JSON.stringify({
        'senderId': $("#privateSenderId").val(),
        'senderName': $("#privateSenderName").val(), 'message': $("#privateMessage").val()
    }));
}

function sendPublic() {
    stompClient.send("/crystals/chat/public/add", {}, JSON.stringify({
        'senderId': $("#publicSenderId").val(),
        'senderName': $("#publicSenderName").val(), 'message': $("#publicMessage").val()
    }));
}

function showGreeting(message) {
    $("#logs").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#sendPrivate").click(function () {
        sendPrivate();
    });
    $("#sendPublic").click(function () {
        sendPublic();
    });
});
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
    var socket = new SockJS('/stomp');
    stompClient = Stomp.over(socket);
    stompClient.connect({
        'login': 'test@mail.ru',
        'password': '$2a$10$F7bZXdBwZC25MZjNGP97rOvwRs59XzQfprE164kFfI3Y5rrg6Ssuy'
    }, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/incoming/complete', function (incoming) {
            showGreeting(JSON.parse(incoming.body).content);
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

function send() {
    stompClient.send("/app/incoming", {}, JSON.stringify({'login': $("#login").val()}));
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
    $("#send").click(function () {
        send();
    });
});
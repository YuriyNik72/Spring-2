var stompClient = null;

window.onload = connect();

function connect() {
  var socket = new SockJS("/socket");
  stompClient =Stomp.over(socket);
  stompClient.connect({}, function (frame) {
      console.log('Connected' + frame);
      stompClient.subscribe('/topic/cost', function (greeting) {
          showGreeting(JSON.parse(greeting.body).content);
      });
  });
}

function sendName() {
    var name = 'товар';
    stompClient.send("/app/cost", {}, JSON.stringify({'cost' : cost}));
}

function showGreeting(message) {
    console.log(message);
    document.getElementById("resultInput").value=message;
}
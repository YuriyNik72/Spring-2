var stompClient = null;

window.onload = () => {
    connect();
}

function connect() {
  var socket = new SockJS('/market/cart-messaging');
  stompClient =Stomp.over(socket);
  stompClient.connect({}, function (frame) {
      console.log('Connected: ' + frame);
      stompClient.subscribe('/user/queue/cost', function (response) {
          var data = JSON.parse(response.body);
          console.log(data);
          $('#resultInput').val(data);
      });
  });
}

function sendMessage() {
    // var name = 'товар';
    stompClient.send("/app/message", {}, JSON.stringify(150));
}

// function showGreeting(message) {
//     console.log(message);
//     document.getElementById("resultInput").value=message;
// }
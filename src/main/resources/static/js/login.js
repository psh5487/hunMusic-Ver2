document.getElementById("loginButton").onclick = function () {
    const email = document.forms['logInForm'].elements['email'].value;
    const password = document.forms['logInForm'].elements['password'].value;

    let req = {};
    req.email = email;
    req.password = password;

    login(req)
};

function login(req) {
    console.log(req);
    const context = $('#contextPathHolder').attr('data-contextPath');
    console.log(context);
    console.log('http://localhost:8080/login');

    axios.post('http://localhost:8080/login', req)
        .then(function (response) {
            console.log('success');
            console.log(response);
            console.log(response.data);
            window.location.href('http://localhost:8080'); //페이지 redirect 하기
        })
        .catch(function (error) {
            console.log(error);
        });
}
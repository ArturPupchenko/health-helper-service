var username = document.getElementById("username");
var password = document.getElementById("password");


function setupListeners() {
    username.addEventListener('keyup', function (event) {
        if (event.key === 'Enter') {
            tryToLogin();
        }
    });
    password.addEventListener('keyup', function (event) {
        if (event.key === 'Enter') {
            tryToLogin();
        }
    });
    document.getElementById('login').addEventListener('click', function () {
        tryToLogin();
    });
}

setupListeners();

function tryToLogin() {
    let usernameValue = username.value;
    let passwordValue = password.value;
    let loginRequestBody = {
        login: usernameValue,
        password: passwordValue
    };

    if (usernameValue === '' || passwordValue === '') {
        alert("Try again");
        return;
    }

    axios.post('/login', loginRequestBody, {
        headers: {
            'Authorization': 'JWT'
        }})
        .then(function (response) {
            localStorage.setItem('JWT', response.headers.authorization);
            console.log(localStorage.getItem("JWT"));
        })
        .catch(function (error) {
            alert("Try again");
            console.log(error);
        });
    axios.get('/exercises', {
        headers: {
            'Authorization' : localStorage.getItem("JWT")
        }})
        .then(function (response) {
            console.log(response);
        })
        .catch(function (error) {
            console.log(error);
        });
}



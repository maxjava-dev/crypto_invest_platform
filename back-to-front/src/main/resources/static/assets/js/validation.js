function validateForm() {
    let success = true;

    let username = document.getElementById("username").value;
    let usernameErrorDiv = document.getElementById("usernameError")

    if (username === "") {
        usernameErrorDiv.textContent = "Логин не должен быть пустым";
        usernameErrorDiv.style.display = "block";
        success = false;
    } else if (username.length > 20) {
        usernameErrorDiv.textContent = "Логин должен содержать не больше 20 символов";
        usernameErrorDiv.style.display = "block";
        success = false;
    } else {
        usernameErrorDiv.style.display = "none";
    }

    let password = document.getElementById("password").value;
    let passwordErrorDiv =  document.getElementById("passwordError")

    if (password === "") {
        passwordErrorDiv.textContent = "Пароль не должен быть пустым";
        passwordErrorDiv.style.display = "block";
        success = false;
    } else if (password.length < 5) {
        passwordErrorDiv.textContent = "Пароль должен содержать не менее 5 символов";
        passwordErrorDiv.style.display = "block";
        success = false;
    } else if (username.length > 20) {
        passwordErrorDiv.textContent = "Пароль должен содержать не более 20 символов";
        passwordErrorDiv.style.display = "block";
        success = false;
    }else {
        passwordErrorDiv.style.display = "none";
    }

    return success;
}
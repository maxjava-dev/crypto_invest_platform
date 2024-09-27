function checkServerError() {
    const urlParams = new URLSearchParams(window.location.search);
    const errorParam = urlParams.get('error');

    if (errorParam !== null) {
        document.getElementById('serverError').style.display = "block";
    }
}
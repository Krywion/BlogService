function check() {
    if (document.getElementById('password').value == document.getElementById('repeat-password').value) {
        document.getElementById('message').style.color = 'green';
        document.getElementById('message').innerHTML = 'matching';
    } else {
        document.getElementById('message').style.color = 'red';
        document.getElementById('message').innerHTML = 'not matching';
    }
}

window.onload = function() {
    document.getElementById('password').addEventListener('keyup', check);
    document.getElementById('repeat-password').addEventListener('keyup', check);
};

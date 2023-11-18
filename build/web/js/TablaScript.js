
$('table').DataTable();
function toggleColor(button) {
    button.classList.toggle('active');
    // Obtiene el contenido actual del botón
    const contenidoActual = button.innerHTML;

    // Cambia el texto del botón según el contenido actual
    if (contenidoActual === 'Activar') {
        button.innerHTML = 'Desactivar';
    } else {
        button.innerHTML = 'Activar';
    }
}
const passwordInput = document.getElementById("password");
const passToggleBtn = document.getElementById("pass-toggle-btn");
passToggleBtn.addEventListener('click', () => {
passToggleBtn.className = passwordInput.type === "password" ? "bx bx-show-slash" : "bx bx-show";
passwordInput.type = passwordInput.type === "password" ? "text" : "password";
});
/*function toggleColor(button) {
    // Toggle the 'active' class on the button
    button.classList.toggle('active');
}


*/
/*
function toggleColor(button) {
    // Obtiene el color actual del botón
    var currentColor = window.getComputedStyle(button, null).getPropertyValue("background-color");

    // Define el color original y el color de cambio
    var originalColor = "#5D718A"; // O el color original que desees
    var changedColor = "##333050"; // O el color de cambio que desees

    // Comprueba el color actual y cambia al otro color
    if (currentColor === originalColor) {
        button.style.backgroundColor = changedColor;
    } else {
        button.style.backgroundColor = originalColor;
    }
}
*/
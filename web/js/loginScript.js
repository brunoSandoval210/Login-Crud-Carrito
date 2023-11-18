const showPopupBtn = document.querySelector(".login-btn");
const formPopup = document.querySelector(".form-popup");
const hidePopupBtn = formPopup.querySelector(".close-btn");
const signupLoginLink = formPopup.querySelectorAll(".bottom-link a");

// Show login popup
showPopupBtn.addEventListener("click", () => {
    document.body.classList.toggle("show-popup");
});

// Hide login popup
hidePopupBtn.addEventListener("click", () => {
    document.body.classList.remove("show-popup");
});

// Show or hide signup form
signupLoginLink.forEach(link => {
    link.addEventListener("click", (e) => {
        e.preventDefault();
        formPopup.classList[link.id === 'signup-link' ? 'add' : 'remove']("show-signup");
    });
});



(function () {
    const fonts = ["cursive", "sans-serif", "serif", "monospace"];
    let captchaValue = "";
    function generateCaptcha() {
        let value = btoa(Math.random() * 1000000000);
        value = value.substr(0, 5 + Math.random() * 5);
        captchaValue = value;
    }
    function setCaptcha() {
        let html = captchaValue.split("").map((char) => {
            const rotate = -20 + Math.trunc(Math.random() * 30);
            const font = Math.trunc(Math.random() * fonts.length);
            return `<span 
style="
  transform:rotate(${rotate}deg);
  font-family:${fonts[font]}
"
>${char}</span>`;
        }).join("");
        document.querySelector(".login-form .captcha .preview").innerHTML = html;
    }
    function initCaptcha() {
        document.querySelector(".login-form .captcha .captcha-refresh").addEventListener("click", function () {
            generateCaptcha();
            setCaptcha();
        });
        generateCaptcha();
        setCaptcha();
    }
    initCaptcha();

    document.querySelector(".login-form #login-btn").addEventListener("click", function (e) {
        e.preventDefault(); // Evita que el formulario se envíe automáticamente
    
        // Obtén los valores de los campos
        let inputEmailValue = document.querySelector(".login-form input[type='text']").value;
        let inputPasswordValue = document.querySelector(".login-form input[type='password']").value;
        let inputCaptchaValue = document.querySelector(".login-form input#captcha-form").value;
    
        // Verifica si algún campo está vacío o si el captcha es incorrecto
        if (inputEmailValue.trim() === "" || inputPasswordValue.trim() === "" || inputCaptchaValue !== captchaValue) {
            // Muestra un mensaje de error
            Swal.fire({
                title: "¡Error de inicio de sesión!",
                text: "Por favor, revisa tus datos e intenta nuevamente.",
                icon: "error",
                customClass: {
                  confirmButton: "custom-confirm-button-class", // Clase personalizada para el botón de confirmación
                },
                buttonsStyling: false, // Desactiva los estilos predeterminados de los botones
              });
        } else {
            // Simula un inicio de sesión exitoso
            Swal.fire({
                title: "¡Inicio de sesión exitoso!",
                text: "Verificando datos",
                icon:  "success",
                customClass: {
                  confirmButton: "custom-confirm-button-class", // Clase personalizada para el botón de confirmación
                },
                buttonsStyling: false, // Desactiva los estilos predeterminados de los botones
              });

        }
    });
})();
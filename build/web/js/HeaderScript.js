// search-box open close js code
let navbar = document.querySelector(".navbar");



// sidebar open close js code
let navLinks = document.querySelector(".nav-links");
let menuOpenBtn = document.querySelector(".navbar .bx-menu");
let menuCloseBtn = document.querySelector(".nav-links .bx-x");
menuOpenBtn.onclick = function() {
navLinks.style.left = "0";
}
menuCloseBtn.onclick = function() {
navLinks.style.left = "-100%";
}


// sidebar submenu open close js code
let habitacionArrow = document.querySelector(".habitacion-arrow");
habitacionArrow.onclick = function() {
 navLinks.classList.toggle("show1");
}
let salonArrow = document.querySelector(".salon-arrow");
salonArrow.onclick = function() {
 navLinks.classList.toggle("show2");
}
let categoriaArrow = document.querySelector(".categoria-arrow");
categoriaArrow.onclick = function() {
 navLinks.classList.toggle("show3");
}
let servicioArrow = document.querySelector(".servicio-arrow");
servicioArrow.onclick = function() {
 navLinks.classList.toggle("show4");
}
let usuarioArrow = document.querySelector(".usuario-arrow");
usuarioArrow.onclick = function() {
 navLinks.classList.toggle("show5");
}
let logoutArrow = document.querySelector(".logout-arrow");
logoutArrow.onclick = function() {
 navLinks.classList.toggle("show6");
}



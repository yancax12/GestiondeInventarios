// Configuración de Stripe
var stripe = Stripe('your-publishable-key-here'); // Usa tu clave pública de Stripe
var elements = stripe.elements();

// Crear un elemento para la tarjeta de crédito
var card = elements.create('card');

// Añadir el elemento de la tarjeta al formulario
card.mount('#card-element');

// Manejar errores de la tarjeta
card.on('change', function(event) {
  var displayError = document.getElementById('card-errors');
  if (event.error) {
    displayError.textContent = event.error.message;
  } else {
    displayError.textContent = '';
  }
});

// Manejar la lógica de pago al enviar el formulario
var form = document.getElementById('payment-form');
form.addEventListener('submit', function(event) {
  event.preventDefault();

  stripe.createToken(card).then(function(result) {
    if (result.error) {
      // Mostrar el error en el formulario
      var errorElement = document.getElementById('card-errors');
      errorElement.textContent = result.error.message;
    } else {
      // Enviar el token al backend
      stripeTokenHandler(result.token);
    }
  });
});

// Enviar el token al servidor
function stripeTokenHandler(token) {
  var form = document.getElementById('payment-form');

  // Crear un campo oculto para almacenar el token
  var hiddenInput = document.createElement('input');
  hiddenInput.setAttribute('type', 'hidden');
  hiddenInput.setAttribute('name', 'stripeToken');
  hiddenInput.setAttribute('value', token.id);

  // Añadir el token al formulario
  form.appendChild(hiddenInput);

  // Enviar el formulario
  form.submit();
}

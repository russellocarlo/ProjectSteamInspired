document.getElementById('show-phone-btn').addEventListener('click', function() {
    var phoneNumberDiv = document.getElementById('phone-number');
    if (phoneNumberDiv.style.display === "none") {
        phoneNumberDiv.style.display = "block"; 
    } else {
        phoneNumberDiv.style.display = "none"; 
    }
})

//controllo cifre dataScadenza
function formatExpiryDate(input) {
    // Rimuovi qualsiasi carattere non numerico
    input.value = input.value.replace(/\D/g, '');

    // Aggiungi lo slash automaticamente dopo i primi due numeri (MM)
    if (input.value.length > 2) {
        input.value = input.value.slice(0, 2) + '/' + input.value.slice(2);
    }
}

function formatCardNumber(input) {
        // Rimuove tutti gli spazi esistenti e qualsiasi carattere non numerico
        let cardNumber = input.value.replace(/\D/g, '').replace(/(.{4})/g, '$1 ').trim();
        
        // Imposta il valore del campo di input formattato
        input.value = cardNumber;
    }

function toggleForm(formId) {
    const form = document.getElementById(formId);
    if (form.style.display === 'none' || form.style.display === '') {
        form.style.display = 'block';
    } else {
        form.style.display = 'none';
    }
}

document.getElementById('aggiorna-recensione-btn').addEventListener("click", function(){
    document.getElementById('form-aggiorna-recensione').style.display = "block";  
})

document.getElementById('annulla-aggiorna-recensione').addEventListener("click", function(){
    document.getElementById('form-aggiorna-recensione').style.display = "none";  
})
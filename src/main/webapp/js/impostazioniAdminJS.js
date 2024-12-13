
function showSection(sectionId) {
    // Nascondi tutte le sezioni
    const sezioni = document.querySelectorAll('section');
   
    sezioni.forEach(sezione => {
        sezione.classList.add('active');
        sezione.classList.remove('hidden');
        sezione.style.display = 'none';
    });

    // Mostra la sezione selezionata
    const sezioneSelezionata = document.getElementById(sectionId);
    if (sezioneSelezionata) {
        sezioneSelezionata.style.display = 'block';
        // Scorre fino alla sezione selezionata
        sezioneSelezionata.scrollIntoView({ behavior: 'smooth' });
    }
}

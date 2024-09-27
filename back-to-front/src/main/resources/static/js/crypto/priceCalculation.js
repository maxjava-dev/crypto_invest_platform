document.getElementById('quantity').addEventListener('input', function() {
    const quantity = parseFloat(this.value);
    const cryptoPrice = parseFloat(document.getElementById('cryptoPrice').textContent);
    const price = quantity * cryptoPrice;
    if (price) {
        document.getElementById('price').textContent = price.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
    } else {
        document.getElementById('price').textContent = "Введите количество";
    }
});
document.getElementById('addRestaurantForm').addEventListener('submit', function (event) {
    event.preventDefault();

    const name = document.getElementById('restaurantName').value;
    const address = document.getElementById('restaurantAddress').value;

    fetch('/restaurants', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name, address })
    })
    .then(response => response.json())
    .then(data => {
        alert('Restaurant added successfully');
    })
    .catch(error => {
        console.error('Error:', error);
    });
});

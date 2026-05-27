// ============================================================
// Q21 — JavaScript: async function loadUsers()
// Fetches GET /api/users, renders names into <ul id="user-list">
// or shows error in <p id="error">
// ============================================================

async function loadUsers() {
    try {
        // fetch() returns a Promise — await pauses execution until the response arrives
        const response = await fetch('/api/users');

        // response.ok is true when HTTP status is 200–299
        // If status is 404, 500, etc., response.ok is false
        if (!response.ok) {
            // Show a meaningful error message in the error paragraph
            document.getElementById('error').textContent =
                `Error: Failed to load users (HTTP ${response.status})`;
            return;   // Stop further execution
        }

        // Parse the JSON response body — also returns a Promise, so we await it
        const users = await response.json();    // Expected format: [{id: 1, name: "Alice"}, ...]

        // Get the <ul id="user-list"> element from the DOM
        const ul = document.getElementById('user-list');
        ul.innerHTML = '';   // Clear any existing list items before adding new ones

        // Loop through each user and create a <li> element for their name
        users.forEach(user => {
            const li = document.createElement('li');   // Create a new <li> element
            li.textContent = user.name;                // Set its text to the user's name
            ul.appendChild(li);                        // Append <li> into the <ul>
        });

    } catch (error) {
        // Catches network errors (e.g., server unreachable, CORS issues)
        document.getElementById('error').textContent =
            `Network error: ${error.message}`;
    }
}

// Call the function when the page loads
loadUsers();

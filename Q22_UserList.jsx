// ============================================================
// Q22 — React: UserList Component
// (a) Bug: useEffect has no dependency array → infinite loop
// (b) Fix: add [] as second argument to useEffect
// (c) Add loading state that shows "Loading..." while fetching
// ============================================================

import { useState, useEffect } from "react";

function UserList() {
    const [users, setUsers] = useState([]);

    // (c) Add a loading state — starts as true since we fetch immediately
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        // (b) FIX: The empty array [] as the second argument means
        //     "run this effect only ONCE — after the first render"
        //     Without [], the effect runs after EVERY render,
        //     and setUsers(data) causes a re-render → infinite loop

        fetch('/api/users')
            .then(res => res.json())
            .then(data => {
                setUsers(data);
                setLoading(false);   // (c) Data has arrived — stop showing loading indicator
            })
            .catch(err => {
                console.error("Failed to fetch users:", err);
                setLoading(false);   // Also stop loading on error to avoid stuck spinner
            });
    }, []);   // <-- THE FIX: empty dependency array = run only on mount

    // (c) While data is being fetched, show a loading message
    if (loading) {
        return <p>Loading...</p>;
    }

    // Once data is ready, render the list
    return (
        <ul>
            {users.map(u => (
                <li key={u.id}>{u.name}</li>
            ))}
        </ul>
    );
}

export default UserList;

// ============================================================
// SUMMARY OF CHANGES:
// (a) Bug: useEffect(() => { ... }) — missing second argument
//     This causes the effect to run after every render.
//     setUsers(data) triggers a re-render → effect runs again → infinite loop.
//
// (b) Fix: useEffect(() => { ... }, [])
//     The empty [] tells React to run this effect only once (on component mount).
//
// (c) Added: const [loading, setLoading] = useState(true)
//     Shows "Loading..." until the fetch completes, then renders the list.
// ============================================================

const routes = [
    {
        path: '/dashboard',
        title: 'Dashboard',
        routes: [
            {
                path: '/dashboard/account',
                title: 'Account'
            },
            {
                path: '/dashboard/users',
                title: 'Users'
            },
            {
                path: '/dashboard/discounts',
                title: 'Discounts'
            },
            {
                path: '/dashboard/available-rooms',
                title: 'Available rooms'
            },
            {
                path: '/dashboard/additional-services',
                title: 'Additional services'
            },
            {
                path: '/dashboard/book-room',
                title: 'Book room'
            },
            {
                path: '/dashboard/my-bookings',
                title: 'My bookings'
            },
            {
                path: '/dashboard/faq',
                title: 'FAQ'
            }
        ]
    }
];

export default routes;
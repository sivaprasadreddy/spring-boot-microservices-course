document.addEventListener('alpine:init', () => {
    Alpine.data('initData', () => ({
        orders: [],
        init() {
            this.loadOrders();
            updateCartItemCount();
        },
        loadOrders() {
            $.getJSON("/api/orders", (data) => {
                //console.log("orders :", data)
                this.orders = data
            });
        },
    }))
});

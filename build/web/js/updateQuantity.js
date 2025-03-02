document.addEventListener("DOMContentLoaded", function () {
    const checkboxes = document.querySelectorAll(".product-checkbox");
    const checkoutForm = document.getElementById("checkoutForm");
    const totalPriceElement = document.getElementById("total-price");

    function updateTotal() {
        let total = 0;
        let selectedItems = document.querySelectorAll(".product-checkbox:checked");

        selectedItems.forEach(checkbox => {
            let row = checkbox.closest("tr");
            let totalCell = row.querySelector(".cart-total-price");
            let price = parseFloat(totalCell.textContent.replace(/₫|,/g, "")) || 0;
            total += price;
        });

        totalPriceElement.textContent = `Total Price: ₫${total.toLocaleString()}`;
    }

    window.updateQuantity = function (amount, cartItemId, event) {
        event.preventDefault();

        let quantityInput = document.getElementById(`quantity_${cartItemId}`);
        let totalPriceCell = document.getElementById(`total_${cartItemId}`);
        let currentQuantity = parseInt(quantityInput.value);
        let maxQuantity = parseInt(quantityInput.max);

        let newQuantity = currentQuantity + amount;

        if (newQuantity >= 1 && newQuantity <= maxQuantity) {
            quantityInput.value = newQuantity;

            fetch(`CartURL?service=updateQuantity&cartItemId=${cartItemId}&newQuantity=${newQuantity}`, {
                method: 'POST',
            })
            .then(response => response.json())
            .then(data => {
                if (data.status === 'success') {
                    let unitPrice = parseFloat(document.getElementById(`row_${cartItemId}`).querySelector(".cart-price").textContent.replace(/₫|,/g, ""));
                    totalPriceCell.textContent = `₫${(unitPrice * newQuantity).toLocaleString()}`;

                    updateTotal(); 
                } else {
                    showAlert("An error occurred while updating the quantity.");
                }
            })
            .catch(error => {
                console.error("Error updating quantity:", error);
                showAlert("Error updating quantity.");
            });
        } else {
            showAlert(`Quantity must be greater than 0 and less than or equal to ${maxQuantity}.`);
        }
    };

    window.checkQuantity = function (cartItemId, event) {
        let quantityInput = document.getElementById(`quantity_${cartItemId}`);
        let currentQuantity = parseInt(quantityInput.value);
        let maxQuantity = parseInt(quantityInput.max);

        if (currentQuantity < 1) {
            quantityInput.value = 1;
        } else if (currentQuantity > maxQuantity) {
            quantityInput.value = maxQuantity;
        }

        fetch(`CartURL?service=updateQuantity&cartItemId=${cartItemId}&newQuantity=${currentQuantity}`, {
            method: 'POST',
        })
        .then(response => response.json())
        .then(data => {
            if (data.status === 'success') {
                document.getElementById(`total_${cartItemId}`).textContent = `₫${data.totalOrderPrice}`;
            } else {
                showAlert("An error occurred while updating the quantity.");
            }
        })
        .catch(error => {
            console.error("Error updating quantity:", error);
            showAlert("Error updating quantity.");
        });
    };

    window.removeItem = function (cartItemId, event) {
        event.preventDefault();

        fetch(`CartURL?service=removeItem&cartItemId=${cartItemId}`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({cartItemId: cartItemId})
        })
        .then(response => response.json())
        .then(data => {
            if (data.status === "success") {
                const row = document.getElementById(`row_${cartItemId}`);
                if (row) {
                    row.remove();
                }
                updateTotal();
                if (document.querySelectorAll(".cart-row").length === 0) {
                    window.location.href = "CartURL?service=showCart&page=1";
                }
            } else {
                showAlert("An error occurred while removing the item.");
            }
        })
        .catch(error => {
            console.error("Error removing item:", error);
            showAlert("Error removing item.");
        });
    };

    function showAlert(message) {
        const alertBox = document.createElement("div");
        alertBox.classList.add("alert-box-error");
        alertBox.innerHTML = `
            <div class="alert-message-error">
                <div class="alert-icon">❌</div>
                <span>${message}</span>
            </div>
        `;
        document.body.appendChild(alertBox);

        setTimeout(() => alertBox.remove(), 3000);
    }

    checkboxes.forEach(checkbox => {
        checkbox.addEventListener("change", updateTotal);
    });

    document.getElementById("select_all").addEventListener("change", function () {
        let isChecked = this.checked;
        checkboxes.forEach(cb => cb.checked = isChecked);
        document.getElementById("select_all_footer").checked = isChecked;
        updateTotal();
    });

    document.getElementById("select_all_footer").addEventListener("change", function () {
        let isChecked = this.checked;
        checkboxes.forEach(cb => cb.checked = isChecked);
        document.getElementById("select_all").checked = isChecked;
        updateTotal();
    });

    checkoutForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const selectedItems = document.querySelectorAll('.product-checkbox:checked');
        if (selectedItems.length === 0) {
            showAlert("You haven't selected any products.");
            return;
        }

        selectedItems.forEach(checkbox => {
            let cartItemId = checkbox.value;
            let quantityInput = document.getElementById(`quantity_${cartItemId}`);
            let newQuantity = quantityInput.value;
            let hiddenQuantityInput = document.querySelector(`input[name="quantity_${cartItemId}"]`);
            
            if (hiddenQuantityInput) {
                hiddenQuantityInput.value = newQuantity; 
            }
        });

        checkoutForm.submit();
    });

    updateTotal();
});

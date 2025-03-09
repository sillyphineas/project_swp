
function addToCart() {
    let productId = document.getElementById("productID").value;
    let color = document.getElementById("colorSelector").value;
    let storage = document.getElementById("storageSelector").value;
    let quantity = document.getElementById("quantity").value;

    if (!productId || productId === "undefined") {
        console.error("ERROR: productID is null or undefined in JavaScript!");
        showCartNotification("An error occurred, please check the product information.");
        return;
    }

    fetch(`CartURL?service=add2cart&productID=${productId}&color=${encodeURIComponent(color)}&storage=${storage}&quantity=${quantity}`, {
        method: "POST",
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Network response was not ok");
        }
        return response.json();
    })
    .then(data => {
        if (data.status === "success") {
            showCartNotification("Product has been successfully added to the cart!");
            updateCartDisplay();
        } else {
            showCartNotification(data.message);
        }
    })
    .catch(error => {
        console.error("An error occurred while adding the product to the cart:", error);
        showCartNotification("An error occurred while adding the product to the cart. Please try again.");
    });
}

function showCartNotification(message) {
    let notification = document.getElementById("cart-notification");
    notification.querySelector("p").textContent = message;
    notification.classList.add("show");
    notification.classList.remove("hidden");

    setTimeout(() => {
        notification.classList.remove("show");
        notification.classList.add("hidden");
    }, 1000); 
}

function updateCartDisplay() {
    console.log("Cart has been updated!");
}

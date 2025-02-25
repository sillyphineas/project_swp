function addToCart() {
    let productId = document.getElementById("productID").value;
    let color = document.getElementById("colorSelector").value;
    let storage = document.getElementById("storageSelector").value;
    let quantity = document.getElementById("quantity").value;

    // Kiểm tra nếu productId không hợp lệ, không dùng alert mà gọi thông báo tùy chỉnh
    if (!productId || productId === "undefined") {
        console.error("ERROR: productID is null or undefined in JavaScript!");
        showCartNotification("Có lỗi xảy ra, vui lòng kiểm tra lại thông tin sản phẩm.");
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
        // Kiểm tra kết quả trả về từ server
        if (data.status === "success") {
            showCartNotification("Sản phẩm đã được thêm vào giỏ hàng thành công!");
            updateCartDisplay();
        } else {
            showCartNotification(data.message);
        }
    })
    .catch(error => {
        console.error("Có lỗi xảy ra khi thêm sản phẩm vào giỏ hàng:", error);
        showCartNotification("Có lỗi xảy ra khi thêm sản phẩm vào giỏ hàng. Vui lòng thử lại.");
    });
}

// Hàm hiển thị thông báo giỏ hàng tùy chỉnh
function showCartNotification(message) {
    let notification = document.getElementById("cart-notification");
    notification.querySelector("p").textContent = message;  // Cập nhật nội dung thông báo
    notification.classList.add("show");  // Hiển thị thông báo

    setTimeout(() => {
        notification.classList.remove("show");  // Ẩn thông báo sau 3 giây
    }, 3000);
}

function updateCartDisplay() {
    console.log("Giỏ hàng đã được cập nhật!");
}

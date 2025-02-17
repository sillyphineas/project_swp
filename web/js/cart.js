/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function addToCart(productId) {
    fetch(`CartURL?service=add2cart&productID=${productId}&quantity=1`, {
        method: 'GET'
    })
    .then(response => {
        if (response.ok) {
            showNotification("Sản phẩm đã được thêm vào Giỏ hàng");
        } else {
            showNotification("Lỗi khi thêm sản phẩm!", true);
        }
    })
    .catch(error => {
        console.error("Lỗi:", error);
        showNotification("Lỗi kết nối đến server!", true);
    });
}

function showNotification(message) {
    let notification = document.getElementById("cart-notification");
    if (!notification) {
        console.error("Không tìm thấy #cart-notification trong HTML!");
        return;
    }

    notification.classList.remove("hidden");
    notification.style.display = "block";
    notification.style.opacity = "1";
    notification.style.visibility = "visible";

    notification.querySelector("p").innerText = message;

    setTimeout(() => {
        notification.style.opacity = "0";
        notification.style.visibility = "hidden";
        notification.style.display = "none";
    }, 1000);
}



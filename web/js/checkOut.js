/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function checkoutSelectedItems() {
    let selectedItems = [];

    // Lặp qua tất cả sản phẩm đã được chọn
    document.querySelectorAll(".product-checkbox:checked").forEach(checkbox => {
        let row = checkbox.closest("tr");
        let itemId = checkbox.getAttribute("data-id");
        let quantity = parseInt(row.querySelector(".cart-quantity-input").value);

        selectedItems.push({ itemId, quantity });
    });

    if (selectedItems.length === 0) {
        alert("Vui lòng chọn ít nhất một sản phẩm để thanh toán!");
        return;
    }

    // Gửi danh sách sản phẩm được chọn đến backend
    fetch("CartURL?service=checkOut", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(selectedItems)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            window.location.href = "checkout.jsp";
        } else {
            alert("Có lỗi xảy ra khi xử lý thanh toán!");
        }
    })
    .catch(error => {
        console.error("Lỗi khi gửi danh sách sản phẩm thanh toán:", error);
    });
}
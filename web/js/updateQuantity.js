document.addEventListener("DOMContentLoaded", function () {
    const checkboxes = document.querySelectorAll(".product-checkbox");
    const selectAllCheckbox = document.getElementById("select_all");
    const selectAllFooterCheckbox = document.getElementById("select_all_footer");
    const totalPriceElement = document.getElementById("total-price");

    // 🔥 Cập nhật tổng tiền khi chọn sản phẩm
    function updateTotal() {
        let total = 0;
        document.querySelectorAll(".product-checkbox:checked").forEach(checkbox => {
            let row = checkbox.closest("tr");
            let totalCell = row.querySelector(".cart-total-price");
            let price = parseFloat(totalCell.textContent.replace(/₫|,/g, ""));
            total += price;
        });
        totalPriceElement.textContent = `Total Price: ₫${total.toLocaleString()}`;
    }

    // 🔥 Cập nhật số lượng sản phẩm khi nhấn +
    function updateQuantity(change, itemId) {
        let quantityElement = document.getElementById(`quantity_${itemId}`);
        let newQuantity = Math.max(1, parseInt(quantityElement.value) + change);
        quantityElement.value = newQuantity;

        let row = quantityElement.closest("tr");
        let pricePerUnit = parseFloat(row.querySelector(".cart-price").textContent.replace(/₫|,/g, ""));
        let newTotal = newQuantity * pricePerUnit;
        row.querySelector(".cart-total-price").textContent = `₫${newTotal.toLocaleString()}`;

        fetch(`CartURL?service=updateQuantity&cartItemId=${itemId}&quantity=${newQuantity}`, {
            method: 'POST'
        });

        updateTotal();
    }

    // 🔥 Xóa sản phẩm khỏi giỏ hàng
    function removeItem(itemId) {
        fetch(`CartURL?service=removeItem&cartItemId=${itemId}`, {
            method: 'POST'
        })
        .then(response => {
            if (response.ok) {
                let row = document.getElementById(`row_${itemId}`);
                if (row) {
                    row.remove();
                }
                updateTotal();
            } else {
                alert("Lỗi khi xóa sản phẩm khỏi giỏ hàng.");
            }
        })
        .catch(error => {
            console.error("Lỗi kết nối khi xóa sản phẩm:", error);
        });
    }

    // 🔥 Chức năng chọn tất cả
    function toggleSelectAll(mainCheckbox) {
        let isChecked = mainCheckbox.checked;
        checkboxes.forEach(cb => cb.checked = isChecked);
        selectAllFooterCheckbox.checked = isChecked;
        updateTotal();
    }

    selectAllCheckbox.addEventListener("change", function () {
        toggleSelectAll(this);
    });

    selectAllFooterCheckbox.addEventListener("change", function () {
        toggleSelectAll(this);
    });

    // 🔥 Cập nhật tổng tiền khi chọn checkbox sản phẩm
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener("change", updateTotal);
    });

    // ** Gán lại các hàm để sử dụng trên HTML **
    window.updateQuantity = updateQuantity;
    window.removeItem = removeItem;
});

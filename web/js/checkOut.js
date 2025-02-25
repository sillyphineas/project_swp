document.addEventListener("DOMContentLoaded", function () {
    const addressSelect = document.getElementById("addressSelect");
    const newAddressFields = document.getElementById("newAddressFields");

    addressSelect.addEventListener("change", function () {
        if (addressSelect.value === "new") {
            newAddressFields.style.display = "block";
        } else {
            newAddressFields.style.display = "none";
        }
    });
});

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>? M? H?p Quà May M?n</title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
        <style>
            body {
                background-image: url("https://toigingiuvedep.vn/wp-content/uploads/2021/11/background-tet-do-dep-nhat.jpg");
                background-size: cover; /* ho?c contain */
                background-repeat: no-repeat;
                background-position: center center;
            }

            h2 {
                color: #28a745;
                font-size: 32px;
                margin-bottom: 30px;
            }

            .gift-boxes {
                display: flex;
                justify-content: center;
                flex-wrap: wrap;
                gap: 30px;
                max-width: 800px;
                margin: 0 auto;
            }

            .gift-box {
                width: 120px;
                height: 120px;
                background-image: url("https://png.pngtree.com/png-clipart/20230502/original/pngtree-boxing-day-gift-box-sky-blue-star-pattern-png-image_9130935.png"); /* S? d?ng ?nh upload */
                background-size: contain;
                background-repeat: no-repeat;
                background-position: center;
                cursor: pointer;
                position: relative;
                transition: transform 0.2s;
            }

            .gift-box:hover {
                transform: scale(1.1);
            }

            /* Overlay modal */
            .modal {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.6);
                display: none;
                justify-content: center;
                align-items: center;
                z-index: 1000;
            }

            .modal-content {
                background: #fff;
                border-radius: 10px;
                padding: 30px;
                max-width: 400px;
                text-align: center;
                position: relative;
                box-shadow: 0 5px 15px rgba(0,0,0,0.3);
                animation: pop 0.3s ease;
            }

            @keyframes pop {
                0% {
                    transform: scale(0.8);
                    opacity: 0;
                }
                100% {
                    transform: scale(1);
                    opacity: 1;
                }
            }

            .modal-content h3 {
                color: #ff9900;
                margin-bottom: 10px;
            }

            .modal-content p {
                font-size: 18px;
                color: #333;
            }

            .close-btn {
                position: absolute;
                top: 10px;
                right: 15px;
                font-size: 20px;
                color: #aaa;
                cursor: pointer;
            }

            .close-btn:hover {
                color: red;
            }

            .gift-img {
                width: 100px;
                margin: 20px auto;
            }

            .gift-title {
                text-align: center;
                color: white;
                font-size: 36px;
                font-weight: bold;
                text-shadow: 2px 2px 8px rgba(0,0,0,0.5);
                margin: 30px 0;
                letter-spacing: 2px;
                font-family: 'Segoe UI', sans-serif;
            }

        </style>
    </head>
    <body>

        <h2 class="gift-title">CLICK TO OPEN YOUR GIFT</h2>
        <br/>
        <br/>
        <div class="gift-boxes">
            <div class="gift-box" data-prize="1"></div>
            <div class="gift-box" data-prize="2"></div>
            <div class="gift-box" data-prize="3"></div>
            <div class="gift-box" data-prize="4"></div>
            <div class="gift-box" data-prize="5"></div>
            <div class="gift-box" data-prize="6"></div>
            <div class="gift-box" data-prize="7"></div>
            <div class="gift-box" data-prize="8"></div>
            <div class="gift-box" data-prize="9"></div>
            <div class="gift-box" data-prize="10"></div>
            <div class="gift-box" data-prize="11"></div>
            <div class="gift-box" data-prize="12"></div>
            <div class="gift-box" data-prize="13"></div>
            <div class="gift-box" data-prize="14"></div>
            <div class="gift-box" data-prize="15"></div>
        </div>

        <!-- Modal -->
        <div class="modal" id="resultModal">
            <div class="modal-content">
                <span class="close-btn" onclick="closeModal()">&times;</span>
                <h3> CHÚC M?NG </h3>
                <img class="gift-img" src="https://i.imgur.com/S4G6HkJ.png" alt="Gift Opened">
                <p id="prize-text">B?n ?ã trúng...</p>
            </div>
        </div>

        <script>
            const prizes = [
                "1 iPad mini 32GB",
                "1 Tai nghe Bluetooth",
                "Voucher gi?m 100K",
                "Không trúng, th? l?i nhé!",
                "1 Chu?t không dây Logitech"
            ];

            const boxes = document.querySelectorAll('.gift-box');
            const modal = document.getElementById('resultModal');
            const prizeText = document.getElementById('prize-text');

            boxes.forEach(box => {
                box.addEventListener('click', () => {
                    const prizeIndex = Math.floor(Math.random() * prizes.length);
                    prizeText.textContent = `B?n ?ã trúng: ${prizes[prizeIndex]}`;
                    modal.style.display = 'flex';
                });
            });

            function closeModal() {
                modal.style.display = 'none';
            }

            // Close modal khi click bên ngoài
            window.addEventListener('click', e => {
                if (e.target === modal) {
                    closeModal();
                }
            });
        </script>

    </body>
</html>

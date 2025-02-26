CREATE DATABASE `guro` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- // 지점 더미 데이터

INSERT INTO `branch` (`address`, `device_pw`, `email`, `name`, `password`, `phone`, `authority`, `status`) VALUES
('서울특별시 강남구 테헤란로 14길 6 남도빌딩 2층', '1234', 'test1@example.com', 'TEST1', '1234', '010-1111-2222', 'ADMIN', '영업'),
('서울특별시 강남구 테헤란로 14길 6 남도빌딩 2층', '1234', 'test2@example.com', 'TEST2', '1234', '010-1111-4444', 'ADMIN', '영업'),
('서울특별시 강남구 테헤란로 14길 6 남도빌딩 2층', '1234', 'test3@example.com', 'TEST3', '1234', '010-1111-3333', 'ADMIN', '영업');

-- // 상품 더미 데이터

INSERT INTO `product` (`id`, `caffeine`, `calories`, `carbohydrates`, `fat`, `protein`, `price`, `barcode`, `category`, `desc`, `image`, `name`) VALUES
(1, 1, 1, 1, 1, 1, 5000, 'BARCODE1', 'Signature', '블랙티와 리치의 조화로운 맛을 느낄 수 있는 음료입니다.', './img/Signature/Black Lychee.png', '블랙 리치'),
(2, 2, 2, 2, 2, 2, 6000, 'BARCODE2', 'Cold Cloud', '흑설탕과 우유의 시원한 맛을 느낄 수 있는 음료입니다.', './img/Cold Cloud/Brown Sugar Cold Brew.png', '브라운슈가 콜드브루'),
(3, 3, 3, 3, 3, 3, 7000, 'BARCODE3', 'Ice Blended', '망고와 패션후르츠의 조화로운 맛을 느낄 수 있는 음료입니다.', './img/Ice Blended/Mango Passionfruit.png', '망고 패션후르츠'),
(4, 4, 4, 4, 4, 4, 8000, 'BARCODE4', 'Fruit Tea', '청포도와 자몽의 조화로운 맛을 느낄 수 있는 음료입니다.', './img/Fruit Tea/Green Grape Tea.png', '청포도 차'),
(5, 5, 5, 5, 5, 5, 9000, 'BARCODE5', 'Milk Tea', '커피와 우유의 조화로운 맛을 느낄 수 있는 음료입니다.', './img/Milk Tea/Brown Sugar Milk Tea.png', '브라운슈가 밀크티');


-- // 재료 더미 데이터

INSERT INTO `ingredient` (`price`, `name`) VALUES
(5000, 'TEST1'),
(5000, 'TEST2'),
(5000, 'TEST3');

-- // 레시피 더미 데이터

INSERT INTO `recipe` (`quantity`, `ingredient_id`, `product_id`) VALUES
(1, 1, 1),
(1, 1, 2),
(1, 2, 2),
(1, 2, 3),
(1, 3, 3),
(1, 1, 4),
(1, 2, 4),
(1, 3, 4),
(1, 2, 5);

-- // 옵션 더미 데이터

INSERT INTO `option` (`id`, `price`, `multi_select`, `group`, `name`) VALUES
(1, 0, 0, 'temperature', 'HOT'),
(2, 0, 0, 'temperature', 'ICE'),
(3, 0, 0, 'size', 'Regular'),
(4, 1000, 0, 'size', 'Large'),
(5, 1500, 0, 'size', 'Kokee-Large'),
(6, 0, 0, 'sugar_content', '0%'),
(7, 0, 0, 'sugar_content', '30%'),
(8, 0, 0, 'sugar_content', '50%'),
(9, 0, 0, 'sugar_content', '70%'),
(10, 0, 0, 'sugar_content', '100%'),
(11, 0, 0, 'ice_content', '없음'),
(12, 0, 0, 'ice_content', '적게'),
(13, 0, 0, 'ice_content', '보통'),
(14, 0, 0, 'ice_content', '많이'),
(15, 0, 0, 'topping', '추가 안 함'),
(16, 500, 1, 'topping', '타피오카 펄'),
(17, 500, 1, 'topping', '화이트 펄'),
(18, 1000, 1, 'topping', '밀크폼'),
(19, 1000, 1, 'topping', '코코넛'),
(20, 1000, 1, 'topping', '알로에');


-- // 상품 옵션 더미 데이터
-- TODO: 상품 옵션 더미데이터 수정해야함... 근데 존나 많음...

-- INSERT INTO `product_option` (`option_id`, `product_id`) VALUES
-- (1, 1),
-- (1, 2),
-- (1, 3),
-- (2, 1),
-- (2, 2),
-- (2, 3),
-- (6, 3),
-- (7, 3),
-- (8, 3);

-- // 지점 재고 더미 데이터

INSERT INTO `branch_inventory` (`quantity`, `unavailable`, `branch_id`, `ingredient_id`) VALUES
(100, 0, 1, 1),
(100, 0, 1, 2),
(100, 0, 1, 3),
(100, 0, 2, 1),
(100, 0, 2, 2),
(100, 1, 2, 3),
(5, 0, 3, 1),
(3, 0, 3, 2),
(0, 1, 3, 3);

-- // 멤버십 더미 데이터

INSERT INTO `membership` (`point_rate`, `required_payment_amount`, `name`, `product_id`) VALUES
(0.01, 0, 'BRONZE', 1),
(0.02, 20000, 'SILVER', 2),
(0.03, 30000, 'GOLD', 3);

-- // 회원 더미 데이터

INSERT INTO `member` (`point`, `stamp`, `total_purchase_count`, `membership_id`, `total_payment_amount`, `email`, `password`, `phone`, `real_name`, `user_name`, `authority`) VALUES
(0, 0, 0, 1, 0, 'test1@test.com', 'test', '010-1111-1111', 'test1', 'test1', 'USER'),
(0, 0, 0, 1, 0, 'test2@test.com', 'test', '010-2222-2222', 'test2', 'test2', 'USER'),
(0, 0, 0, 1, 0, 'test3@test.com', 'test', '010-3333-3333', 'test3', 'test3', 'USER');

-- // 쿠폰 더미 데이터

INSERT INTO `coupon` (`member_id`, `product_id`, `product_order_id`) VALUES
(1, 1, null),
(2, 2, null),
(3, 3, null);

-- // 자주 묻는 질문 더미 데이터

INSERT INTO `faq` (`date`, `category`, `title`, `content`) VALUES
('2024-01-01', '카테고리1', '제목1', '내용1'),
('2024-01-01', '카테고리2', '제목2', '내용2'),
('2024-01-01', '카테고리3', '제목3', '내용3');

-- // 지점 품절 더미 데이터

INSERT INTO `product_unavailable` (`branch_id`, `product_id`) VALUES
(2, 2);

-- // 공지사항 더미 데이터

INSERT INTO `notice` (`title`, `text`, `date`, `view`, `version`) VALUES
('제목1', '내용1', '2024-01-01', 1, 1),
('제목2', '내용2', '2024-01-02', 2, 1),
('제목3', '내용3', '2024-01-03', 3, 1);


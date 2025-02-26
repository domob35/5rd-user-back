-- Insert Signature drinks
INSERT INTO product (pd_id, pd_category, pd_name, pd_price, image, `desc`, iced_only, extra_price) VALUES
(1, 'Signature', '블랙 리치', 5000, './img/Signature/Black Lychee.png', '블랙티와 리치의 조화로운 맛을 느낄 수 있는 음료입니다.', false, 500),
(2, 'Signature', '드래곤과 장미', 5000, './img/Signature/Dragon and Rose.png', '청포도와 장미의 조화로운 맛을 느낄 수 있는 음료입니다.', false, 500),
(3, 'Signature', '나비의 꿈', 5000, './img/Signature/Dream Of Butterfly.png', '청포도와 자몽의 조화로운 맛을...', false, 500),
(4, 'Signature', '조지아 온 마이 마인드', 5000, './img/Signature/Georgia On My Mind.png', '제주 한라봉의 달콤한 맛을 느낄 수 있는 음료입니다.', false, 500),
(5, 'Signature', '망고 패션후르츠', 5000, './img/Signature/Mango Passionfruit.png', '망고와 패션후르츠의 조화로운 맛을 느낄 수 있는 음료입니다.', false, 500),
(6, 'Signature', '퍼플 러브', 5000, './img/Signature/Purple Love.png', '블루베리의 달콤한 맛을 느낄 수 있는 음료입니다.', false, 500),
(7, 'Signature', '샌프란시스코의 장미', 5000, './img/Signature/Rose From San Francisco.png', '자몽의 아름다운 맛을 느낄 수 있는 음료입니다.', false, 500);

-- Insert Cold Cloud drinks
INSERT INTO product (pd_id, pd_category, pd_name, pd_price, image, `desc`, iced_only, extra_price) VALUES
(8, 'Cold Cloud', '브라운슈가 콜드브루', 5000, './img/Cold Cloud/Brown Sugar Cold Brew.png', '흑설탕과 우유의 시원한 맛을 느낄 수 있는 음료입니다.', false, 500),
(9, 'Cold Cloud', '브라운슈가 달고나라떼', 5000, './img/Cold Cloud/Brown Sugar Dalgonatte.png', '기본 설명', false, 500),
(10, 'Cold Cloud', '오레오 브라운슈가', 5000, './img/Cold Cloud/Oreo Brown Sugar.png', '기본 설명', false, 500),
(11, 'Cold Cloud', '솜사탕 콜드브루', 5000, './img/Cold Cloud/Sweet Cloud Cold Brew.png', '기본 설명', false, 500),
(12, 'Cold Cloud', '솜사탕 그린티', 5000, './img/Cold Cloud/Sweet Cloud Green Brew.png', '기본 설명', false, 500);

-- Insert Ice Blended drinks
INSERT INTO product (pd_id, pd_category, pd_name, pd_price, image, `desc`, iced_only, extra_price) VALUES
(13, 'Ice Blended', '용과', 5000, './img/Ice Blended/Dragonfruit.png', '기본 설명', false, 500),
(14, 'Ice Blended', '리치', 5000, './img/Ice Blended/Lychee.png', '기본 설명', false, 500),
(15, 'Ice Blended', '망고', 5000, './img/Ice Blended/Mango.png', '기본 설명', false, 500),
(16, 'Ice Blended', '아이스망고 패션후르츠', 5000, './img/Ice Blended/Ice Mango Passionfruit.png', '기본 설명', false, 500),
(17, 'Ice Blended', '말차', 5000, './img/Ice Blended/Matcha.png', '기본 설명', false, 500),
(18, 'Ice Blended', '오레오', 5000, './img/Ice Blended/Oreo.png', '기본 설명', false, 500),
(19, 'Ice Blended', '피나콜라다', 5000, './img/Ice Blended/Pina Colada.png', '기본 설명', false, 500),
(20, 'Ice Blended', '스트로베리', 5000, './img/Ice Blended/Strawberry.png', '기본 설명', false, 500),
(21, 'Ice Blended', '타로', 5000, './img/Ice Blended/Taro.png', '기본 설명', false, 500);

-- Insert KOKEE Fruit Tea drinks
INSERT INTO product (pd_id, pd_category, pd_name, pd_price, image, `desc`, iced_only, extra_price) VALUES
(22, 'KOKEE Fruit Tea', '청포도 차', 5000, './img/Fruit Tea/Green Grape Tea.png', '기본 설명', false, 500),
(23, 'KOKEE Fruit Tea', '허니 자몽 블랙티', 5000, './img/Fruit Tea/Honey Grapefruit Black Tea.png', '기본 설명', false, 500),
(24, 'KOKEE Fruit Tea', '오렌지 상그리아', 5000, './img/Fruit Tea/Orange Sangria.png', '기본 설명', false, 500),
(25, 'KOKEE Fruit Tea', '스트로베리 버진 모히토', 5000, './img/Fruit Tea/Strawberry Virgin Mojito.png', '기본 설명', false, 500);

-- Insert Milk Tea drinks
INSERT INTO product (pd_id, pd_category, pd_name, pd_price, image, `desc`, iced_only, extra_price) VALUES
(26, 'Milk Tea', '브라운슈가 밀크티', 5000, './img/Milk Tea/Brown Sugar Milk Tea.png', '기본 설명', false, 500),
(27, 'Milk Tea', '클래식 차이티', 5000, './img/Milk Tea/Classic Thai Tea.png', '기본 설명', false, 500),
(28, 'Milk Tea', '코코넛 밀크티', 5000, './img/Milk Tea/Coconut Milk Tea.png', '기본 설명', false, 500),
(29, 'Milk Tea', '커피 밀크티', 5000, './img/Milk Tea/Coffee Milk Tea.png', '기본 설명', false, 500),
(30, 'Milk Tea', '허니 밀크티', 5000, './img/Milk Tea/Honey Milk Tea.png', '기본 설명', false, 500),
(31, 'Milk Tea', '하우스 밀크티', 5000, './img/Milk Tea/House Milk Tea.png', '기본 설명', false, 500),
(32, 'Milk Tea', '코키 밀크티', 5000, './img/Milk Tea/KOKEE Milk Tea.png', '기본 설명', false, 500),
(33, 'Milk Tea', '말차 라떼', 5000, './img/Milk Tea/Matcha Latte.png', '기본 설명', false, 500),
(34, 'Milk Tea', '오레오 밀크티', 5000, './img/Milk Tea/Oreo Milk Tea.png', '기본 설명', false, 500),
(35, 'Milk Tea', '스트로베리 밀크티', 5000, './img/Milk Tea/Strawberry Milk Tea.png', '기본 설명', false, 500);

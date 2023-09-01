package com.green.winey_final.cart;

import com.green.winey_final.cart.model.CartInsDto;
import com.green.winey_final.cart.model.CartUpdDto;
import com.green.winey_final.cart.model.CartVo;
import com.green.winey_final.cart.model.CartdelDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wine")
@RequiredArgsConstructor
@Tag(name = "장바구니")
public class CartController {

    private final CartService service;


    @PostMapping("/cartadd") //장바구니 추가
    @Operation(summary = "장바구니 추가", description =
            "productId: 상품 PK값,<br>"
                    + "quantity: 수량,<br>" +
                    "return값이 정상인 경우 저장된 quantity 수. 나머지는 에러반환")
    public int postCart(@RequestBody CartInsDto dto) {
        return service.insCart(dto);
    }

    @GetMapping("/filledcart") //장바구니 출력(buy_yn이 0인거만 출력되게)
    @Operation(summary = "장바구니 출력", description =
            "userId: 유저PK값, <br>"
                    + "quantity: 수량, <br>"
                    + "nmKor: 한글 이름, <br>"
                    + "nmEng: 영어 이름, <br>"
                    + "price: 가격, <br>"
                    + "salePrice: 할인가격, <br>"
                    + "pic: 사진, <br>")
    public List<CartVo> getFilledCart() {
        return service.selCart();
    }

    @DeleteMapping("/cartdelete") // 장바구니에 있는 상품 삭제
    @Operation(summary = "장바구니 삭제", description =
            "cartId: cart pk값 <br>")
    public int delCart(@RequestBody CartdelDto dto) {
        return service.delCart(dto);
    }


    @PutMapping("/productquantity") //장바구니 수량 변경
    @Operation(summary = "장바구니 수량변경", description =
            "quantity: 수량, <br>"
                    + "cartId: cart pk값 <br>")
    public int putCart(@RequestParam long cartId, @RequestParam int quantity) {
        CartUpdDto dto = new CartUpdDto();
        dto.setCartId(cartId);
        dto.setQuantity(quantity);
        service.updCart(dto);
        return 1;
    }

}

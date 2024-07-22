package com.example.lambdapattern._13decorator;

import com.example.lambdapattern._13decorator.Extras.Milk;
import com.example.lambdapattern._13decorator.Extras.Mocha;
import org.junit.jupiter.api.Test;

public class CoffeeOrderTest {

    @Test
    void Test3() {
        Coffee coffee = new Coffee(Cup.HOT_SMALL, CoffeeBean.Arabica);
        new Milk().andThen(new Mocha()).add(coffee);

        System.out.println(coffee);
    }

    @Test
    void Test_추가주문객체add방식() {
        Coffee coffee = new Coffee(Cup.HOT_SMALL, CoffeeBean.Arabica)
                .addExtraOrder(new Milk())
                .addExtraOrder(new Mocha());

        System.out.println(coffee);
    }

    @Test
    void Test2_추가주문배열방식() {
        Coffee coffee = new Coffee(Cup.HOT_SMALL, CoffeeBean.Arabica)
                .addExtraOrder(new Milk(),new Mocha());

        System.out.println(coffee);
    }

    @Test
    void Test_추가주문객체add방식2() {
        Coffee coffee = new Coffee(Cup.HOT_SMALL, CoffeeBean.Arabica)
                .addExtraOrder(Milk::new).addExtraOrder(Mocha::new);

        System.out.println(coffee);
    }

    @Test
    void Test_추가주문객체add방식3() {
        Coffee coffee = new Coffee(Cup.HOT_SMALL, CoffeeBean.Arabica)
                .addExtraOrder(Milk::new, Mocha::new, Mocha::new);

        System.out.println(coffee);
    }



}

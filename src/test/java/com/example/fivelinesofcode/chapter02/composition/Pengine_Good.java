package com.example.fivelinesofcode.chapter02.composition;

public class Pengine_Good implements Bird {
    private Bird bird = new CommonBird();   //컴포지션

    @Override
    public boolean hasBeak() {
        return bird.hasBeak();  //호출 위임
    }

    @Override
    public boolean canFly() {
        return false;
    }
}

/**
 * 컴포지션을 사용하여 CommonBird의 기능을 재사용하고, canFly() 메서드만 오버라이드하여 사용하였다.
 *
 * 만약 Bird 인터페이스에 새로운 메서드 canSwim()이 추가되면, CommonBird와 Pengine(_Good) 클래스 모두
 * 수정이 되어야 한다.
 *
 * 이는 컴포지션을 사용했기 때문에 구현이 명확해지는 부분이다.
 *
 *
 *
 * 컴포지션을 사용하지 않고, 상속을 사용하면 CommonBird에만
 * 새로운 canSwim()을 추가하면 되기 때문에 수정은 쉽지만,
 * CommonBird를 상속받은 Bird 클래스들이 모두 CommonBird.canSwim()으로 처리가 되어
 * Side Effect가 발생할 수 있다.
 *
 *
 * extends 보다는 implements를 사용하여 상속을 피하고,
 * 컴포지션을 사용해 명확하게 구현을 위임하는 것이 좋다.
 */
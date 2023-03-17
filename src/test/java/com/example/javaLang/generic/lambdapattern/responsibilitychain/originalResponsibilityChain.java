package com.example.javaLang.generic.lambdapattern.responsibilitychain;

import org.junit.jupiter.api.Test;

public class originalResponsibilityChain {
    public abstract class ProcessingObject<T> {
        protected ProcessingObject<T> nextProcessor;    //자신과 동일한 객체를 멤버로 받음

        abstract protected T handleWork(T input);

        //하위 객체 등록
        public void setSuccessor(ProcessingObject<T> successor) {
            this.nextProcessor = successor;
        }

        public T handle(T input) {
            T result = handleWork(input);

            if(nextProcessor != null) {
                return nextProcessor.handle(result);    //템플릿메서드 패턴도 사용됨
            }

            return result;
        }
    }

    public class HeaderTextPRocessing extends ProcessingObject<String> {
        public String handleWork(String text) {
            return "[Header]" + text;
        }
    }

    public class SpellCheckerPRocessing extends ProcessingObject<String> {

        @Override
        protected String handleWork(String text) {
            return text.replaceAll("labda", "Lambda");
        }
    }

    @Test
    void 의무체인패턴_테스트() {
        ProcessingObject<String> p1 = new HeaderTextPRocessing();
        ProcessingObject<String> p2 = new SpellCheckerPRocessing();

        p1.setSuccessor(p2);
        String result = p1.handle("Isn't labda really usable?!!");

        System.out.println(result);
    }
}

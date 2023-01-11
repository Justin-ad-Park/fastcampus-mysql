package com.example.javaLang.generic.wildcards;

import org.junit.jupiter.api.Test;

class CourseTest {

    private static void registerCourse(Course<?> course) {
        return;
    }

    private static void registerCourseStudentByWildcards(
            Course<? extends Student> course
    ) {
        return;
    }

    @Test
    void 와일드카드테스트() {
        registerCourse(new Course<Person>("일반인 과정", 5));
        registerCourse(new Course<Student>("학생 과정", 5));
        registerCourse(new Course<Worker>("근로자 과정", 5));
        registerCourse(new Course<HighStudent>("고등학생 과정", 5));


        //registerCourseStudentByWildcards(new Course<Person>("일반인 과정", 5));   //캐스팅 에러 발생함
        registerCourseStudentByWildcards(new Course<Student>("학생 과정", 5));
        //registerCourseStudentByWildcards(new Course<Worker>("근로자 과정", 5));   //캐스팅 에러 발생함
        registerCourseStudentByWildcards(new Course<HighStudent>("고등학생 과정", 5));
    }
}
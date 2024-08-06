package com.example.javaLang.generic.streamtest.collect.testresult;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class DownstreamCollectorExample {


    private static @NotNull List<Employee> getEmployees() {
        return List.of(
                new Employee("Alice", "Engineering", 50000),
                new Employee("Bob", "Engineering", 60000),
                new Employee("Charlie", "HR", 70000),
                new Employee("Diana", "HR", 80000)
        );
    }

    @Test
    void reducing() {
        Collector<Employee, ?, Integer> collector = Collectors.reducing(0, Employee::getSalary, Integer::sum);

        Map<String, Integer> result = getEmployees().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, collector));

        System.out.println(result);
    }

    @Test
    void reducing2() {
        var collector = Collectors.reducing(0, (Employee e) -> e.getSalary(), Integer::sum);

        var result = getEmployees().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, collector));

        System.out.println(result);
    }

    @Test
    void test() {
        List<Employee> employees = getEmployees();

        // Group employees by department and calculate average salary in each department
        Map<String, Double> averageSalaryByDepartment = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.averagingInt(Employee::getSalary)
                ));

        System.out.println(averageSalaryByDepartment);


        var collectNameToSet = Collectors.mapping(Employee::getName, Collectors.toSet());

        Map<String, Set<String>> result = employees.stream()
                .collect(Collectors.groupingByConcurrent(Employee::getDepartment,
                        collectNameToSet));

        System.out.println(result);
    }

    @Test
    void toSetTest() {
        Set<Employee> employeeSet = getEmployees().stream()
                .collect(Collectors.toSet());

        employeeSet.forEach(System.out::println);
    }

    @Test
    void toSetName() {
        Set<String> employeeNames = getEmployees().stream()
                .collect(Collectors.mapping(Employee::getName, Collectors.toSet()));

        employeeNames.forEach(System.out::println);
    }

    @Test
    void toMapTest() {
        Map<String, Employee> employeeMap = getEmployees().stream()
                .collect(Collectors.toMap(Employee::getName, employee -> employee));

        employeeMap.forEach((name, employee) -> System.out.println(name + " -> " + employee));
    }

    @Test
    void groupByTest() {
        var departments = getEmployees().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));

        System.out.println(departments);
    }


    static class Employee {
        private final String name;
        private final String department;
        private final int salary;

        public Employee(String name, String department, int salary) {
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        public String getName() {
            return name;
        }

        public String getDepartment() {
            return department;
        }

        public int getSalary() {
            return salary;
        }

        @Override
        public String toString() {
            return "{" +
                    "name='" + name + '\'' +
                    ", department='" + department + '\'' +
                    ", salary=" + salary +
                    '}';
        }
    }
}

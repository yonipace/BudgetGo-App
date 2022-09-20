package app.core.util.xlsx;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class FieldData {

    public static void main(String[] args) {

        Person p = new Person(1, "yoni", 31);
        Person p2 = new Person(2, "yoni2", 27);

        List<Person> list = List.of(p, p2);


        PersonExcelWriter personExcelWriter = new PersonExcelWriter(list);


        try {
            personExcelWriter.writeToExcel("src/main/resources/static/files", "Person Sheet");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Field[] fields = p.getClass().getDeclaredFields();


//        Arrays.stream(fields).forEach(field -> System.out.println(field.getName()));

//        List<Person> list = List.of(p, p2);

//        list.forEach(person -> {
//            ReflectionUtils.doWithFields(person.getClass(), field -> {
//                System.out.println("Field name: " + field.getName());
//                field.setAccessible(true);
//                System.out.println("Field value: " + field.get(person));
//            });
//        });

//        ReflectionUtils.doWithFields(p.getClass(), field -> {
//            System.out.println("Field name: " + field.getName());
//            field.setAccessible(true);
//            System.out.println("Field value: " + field.get(p));
//        });


    }

}

package ro.ubb.catalog.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * Created by radu.
 */
public class ClientApp {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "ro.ubb.catalog.client.config"
                );

        Console console = context.getBean(Console.class);
        console.runConsole();
    }

//        String url = "http://localhost:8080/api/students";

//        StudentDto savedStudent = restTemplate.postForObject(
//                URL,
//                new StudentDto("sn1", "s1", 111),
//                StudentDto.class);
//        System.out.println("savedStudent: " + savedStudent);
//
//        System.out.println("update:");
//        savedStudent.setName("updated");
//        restTemplate.put(URL + "/{id}", savedStudent, savedStudent.getId());
//        printAllStudents(restTemplate);
//
//        System.out.println("delete: ");
//        restTemplate.delete(URL + "/{id}", savedStudent.getId());
//        printAllStudents(restTemplate);
//
//        System.out.println("bye ");
//    }
//
//    private static void printAllStudents(RestTemplate restTemplate) {
//        StudentsDto allStudents = restTemplate.getForObject(URL, StudentsDto.class);
//        System.out.println(allStudents);
//    }
}

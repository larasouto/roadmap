package streams;

import static streams._Stream.Gender.*;
import java.util.Arrays;
import java.util.List;
//import java.util.stream.Collectors;

public class _Stream {
  public static void main(String[] args) {

    List<Person> people = Arrays.asList(
      new Person("Matheus", MALE),
      new Person("Lara", FEMALE),
      new Person("Bob", PREFER_NOT_TO_SAY)
    );

    people.stream()
          .map(person -> person.name)
          .mapToInt(String::length)
          //.collect(Collectors.toSet())
          .forEach(System.out::println);

    boolean containsOnlyFemales = people.stream()
            .allMatch(person -> FEMALE.equals(person.gender));

    System.out.println(containsOnlyFemales);
  }
    static class Person {
      private final String name;
      private final Gender gender;

      Person(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
      }

      @Override
      public String toString() {
        return "Person{" +
        "name='" + name + '\'' +
        ", gender=" + gender + 
        '}';

      }
    }

    enum Gender {
      MALE, FEMALE, PREFER_NOT_TO_SAY
    }
}


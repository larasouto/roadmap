package optionals;

import java.util.Optional;

public class OptionalTutorial {
  public static void main(String[] args) {
    Optional<Cat> optionalCat = findCatByName("Fred");

    if (optionalCat.isPresent()) {
      System.out.println(optionalCat.get().getAge());
    }
    else{
      System.out.println(0);
    }

    optionalCat.map(Cat::getAge)
      .orElse(0);
  }

  private static Optional<Cat> findCatByName(String name) {
    Cat cat = new Cat(name, 3);
    return Optional.ofNullable(cat);
  }
}

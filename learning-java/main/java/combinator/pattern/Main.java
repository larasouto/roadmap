package combinator.pattern;

import java.time.LocalDate;

import static combinator.pattern.CustomerRegistrationValidator.*;

public class Main {

    public static void main(String[] args) {
        Customer customer = new Customer(
                "Lara",
                "lara@gmail.com",
                "+55999670485",
                LocalDate.of(2001, 5,19)
        );

        ValidationResult result = isEmailValid()
                .and(isPhoneNumberValid())
                .and(isAnAdult())
                .apply(customer);

        System.out.println(result);

        if (result != ValidationResult.SUCCESS) {
            throw new IllegalStateException(result.name());
        }
    }
}
# idx-commons
IDX commons libraries

## Check

The **Frostnova Check Framework** offers a `Check` class for
**parameter/value validation** using a rich and expandable set of utility functions.

Maven:

    <dependency>
        <groupId>ch.frostnova</groupId>
        <artifactId>check</artifactId>
        <version>1.0.0</version>
    </dependency>
    
Gradle:

    compile 'ch.frostnova:check:1.0.0'

### Usage

Checks can be performed on:

- **optional values** (value can be null, checks only executed when it's not null)
- **required values** (value checked to not to be null first, remaining checks only executed when it's not null)

Failing checks result in an `IllegalArgumentException`, with a meaningful message which check has failed, including the parameter name.
<br>
Example: `java.lang.IllegalArgumentException: 'customer id' must have at least 6 characters`

Example usage:

- Check that a value (here: parameter 'url' named 'connection url') is present (not null):

  `Check.required(url, "connection url");`

- Check that the name is not null, not blank and has no more than 20 characters:

  `Check.required(name, "name", CheckString.notBlank(), CheckString.max(20));`

- Check that an optional value is a finite number (not NaN or +/-Infinite) and > 0:

  `Check.optional(val, "value", CheckNumber.finite(), CheckNumber.greaterThan(0));`

- Check that a collection is not empty, and does not contain null-Elements:

  `Check.required(items, "items", CheckCollection.notEmpty(), CheckCollection.noNullElements());`

- Check that a String token has an expected format (alphanumeric-uppercase and exactly 32 characters):

  `Check.required(token, "token", CheckString.format("[A-Z0-9]{32}", "alphanumeric uppercase"));`

Custom checks can be created with any implementation of the `Verify` functional interface. This interface also provides static methods
to create implementations based on `Predicate` and predefined or dynamic failure messages.

Examples for custom validation:

Verify that a (zoned) date must be in the future

    Verify<ZonedDateTime> futureDate = d -> {
        if (!d.isAfter(ZonedDateTime.now())) {
         throw new IllegalArgumentException("must be in the future");
        }
    };
    Check.required(date, "execution date", futureDate);
    
or    

    Check.required(date, "execution date",
        Verify.that(d -> !d.isAfter(ZonedDateTime.now()), "must be in the future"));

## Keygen

Key generator based on (customizable) alphabets.

Can be used to create keys of a given

* alphabet (allowed characters)
* length
* unit (bits, bytes, digists/characters)
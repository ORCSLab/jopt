package jopt.core.utils;

import lombok.NonNull;

/**
 * Class that implements useful methods for comparing real numbers based on a 
 * least significant difference.
 *
 * <p>
 * Given two numbers, x<sub>1</sub> and x<sub>2</sub>, and also a parameter of 
 * least significant difference p. The values x<sub>1</sub> and x<sub>2</sub> 
 * are considered different if and only if the absolute value of the difference 
 * between x<sub>1</sub> and x<sub>2</sub> is greater than or equal to p.
 *
 * <p>
 * Being x<sub>1</sub> and x<sub>2</sub> considered different, the comparison of
 * greater than and less than is done as is usually carried out.
 */
public final class NumberComparator {

    /**
     * Default value for least significant difference. Its value is equal to 1.0E-6.
     */
    public static final double DEFAULT_SIGNIFICANCE = 1.0E-6;

    /**
     * Compares its two arguments for order. Returns a negative integer, zero,
     * or a positive integer as the first argument is less than, equal to, or
     * greater than the second.
     *
     * <p>
     * It is equivalent to use {@link #compare(Number, Number, double)
     * compare(first, second, precision)} with {@code precision} equal to
     * {@link #DEFAULT_SIGNIFICANCE}.
     *
     * @param   first
     *          The first number
     * @param   second
     *          The second number
     *
     * @return  A negative integer, zero, or a positive integer as the first
     *          number is less than, equal to, or greater than the second number
     *
     * @throws  NullPointerException
     *          If {@code first} or {@code second} are null references
     */
    public static int compare(Number first, Number second) {
        return NumberComparator.compare(first, second, DEFAULT_SIGNIFICANCE);
    }

    /**
     * Compares its two arguments for order. Returns a negative integer, zero,
     * or a positive integer as the first argument is less than, equal to, or
     * greater than the second.
     *
     * @param   first
     *          The first number
     * @param   second 
     *          The second object to be compared
     * @param   precision
     *          Value for least significant difference
     *
     * @return  A negative integer, zero, or a positive integer as the first
     *          number is less than, equal to, or greater than the second number
     *
     * @throws  NullPointerException
     *          If {@code first} or {@code second} are null references
     */
    public static int compare(@NonNull Number first, @NonNull Number second, double precision) {

        if (Math.abs(first.doubleValue() - second.doubleValue()) < precision) {
            return 0;
        } else if (first.doubleValue() < second.doubleValue()) {
            return -1;
        } else { // if (first.doubleValue() > second.doubleValue())
            return 1;
        }
    }

}

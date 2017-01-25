package com.examples.shop;

import java.text.NumberFormat;
import java.util.Arrays;

public class Fruitshop
{
    public static void main(String [] args)
    {
        if (args.length == 0)
        {
            System.out.println("Run with the product list as a quoted string \"[ Apple, Apple, Orange, Orange, Orange, Apple ]\"");
            return;
        }

        final Fruitshop shop = new Fruitshop();
        System.out.println(shop.scannedProducts(args[0]));
    }

    /**
     * Calculate for a list of ProductId's the total cost.
     *
     * @param productIds - A string of the product ID's e.g.
     *                     "Apple Apple, Orange, Apple", "[ Apple Apple, Orange, Apple ]"
     * @return A string of the calculated value of the products e.g. "£2.05"
     * @throws RuntimeException - Should an invalid product Id be given, to ensure fail-fast behaviour
     */
    public String scannedProducts(String productIds)
    {
        // Allow for removal of wrapping '[' ']' markers
        {
            final String cleanedString = productIds.trim();
            productIds =  (cleanedString.startsWith("[") && cleanedString.endsWith("]"))
                    ? cleanedString.substring(1,cleanedString.length()-2)
                    : cleanedString;
            if (productIds.length() == 0)
                return _currencyFormat.format( 0 );
        }

        // Translate [ Apple, Apple, Orange, Apple ] -> "Apple", "Apple", "Orange", "Apple"
        final String[] products = productIds.split(",");
        if (products.length == 0)
            return _currencyFormat.format( 0 );

        // Map the keys passed in to to the actual product instances
        final Product[] productsArray = Arrays.stream(products).map(Fruitshop::mapToProduct).toArray(size -> new Product[size]);
        return _currencyFormat.format( calculateTotalProducts(productsArray) );
    }

    /**
     * For the given set of products, calculate the total cost representation
     * @param productsArray - The array of references to "Product" instances to calculate the cost against
     * @return - The total charge to be assigned e.g. "2.05"
     */
    public final double calculateTotalProducts(final Product[] productsArray)
    {
        // Calculate the non discounted total value in base currency units
        long totalValue = Arrays.stream(productsArray).mapToInt(p->p.priceInPence).sum();

        // Apply discount for (buy one / get one free on apples)
        totalValue -= calcApplesDiscount(productsArray);

        // Apply discount for (3 for the price of 2 on Oranges)
        totalValue -= calcOrangessDiscount(productsArray);

        // Translate from base units of pence to GBP
        return totalValue / 100d;
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Discount definitions
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Return the amount to deduct for (buy one, get one free on apples)
     * @param products - The array of product pointers / references, purchased by the customer
     * @return - The total value to deduct in base SI units of currency. (e.g. pence for GBP)
     */
    public final long calcApplesDiscount(final Product[] products)
    {
        final long appleCount = Arrays.stream(products).filter(p -> p.equals(_apple)).count();
        return ((appleCount / 2) * _apple.priceInPence);
    }

    /**
     * Return the amount to deduct for (3 for the price of 2 on oranges)
     * @param products - The array of product pointers / references, purchased by the customer
     * @return - The total value to deduct in base SI units of currency. (e.g. pence for GBP)
     */
    public final long calcOrangessDiscount(final Product[] products)
    {
        final long orangeCount = Arrays.stream(products).filter(p -> p.equals(_orange)).count();
        return (orangeCount / 3) * _orange.priceInPence;
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Product Id -> Instance mapping
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Returns a reference to the product the productId references.
     *
     * @param productId - The string that identifies the product Id. (Case sensitive).
     *                    The String will be trimmed, in case of inaccurate input.
     * @return A reference to the shared instance of product the product Id represents.
     * @throws RuntimeException Should the product id not match to ensure fail-fast behaviour as this incorrect charging
     *                          is unacceptable.
     */
    public static final Product mapToProduct(final String productId)
    {
        // If the product range increases, then it may become more efficient to store the productId's in a collection,
        // and lookup the instance, based on the key.
        final String cleanedProductId = productId.trim();
        if (cleanedProductId.equals(_apple.productId))
            return _apple;
        else if (cleanedProductId.equals(_orange.productId))
            return _orange;

        throw new RuntimeException("Contact support - there is an issue with the product in this list \""+productId+"\"");
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * The common number formatter used to format numbers into a currency based notation.
     */
    final static NumberFormat _currencyFormat = NumberFormat.getCurrencyInstance();


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Product Definitions
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * It is assumed that there will be many instances of this class running within the JVM, therefore these are
     * kept as constant's for reuse.
     */
    public final static Product _apple = new Product("Apple",60);
    public final static Product _orange = new Product( "Orange", 25);

}

package com.examples.shop;

/**
 * This represents a product sold by the store
 */
public class Product
{

    /**
     * Construct a product with the given product UID (productId) and the price in the base SI units of pence for GBP.
     * @param productId - The UID of the product (E.g. Apple / Orange)
     * @param priceInPence - The price in pence of the product
     * @throws RuntimeException - Should the price be less than 0 (paid to receive),
     *                            or a product id is not specified (null or empty in length).
     */
    public Product(final String productId, final int priceInPence)
    {
        if (priceInPence < 0)
            throw new RuntimeException("Programatic error: Price must be 0 or greater");
        if (productId == null || productId.length() == 0)
            throw new RuntimeException("Programatic error: Product Id is required to uniquely identify the product");

        this.productId = productId;
        this.priceInPence = priceInPence;
    }

    // The unique key used to identify this products instance
    protected final String productId;

    // The products value is stored in pence to prevent rounding errors with FP calculations
    protected final int priceInPence;
}

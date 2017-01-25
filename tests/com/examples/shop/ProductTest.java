package com.examples.shop;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test
public class ProductTest
{

    /**
     * Test product construction constraints
     */

    /**
     * A native price should not be allowed.
     */
    @Test(expectedExceptions = RuntimeException.class)
    public void testProductInvalidPrice() throws Exception
    {
        new Product("MyItem",-1);
    }

    /**
     * An item may be free such as a catalogue put in the bag
     */
    @Test
    public void testProductFreePrice() throws Exception
    {
        final int testPrice = 0;
        Product testItem = new Product("MyItem",testPrice);
        assertEquals(testItem.priceInPence,testPrice);
    }

    /**
     * Positive priced items are expected
     */
    @Test
    public void testProductPositivePrice() throws Exception
    {
        final int testPrice = 125;
        Product testItem = new Product("MyItem",testPrice);
        assertEquals(testItem.priceInPence,testPrice);
    }

    /**
     * Check that null definitions for the products ID (Key if this was in a collection) are not allowed,
     * and fail-fast.
     */
    @Test(expectedExceptions = RuntimeException.class)
    public void testProductInvalidProductIdAsNull() throws Exception
    {
        new Product(null,100);
    }

    /**
     * Check that empty definitions for the products ID (Key if this was in a collection) are not allowed
     * and fail-fast.
     */
    @Test(expectedExceptions = RuntimeException.class)
    public void testProductInvalidProductIdAsEmpty() throws Exception
    {
        new Product("",100);
    }

    /**
     * Check that a string entered for the product ID is allowed
     */
    @Test
    public void testProductId() throws Exception
    {
        final String productId = "MyItem";
        Product testItem = new Product(productId,0);
        assertEquals(testItem.productId,productId);
    }

}
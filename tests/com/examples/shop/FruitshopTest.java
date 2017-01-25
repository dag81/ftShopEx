package com.examples.shop;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test
public class FruitshopTest {

    Fruitshop shop = null;

    @BeforeMethod
    public void setUp() throws Exception {
        shop = new Fruitshop();
    }

    /**
     * Test string -> product instance mappings work for each fixed item
     */

    /**
     * Check an empty string results in an error
     */
    @Test(expectedExceptions = RuntimeException.class)
    public void testMapToProduct() throws Exception
    {
        Fruitshop.mapToProduct("");
    }

    /**
     * Validate "Apple" validates to the Apple instance
     */

    @Test
    public void testMapToProductApple() throws Exception
    {
        assertEquals(Fruitshop.mapToProduct("Apple"), Fruitshop._apple);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testMapToProductAppleInvalidCase() throws Exception
    {
        assertEquals(Fruitshop.mapToProduct("apple"), Fruitshop._apple);
    }

    /**
     * Validate "Orange" validates to the Orange instance
     */

    @Test
    public void testMapToProductOrange() throws Exception
    {
        assertEquals(Fruitshop.mapToProduct("Orange"), Fruitshop._orange);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testMapToProductOrangeInvalidCase() throws Exception
    {
        assertEquals(Fruitshop.mapToProduct("orange"), Fruitshop._apple);
    }

    /**
     * Validate combinations of products are being added as expected
     */

    @Test
    public void testScannedProducts() throws Exception {
        assertEquals(shop.scannedProducts(""), "£0.00");
        assertEquals(shop.scannedProducts("Apple"), "£0.60");
        assertEquals(shop.scannedProducts("Orange"), "£0.25");
        assertEquals(shop.scannedProducts("Apple, Apple"), "£1.20");
        assertEquals(shop.scannedProducts("Orange, Apple"), "£0.85");
        assertEquals(shop.scannedProducts("Orange, Orange, Orange"), "£0.75");

        assertEquals(shop.scannedProducts("Apple, Apple, Orange, Apple"), "£2.05");
    }

    /**
     * Validate that addition logic is being applied during the calculation
     */

    @Test
    public void testCalculateTotalProducts() throws Exception
    {
        assertEquals(shop.calculateTotalProducts(new Product[] {}),0d);
        assertEquals(shop.calculateTotalProducts(new Product[] { Fruitshop._apple}), Fruitshop._apple.priceInPence / 100d);
        assertEquals(shop.calculateTotalProducts(new Product[] { Fruitshop._orange}), Fruitshop._orange.priceInPence / 100d);
        assertEquals(shop.calculateTotalProducts(new Product[] { Fruitshop._orange, Fruitshop._apple}),(Fruitshop._apple.priceInPence + Fruitshop._orange.priceInPence) / 100d);
    }

}
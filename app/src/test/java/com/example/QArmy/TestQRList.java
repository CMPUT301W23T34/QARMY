/*
 * TestQRList
 *
 * Version: 1.0
 *
 * Date: 2023-03-09
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 */

package com.example.QArmy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.QRList;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Provide tests for the QRList class.
 * @author Kai Luedemann
 * @version 1.0
 * @see QRList
 */
public class TestQRList {

    /**
     * Create a mock QR code
     * @return Empty QR code
     */
    public QRCode MockCode() {
        return new QRCode();
    }

    /**
     * Create a mock QR list
     * @return Empty QR list
     */
    public QRList MockQRList() {
        return new QRList();
    }

    /**
     * Test the add method
     */
    @Test
    public void testAdd() {
        /*
         * Add to empty
         * Add to non-empty
         */
        QRList list = MockQRList();
        int size = list.getCount();

        // Add to empty list
        list.add(MockCode());
        assertEquals(size + 1, list.getCount());

        // Add new code
        list.add(MockCode());
        assertEquals(size + 2, list.getCount());
    }

    /**
     * Test the remove method
     */
    @Test
    public void testRemove() {
        /*
         * Delete nonexistent
         * Delete existing
         */
        QRList list = MockQRList();
        QRCode code = MockCode();
        list.add(code);
        int size = list.getCount();

        // Remove nonexistent code
        list.remove(MockCode());
        assertEquals(size, list.getCount());

        // Remove existing code
        list.remove(code);
        assertEquals(size - 1, list.getCount());
    }

    /**
     * Test the modify method
     */
    @Test
    public void testModify() {
        /*
         * Empty query
         * Empty list
         * Replacement
         */

        // Empty query
        QRList list = MockQRList();
        list.add(MockCode());
        ArrayList<QRCode> codes = new ArrayList<>();
        list.modify(codes);
        assertEquals(0, list.getCount());

        // Empty list
        codes.add(MockCode());
        codes.add(MockCode());
        list.modify(codes);
        assertEquals(codes.size(), list.getCount());

        // Replacement
        list = MockQRList();
        QRCode code1 = MockCode();
        QRCode code2 = MockCode();
        QRCode code3 = MockCode();
        codes = new ArrayList<>();
        list.add(code1);
        list.add(code2);
        codes.add(code2);
        codes.add(code3);

        list.modify(codes);
        assertFalse(list.getList().contains(code1));
        assertTrue(list.getList().contains(code2));
        assertTrue(list.getList().contains(code3));
    }

    /**
     * Test that the summary statistics display correctly
     */
    @Test
    public void testSummary() {
        /*
         * Empty
         * Only 0
         * 0 and nonzero
         * Only nonzero
         * Duplicate
         * Integer overflow: Not tested as behaviour is not specified
         */


        // Empty
        QRList list = new QRList();
        assertEquals(list.getCount(), 0);
        assertEquals(list.getMax(), -1);
        assertEquals(list.getMin(), -1);
        assertEquals(list.getTotal(), 0);

        // Only zero
        QRCode code = new QRCode();
        code.setScore(0);
        list.add(code);
        assertEquals(list.getCount(), 1);
        assertEquals(list.getMax(), 0);
        assertEquals(list.getMin(), 0);
        assertEquals(list.getTotal(), 0);

        // Zero and nonzero
        QRCode code2 = new QRCode();
        code2.setScore(10);
        list.add(code2);
        assertEquals(list.getCount(), 2);
        assertEquals(list.getMax(), 10);
        assertEquals(list.getMin(), 0);
        assertEquals(list.getTotal(), 10);

        // Single nonzero
        list.remove(code);
        assertEquals(list.getCount(), 1);
        assertEquals(list.getMax(), 10);
        assertEquals(list.getMin(), 10);
        assertEquals(list.getTotal(), 10);

        // Duplicate
        list.add(code2);
        assertEquals(list.getCount(), 2);
        assertEquals(list.getMax(), 10);
        assertEquals(list.getMin(), 10);
        assertEquals(list.getTotal(), 20);
    }
}

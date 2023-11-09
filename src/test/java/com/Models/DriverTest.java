package com.Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DriverTest {

    private Driver driver;

    @BeforeEach
    void setUp() {
        driver = new Driver("Lewis Hamilton", 36, "Manly", "W12", 573);
    }

    @Test
    void getName() {
        assertEquals("Lewis Hamilton", driver.getName());
    }

    @Test
    void getAge() {
        assertEquals(36, driver.getAge());
    }

    @Test
    void getTeam() {
        assertEquals("Manly", driver.getTeam());
    }

    @Test
    void getCar() {
        assertEquals("W12", driver.getCar());
    }

    @Test
    void getPoints() {
        assertEquals(573, driver.getPoints());
    }

    @Test
    void setName() {
        driver.setName("Max Verstappen");
        assertEquals("Max Verstappen", driver.getName());
    }

    @Test
    void setAge() {
        driver.setAge(24);
        assertEquals(24, driver.getAge());
    }

    @Test
    void setTeam() {
        driver.setTeam("Red Bull Racing");
        assertEquals("Red Bull Racing", driver.getTeam());
    }

    @Test
    void setCar() {
        driver.setCar("RB16B");
        assertEquals("RB16B", driver.getCar());
    }

    @Test
    void setPoints() {
        driver.setPoints(368);
        assertEquals(368, driver.getPoints());
    }

    @Test
    void isSelected() {
        assertFalse(driver.isSelected());
    }

    @Test
    void setSelected() {
        driver.setSelected(true);
        assertTrue(driver.isSelected());
    }

    @Test
    void testToString() {
        String expected = "Driver{name='Lewis Hamilton', age=36, team='Manly', car='W12', points=573}";
        assertEquals(expected, driver.toString());
    }

    @Test
    void toCsvString() {
        String expected = "Lewis Hamilton,36,Manly,W12,573";
        assertEquals(expected, driver.toCsvString());
    }

    @Test
    void fromCsvString() {
        String csvString = "Max Verstappen,24,Red Bull Racing,RB16B,368";
        Driver expectedDriver = new Driver("Max Verstappen", 24, "Red Bull Racing", "RB16B", 368);
        assertEquals(expectedDriver.getName(), Driver.fromCsvString(csvString).getName());
        assertEquals(expectedDriver.getAge(), Driver.fromCsvString(csvString).getAge());
        assertEquals(expectedDriver.getTeam(), Driver.fromCsvString(csvString).getTeam());
        assertEquals(expectedDriver.getCar(), Driver.fromCsvString(csvString).getCar());
        assertEquals(expectedDriver.getPoints(), Driver.fromCsvString(csvString).getPoints());
    }

    @Test
    void selectedProperty() {
        assertFalse(driver.selectedProperty().getValue());
    }
}

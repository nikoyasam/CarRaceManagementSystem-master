package com.Utilities;

import com.Models.Car;
import com.Models.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CarManager {
    public static LinkedList<Car> shuffle(LinkedList<Car> cars) {
        List<Car> carList = new ArrayList<Car>();
        LinkedList<Car> randomCarList = new LinkedList<Car>();
        for (Car car : cars) carList.add(car);
        Collections.shuffle(carList);
        for (int i = 0; i < carList.size(); i++) randomCarList.add(carList.get(i));
        return randomCarList;
    }
}

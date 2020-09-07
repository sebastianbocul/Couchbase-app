package com.sebix.couchbase_app.models;

import android.os.Build;
import androidx.annotation.RequiresApi;
import java.util.Objects;

public class Numbers {
    int number1;
    int number2;

    public Numbers(int number1, int number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    public Numbers() {
    }

    public int getNumber1() {
        return number1;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }

    public int getNumber2() {
        return number2;
    }

    public void setNumber2(int number2) {
        this.number2 = number2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Numbers numbers = (Numbers) o;
        return number1 == numbers.number1 &&
                number2 == numbers.number2;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(number1, number2);
    }

    @Override
    public String toString() {
        return "Numbers{" +
                "number1=" + number1 +
                ", number2=" + number2 +
                '}';
    }
}

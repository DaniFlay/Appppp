package com.example.aplicacioncolegio.extras;

import com.example.aplicacioncolegio.clases.Clase;

import java.util.Comparator;

public class ComparatorClases implements Comparator<Clase> {
    @Override
    public int compare(Clase o1, Clase o2) {
        return Integer.compare(o1.getId(), o2.getId());
    }
}

package com.dam.sendmeal.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class OrderWithPlates {
    @Embedded
    public Order order;
    @Relation(
            parentColumn = "idOrder",
            entityColumn = "idPlate",
            associateBy = @Junction(PlateOrderRelation.class)
    )
    public List<Plate> plates;
}

package com.dam.sendmeal.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;
/*
public class PlatesInOrder {
    @Embedded
    Order order;
    @Relation(
            parentColumn = "idOrder",
            //entity = Plate.class,
            entityColumn = "idPlate"/*,
            associateBy = @Junction(
                    value = PlateOrderJoin.class,
                    parentColumn = "idOrder",
                    entityColumn = "idPlate")
            )
    List<Plate> plates;

}
*/

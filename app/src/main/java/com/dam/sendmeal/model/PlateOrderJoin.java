package com.dam.sendmeal.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

/*@Entity(tableName = "plateOrderJoin",
        primaryKeys = {"idOrder", "idPlate"},
        foreignKeys = { @ForeignKey(entity = Plate.class, parentColumns = "idPlate", childColumns = "idPlate"),
                        @ForeignKey(entity = Order.class, parentColumns = "idOrder", childColumns = "idOrder")})

public class PlateOrderJoin {
    public long idOrder;
    public long idPlate;

    public PlateOrderJoin(long idOrder) {
        this.idOrder = idOrder;
    }
}
*/


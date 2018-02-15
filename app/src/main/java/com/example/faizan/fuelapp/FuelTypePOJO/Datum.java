package com.example.faizan.fuelapp.FuelTypePOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by faizan on 2/15/2018.
 */

public class Datum {


    @SerializedName("typeId")
    @Expose
    private String typeId;
    @SerializedName("typeName")
    @Expose
    private String typeName;
    @SerializedName("icon")
    @Expose
    private String icon;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

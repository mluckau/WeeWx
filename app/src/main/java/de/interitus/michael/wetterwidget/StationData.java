package de.interitus.michael.wetterwidget;

/**
 * Created by michael on 29.10.15.
 */
public class StationData {

    public String name;
    public String zeit;
    public String currentTemp;

    public StationData(String name, String zeit, String currentTemp){

        this.name = name;
        this.zeit = zeit;
        this.currentTemp = currentTemp;

    }

}

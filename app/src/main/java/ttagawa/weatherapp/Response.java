
package ttagawa.weatherapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("conditions")
    @Expose
    public Conditions conditions;
    @SerializedName("result")
    @Expose
    public String result;

}

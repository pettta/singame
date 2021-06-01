package sin.save;

import org.json.JSONObject;
/**
 * Name: ISaveable.java
 * Purpose: Interface for an object to be saved.
 * Last Updated: 6/1/2021
 * Author: Zacharia Bridgers
 * Dependencies: None
 */
public interface ISaveable {

    public JSONObject write(JSONObject obj);

    public ISaveable read(JSONObject obj);

}

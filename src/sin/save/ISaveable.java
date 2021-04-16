package sin.save;

import org.json.JSONObject;

public interface ISaveable {

    public JSONObject write(JSONObject obj);

    public ISaveable read(JSONObject obj);

}

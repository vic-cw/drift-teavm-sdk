package com.github.viccw.driftteavmsdk;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;

class JavaScriptOperabilityHelper {

    @JSBody(params = {"variableName"},
            script = "return window.hasOwnProperty(variableName)")
    public static native boolean globalVariableIsDefined(
            String variableName);

    public static JSObject stringMapToJavaScriptObject(
            Map<String, String> map) {
        if (map == null) {
            return null;
        }
        final int size = map.size();
        List<String> keys = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            keys.add(entry.getKey());
            values.add(entry.getValue());
        }
        return JavaScriptOperabilityHelper
                .javascriptObjectFromKeysAndValues(
                keys.toArray(new String[size]),
                values.toArray(new String[size]));
    }

    @JSBody(params = {"keys", "values"},
            script = "var result = {};"
                    + "for (var i = 0; i < keys.length; i++) {"
                    + "    result[keys[i]] = values[i];"
                    + "}"
                    + "return result;")
    private static native JSObject javascriptObjectFromKeysAndValues(
            String[] keys, String[] values);
}

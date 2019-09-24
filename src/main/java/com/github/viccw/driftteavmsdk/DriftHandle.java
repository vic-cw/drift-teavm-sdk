package com.github.viccw.driftteavmsdk;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;
import org.teavm.jso.JSProperty;

abstract class DriftHandle implements JSObject {

    @JSBody(script = "return drift;")
    static native DriftHandle getInstance();

    @JSFunctor
    interface EventHandler extends JSObject {
        void handleEvent();
    }

    static class EventNames {
        static final String READY = "ready";
    }

    abstract void on(String eventName, EventHandler eventHandler);

    abstract void identify(String userId, JSObject userDetails);

    @JSProperty
    abstract DriftApiHandle getApi();
}

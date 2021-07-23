package com.appflip.plugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.chariotsolutions.nfc.plugin.Util;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class AppFlip extends CordovaPlugin {

    private static final String TAG = "TAG";
    private static final String EXTRA_APP_FLIP_AUTHORIZATION_CODE = "AUTHORIZATION_CODE";
    public static String client_id;
    public static CallbackContext globalCallback;

    public void getInitialPushPayload(CallbackContext callback) {
        if(client_id == null) {
            Log.d(TAG, "getInitialPushPayload: null");
            callback.success((String) null);
            return;
        }
        Log.d(TAG, client_id+"getInitialPushPayload");
        try {
            String clientID = client_id;
            client_id = null;
            //callback.success(jo);
            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, clientID);
            pluginResult.setKeepCallback(true);
            callback.sendPluginResult(pluginResult);
            globalCallback = callback;
        } catch(Exception error) {
            try {
                callback.error(exceptionToJson(error));
            }
            catch (JSONException jsonErr) {
                Log.e(TAG, "Error when parsing json", jsonErr);
            }
        }
    }

    private JSONObject exceptionToJson(final Exception exception) throws JSONException {
        return new JSONObject() {
            {
                put("message", exception.getMessage());
                put("cause", exception.getClass().getName());
                put("stacktrace", exception.getStackTrace().toString());
            }
        };
    }

    public static void setInitialPushPayload(String clientId) {
        client_id = clientId;
        if(globalCallback!=null){
            Log.d(TAG, "setInitialPushPayload");
            //callback.success(jo);
            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, client_id);
            pluginResult.setKeepCallback(true);
            globalCallback.sendPluginResult(pluginResult);
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        } else if(action.equals("getInitialPushPayload")) {
            getInitialPushPayload(callbackContext);
        } else if(action.equals("sendAuthCode")) {
            String code = args.getString(0);
            String status = args.getString(1);
            String message = args.getString(2);
            sendAuthCode(code,status,message,callbackContext);
        }
        return false;
    }

    private void sendAuthCode(String code, String status, String message, CallbackContext callbackContext) {
        Intent returnIntent = new Intent();
        String errorCodeString = status;
        int errorCode = 0;
        if(errorCodeString!=null){
            try{
                errorCode = Integer.valueOf(errorCodeString);
            } catch (NumberFormatException e){
                return;
            }
        }
        if(code!=null){
            if(code.length()>0) {
                String authCode = code;
                returnIntent.putExtra(EXTRA_APP_FLIP_AUTHORIZATION_CODE, authCode);
                //setResult(Activity.RESULT_OK, returnIntent);
            }
        }
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}

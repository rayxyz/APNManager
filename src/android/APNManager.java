package com.eruipan.cordova.plugin.apnmanager;

import java.util.List;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

public class APNManager extends CordovaPlugin {
	
	private static final String APN_TAG = "APN";
	
	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		Log.i(APN_TAG, "The APNManager instance is initializing...");
		super.initialize(cordova, webView);
	}

	@Override
    public boolean execute(String action, JSONArray args, 
    		CallbackContext callbackContext) throws JSONException {
		Log.i(APN_TAG, "action: " + action);
        if(action.equals("echo")) {
            String message = args.getString(0);
            this.echo(message, callbackContext);
            return true;
        }
        
        if(action.equals("getCurrAPNId")) {
        	this.getCurrAPNId(cordova.getActivity().getBaseContext(), callbackContext);
        	return true;
        }
        
		if(action.equals("getCurrAPNInfo")) {
			this.getCurrAPNInfo(this.webView.getContext(), callbackContext);
			return true;
		}
		
		if(action.equals("getAPNList")) {
			this.getAPNList(this.webView.getContext(), callbackContext);
			return true;
		}
		
		if(action.equals("getAvailableAPNList")) {
			this.getAvailableAPNList(this.webView.getContext(), callbackContext);
			return true;
		}
		
		if(action.equals("setPreferAPN")) {
			Log.i(APN_TAG, "new APN ID: " + args.getString(0));
			this.setPreferAPN(this.webView.getContext(), args.getString(0), args.getString(1), callbackContext);
			return true;
		}
		
		if(action.equals("addAPN")) {
			Log.e("APNManager", "in addAPN condition branch...");
			this.addAPN(this.webView.getContext(), callbackContext);
		}
		
		if(action.equals("startAPNSettings")) {
			Log.i(APN_TAG, "starting apn settings...");
			this.startAPNSettings(callbackContext);
			return true;
		}
		
        return false;
    }

    private void echo(String message, CallbackContext callbackContext) {
        if(message != null && message.length() > 0) {
            callbackContext.success("Echoed from native code: " + message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
    
    private void getCurrAPNId(final Context context, final CallbackContext callbackContext) {
    	cordova.getThreadPool().execute(new Runnable() {
            public void run() {
            	try {
            		callbackContext.success(new JSONObject().put("apn_id", APNSwitcher.getCurrAPNId(context)));
            	} catch (Exception e) {
            		callbackContext.error(e.getMessage());
            	}
            }
        });
    }
    
    private void getCurrAPNInfo(final Context context, final CallbackContext callbackContext) {
    	cordova.getThreadPool().execute(new Runnable() {
            public void run() {
            	try {
            		APN apn = APNSwitcher.getCurrAPNInfo(context);
            		JSONObject jsonObj = new JSONObject();
            		jsonObj.put("curr_apn_id", apn.getId());
            		jsonObj.put("curr_apn_name", apn.getApn());
            		jsonObj.put("curr_apn_type", apn.getType());
            		callbackContext.success(jsonObj);
            	} catch (Exception e) {
            		callbackContext.error(e.getMessage());
            	}
            }
        });
    }
    
    private void getAPNList(final Context context, final CallbackContext callbackContext) {
    	cordova.getThreadPool().execute(new Runnable() {
            public void run() {
            	try {
            		List<APN> apnList = APNSwitcher.getAPNList(context);
            		callbackContext.success(new JSONArray().put(apnList));
            	} catch (Exception e) {
            		callbackContext.error(e.getMessage());
            	}
            }
        });
    }
    
    private void getAvailableAPNList(final Context context, final CallbackContext callbackContext) {
    	cordova.getThreadPool().execute(new Runnable() {
            public void run() {
            	try {
            		List<APN> apnList = APNSwitcher.getAvailableAPNList(context);
            		callbackContext.success(new JSONArray().put(apnList));
            	} catch (Exception e) {
            		callbackContext.error(e.getMessage());
            	}
            }
        });
    }
    
	private void setPreferAPN(final Context context, final String apnId,
			final String apnName, final CallbackContext callbackContext) {
		try {
			APNSwitcher.setPreferAPN(context, apnId, apnName);
			callbackContext.success("Set default apn complete!");
		} catch (Exception e) {
			callbackContext.error(e.getMessage());
		}
	}
	
	private void addAPN(final Context context, final CallbackContext callbackContext) {
		try {
			int apnId = APNSwitcher.addAPN(context);
			callbackContext.success(apnId);
		} catch (Exception e) {
			Log.e("APNManager exception of addAPN", "msg: " + e.getMessage());
			callbackContext.error(e.getMessage());
		}
	}
    
    private void startAPNSettings(final CallbackContext callbackContext) {
    	try {
    		cordova.startActivityForResult(this, APNSwitcher.getAPNIntent(), 100);
//    		cordova.setActivityResultCallback(this);
    		callbackContext.success("jump to apn settings successfully.");
    		closeWIFINetwork();
    	} catch (Exception e) {
    		callbackContext.error(e.getMessage());
    	}
    }
    
    private void closeWIFINetwork() {
    	WifiManager wifiManager = (WifiManager) cordova.getActivity().getSystemService(Context.WIFI_SERVICE); 
    	if (wifiManager.isWifiEnabled()) { 
    		wifiManager.setWifiEnabled(false); 
    		Log.i(APN_TAG, "close wifi network complete");
		}
    }
    
}

package com.eruipan.cordova.plugin.apnmanager;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class APNSwitcher {

	private static String TAG = "APNSwitcher";
	private static final Uri APN_CARRIERS_URI = Uri
			.parse("content://telephony/carriers");//APNs
	private static final Uri PREFERRED_APN_URI = Uri
			.parse("content://telephony/carriers/preferapn");//current APN
	private static String[] projection = { "_id", "apn", "type", "current",
			"proxy", "port" };

	public static String getCurrAPNId(Context context) {
		ContentResolver resoler = context.getContentResolver();
		// String[] projection = new String[] { "_id" };
		Cursor cur = resoler.query(PREFERRED_APN_URI, projection, null, null,
				null);
		String apnId = null;
		if (cur != null && cur.moveToFirst()) {
			apnId = cur.getString(cur.getColumnIndex("_id"));
		}
		Log.i(TAG, "getCurApnId:" + apnId);
		return apnId;
	}

	public static APN getCurrAPNInfo(final Context context) {
		ContentResolver resoler = context.getContentResolver();
		// String[] projection = new String[] { "_id" };
		Cursor cur = resoler.query(PREFERRED_APN_URI, projection, null, null,
				null);
		APN apn = new APN();
		if (cur != null && cur.moveToFirst()) {
			apn.setId(cur.getString(cur.getColumnIndex("_id")));
			apn.setApn(cur.getString(cur.getColumnIndex("apn")));
			apn.setType(cur.getString(cur.getColumnIndex("type")));
		}
		return apn;
	}

	public static ArrayList<APN> getAPNList(final Context context) {

		ContentResolver contentResolver = context.getContentResolver();

		Cursor cr = contentResolver.query(APN_CARRIERS_URI, projection, null,
				null, null);

		ArrayList<APN> apnList = new ArrayList<APN>();

		if (cr != null && cr.moveToFirst()) {
			do {
				Log.d(TAG,
						cr.getString(cr.getColumnIndex("_id")) + ";"
								+ cr.getString(cr.getColumnIndex("apn")) + ";"
								+ cr.getString(cr.getColumnIndex("type")) + ";"
								+ cr.getString(cr.getColumnIndex("current"))
								+ ";"
								+ cr.getString(cr.getColumnIndex("proxy")));
				APN apn = new APN();
				apn.setId(cr.getString(cr.getColumnIndex("_id")));
				apn.setApn(cr.getString(cr.getColumnIndex("apn")));
				apn.setType(cr.getString(cr.getColumnIndex("type")));
				apnList.add(apn);
			} while (cr.moveToNext());

			cr.close();
		}
		return apnList;
	}

	public static ArrayList<APN> getAvailableAPNList(final Context context) {
		ContentResolver contentResolver = context.getContentResolver();
		Cursor cr = contentResolver.query(APN_CARRIERS_URI, projection,
				"current is not null", null, null);
		ArrayList<APN> apnList = new ArrayList<APN>();
		if (cr != null && cr.moveToFirst()) {
			do {
				Log.d(TAG,
						cr.getString(cr.getColumnIndex("_id")) + ";"
								+ cr.getString(cr.getColumnIndex("apn")) + ";"
								+ cr.getString(cr.getColumnIndex("type")) + ";"
								+ cr.getString(cr.getColumnIndex("current"))
								+ ";"
								+ cr.getString(cr.getColumnIndex("proxy")));
				APN apn = new APN();
				apn.setId(cr.getString(cr.getColumnIndex("_id")));
				apn.setApn(cr.getString(cr.getColumnIndex("apn")));
				apn.setType(cr.getString(cr.getColumnIndex("type")));
				apnList.add(apn);
			} while (cr.moveToNext());

			cr.close();
		}
		return apnList;
	}

	public static void setPreferAPN(final Context context, String apnId, String apnName) {
		 ContentResolver resolver = context.getContentResolver();
		 ContentValues cv = new ContentValues();
		 cv.put("apn", apnName);
		 cv.put("apn_id", apnId);
//		 cv.put("type", "");
		 resolver.update(PREFERRED_APN_URI, cv, null, null);
		 Log.e(TAG, "setApn");
		 Log.e(TAG, "id: " + apnId);
	}
	
	private static boolean checkAPNExists(Context context, String apn) {
		 Log.e(TAG, "in checkAPNExists");
		 Cursor cr = context.getContentResolver().query(APN_CARRIERS_URI, projection,
				"apn = '" + apn + "'", null, null);
		 Log.e(TAG, "after query ....");
		 if(cr.getCount() > 0) {
			Log.e(TAG,
					"bnoa private mobile network is not null, it is : "
							+ cr.getColumnIndex("apn") + ", and apnId: "
							+ cr.getColumnIndex("_id"));
			 return true;
		 }
		 return false;
	}
	
	/**
	 * 
	 * @param context
	 * @return -1 -> failed; 0 -> apn exists; valid apnId -> success.
	 */
	public static int addAPN(final Context context) {
		Log.e(TAG, "in addAPN navtive code");
		if(checkAPNExists(context, "bngdt.yn")) {
			deleteAPN(context, "bngdt.yn");
		}
		Log.e(TAG, "after checkAPNExists...");
		int apnId = -1;
        ContentResolver resolver = context.getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put("name", "New APN");
        cv.put("apn", "bngdt.yn");
        cv.put("type", "default,supl,net");
        cv.put("mcc", "460");
        cv.put("mnc", "00");
        cv.put("numeric", "46000");
        cv.put("proxy", "10.0.0.172");
        cv.put("port", "80");
        cv.put("current", "1");
        cv.put("mmsproxy", "");
        cv.put("mmsport", "");
        cv.put("user", "");
        cv.put("server", "");
        cv.put("password", "");
        cv.put("mmsc", "");
        
        Cursor c = null;
        Log.e(TAG, "before try_catch block...");
        try {
            Uri newRow = resolver.insert(APN_CARRIERS_URI, cv);
            Log.e(TAG, "after inser sentence");
            if (newRow != null) {
                c = resolver.query(newRow, null, null, null, null);
                int idindex = c.getColumnIndex("_id");
                c.moveToFirst();
                apnId = c.getShort(idindex);
                Log.e(TAG, "New ID: " + apnId
                        + ": Inserting new APN succeeded!");
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        
        Log.e(TAG, "after try_catch code block");

        if (c != null) {
        	if(!c.isClosed()) {
        		c.close();
        	}
        }
            
        Log.e(TAG, "before return apnId, apnId: " + apnId);
        
        return apnId;
	}
	
	private static void deleteAPN(final Context context, String apn) {
		try {
			ArrayList<APN> apnList = getAvailableAPNList(context);
			Log.e(TAG, "length of apnList: " + apnList.size());
			ContentResolver contentResolver = context.getContentResolver();
			for(APN apnObj : apnList) {
				if(apnObj.getApn().equals(apn)) {
					Log.e(TAG, "before deleting apn with id: " + apnObj.getId());
					contentResolver.delete(APN_CARRIERS_URI, "_id = ?", new String[] {apnObj.getId()});
				}
			}
			Log.e(TAG, "delete apn -> " + apn + " complete");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Intent getAPNIntent() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		// intent.setClassName("com.android.phone",
		// "com.android.phone.Settings");
		intent.setAction("android.settings.APN_SETTINGS");
		return intent;
	}


}

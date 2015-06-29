/**
 * @author Wnag Rui
 * @email wangenglishkl@126.com
 */
cordova.define("com.eruipan.cordova.plugin.apn.APNManager", function(require, exports, module) { 
var argscheck = require('cordova/argscheck'),
    utils = require('cordova/utils'),
    exec = require('cordova/exec');

var APNManager = function() {};

APNManager.echo = function(successCallback, erroCallback) {
    exec(successCallback, erroCallback, "APNManager", "echo", ["Hello, navtive!"]);
};

APNManager.getCurrAPNId = function(successCallback, erroCallback) {
    exec(successCallback, erroCallback, "APNManager", "getCurrAPNId", []);
};

APNManager.getCurrAPNInfo = function(successCallback, erroCallback) {
    exec(successCallback, erroCallback, "APNManager", "getCurrAPNInfo", []);
};

APNManager.getAPNList = function(successCallback, erroCallback) {
    exec(successCallback, erroCallback, "APNManager", "getAPNList", []);
};

APNManager.getAvailableAPNList = function(successCallback, erroCallback) {
    exec(successCallback, erroCallback, "APNManager", "getAvailableAPNList", []);
};

APNManager.setPreferAPN = function(apnId, apnName, successCallback, erroCallback) {
    exec(successCallback, erroCallback, "APNManager", "setPreferAPN", [apnId, apnName]);
};

APNManager.addAPN = function(successCallback, erroCallback) {
    exec(successCallback, erroCallback, "APNManager", "addAPN", []);
};

APNManager.startAPNSettings = function(successCallback, errorCallback) {
	exec(successCallback, errorCallback, "APNManager", "startAPNSettings", []);
}

module.exports = APNManager;

});

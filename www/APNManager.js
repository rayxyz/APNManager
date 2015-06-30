/**
 * @author Wnag Rui
 * @email wangenglishkl@126.com
 */

var APNManager = function() {};

APNManager.prototype.echo = function(successCallback, erroCallback) {
    cordova.exec(successCallback, erroCallback, "APNManager", "echo", ["Hello, navtive!"]);
};

APNManager.prototype.getCurrAPNId = function(successCallback, erroCallback) {
    cordova.exec(successCallback, erroCallback, "APNManager", "getCurrAPNId", []);
};

APNManager.prototype.getCurrAPNInfo = function(successCallback, erroCallback) {
    cordova.exec(successCallback, erroCallback, "APNManager", "getCurrAPNInfo", []);
};

APNManager.prototype.getAPNList = function(successCallback, erroCallback) {
    cordova.exec(successCallback, erroCallback, "APNManager", "getAPNList", []);
};

APNManager.prototype.getAvailableAPNList = function(successCallback, erroCallback) {
    cordova.exec(successCallback, erroCallback, "APNManager", "getAvailableAPNList", []);
};

APNManager.prototype.setPreferAPN = function(apnId, apnName, successCallback, erroCallback) {
    cordova.exec(successCallback, erroCallback, "APNManager", "setPreferAPN", [apnId, apnName]);
};

APNManager.prototype.addAPN = function(successCallback, erroCallback) {
    cordova.exec(successCallback, erroCallback, "APNManager", "addAPN", []);
};

APNManager.prototype.startAPNSettings = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "APNManager", "startAPNSettings", []);
}

module.exports = APNManager;
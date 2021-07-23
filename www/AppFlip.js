    var exec = require('cordova/exec');
    
    coolMethod = function (arg0, success, error) {
        exec(success, error, 'AppFlip', 'coolMethod', [arg0]);
    };
    
    var appflip = {
        getInitialPushPayload: function (win, fail) {
                   return cordova.exec(win, fail, "AppFlip", "getInitialPushPayload", []);
                },
        sendAuthCode: function(auth_data, win, fail) {
        let code = auth_data.code ? auth_data.code : null;
        let error_status = auth_data.status ? auth_data.status : null;
        let error_message = auth_data.message ? auth_data.message : null;
                return cordova.exec(win, fail, "AppFlip", "sendAuthCode", [code,error_status,error_message]);
            }
    }
    
    window.appflip = appflip;
    
    var AppFlip = {
            appflip: appflip
        };
    
      module.exports = AppFlip;    
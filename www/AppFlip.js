var exec = require('cordova/exec');

coolMethod = function (arg0, success, error) {
    exec(success, error, 'AppFlip', 'coolMethod', [arg0]);
};

var appflip = {
    getInitialPushPayload: function (win, fail) {
                cordova.exec(win, fail, "AppFlip", "getInitialPushPayload", []);
            },
}

var AppFlip = {
        appflip: appflip
    };

  module.exports = AppFlip;

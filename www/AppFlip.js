const pluginName = "AppFlip";

var exec = function exec(method, params) {
  return new Promise(function (resolve, reject) {
    return cordova.exec(resolve, reject, pluginName, method, params);
  });
};

var appflip = {
  getInitialPushPayload: function () {
    return exec("getInitialPushPayload", []);
  },
  sendAuthCode: function (auth_data) {
    let code = auth_data.code ? auth_data.code : null;
    let error_status = auth_data.status ? auth_data.status : null;
    let error_message = auth_data.message ? auth_data.message : null;
    return exec("sendAuthCode", [code, error_status, error_message]);
  },
};

window.appflip = appflip;

var AppFlip = {
  appflip: appflip,
};

module.exports = AppFlip;

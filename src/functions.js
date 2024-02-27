
function chooseRandCountryKey(keys) {
    var i = 0
    keys.forEach(function(elem) {
        i++
    })
    return  $jsapi.random(i);
}
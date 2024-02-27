function chooseRandomCountry() {
    return $Countries[chooseRandCountryKey(Object.keys($Countries))];
}

function chooseRandCountryKey(keys) {
    var i = 0
    keys.forEach(function(elem) {
        i++
    })
    return  $jsapi.random(i);
}
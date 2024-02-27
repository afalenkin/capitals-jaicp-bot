require: slotfilling/slotFilling.sc
  module = sys.zb-common
  
require: text/text.sc
    module = zenbot-common
    
require: where/where.sc
    module = zenbot-common

require: common.js
    module = zenbot-common

require: hangmanGameData.csv
    name = HangmanGameData
    var = $HangmanGameData
    
require: functions.js

patterns:
    $Word = $entity<HangmanGameData> || converter = function ($parseTree) {
        var id = $parseTree.HangmanGameData[0].value;
        return $HangmanGameData[id].value;
        };

theme: /

    state: Start
        q!: $regex</start>
        script:  $session.score = 0
        a: Я хочу сыграть с тобой в игру: "Какой город является столицей страны"
        
        state: Go
            q!: $regex</go>
            go!: /PlayTheGame

        state: Default
            event: noMatch
            a: Это не похоже на ответ. Попробуйте еще раз.

    state: PlayTheGame
        script:
            $session.country = getRandomCountry()
            $reactions.answer("Какой город является столицей {{$parseTree.session.country.country}}");
            $reactions.transition("/Check");


    state: Check
        q: * $City *
        script: 
            if checkCity
        a: Город: {{$parseTree._City.name}}
        go!: /PlayTheGame
        

    state: NoMatch
        event!: noMatch
        a: Это не город: {{$request.query}}

    state: stop
        intent: /stop
        a: Вы сдались? Набрано всего 0 очков, для победы нужно набрать 1.
        script:
            $session = {};
            $client = {};
        go!: /


require: slotfilling/slotFilling.sc
  module = sys.zb-common
  
require: text/text.sc
    module = zenbot-common
    
require: where/where.sc
    module = zenbot-common

require: common.js
    module = zenbot-common
    
require: functions.js

theme: /

    state: Start
        q!: $regex</start>
        script:  
            $session = {}
            $session.score = 0
        a: Я хочу сыграть с тобой в игру: "Какой город является столицей страны"
        
        state: Go
            q!: $regex</go>
            go!: /PlayTheGame

        state: Default
            event: noMatch
            a: Это не похоже на ответ. Попробуйте еще раз.

    state: PlayTheGame
        a: play
        script:
            $session.country = $Geography[chooseRandCountryKey(Object.keys($Geography))];
            # var country = $session.country.value.country
            $reactions.answer("Какой город является столицей {{$session.country.value.country}}");
            # $reactions.transition("/Check");


    # state: Check
    #     q: * $City *
    #     script: 
    #     a: Город: {{$parseTree._City.name}}
    #     go!: /PlayTheGame
        

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


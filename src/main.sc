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
            $reactions.answer("Ваш счет {{$session.score}}");
            $reactions.answer("Какой город является столицей {{$session.country.value.country}}?");
        go!: /Check


    state: Check
        state: CheckCity
            q: * $City *
            script:
                $reactions.answer("Должно быть {{$session.country.value.name}}?");
                # if ($parseTree._City.name == $session.country.value.name) {
                #     $session.score = $session.score + 1
                #     $reactions.answer("Правильный ответ! Ваш счет {{$session.score}}");
                # } else {
                #     $session.score = $session.score - 1
                #     $reactions.answer("Неправильный ответ! Минус балл! Ваш счет {{$session.score}}");
                #     $reactions.answer("Должно быть {{$session.country.value.name}}?");
                # }
            go!: /PlayTheGame
            
        state: NoMatch
            event!: noMatch
            a: Это не город!
        

    state: stop
        intent!: /stop
        a: Вы сдались? Набрано всего {{$session.score}} очков, для победы нужно набрать {{$session.score + 1}}.
        script:
            $session = {};
            $client = {};
        go!: /


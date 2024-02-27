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
        a: Я хочу сыграть с тобой в игру: "Какой город является столицей страны". Если готов - ответь /go, когда надоест - ответь /stop
        
    state: Go
        q!: $regex</go>
        go!: /PlayTheGame
        
    state: PlayTheGame || modal = true
        script:
            $session = {}
            $session.score = 0
        go!: /PlayTheGame/Ask

        state: Ask
            script:
                $session.country = $Geography[chooseRandCountryKey(Object.keys($Geography))];
                $reactions.answer("Ваш счет {{$session.score}}");
                $reactions.answer("Какой город является столицей {{$session.country.value.country}}?");
            go!: /PlayTheGame/Check


        state: Check
            state: CheckCity
                q: * $City *
                a: $reactions.answer("Ошибка! Столица {{$session.country.value.country}} - {{$session.country.value.name}}! Минус балл!");
                # script:
                #     if ({$parseTree._City.name == $session.country.value.name) {
                #         $reactions.answer("Верно! Плюс балл!");
                #         $session.score = $session.score + 1
                #     } else {
                #         $reactions.answer("Ошибка! Столица {{$session.country.value.country}} - {{$session.country.value.name}}! Минус балл!");
                #         $session.score = $session.score - 1
                #     }
                go!: /PlayTheGame/Ask
                
            state: LocalCatchAll
                event: noMatch
                a: Это не город.
                
    state: Default
        event!: noMatch
        a: Это не похоже на ответ. Попробуйте еще раз.
    
    state: stop
        q!: $regex</stop>
        a: Вы сдались? Набрано всего {{$session.score}} очков, для победы нужно набрать {{$session.score + 1}}.
        go!: /Start
    